package com.myapp.uilocalisationassignment;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LocalizationService {

    private final Map<String, Map<String, String>> cache = new HashMap<>();
    private static final Logger logger = Logger.getLogger(LocalizationService.class.getName());

    public Map<String, String> getLocalization(String language) {

        // 🔥 Cache hit
        if (cache.containsKey(language)) {
            return cache.get(language);
        }

        // 🔥 TEST MODE: return fake translations, skip DB entirely
        if (AppController.TEST_MODE) {
            Map<String, String> testTranslations = getTestModeLocalization(language);
            cache.put(language, testTranslations);
            return testTranslations;
        }

        Map<String, String> translations = new HashMap<>();
        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection == null) {
                logger.warning("Connection is null — DB unavailable");
                return translations;
            }

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, language);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        translations.put(
                                rs.getString("key"),
                                rs.getString("value")
                        );
                    }
                }
            }

            cache.put(language, translations);

        } catch (SQLException e) {
            logger.warning("Failed getting localization: " + e.getMessage());
        }

        return translations;
    }

    // 🔥 Fake translations for TEST_MODE
    private Map<String, String> getTestModeLocalization(String lang) {
        return switch (lang.toLowerCase()) {
            case "fr" -> Map.of(
                    "tripDistanceText", "Distance",
                    "fuelConsumptionRateText", "Consommation",
                    "fuelCostText", "Coût du carburant",
                    "btnCalculate", "Calculer",
                    "resultText", "Carburant: {0}, Coût: {1}"
            );
            case "ja" -> Map.of(
                    "tripDistanceText", "距離",
                    "fuelConsumptionRateText", "燃費",
                    "fuelCostText", "燃料費",
                    "btnCalculate", "計算",
                    "resultText", "燃料: {0}, コスト: {1}"
            );
            case "fa" -> Map.of(
                    "tripDistanceText", "مسافت",
                    "fuelConsumptionRateText", "مصرف سوخت",
                    "fuelCostText", "هزینه سوخت",
                    "btnCalculate", "محاسبه",
                    "resultText", "سوخت: {0}, هزینه: {1}"
            );
            default -> Map.of(
                    "tripDistanceText", "Trip Distance",
                    "fuelConsumptionRateText", "Fuel Consumption",
                    "fuelCostText", "Fuel Cost",
                    "btnCalculate", "Calculate",
                    "resultText", "Fuel: {0}, Cost: {1}"
            );
        };
    }

    public void clearCache() {
        cache.clear();
    }
}
