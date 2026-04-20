package com.myapp.uilocalisationassignment;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to load properties file.");
            }
            Properties props = new Properties();
            props.load(input);

            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
        } catch (Exception e) {
            logger.warning("Error:" + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.warning("Error:" + e.getMessage());
            throw e;
        }
    }
}
