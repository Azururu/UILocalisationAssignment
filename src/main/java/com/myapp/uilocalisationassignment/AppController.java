package com.myapp.uilocalisationassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class AppController {
    @FXML private Label tripDistanceText;
    @FXML private Label fuelConsumptionRateText;
    @FXML private Label fuelCostText;
    @FXML private TextField tripDistanceInput;
    @FXML private TextField fuelConsumptionRateInput;
    @FXML private TextField fuelCostInput;
    @FXML private ComboBox<String> comboBox;
    @FXML private Label resultText;
    @FXML private VBox rootVBox;
    @FXML private Button btnCalculate;

    private LocalizationService localizationService = new LocalizationService();
    private Map<String, String> bundle;
    private Locale currentLocale = new Locale("en", "US");
    private double totalFuel = 0;
    private double totalCost = 0;
    private CalculationService calculationService = new CalculationService();
    private static final Logger logger = Logger.getLogger(AppController.class.getName());

    @FXML
    public void initialize() {
        setLanguage(currentLocale);
    }

    @FXML
    public void handleCalculate(ActionEvent actionEvent) {
        try {
            double distance = Double.parseDouble(tripDistanceInput.getText());
            double consumption = Double.parseDouble(fuelConsumptionRateInput.getText());
            double price = Double.parseDouble(fuelCostInput.getText());

            totalFuel = calculationService.calculateTotalFuel(distance, consumption);
            totalCost = calculationService.calculateTotalCost(totalFuel, price);

            resultText.setText(MessageFormat.format(bundle.getOrDefault("resultText", "Fuel: {0}, Cost: {1}"), totalFuel, totalCost));

            calculationService.saveCalculation(
                    distance,
                    consumption,
                    price,
                    totalFuel,
                    totalCost,
                    currentLocale.getLanguage()
            );
        } catch (NumberFormatException e) {
            logger.warning("Invalid numeric input: " + e.getMessage());
        }
    }

    @FXML
    public void handleLanguageChange(ActionEvent actionEvent) {
        String lang = switch (comboBox.getValue()) {
            case "FR" -> "fr";
            case "JP" -> "ja";
            case "FA" -> "fa";
            default -> "en";
        };

        setLanguage(new Locale(lang));
    }

    private void setLanguage(Locale locale) {
        currentLocale = locale;

        String lang = locale.getLanguage();
        bundle = localizationService.getLocalization(lang);

        if (bundle == null || bundle.isEmpty()) {
            resultText.setText("⚠️ Failed to load language data");
            return;
        }

        tripDistanceText.setText(bundle.getOrDefault("tripDistanceText", "Trip Distance"));
        fuelConsumptionRateText.setText(bundle.getOrDefault("fuelConsumptionRateText", "Fuel Consumption"));
        fuelCostText.setText(bundle.getOrDefault("fuelCostText", "Fuel Cost"));
        btnCalculate.setText(bundle.getOrDefault("btnCalculate", "Calculate"));

        resultText.setText(MessageFormat.format(bundle.getOrDefault("resultText", "Fuel: {0}, Cost: {1}"), totalFuel, totalCost));
    }
}
