package io.github.fr24zh.localizer;

import java.util.HashSet;
import java.util.Set;

final class ResourceHookDiagnostics {
    private static final String[] PRIORITY_ENTRY_NAMES = new String[]{
            "cab_calibrated_altitude",
            "cab_ground_speed",
            "cab_more_info",
            "settings_weather_basic_weather",
            "settings_weather_basic_weather_desc"
    };

    private final int ordinaryEventBudget;
    private final int priorityEventBudget;
    private final Set<String> emittedPriorityEntries = new HashSet<>();
    private int emittedEvents;
    private int emittedOrdinaryEvents;

    ResourceHookDiagnostics(int maximumEvents) {
        this.priorityEventBudget = maximumEvents >= PRIORITY_ENTRY_NAMES.length
                ? PRIORITY_ENTRY_NAMES.length : 0;
        this.ordinaryEventBudget = Math.max(0, maximumEvents - priorityEventBudget);
    }

    synchronized String installation(String method, int hookCount) {
        if (!reserveOrdinaryEvent()) {
            return null;
        }
        return "resource-hook install method=" + safeMethod(method)
                + " hookCount=" + Math.max(0, hookCount);
    }

    synchronized String callback(
            String method,
            Integer resourceId,
            String entryName,
            Object result,
            boolean dictionaryHit,
            boolean replacementApplied,
            String skipReason) {
        if (!reserveCallbackEvent(entryName)) {
            return null;
        }
        return "resource-hook callback method=" + safeMethod(method)
                + " resourceId=" + resourceId(resourceId)
                + " entryName=" + safeEntryName(entryName)
                + " resultClass=" + resultClass(result)
                + " dictionaryHit=" + dictionaryHit
                + " replacementApplied=" + replacementApplied
                + " skipReason=" + safeSkipReason(skipReason);
    }

    private boolean reserveOrdinaryEvent() {
        if (emittedEvents >= ordinaryEventBudget + priorityEventBudget
                || emittedOrdinaryEvents >= ordinaryEventBudget) {
            return false;
        }
        emittedOrdinaryEvents++;
        emittedEvents++;
        return true;
    }

    private boolean reserveCallbackEvent(String entryName) {
        if (priorityEventBudget > 0
                && isPriorityEntry(entryName)
                && !emittedPriorityEntries.contains(entryName)
                && emittedEvents < ordinaryEventBudget + priorityEventBudget) {
            emittedPriorityEntries.add(entryName);
            emittedEvents++;
            return true;
        }
        return reserveOrdinaryEvent();
    }

    private static boolean isPriorityEntry(String entryName) {
        for (String priorityEntryName : PRIORITY_ENTRY_NAMES) {
            if (priorityEntryName.equals(entryName)) {
                return true;
            }
        }
        return false;
    }

    private static String resourceId(Integer resourceId) {
        return resourceId == null ? "none" : String.format("0x%08x", resourceId);
    }

    private static String resultClass(Object result) {
        if (result == null) {
            return "null";
        }
        if (result instanceof String) {
            return "java.lang.String";
        }
        if (result instanceof StringBuilder) {
            return "java.lang.StringBuilder";
        }
        if ("android.text.SpannedString".equals(result.getClass().getName())) {
            return "android.text.SpannedString";
        }
        return "other";
    }

    private static String safeMethod(String value) {
        return isToken(value) ? value : "unknown";
    }

    private static String safeEntryName(String value) {
        if (value == null) {
            return "unavailable";
        }
        return isPriorityEntry(value) ? value : "redacted";
    }

    private static String safeSkipReason(String value) {
        return isToken(value) ? value : "unknown";
    }

    private static boolean isToken(String value) {
        return value != null && value.matches("[A-Za-z0-9_.-]+");
    }
}
