package com.myapp.uilocalisationassignment;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LocalizationService {

    private final Map<String, Map<String, String>> cache = new HashMap<>();

    private static final Logger logger = Logger.getLogger(LocalizationService.class.getName());


    public Map<String, String> getLocalization(String language) {
        if (cache.containsKey(language)) {
            return cache.get(language);
        }

        Map<String, String> translations = new HashMap<>();

        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, language);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    translations.put(
                            rs.getString("key"),
                            rs.getString("value")
                    );
                }
            }
            cache.put(language, translations);
        } catch (SQLException e) {
            logger.warning("Failed getting localization:" + e.getMessage());
        }
        return translations;
    }

    public void clearCache() {
        cache.clear();
    }
}
