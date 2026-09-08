package io.github.fr24zh.localizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class ResourceHookDiagnosticsTest {
    @Test
    public void emitsBoundedStructuredCallbackWithoutOriginalBusinessText() {
        ResourceHookDiagnostics diagnostics = new ResourceHookDiagnostics(2);

        String first = diagnostics.callback(
                "getString",
                Integer.valueOf(0x7f1401e6),
                "cab_calibrated_altitude",
                new StringBuilder("BAROMETRIC ALT. secret-flight-data"),
                true,
                true,
                "applied");
        String second = diagnostics.installation("getText", 2);

        assertTrue(first.contains("method=getString"));
        assertTrue(first.contains("resourceId=0x7f1401e6"));
        assertTrue(first.contains("entryName=cab_calibrated_altitude"));
        assertTrue(first.contains("resultClass=java.lang.StringBuilder"));
        assertTrue(first.contains("dictionaryHit=true"));
        assertTrue(first.contains("replacementApplied=true"));
        assertTrue(first.contains("skipReason=applied"));
        assertFalse(first.contains("BAROMETRIC"));
        assertFalse(first.contains("secret-flight-data"));
        assertEquals("resource-hook install method=getText hookCount=2", second);
        assertNull(diagnostics.callback(
                "getText",
                Integer.valueOf(1),
                "ignored",
                "must-not-leak",
                false,
                false,
                "rate_limited"));
    }

    @Test
    public void normalizesUnavailableFieldsWithoutLeakingTheirValues() {
        ResourceHookDiagnostics diagnostics = new ResourceHookDiagnostics(1);

        String message = diagnostics.callback(
                null,
                null,
                "not a resource/name",
                null,
                false,
                false,
                null);

        assertTrue(message.contains("method=unknown"));
        assertTrue(message.contains("resourceId=none"));
        assertTrue(message.contains("entryName=redacted"));
        assertTrue(message.contains("resultClass=null"));
        assertTrue(message.contains("skipReason=unknown"));
        assertFalse(message.contains("not a resource/name"));
    }

    @Test
    public void preservesOneDiagnosticForEachPriorityResourceAfterStartupFlood() {
        ResourceHookDiagnostics diagnostics = new ResourceHookDiagnostics(64);
        for (int index = 0; index < 64; index++) {
            diagnostics.callback(
                    "getString",
                    Integer.valueOf(index),
                    "unrelated_startup_resource_" + index,
                    "must-not-leak-" + index,
                    false,
                    false,
                    "dictionary_miss");
        }

        String[] priorityEntries = new String[]{
                "cab_calibrated_altitude",
                "cab_ground_speed",
                "cab_more_info",
                "settings_weather_basic_weather",
                "settings_weather_basic_weather_desc"
        };
        for (String entryName : priorityEntries) {
            String message = diagnostics.callback(
                    "getText",
                    Integer.valueOf(0x7f140001),
                    entryName,
                    "sensitive-original-text",
                    true,
                    true,
                    "applied");
            assertTrue("priority resource should remain observable: " + entryName,
                    message != null);
            assertTrue(message.contains("entryName=" + entryName));
            assertFalse(message.contains("sensitive-original-text"));
        }
    }

    @Test
    public void doesNotExceedMaximumWithReservedPriorityBudget() {
        ResourceHookDiagnostics diagnostics = new ResourceHookDiagnostics(64);
        int emitted = 0;
        for (int index = 0; index < 59; index++) {
            assertTrue(diagnostics.callback(
                    "getText",
                    Integer.valueOf(index),
                    "unrelated_resource_" + index,
                    "sensitive-" + index,
                    false,
                    false,
                    "dictionary_miss") != null);
            emitted++;
        }

        String[] priorityEntries = new String[]{
                "cab_calibrated_altitude",
                "cab_ground_speed",
                "cab_more_info",
                "settings_weather_basic_weather",
                "settings_weather_basic_weather_desc"
        };
        for (String entryName : priorityEntries) {
            assertTrue(diagnostics.callback(
                    "getString",
                    Integer.valueOf(0x7f140001),
                    entryName,
                    "sensitive-priority",
                    true,
                    true,
                    "applied") != null);
            emitted++;
        }

        assertEquals(64, emitted);
        assertNull(diagnostics.callback(
                "getText",
                Integer.valueOf(65),
                "another_resource",
                "must-not-leak",
                false,
                false,
                "rate_limited"));
    }

    @Test
    public void redactsAllNonPriorityEntryNamesAndUnknownResultClasses() {
        ResourceHookDiagnostics diagnostics = new ResourceHookDiagnostics(4);
        String[] sensitiveEntryNames = new String[]{
                "google_api_key",
                "project_id",
                "Flight123"
        };

        for (String entryName : sensitiveEntryNames) {
            String message = diagnostics.callback(
                    "getString",
                    Integer.valueOf(1),
                    entryName,
                    new SensitiveResult(),
                    false,
                    false,
                    "dictionary_miss");
            assertTrue(message.contains("entryName=redacted"));
            assertTrue(message.contains("resultClass=other"));
            assertFalse(message.contains(entryName));
            assertFalse(message.contains(SensitiveResult.class.getName()));
        }
    }

    private static final class SensitiveResult {
    }
}
