package io.github.fr24zh.localizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class DynamicLabelTranslation {
    private static final String IDENTIFIER = "([A-Z0-9-]{1,20})";
    private static final Pattern RECENT_FLIGHTS =
            Pattern.compile("\\ARecent " + IDENTIFIER + " flights\\z");
    private static final Pattern FLIGHT =
            Pattern.compile("\\AFlight: " + IDENTIFIER + "\\z");
    private static final Pattern CALL_SIGN =
            Pattern.compile("\\ACall sign: " + IDENTIFIER + "\\z");
    private static final Pattern AIRCRAFT =
            Pattern.compile("\\AAircraft: " + IDENTIFIER + "\\z");

    private DynamicLabelTranslation() {
    }

    static Object translateText(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        Matcher matcher = RECENT_FLIGHTS.matcher((String) value);
        if (!matcher.matches()) {
            return value;
        }
        return "最近的 " + matcher.group(1) + " 航班";
    }

    static Object translateContentDescription(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        String source = (String) value;
        Object translated = translate(FLIGHT, "航班：", source);
        if (translated != source) {
            return translated;
        }
        translated = translate(CALL_SIGN, "呼号：", source);
        if (translated != source) {
            return translated;
        }
        return translate(AIRCRAFT, "机型：", source);
    }

    private static Object translate(Pattern pattern, String prefix, String source) {
        Matcher matcher = pattern.matcher(source);
        if (!matcher.matches()) {
            return source;
        }
        return prefix + matcher.group(1);
    }
}
