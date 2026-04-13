package com.myapp.uilocalisationassignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Enhanced unit tests for AppController class
 * Tests localization, calculations, edge cases, and UI interactions
 */
@DisplayName("AppController Enhanced Tests")
public class AppControllerEnhancedTest {

    private AppController appController;
    private TextField tripDistanceInput;
    private TextField fuelConsumptionRateInput;
    private TextField fuelCostInput;
    private Label resultText;
    private Label tripDistanceText;
    private Label fuelConsumptionRateText;
    private Label fuelCostText;
    private Button btnCalculate;
    private ComboBox<String> comboBox;

    @BeforeEach
    public void setUp() throws Exception {
        URL fxmlURL = getClass().getResource("/com/myapp/uilocalisationassignment/calculate-app.fxml");
        assertNotNull(fxmlURL, "FXML file should be found");
        
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
        VBox root = fxmlLoader.load();
        
        appController = fxmlLoader.getController();
        assertNotNull(appController, "Controller should be initialized");
        
        // Get references to UI elements
        tripDistanceInput = (TextField) root.lookup("#tripDistanceInput");
        fuelConsumptionRateInput = (TextField) root.lookup("#fuelConsumptionRateInput");
        fuelCostInput = (TextField) root.lookup("#fuelCostInput");
        resultText = (Label) root.lookup("#resultText");
        tripDistanceText = (Label) root.lookup("#tripDistanceText");
        fuelConsumptionRateText = (Label) root.lookup("#fuelConsumptionRateText");
        fuelCostText = (Label) root.lookup("#fuelCostText");
        btnCalculate = (Button) root.lookup("#btnCalculate");
        comboBox = (ComboBox<String>) root.lookup("#comboBox");
    }

    // ============== Initialization Tests ==============

    /**
     * Test controller initializes with default English locale
     */
    @Test
    @DisplayName("Should initialize with default English locale")
    public void testDefaultLocaleInitialization() {
        assertNotNull(tripDistanceText.getText(), "tripDistanceText should have content");
        assertFalse(tripDistanceText.getText().isEmpty(), "Labels should be initialized with text");
        assertNotNull(fuelConsumptionRateText.getText(), "fuelConsumptionRateText should have content");
        assertNotNull(fuelCostText.getText(), "fuelCostText should have content");
    }

    /**
     * Test all UI components are initialized
     */
    @Test
    @DisplayName("All UI components should be properly initialized")
    public void testAllComponentsInitialized() {
        assertNotNull(tripDistanceInput, "tripDistanceInput should exist");
        assertNotNull(fuelConsumptionRateInput, "fuelConsumptionRateInput should exist");
        assertNotNull(fuelCostInput, "fuelCostInput should exist");
        assertNotNull(resultText, "resultText should exist");
        assertNotNull(btnCalculate, "btnCalculate should exist");
        assertNotNull(comboBox, "comboBox should exist");
    }

    // ============== Calculation Tests ==============

    /**
     * Test basic fuel cost calculation
     */
    @Test
    @DisplayName("Should calculate fuel cost with basic values")
    public void testBasicCalculation() {
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("6");
        fuelCostInput.setText("1.5");
        
        appController.handleCalculate(null);
        
        String result = resultText.getText();
        assertNotNull(result);
        assertTrue(result.contains("6"), "Result should contain fuel amount");
        assertTrue(result.contains("9"), "Result should contain cost");
    }

    /**
     * Test calculation with larger values
     */
    @Test
    @DisplayName("Should calculate fuel cost with larger values")
    public void testCalculationLargeValues() {
        tripDistanceInput.setText("500");
        fuelConsumptionRateInput.setText("8");
        fuelCostInput.setText("2.0");
        
        appController.handleCalculate(null);
        
        String result = resultText.getText();
        assertTrue(result.contains("40"), "40L of fuel expected");
        assertTrue(result.contains("80"), "Cost of 80 expected");
    }

    /**
     * Test calculation with decimal values
     */
    @Test
    @DisplayName("Should handle decimal input values")
    public void testCalculationDecimalValues() {
        tripDistanceInput.setText("250.5");
        fuelConsumptionRateInput.setText("7.5");
        fuelCostInput.setText("1.75");
        
        assertDoesNotThrow(() -> appController.handleCalculate(null),
                "Should not throw exception with decimal values");
        
        String result = resultText.getText();
        assertNotNull(result);
    }

    /**
     * Test calculation with very small values
     */
    @Test
    @DisplayName("Should handle very small values")
    public void testCalculationSmallValues() {
        tripDistanceInput.setText("10");
        fuelConsumptionRateInput.setText("3");
        fuelCostInput.setText("0.5");
        
        appController.handleCalculate(null);
        
        String result = resultText.getText();
        assertNotNull(result);
    }

    /**
     * Test calculation updates result text
     */
    @Test
    @DisplayName("Should update result text after calculation")
    public void testResultTextUpdates() {
        String initialResult = resultText.getText();
        
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        
        appController.handleCalculate(null);
        
        String updatedResult = resultText.getText();
        assertNotEquals(initialResult, updatedResult, "Result should update after calculation");
    }

    /**
     * Test multiple calculations in sequence
     */
    @Test
    @DisplayName("Should handle multiple calculations in sequence")
    public void testMultipleCalculations() {
        // First calculation
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        appController.handleCalculate(null);
        String result1 = resultText.getText();
        
        // Second calculation
        tripDistanceInput.setText("200");
        fuelConsumptionRateInput.setText("6");
        fuelCostInput.setText("1.5");
        appController.handleCalculate(null);
        String result2 = resultText.getText();
        
        assertNotEquals(result1, result2, "Results should differ for different inputs");
    }

    // ============== Language Change Tests ==============

