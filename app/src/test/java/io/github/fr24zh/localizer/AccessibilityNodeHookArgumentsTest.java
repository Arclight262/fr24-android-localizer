package io.github.fr24zh.localizer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public final class AccessibilityNodeHookArgumentsTest {
    @Test
    public void replacesOnlyTheFirstDescriptionArgument() {
        Object untouched = new Object();
        Object[] arguments = {"Flight: HBH8040", untouched};

        AccessibilityNodeHookArguments.translateDescription(
                arguments,
                new AccessibilityNodeHookArguments.Translator() {
                    @Override
                    public Object translate(Object value) {
                        return "航班：HBH8040";
                    }
                });

        assertEquals("航班：HBH8040", arguments[0]);
        assertSame(untouched, arguments[1]);
    }

    @Test
    public void preservesMissingAndNonCharSequenceArguments() {
        Object nonText = new Object();
        Object[] nonTextArguments = {nonText};

        AccessibilityNodeHookArguments.translateDescription(null, rejectingTranslator());
        AccessibilityNodeHookArguments.translateDescription(new Object[0], rejectingTranslator());
        AccessibilityNodeHookArguments.translateDescription(nonTextArguments, rejectingTranslator());

        assertSame(nonText, nonTextArguments[0]);
    }

    @Test
    public void preservesAllArgumentsWhenTranslatorThrows() {
        String original = "Flight: HBH8040";
        Object trailing = new Object();
        Object[] arguments = {original, trailing};
        Object[] snapshot = arguments.clone();

        AccessibilityNodeHookArguments.translateDescription(
                arguments,
                new AccessibilityNodeHookArguments.Translator() {
                    @Override
                    public Object translate(Object value) {
                        throw new IllegalStateException("synthetic translator failure");
                    }
                });

        assertArrayEquals(snapshot, arguments);
        assertSame(original, arguments[0]);
        assertSame(trailing, arguments[1]);
    }

    private static AccessibilityNodeHookArguments.Translator rejectingTranslator() {
        return new AccessibilityNodeHookArguments.Translator() {
            @Override
            public Object translate(Object value) {
                throw new AssertionError("translator must not be called");
            }
        };
    }
}
