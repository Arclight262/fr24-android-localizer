package io.github.fr24zh.localizer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class HookArgumentsTest {
    @Test
    public void extractsResourceIdAndFormatArguments() {
        Object[] formatArguments = new Object[]{"NS8035"};
        Object[] hookArguments = new Object[]{0x7f1401fe, formatArguments};
        assertEquals(Integer.valueOf(0x7f1401fe), HookArguments.resourceId(hookArguments));
        assertArrayEquals(formatArguments, HookArguments.formatArguments(hookArguments));
    }

    @Test
    public void rejectsMissingOrWrongArgumentTypes() {
        assertNull(HookArguments.resourceId(null));
        assertNull(HookArguments.resourceId(new Object[]{"not-an-id"}));
        assertNull(HookArguments.formatArguments(new Object[]{123}));
        assertNull(HookArguments.formatArguments(new Object[]{123, "not-an-array"}));
    }

    @Test
    public void distinguishesExplicitFormatCallsIncludingEmptyVarargs() {
        assertTrue(HookArguments.hasFormatArguments(
                new Object[]{123, new Object[]{"NS8035"}}));
        assertTrue(HookArguments.hasFormatArguments(
                new Object[]{123, new Object[]{}}));
        assertFalse(HookArguments.hasFormatArguments(new Object[]{123}));
        assertFalse(HookArguments.hasFormatArguments(
                new Object[]{123, "default text"}));
    }

    @Test
    public void extractsOnlyNonNegativeTypedArrayIndexes() {
        assertEquals(Integer.valueOf(0), HookArguments.typedArrayIndex(new Object[]{0}));
        assertEquals(Integer.valueOf(7), HookArguments.typedArrayIndex(new Object[]{7}));
        assertNull(HookArguments.typedArrayIndex(null));
        assertNull(HookArguments.typedArrayIndex(new Object[]{}));
        assertNull(HookArguments.typedArrayIndex(new Object[]{-1}));
        assertNull(HookArguments.typedArrayIndex(new Object[]{"0"}));
    }

    @Test
    public void extractsOnlyStrictQuantityStringArguments() {
        Object[] formatArguments = new Object[]{10};
        Object[] hookArguments = new Object[]{0x7f1401fe, 10, formatArguments};

        assertEquals(
                Integer.valueOf(10),
                HookArguments.quantityStringQuantity(hookArguments));
        assertArrayEquals(
                formatArguments,
                HookArguments.quantityStringFormatArguments(hookArguments));
    }

    @Test
    public void rejectsNonStrictQuantityStringArgumentShapes() {
        Object[] rawTwoArgumentShape = new Object[]{0x7f1401fe, 10};
        Object[] negativeQuantity = new Object[]{0x7f1401fe, -1, new Object[]{-1}};
        Object[] nonIntegerQuantity = new Object[]{0x7f1401fe, Long.valueOf(10L), new Object[]{10}};
        Object[] nonArrayVarargs = new Object[]{0x7f1401fe, 10, "10"};
        Object[] extraArgument = new Object[]{0x7f1401fe, 10, new Object[]{10}, "extra"};

        assertNull(HookArguments.quantityStringQuantity(rawTwoArgumentShape));
        assertNull(HookArguments.quantityStringFormatArguments(rawTwoArgumentShape));
        assertNull(HookArguments.quantityStringQuantity(negativeQuantity));
        assertNull(HookArguments.quantityStringFormatArguments(negativeQuantity));
        assertNull(HookArguments.quantityStringQuantity(nonIntegerQuantity));
        assertNull(HookArguments.quantityStringFormatArguments(nonIntegerQuantity));
        assertNull(HookArguments.quantityStringQuantity(nonArrayVarargs));
        assertNull(HookArguments.quantityStringFormatArguments(nonArrayVarargs));
        assertNull(HookArguments.quantityStringQuantity(extraArgument));
        assertNull(HookArguments.quantityStringFormatArguments(extraArgument));
    }
}
