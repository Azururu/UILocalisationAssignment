package com.myapp.uilocalisationassignment;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class LocalizationServiceTest {

    @Test
    void cacheShouldStoreResults() {
        LocalizationService service = new LocalizationService();

        Map<String, String> first = service.getLocalization("en");
        Map<String, String> second = service.getLocalization("en");

        assertSame(first, second); // same object = cached
    }
}