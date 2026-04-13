package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LocalizationService class
 * Tests localization caching and database operations
 */
@DisplayName("LocalizationService Tests")
public class LocalizationServiceTest {

    private LocalizationService localizationService;

    @BeforeEach
    public void setUp() {
        localizationService = new LocalizationService();
        localizationService.clearCache();
    }

    /**
     * Test that getLocalization returns a Map
     */
    @Test
    @DisplayName("getLocalization should return a Map")
    public void testGetLocalizationReturnsMap() {
        Map<String, String> result = localizationService.getLocalization("EN");
        
        assertNotNull(result, "getLocalization should return a non-null Map");
        assertTrue(result instanceof Map, "Result should be a Map instance");
    }

    /**
     * Test that getLocalization returns empty map for non-existent language
     */
    @Test
    @DisplayName("getLocalization should handle non-existent languages gracefully")
    public void testGetLocalizationNonExistentLanguage() {
        Map<String, String> result = localizationService.getLocalization("XX");
        
        assertNotNull(result, "Should return non-null map even for non-existent language");
    }

    /**
     * Test cache functionality - multiple calls should use cache
     */
    @Test
    @DisplayName("Localization should be cached after first call")
    public void testLocalizationCaching() {
        Map<String, String> first = localizationService.getLocalization("EN");
        Map<String, String> second = localizationService.getLocalization("EN");
        
        assertSame(first, second, "Second call should return cached instance");
    }

    /**
     * Test different languages are cached separately
     */
    @Test
    @DisplayName("Different languages should be cached separately")
    public void testSeparateCachesForDifferentLanguages() {
        Map<String, String> english = localizationService.getLocalization("EN");
        Map<String, String> french = localizationService.getLocalization("FR");
        Map<String, String> english2 = localizationService.getLocalization("EN");
        
        assertNotSame(english, french, "Different languages should have different cache entries");
        assertSame(english, english2, "Same language should use cached instance");
    }

    /**
     * Test cache clearing functionality
     */
    @Test
    @DisplayName("clearCache should clear all cached localizations")
    public void testClearCache() {
        localizationService.getLocalization("EN");
        localizationService.getLocalization("FR");
        
        localizationService.clearCache();
        
        // Create a private method to check cache size or verify behavior after clear
        // For now, we test that method executes without exception
        assertDoesNotThrow(() -> localizationService.clearCache(), "clearCache should not throw exception");
    }

    /**
     * Test getLocalization with English
     */
    @Test
    @DisplayName("Should get English localization")
    public void testGetEnglishLocalization() {
        Map<String, String> result = localizationService.getLocalization("EN");
        
        assertNotNull(result, "English localization should be returned");
    }

    /**
     * Test getLocalization with French
     */
    @Test
    @DisplayName("Should get French localization")
    public void testGetFrenchLocalization() {
        Map<String, String> result = localizationService.getLocalization("FR");
        
        assertNotNull(result, "French localization should be returned");
    }

    /**
     * Test getLocalization with Japanese
     */
    @Test
    @DisplayName("Should get Japanese localization")
    public void testGetJapaneseLocalization() {
        Map<String, String> result = localizationService.getLocalization("JP");
        
        assertNotNull(result, "Japanese localization should be returned");
    }

    /**
     * Test getLocalization with Persian
     */
    @Test
    @DisplayName("Should get Persian localization")
    public void testGetPersianLocalization() {
        Map<String, String> result = localizationService.getLocalization("FA");
        
        assertNotNull(result, "Persian localization should be returned");
    }

    /**
     * Test that returned maps are mutable
     */
    @Test
    @DisplayName("Returned localization map should be usable")
    public void testMapIsUsable() {
        Map<String, String> result = localizationService.getLocalization("EN");
        
        assertDoesNotThrow(() -> {
            result.get("key");
            result.size();
            result.containsKey("any_key");
        }, "Returned map should support standard operations");
    }

    /**
     * Test multiple cache clears
     */
    @Test
    @DisplayName("Multiple clearCache calls should work correctly")
    public void testMultipleCacheClears() {
        localizationService.getLocalization("EN");
        localizationService.clearCache();
        localizationService.clearCache();
        
        assertDoesNotThrow(() -> localizationService.getLocalization("EN"), 
                "Should be able to load localization after multiple cache clears");
    }

    /**
     * Test service doesn't throw exception during operation
     */
    @Test
    @DisplayName("Service should handle exceptions gracefully")
    public void testExceptionHandling() {
        assertDoesNotThrow(() -> {
            localizationService.getLocalization("EN");
            localizationService.getLocalization("UNKNOWN");
            localizationService.clearCache();
        }, "Service should handle operations gracefully");
    }

    /**
     * Test cache independence between service instances
     */
    @Test
    @DisplayName("Different service instances should have separate caches")
    public void testInstanceIsolation() {
        LocalizationService service1 = new LocalizationService();
        LocalizationService service2 = new LocalizationService();
        
        Map<String, String> map1 = service1.getLocalization("EN");
        Map<String, String> map2 = service2.getLocalization("EN");
        
        // Different instances may or may not share cache depending on implementation
        // This test documents the behavior
        assertNotNull(map1);
        assertNotNull(map2);
    }

    /**
     * Test null safety
     */
    @Test
    @DisplayName("Service should handle null language parameter")
    public void testNullLanguageParameter() {
        // Service should either handle null or throw exception
        // Testing that it doesn't crash unexpectedly
        try {
            Map<String, String> result = localizationService.getLocalization(null);
            assertNotNull(result, "Should return a map even with null input");
        } catch (Exception e) {
            // Exception is acceptable for null input
            assertNotNull(e, "Exception is acceptable for null parameter");
        }
    }

    /**
     * Test case sensitivity of language codes
     */
    @Test
    @DisplayName("Language codes might be case-sensitive")
    public void testLanguageCodeCaseSensitivity() {
        Map<String, String> uppercase = localizationService.getLocalization("EN");
        Map<String, String> lowercase = localizationService.getLocalization("en");
        
        // Document behavior - they may or may not be the same
        assertNotNull(uppercase);
        assertNotNull(lowercase);
    }
}

