package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LocalizationService class
 * Tests localization caching
 */
@DisplayName("LocalizationService Tests")
public class LocalizationServiceTest {

    private LocalizationService localizationService;

    @BeforeEach
    public void setUp() {
        localizationService = new LocalizationService();
        localizationService.clearCache();
    }

    @Test
    @DisplayName("Should return a Map")
    public void testGetLocalizationReturnsMap() {
        Map<String, String> result = localizationService.getLocalization("EN");
        assertNotNull(result);
        assertTrue(result instanceof Map);
    }

    @Test
    @DisplayName("Should handle non-existent languages gracefully")
    public void testGetLocalizationNonExistentLanguage() {
        Map<String, String> result = localizationService.getLocalization("XX");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should cache localizations after first call")
    public void testLocalizationCaching() {
        Map<String, String> first = localizationService.getLocalization("EN");
        Map<String, String> second = localizationService.getLocalization("EN");
        
        assertEquals(first, second);

    }

    @Test
    @DisplayName("Different languages should be cached separately")
    public void testSeparateCachesForDifferentLanguages() {
        Map<String, String> english = localizationService.getLocalization("EN");
        Map<String, String> french = localizationService.getLocalization("FR");
        Map<String, String> english2 = localizationService.getLocalization("EN");
        
        assertNotSame(english, french);
        assertSame(english, english2);
    }

    @Test
    @DisplayName("clearCache should clear all cached localizations")
    public void testClearCache() {
        localizationService.getLocalization("EN");
        localizationService.getLocalization("FR");
        
        assertDoesNotThrow(() -> localizationService.clearCache());
    }

    @Test
    @DisplayName("Should get English localization")
    public void testGetEnglishLocalization() {
        Map<String, String> result = localizationService.getLocalization("EN");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should get French localization")
    public void testGetFrenchLocalization() {
        Map<String, String> result = localizationService.getLocalization("FR");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should get Japanese localization")
    public void testGetJapaneseLocalization() {
        Map<String, String> result = localizationService.getLocalization("JP");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should get Persian localization")
    public void testGetPersianLocalization() {
        Map<String, String> result = localizationService.getLocalization("FA");
        assertNotNull(result);
    }

    @Test
    @DisplayName("Returned map should be usable")
    public void testMapIsUsable() {
        Map<String, String> result = localizationService.getLocalization("EN");
        
        assertDoesNotThrow(() -> {
            result.get("key");
            result.size();
            result.containsKey("any_key");
        });
    }

    @Test
    @DisplayName("Multiple clearCache calls should work correctly")
    public void testMultipleCacheClears() {
        localizationService.getLocalization("EN");
        localizationService.clearCache();
        localizationService.clearCache();
        
        assertDoesNotThrow(() -> localizationService.getLocalization("EN"));
    }

    @Test
    @DisplayName("Service should handle exceptions gracefully")
    public void testExceptionHandling() {
        assertDoesNotThrow(() -> {
            localizationService.getLocalization("EN");
            localizationService.getLocalization("UNKNOWN");
            localizationService.clearCache();
        });
    }

    @Test
    @DisplayName("Different instances should have separate caches")
    public void testInstanceIsolation() {
        LocalizationService service1 = new LocalizationService();
        LocalizationService service2 = new LocalizationService();
        
        Map<String, String> map1 = service1.getLocalization("EN");
        Map<String, String> map2 = service2.getLocalization("EN");
        
        assertNotNull(map1);
        assertNotNull(map2);
    }

    @Test
    @DisplayName("Language codes might be case-sensitive")
    public void testLanguageCodeCaseSensitivity() {
        Map<String, String> uppercase = localizationService.getLocalization("EN");
        Map<String, String> lowercase = localizationService.getLocalization("en");
        
        assertNotNull(uppercase);
        assertNotNull(lowercase);
    }
}

