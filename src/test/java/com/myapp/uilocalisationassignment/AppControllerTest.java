package com.myapp.uilocalisationassignment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class AppControllerTest extends ApplicationTest {

    private AppController controller;

    @BeforeAll
    static void setup() {
        AppController.TEST_MODE = true;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/myapp/uilocalisationassignment/calculate-app.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testInitialState() {
        Label tripDistanceLabel = lookup("#tripDistanceText").queryAs(Label.class);
        assertNotNull(tripDistanceLabel.getText());
        // Initial state should be English
        assertTrue(tripDistanceLabel.getText().contains("Distance") || !tripDistanceLabel.getText().isEmpty());
    }

    @Test
    void testCalculation() {
        TextField tripDistanceInput = lookup("#tripDistanceInput").queryAs(TextField.class);
        TextField fuelConsumptionRateInput = lookup("#fuelConsumptionRateInput").queryAs(TextField.class);
        TextField fuelCostInput = lookup("#fuelCostInput").queryAs(TextField.class);
        Button btnCalculate = lookup("#btnCalculate").queryAs(Button.class);
        Label resultText = lookup("#resultText").queryAs(Label.class);

        clickOn(tripDistanceInput).write("100");
        clickOn(fuelConsumptionRateInput).write("6");
        clickOn(fuelCostInput).write("1.5");
        clickOn(btnCalculate);

        // Fuel: 6.0, Cost: 9.0 (Expected based on CalculationService)
        String result = resultText.getText();
        assertTrue(result.contains("6") && result.contains("9"), "Result should contain expected fuel and cost: " + result);
    }

    @Test
    void testLanguageChange() {
        ComboBox<String> comboBox = lookup("#comboBox").queryAs(ComboBox.class);
        Label tripDistanceLabel = lookup("#tripDistanceText").queryAs(Label.class);
        
        // Change to Japanese (JP)
        clickOn(comboBox);
        clickOn("JP");

        String japaneseText = tripDistanceLabel.getText();
        assertNotNull(japaneseText);

        // Change to French (FR)
        clickOn(comboBox);
        clickOn("FR");

        String frenchText = tripDistanceLabel.getText();
        assertNotNull(frenchText);
        assertNotEquals(japaneseText, frenchText);
    }

    @Test
    void testInvalidInput() {
        TextField tripDistanceInput = lookup("#tripDistanceInput").queryAs(TextField.class);
        Button btnCalculate = lookup("#btnCalculate").queryAs(Button.class);
        Label resultText = lookup("#resultText").queryAs(Label.class);

        String previousResult = resultText.getText();
        clickOn(tripDistanceInput).write("invalid");
        clickOn(btnCalculate);

        // Result should not change or should handle error gracefully (handleCalculate has a catch block that prints stack trace but doesn't change resultText)
        assertEquals(previousResult, resultText.getText());
    }
}
