package com.myapp.uilocalisationassignment;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CalculationService {

    private static final Logger logger = Logger.getLogger(CalculationService.class.getName());

    public void saveCalculation(double distance, double consumption, double price, double totalFuel, double totalCost, String language) {
        String sql = "INSERT INTO calculation_records (distance, consumption, price, total_fuel, total_cost, language) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, distance);
            stmt.setDouble(2, consumption);
            stmt.setDouble(3, price);
            stmt.setDouble(4, totalFuel);
            stmt.setDouble(5, totalCost);
            stmt.setString(6, language);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                logger.info("Calculation saved successfully");
            }
        } catch (SQLException e) {
            logger.warning("Failed to save calculation: " + e.getMessage());
        }
    }

    public double calculateTotalFuel(double distance, double consumption) {
        return (consumption / 100) * distance;
    }

    public double calculateTotalCost(double totalFuel, double price) {
        return totalFuel * price;
    }
}
