package io.github.fr24zh.localizer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class JavaFormatSignatureTest {
    @Test
    public void detectsImplicitAndExplicitFormatterArguments() {
        assertTrue(JavaFormatSignature.hasArguments("More %s information"));
        assertTrue(JavaFormatSignature.hasArguments("Count: %d"));
        assertTrue(JavaFormatSignature.hasArguments("%1$s / %2$d"));
    }

    @Test
    public void treatsLiteralPercentAndNewlineAsNonArgumentControls() {
        assertFalse(JavaFormatSignature.hasArguments("Load 100%% complete%n"));
        assertTrue(JavaFormatSignature.hasArguments("Load 100%%: %s%n"));
    }
}
