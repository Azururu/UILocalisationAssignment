package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CalculationService class
 * Tests fuel calculation logic
 */
@DisplayName("CalculationService Tests")
public class CalculationServiceTest {

    private CalculationService calculationService;

    @BeforeEach
    public void setUp() {
        // Use a test double that does NOT touch the database
        calculationService = new CalculationService() {
            @Override
            public void saveCalculation(double distance,
                                        double consumption,
                                        double price,
                                        double totalFuel,
                                        double totalCost,
                                        String language) {
                // no-op in tests: avoid DB access
            }
        };
    }

    @Test
    @DisplayName("Should calculate total fuel correctly")
    public void testCalculateTotalFuelBasic() {
        double result = calculationService.calculateTotalFuel(100, 6);
        assertEquals(6.0, result, 0.001);
    }

    @Test
    @DisplayName("Should calculate total fuel for large distance")
    public void testCalculateTotalFuelLargeDistance() {
        double result = calculationService.calculateTotalFuel(500, 8);
        assertEquals(40.0, result, 0.001);
    }

    @Test
    @DisplayName("Should handle decimal consumption rates")
    public void testCalculateTotalFuelDecimalConsumption() {
        double result = calculationService.calculateTotalFuel(250.5, 7.5);
        double expected = (7.5 / 100) * 250.5;
        assertEquals(expected, result, 0.001);
    }

    @Test
    @DisplayName("Should return zero fuel for zero distance")
    public void testCalculateTotalFuelZeroDistance() {
        double result = calculationService.calculateTotalFuel(0, 6);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    @DisplayName("Should return zero fuel for zero consumption")
    public void testCalculateTotalFuelZeroConsumption() {
        double result = calculationService.calculateTotalFuel(100, 0);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    @DisplayName("Should calculate total cost correctly")
    public void testCalculateTotalCostBasic() {
        double result = calculationService.calculateTotalCost(6.0, 1.5);
        assertEquals(9.0, result, 0.001);
    }

    @Test
    @DisplayName("Should calculate cost with decimal price")
    public void testCalculateTotalCostDecimalPrice() {
        double result = calculationService.calculateTotalCost(40.0, 2.35);
        double expected = 40.0 * 2.35;
        assertEquals(expected, result, 0.001);
    }

    @Test
    @DisplayName("Should return zero cost for zero fuel")
    public void testCalculateTotalCostZeroFuel() {
        double result = calculationService.calculateTotalCost(0.0, 1.5);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    @DisplayName("Should return zero cost for zero price")
    public void testCalculateTotalCostZeroPrice() {
        double result = calculationService.calculateTotalCost(10.0, 0.0);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    @DisplayName("Should handle very small price values")
    public void testCalculateTotalCostSmallPrice() {
        double result = calculationService.calculateTotalCost(50.0, 0.01);
        assertEquals(0.5, result, 0.001);
    }

    @Test
    @DisplayName("Should handle large fuel and price values")
    public void testCalculateTotalCostLargeValues() {
        double result = calculationService.calculateTotalCost(1000.0, 3.5);
        assertEquals(3500.0, result, 0.001);
    }

    @Test
    @DisplayName("Should work correctly in full workflow")
    public void testFullCalculationWorkflow() {
        double totalFuel = calculationService.calculateTotalFuel(300, 7);
