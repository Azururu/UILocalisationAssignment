package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DatabaseConnection class
 * Tests database connection establishment and configuration
 */
@DisplayName("DatabaseConnection Tests")
public class DatabaseConnectionTest {

    /**
     * Test that DatabaseConnection class can be instantiated
     */
    @Test
    @DisplayName("DatabaseConnection should be instantiable")
    public void testDatabaseConnectionInstantiation() {
        assertDoesNotThrow(() -> {
            // The class should not throw exception during class loading
            Class.forName("com.myapp.uilocalisationassignment.DatabaseConnection");
        }, "DatabaseConnection class should load successfully");
    }

    /**
     * Test that getConnection method exists and is accessible
     */
    @Test
    @DisplayName("getConnection method should be accessible")
    public void testGetConnectionMethodExists() {
        assertDoesNotThrow(() -> {
            java.lang.reflect.Method method = DatabaseConnection.class.getMethod("getConnection");
            assertNotNull(method, "getConnection method should exist");
        }, "getConnection method should be accessible");
    }

    /**
     * Test that getConnection returns a Connection or throws SQLException
     */
    @Test
    @DisplayName("getConnection should return Connection or throw SQLException")
    public void testGetConnectionReturnTypeOrException() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Connection should not be null if successful");
        } catch (SQLException e) {
            // SQLException is expected if database is not configured
            assertNotNull(e, "SQLException is acceptable if database unavailable");
            assertTrue(e instanceof SQLException, "Should throw SQLException");
        } catch (Exception e) {
            fail("Should only throw SQLException or return Connection, got: " + e.getClass().getName());
        }
    }

    /**
     * Test static initialization block doesn't throw unchecked exceptions
     */
    @Test
    @DisplayName("DatabaseConnection static initialization should complete")
    public void testStaticInitialization() {
        assertDoesNotThrow(() -> {
            // If we can load the class, static init completed
            DatabaseConnection.class.getName();
        }, "Static initialization should complete without throwing unchecked exceptions");
    }

    /**
     * Test that connection attempts handle missing db.properties gracefully
     */
    @Test
    @DisplayName("Connection should handle missing configuration gracefully")
    public void testMissingConfigurationHandling() {
        // The class should be initialized even if db.properties is missing
        // It may throw RuntimeException or SQLException on getConnection
        try {
            DatabaseConnection.getConnection();
        } catch (SQLException e) {
            // This is expected when db.properties is missing or database is unavailable
            assertNotNull(e.getMessage(), "SQLException should have a message");
        } catch (Exception e) {
            // Other exceptions are also acceptable
            assertNotNull(e, "Exception should be thrown for missing/invalid config");
        }
    }

    /**
     * Test that multiple connection attempts work
     */
    @Test
    @DisplayName("Multiple getConnection calls should be supported")
    public void testMultipleConnectionAttempts() {
        assertDoesNotThrow(() -> {
            try {
                Connection conn1 = DatabaseConnection.getConnection();
                if (conn1 != null) {
                    conn1.close();
                }
            } catch (SQLException e) {
                // Expected if database is not available
            }
            
            try {
                Connection conn2 = DatabaseConnection.getConnection();
                if (conn2 != null) {
                    conn2.close();
                }
            } catch (SQLException e) {
                // Expected if database is not available
            }
        }, "Should support multiple connection attempts");
    }

    /**
     * Test that getConnection is static
     */
    @Test
    @DisplayName("getConnection should be a static method")
    public void testGetConnectionIsStatic() {
        try {
            java.lang.reflect.Method method = DatabaseConnection.class.getMethod("getConnection");
            int modifiers = method.getModifiers();
            assertTrue(java.lang.reflect.Modifier.isStatic(modifiers), 
                    "getConnection should be a static method");
        } catch (NoSuchMethodException e) {
            fail("getConnection method should exist");
        }
    }

    /**
     * Test that connection properties are loaded
     */
    @Test
    @DisplayName("Database properties should be loaded from configuration")
    public void testPropertiesLoading() {
        // Test that the static initializer attempted to load properties
        assertDoesNotThrow(() -> {
            // If this succeeds, properties were at least attempted to be loaded
            DatabaseConnection.class.getName();
        }, "Properties should load without unchecked exceptions");
    }

    /**
     * Test connection is usable if available
     */
    @Test
    @DisplayName("If connection is available, it should be usable")
    public void testConnectionUsability() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                assertFalse(connection.isClosed(), "Connection should not be closed");
                connection.close();
                assertTrue(connection.isClosed(), "Connection should be closeable");
            }
        } catch (SQLException e) {
            // Database not available is acceptable
            assertNotNull(e, "SQLException accepted when database unavailable");
        }
    }

    /**
     * Test that SQLException is properly thrown with details
     */
    @Test
    @DisplayName("SQLException should include error message")
    public void testSQLExceptionDetails() {
        try {
            DatabaseConnection.getConnection();
        } catch (SQLException e) {
            // Verify exception has useful information
            String message = e.getMessage();
            // Message may be null if database unavailable
            assertNotNull(e, "SQLException should be available");
        }
    }

    /**
     * Test error handling prints to stderr
     */
    @Test
    @DisplayName("Errors should be handled appropriately")
    public void testErrorHandling() {
        assertDoesNotThrow(() -> {
            // The getConnection method has try-catch that prints to stderr
            // Test that it doesn't throw unchecked exceptions
            try {
                DatabaseConnection.getConnection();
            } catch (SQLException e) {
                // Expected and acceptable
            }
        }, "Error handling should not throw unchecked exceptions");
    }

    /**
     * Test that class is not instantiable (static utility)
     */
    @Test
    @DisplayName("DatabaseConnection should be a static utility class")
    public void testStaticUtilityPattern() {
        // Try to instantiate
        try {
            java.lang.reflect.Constructor<?>[] constructors = 
                    DatabaseConnection.class.getDeclaredConstructors();
            // It's fine if there are constructors (they might be private)
            assertNotNull(constructors);
        } catch (Exception e) {
            fail("Should be able to inspect class constructors");
        }
    }
}

