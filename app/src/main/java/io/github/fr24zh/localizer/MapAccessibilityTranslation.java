package io.github.fr24zh.localizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MapAccessibilityTranslation {
    private static final Pattern FLIGHT =
            Pattern.compile("\\AFlight: ([A-Z0-9-]{1,20})\\z");

    private MapAccessibilityTranslation() {
    }

    static Object translate(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        Matcher matcher = FLIGHT.matcher((String) value);
        if (!matcher.matches()) {
            return value;
        }
        return "航班：" + matcher.group(1);
    }
}
