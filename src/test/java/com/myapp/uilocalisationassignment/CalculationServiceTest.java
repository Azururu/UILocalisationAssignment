package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CalculationService class
 * Tests fuel calculation logic and database operations
 */
@DisplayName("CalculationService Tests")
public class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    public void setUp() {
        calculationService = new CalculationService();
    }

    /**
     * Test basic fuel consumption calculation
     */
    @Test
    @DisplayName("Should calculate total fuel correctly with standard values")
    public void testCalculateTotalFuelBasic() {
        double distance = 100;
        double consumption = 6;
        
        double result = calculationService.calculateTotalFuel(distance, consumption);
        
        assertEquals(6.0, result, 0.001, "Fuel calculation should be (consumption/100)*distance");
    }

    /**
     * Test fuel calculation with larger distance
     */
    @Test
    @DisplayName("Should calculate total fuel for longer distance")
    public void testCalculateTotalFuelLargeDistance() {
        double distance = 500;
        double consumption = 8;
        
        double result = calculationService.calculateTotalFuel(distance, consumption);
        
        assertEquals(40.0, result, 0.001, "Fuel calculation for 500km at 8L/100km should be 40L");
    }

    /**
     * Test fuel calculation with decimal consumption rate
     */
    @Test
    @DisplayName("Should handle decimal consumption rates")
    public void testCalculateTotalFuelDecimalConsumption() {
        double distance = 250.5;
        double consumption = 7.5;
        
        double result = calculationService.calculateTotalFuel(distance, consumption);
        
        double expected = (7.5 / 100) * 250.5;
        assertEquals(expected, result, 0.001, "Should correctly calculate with decimal values");
    }

    /**
     * Test fuel calculation with zero distance
     */
    @Test
    @DisplayName("Should return zero fuel for zero distance")
    public void testCalculateTotalFuelZeroDistance() {
        double distance = 0;
        double consumption = 6;
        
        double result = calculationService.calculateTotalFuel(distance, consumption);
        
        assertEquals(0.0, result, 0.001, "Zero distance should result in zero fuel");
    }

    /**
     * Test fuel calculation with zero consumption
     */
    @Test
    @DisplayName("Should return zero fuel for zero consumption rate")
    public void testCalculateTotalFuelZeroConsumption() {
        double distance = 100;
        double consumption = 0;
        
        double result = calculationService.calculateTotalFuel(distance, consumption);
        
        assertEquals(0.0, result, 0.001, "Zero consumption should result in zero fuel");
    }

    /**
     * Test fuel cost calculation basic
     */
    @Test
    @DisplayName("Should calculate total cost correctly")
    public void testCalculateTotalCostBasic() {
        double totalFuel = 6.0;
        double price = 1.5;
        
        double result = calculationService.calculateTotalCost(totalFuel, price);
        
        assertEquals(9.0, result, 0.001, "Cost should be totalFuel * price");
    }

    /**
     * Test fuel cost calculation with decimal price
     */
    @Test
    @DisplayName("Should calculate cost with decimal fuel price")
    public void testCalculateTotalCostDecimalPrice() {
        double totalFuel = 40.0;
        double price = 2.35;
        
        double result = calculationService.calculateTotalCost(totalFuel, price);
        
        double expected = 40.0 * 2.35;
        assertEquals(expected, result, 0.001, "Should correctly calculate cost with decimal price");
    }

    /**
     * Test fuel cost calculation with zero fuel
     */
    @Test
    @DisplayName("Should return zero cost for zero fuel")
    public void testCalculateTotalCostZeroFuel() {
        double totalFuel = 0.0;
        double price = 1.5;
        
        double result = calculationService.calculateTotalCost(totalFuel, price);
        
        assertEquals(0.0, result, 0.001, "Zero fuel should result in zero cost");
    }

    /**
     * Test fuel cost calculation with zero price
     */
    @Test
    @DisplayName("Should return zero cost for zero price")
    public void testCalculateTotalCostZeroPrice() {
        double totalFuel = 10.0;
        double price = 0.0;
        
        double result = calculationService.calculateTotalCost(totalFuel, price);
        
        assertEquals(0.0, result, 0.001, "Zero price should result in zero cost");
    }

    /**
     * Test cost calculation with very small price
     */
    @Test
    @DisplayName("Should handle very small price values")
    public void testCalculateTotalCostSmallPrice() {
        double totalFuel = 50.0;
        double price = 0.01;
        
        double result = calculationService.calculateTotalCost(totalFuel, price);
        
        assertEquals(0.5, result, 0.001, "Should correctly calculate with very small price");
    }

    /**
     * Test cost calculation with large values
     */
    @Test
    @DisplayName("Should handle large fuel and price values")
    public void testCalculateTotalCostLargeValues() {
        double totalFuel = 1000.0;
        double price = 3.5;
        
        double result = calculationService.calculateTotalCost(totalFuel, price);
        
        assertEquals(3500.0, result, 0.001, "Should correctly calculate with large values");
    }

    /**
     * Test combined calculation workflow
     */
    @Test
    @DisplayName("Should work correctly in full calculation workflow")
    public void testFullCalculationWorkflow() {
        // Step 1: Calculate fuel
        double distance = 300;
        double consumption = 7;
        double totalFuel = calculationService.calculateTotalFuel(distance, consumption);
        
        // Expected: (7/100)*300 = 21L
        assertEquals(21.0, totalFuel, 0.001);
        
        // Step 2: Calculate cost
        double price = 1.8;
        double totalCost = calculationService.calculateTotalCost(totalFuel, price);
        
        // Expected: 21 * 1.8 = 37.8
        assertEquals(37.8, totalCost, 0.001);
    }

    /**
     * Test negative distance is handled (should not occur but test boundary)
     */
    @Test
    @DisplayName("Should return negative fuel for negative distance")
    public void testCalculateTotalFuelNegativeDistance() {
        double distance = -100;
        double consumption = 6;
        
        double result = calculationService.calculateTotalFuel(distance, consumption);
        
        assertEquals(-6.0, result, 0.001, "Negative distance produces negative fuel mathematically");
    }

    /**
     * Test saveCalculation method doesn't throw exception
     */
    @Test
    @DisplayName("saveCalculation should handle exceptions gracefully")
    public void testSaveCalculationDoesNotThrow() {
        assertDoesNotThrow(() -> {
            calculationService.saveCalculation(100, 6, 1.5, 6.0, 9.0, "EN");
        }, "saveCalculation should not throw exceptions");
    }

    /**
     * Test saveCalculation with different languages
     */
    @Test
    @DisplayName("saveCalculation should accept various language codes")
    public void testSaveCalculationWithVariousLanguages() {
        assertDoesNotThrow(() -> {
            calculationService.saveCalculation(100, 6, 1.5, 6.0, 9.0, "FR");
            calculationService.saveCalculation(100, 6, 1.5, 6.0, 9.0, "JP");
            calculationService.saveCalculation(100, 6, 1.5, 6.0, 9.0, "FA");
            calculationService.saveCalculation(100, 6, 1.5, 6.0, 9.0, "EN");
        }, "Should accept various language codes");
    }

    /**
     * Test precision of calculations
     */
    @Test
    @DisplayName("Should maintain precision in floating point calculations")
    public void testCalculationPrecision() {
        double distance = 123.456;
        double consumption = 5.678;
        double price = 1.234;
        
        double totalFuel = calculationService.calculateTotalFuel(distance, consumption);
        double totalCost = calculationService.calculateTotalCost(totalFuel, price);
        
        // Verify calculations maintain reasonable precision
        assertTrue(totalFuel > 0, "Fuel should be positive");
        assertTrue(totalCost > 0, "Cost should be positive");
        assertEquals(totalFuel * price, totalCost, 0.01, "Cost should equal fuel times price");
    }
}

