package io.github.fr24zh.localizer;

import android.text.SpannedString;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public final class MapAccessibilityTranslationTest {
    @Test
    public void translatesOnlyAuditedFlightNodeDescriptionsAndPreservesIdentifiers() {
        assertEquals(
                "航班：HBH8040",
                MapAccessibilityTranslation.translate("Flight: HBH8040"));
        assertEquals(
                "航班：A1-B2",
                MapAccessibilityTranslation.translate("Flight: A1-B2"));
    }

    @Test
    public void rejectsEveryUnauditedStringShapeByIdentity() {
        String[] rejected = {
                "",
                "Flight:",
                "Flight: ",
                "Flight: HBH 8040",
                "Flight: HBH8040 ",
                " Flight: HBH8040",
                "Flight: HBH8040\n",
                "Flight:\nHBH8040",
                "Flight: hbh8040",
                "Flight: 航班",
                "Flight: ABCDEFGHIJKLMNOPQRSTU",
                "Flight: HBH8040 extra",
                "prefix Flight: HBH8040",
                "Flight: no call sign",
                "Call sign: HBH8040",
                "Aircraft: B738",
                "More HBH8040 information"
        };

        for (String value : rejected) {
            assertSame(value, MapAccessibilityTranslation.translate(value));
        }
    }

    @Test
    public void preservesNullAndEveryNonStringByIdentity() {
        StringBuilder mutable = new StringBuilder("Flight: HBH8040");
        SpannedString spanned = new SpannedString("Flight: HBH8040");
        CharSequence unknown = new CharSequence() {
            @Override
            public int length() {
                return 15;
            }

            @Override
            public char charAt(int index) {
                return "Flight: HBH8040".charAt(index);
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return "Flight: HBH8040".subSequence(start, end);
            }

            @Override
            public String toString() {
                return "Flight: HBH8040";
            }
        };

        assertNull(MapAccessibilityTranslation.translate(null));
        assertSame(mutable, MapAccessibilityTranslation.translate(mutable));
        assertSame(spanned, MapAccessibilityTranslation.translate(spanned));
        assertSame(unknown, MapAccessibilityTranslation.translate(unknown));
    }
}
