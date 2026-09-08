package io.github.fr24zh.localizer;

final class AccessibilityNodeHookArguments {
    interface Translator {
        Object translate(Object value);
    }

    private AccessibilityNodeHookArguments() {
    }

    static void translateDescription(Object[] arguments, Translator translator) {
        if (arguments == null
                || arguments.length == 0
                || !(arguments[0] instanceof CharSequence)) {
            return;
        }
        Object original = arguments[0];
        try {
            Object translated = translator.translate(original);
            if (translated != original) {
                arguments[0] = translated;
            }
        } catch (Throwable ignored) {
            arguments[0] = original;
        }
    }
}