    /**
     * Test language change to French
     */
    @Test
    @DisplayName("Should change language to French")
    public void testChangeToFrench() {
        comboBox.setValue("FR");
        appController.handleLanguageChange(null);
        
        assertNotNull(tripDistanceText.getText());
        assertFalse(tripDistanceText.getText().isEmpty());
    }

    /**
     * Test language change to Japanese
     */
    @Test
    @DisplayName("Should change language to Japanese")
    public void testChangeToJapanese() {
        comboBox.setValue("JP");
        appController.handleLanguageChange(null);
        
        assertNotNull(tripDistanceText.getText());
        assertFalse(tripDistanceText.getText().isEmpty());
    }

    /**
     * Test language change to Farsi
     */
    @Test
    @DisplayName("Should change language to Farsi")
    public void testChangeToFarsi() {
        comboBox.setValue("FA");
        appController.handleLanguageChange(null);
        
        assertNotNull(tripDistanceText.getText());
        assertFalse(tripDistanceText.getText().isEmpty());
    }

    /**
     * Test language change back to English
     */
    @Test
    @DisplayName("Should switch back to English")
    public void testChangeBackToEnglish() {
        // Change to French
        comboBox.setValue("FR");
        appController.handleLanguageChange(null);
        
        // Change back to English
        comboBox.setValue("EN");
        appController.handleLanguageChange(null);
        
        assertNotNull(tripDistanceText.getText());
    }

    /**
     * Test all language options work
     */
    @Test
    @DisplayName("Should support all language options")
    public void testAllLanguageOptions() {
        String[] languages = {"EN", "FR", "JP", "FA"};
        
        for (String lang : languages) {
            comboBox.setValue(lang);
            assertDoesNotThrow(() -> appController.handleLanguageChange(null),
                    "Should handle language: " + lang);
        }
    }

    /**
     * Test language switching preserves data
     */
    @Test
    @DisplayName("Should preserve input data when switching languages")
    public void testLanguageSwitchPreservesData() {
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("6");
        fuelCostInput.setText("1.5");
        
        comboBox.setValue("FR");
        appController.handleLanguageChange(null);
        
        assertEquals("100", tripDistanceInput.getText(), "Distance should be preserved");
        assertEquals("6", fuelConsumptionRateInput.getText(), "Consumption should be preserved");
        assertEquals("1.5", fuelCostInput.getText(), "Price should be preserved");
    }

    /**
     * Test rapid language changes
     */
    @Test
    @DisplayName("Should handle rapid language changes")
    public void testRapidLanguageChanges() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++) {
                comboBox.setValue("EN");
                appController.handleLanguageChange(null);
                comboBox.setValue("FR");
                appController.handleLanguageChange(null);
            }
        }, "Should handle rapid language changes");
    }

    // ============== Edge Case Tests ==============

    /**
     * Test calculation with zero values
     */
    @Test
    @DisplayName("Should handle zero distance calculation")
    public void testZeroDistanceCalculation() {
        tripDistanceInput.setText("0");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        
        assertDoesNotThrow(() -> appController.handleCalculate(null),
                "Should handle zero distance");
    }

    /**
     * Test calculation with maximum reasonable values
     */
    @Test
    @DisplayName("Should handle large calculation values")
    public void testLargeValueCalculation() {
        tripDistanceInput.setText("10000");
        fuelConsumptionRateInput.setText("15");
        fuelCostInput.setText("10");
        
        assertDoesNotThrow(() -> appController.handleCalculate(null),
                "Should handle large values");
    }

    /**
     * Test handleCalculate with null ActionEvent
     */
    @Test
    @DisplayName("Should handle null ActionEvent")
    public void testHandleCalculateWithNull() {
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        
        assertDoesNotThrow(() -> appController.handleCalculate(null),
                "Should handle null ActionEvent");
    }

    /**
     * Test handleLanguageChange with null ActionEvent
     */
    @Test
    @DisplayName("Should handle null ActionEvent in language change")
    public void testHandleLanguageChangeWithNull() {
        comboBox.setValue("FR");
        
        assertDoesNotThrow(() -> appController.handleLanguageChange(null),
                "Should handle null ActionEvent");
    }

    /**
     * Test buttons are functional
     */
    @Test
    @DisplayName("Calculate button should exist and be clickable")
    public void testCalculateButtonFunctionality() {
        assertNotNull(btnCalculate);
        assertFalse(btnCalculate.isDisabled(), "Button should not be disabled");
        
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        
        // Simulate button click
        assertDoesNotThrow(() -> appController.handleCalculate(
                new ActionEvent(btnCalculate, ActionEvent.NULL_SOURCE_TARGET)),
                "Button click should trigger calculation");
    }

    // ============== State Tests ==============

    /**
     * Test that repeated calculations are independent
     */
    @Test
    @DisplayName("Repeated calculations should be independent")
    public void testCalculationIndependence() {
        // First calculation
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        appController.handleCalculate(null);
        String result1 = resultText.getText();
        
        // Change values and recalculate
        tripDistanceInput.setText("200");
        appController.handleCalculate(null);
        String result2 = resultText.getText();
        
        // Results should reflect new values
        assertNotEquals(result1, result2);
    }

    /**
     * Test controller maintains state across operations
     */
    @Test
    @DisplayName("Controller should maintain state across operations")
    public void testStateConsistency() {
        tripDistanceInput.setText("100");
        fuelConsumptionRateInput.setText("5");
        fuelCostInput.setText("1.0");
        
        appController.handleCalculate(null);
        String resultAfterCalc = resultText.getText();
        
        comboBox.setValue("FR");
        appController.handleLanguageChange(null);
        
        // Values should still be there
        assertEquals("100", tripDistanceInput.getText());
    }
}

