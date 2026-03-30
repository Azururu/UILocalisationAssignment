package com.myapp.uilocalisationassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Locale;
import java.util.ResourceBundle;

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

    private ResourceBundle bundle;
    private Locale currentLocale = new Locale("en", "US");

    @FXML
    public void initialize() {
        setLanguage(currentLocale);
    }

    @FXML
    public void handleCalculate(ActionEvent actionEvent) {
        double totalFuel = ((Double.parseDouble(fuelConsumptionRateInput.getText()) / 100) * Double.parseDouble(tripDistanceInput.getText()));

        double totalCost = totalFuel * Double.parseDouble(fuelCostInput.getText());

        resultText.setText(String.format("Total fuel needed: %.2f liters\nTotal cost: %.2f", totalFuel, totalCost));
    }

    @FXML
    public void handleLanguageChange(ActionEvent actionEvent) {
        String selected = comboBox.getValue();
        switch (selected) {
            case "EN" -> setLanguage(new Locale("en", "US"));
            case "FR" -> setLanguage(new Locale("fr", "FR"));
            case "JP" -> setLanguage(new Locale("ja", "JP"));
            case "FA" -> setLanguage(new Locale("fa", "IR"));
        }
    }

    private void setLanguage(Locale locale) {
        currentLocale = locale;

        bundle = ResourceBundle.getBundle("messages", currentLocale);

        tripDistanceText.setText(bundle.getString("tripDistanceText"));
        fuelConsumptionRateText.setText(bundle.getString("fuelConsumptionRateText"));
        fuelCostText.setText(bundle.getString("fuelCostText"));
        btnCalculate.setText(bundle.getString("btnCalculate"));
        // This method would contain logic to change the UI text based on the selected language.
        // For example, it could load a resource bundle and update the text of all labels and buttons.
    }
}
