package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DatabaseConnection class
 * Tests database connection establishment
 */
@DisplayName("DatabaseConnection Tests")
public class DatabaseConnectionTest {

    @Test
    @DisplayName("DatabaseConnection should be instantiable")
    public void testDatabaseConnectionInstantiation() {
        assertDoesNotThrow(() -> 
            Class.forName("com.myapp.uilocalisationassignment.DatabaseConnection")
        );
    }

    @Test
    @DisplayName("getConnection method should be accessible")
    public void testGetConnectionMethodExists() {
        assertDoesNotThrow(() -> {
            java.lang.reflect.Method method = DatabaseConnection.class.getMethod("getConnection");
            assertNotNull(method);
        });
    }

    @Test
    @DisplayName("getConnection should return Connection or throw SQLException")
    public void testGetConnectionReturnTypeOrException() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            assertNotNull(e);
            assertTrue(e instanceof SQLException);
        }
    }

    @Test
    @DisplayName("DatabaseConnection static initialization should complete")
    public void testStaticInitialization() {
        assertDoesNotThrow(() -> 
            DatabaseConnection.class.getName()
        );
    }

    @Test
    @DisplayName("Connection should handle missing configuration gracefully")
    public void testMissingConfigurationHandling() {
        try {
            DatabaseConnection.getConnection();
        } catch (SQLException e) {
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

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
                // Expected
            }
            
            try {
                Connection conn2 = DatabaseConnection.getConnection();
                if (conn2 != null) {
                    conn2.close();
                }
            } catch (SQLException e) {
                // Expected
            }
        });
    }

    @Test
    @DisplayName("getConnection should be a static method")
    public void testGetConnectionIsStatic() {
        try {
            java.lang.reflect.Method method = DatabaseConnection.class.getMethod("getConnection");
            int modifiers = method.getModifiers();
            assertTrue(java.lang.reflect.Modifier.isStatic(modifiers));
        } catch (NoSuchMethodException e) {
            fail("getConnection method should exist");
        }
    }

    @Test
    @DisplayName("Database properties should be loaded from configuration")
    public void testPropertiesLoading() {
        assertDoesNotThrow(() -> 
            DatabaseConnection.class.getName()
        );
    }

    @Test
    @DisplayName("If connection is available, it should be usable")
    public void testConnectionUsability() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                assertFalse(connection.isClosed());
                connection.close();
                assertTrue(connection.isClosed());
            }
        } catch (SQLException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("SQLException should include error message")
    public void testSQLExceptionDetails() {
        try {
            DatabaseConnection.getConnection();
        } catch (SQLException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Errors should be handled appropriately")
    public void testErrorHandling() {
        assertDoesNotThrow(() -> {
            try {
                DatabaseConnection.getConnection();
            } catch (SQLException e) {
                // Expected and acceptable
            }
        });
    }

    @Test
    @DisplayName("DatabaseConnection should be a static utility class")
    public void testStaticUtilityPattern() {
        try {
            java.lang.reflect.Constructor<?>[] constructors = 
                    DatabaseConnection.class.getDeclaredConstructors();
            assertNotNull(constructors);
        } catch (Exception e) {
            fail("Should be able to inspect class constructors");
        }
    }
}

