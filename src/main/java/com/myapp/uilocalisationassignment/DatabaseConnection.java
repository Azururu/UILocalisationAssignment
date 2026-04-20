package com.myapp.uilocalisationassignment;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static String url;
    private static String user;
    private static String password;

    private DatabaseConnection() {}

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            if (input != null) {
                props.load(input);
            }

            // Prioritize Environment Variables, fallback to db.properties
            url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : props.getProperty("db.url");
            user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : props.getProperty("db.user");
            password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : props.getProperty("db.password");

        } catch (Exception e) {
            logger.warning("Error loading database configuration: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to DB using URL: " + url, e);
        }
    }
}
