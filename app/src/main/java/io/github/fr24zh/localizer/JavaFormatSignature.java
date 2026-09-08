package io.github.fr24zh.localizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class JavaFormatSignature {
    private static final Pattern SPECIFIER = Pattern.compile(
            "%(?:(\\d+)\\$)?[-#+ 0,(]*(?:\\d+)?(?:\\.\\d+)?([tT])?([a-zA-Z%])");
    private JavaFormatSignature() {
    }

    static boolean hasArguments(String template) {
        if (template == null) {
            return false;
        }
        int position = 0;
        while (position < template.length()) {
            int percent = template.indexOf('%', position);
            if (percent < 0) {
                break;
            }
            Matcher matcher = SPECIFIER.matcher(template);
            matcher.region(percent, template.length());
            if (!matcher.lookingAt()) {
                return true;
            }
            String explicitIndex = matcher.group(1);
            String dateTimePrefix = matcher.group(2);
            char conversion = matcher.group(3).charAt(0);
            if (conversion == '%' || conversion == 'n') {
                if (explicitIndex != null || dateTimePrefix != null) {
                    return true;
                }
            } else {
                return true;
            }
            position = matcher.end();
        }
        return false;
    }
}
