package io.github.fr24zh.localizer;

import android.text.SpannedString;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public final class DynamicLabelTranslationTest {
    @Test
    public void translatesOnlyTheFourAuditedPlainStringShapes() {
        assertEquals(
                "最近的 B-1971 航班",
                DynamicLabelTranslation.translateText("Recent B-1971 flights"));
        assertEquals(
                "航班：HBH8040",
                DynamicLabelTranslation.translateContentDescription("Flight: HBH8040"));
        assertEquals(
                "呼号：HBH8040",
                DynamicLabelTranslation.translateContentDescription("Call sign: HBH8040"));
        assertEquals(
                "机型：B738",
                DynamicLabelTranslation.translateContentDescription("Aircraft: B738"));
    }

    @Test
    public void preservesCapturedBusinessIdentifiersExactly() {
        assertEquals(
                "最近的 B-1A2B3 航班",
                DynamicLabelTranslation.translateText("Recent B-1A2B3 flights"));
        assertEquals(
                "航班：A1-B2",
                DynamicLabelTranslation.translateContentDescription("Flight: A1-B2"));
        assertEquals(
                "呼号：ABC123",
                DynamicLabelTranslation.translateContentDescription("Call sign: ABC123"));
        assertEquals(
                "机型：B77W",
                DynamicLabelTranslation.translateContentDescription("Aircraft: B77W"));
    }

    @Test
    public void keepsTextAndContentDescriptionBoundariesSeparate() {
        String contentShape = "Flight: HBH8040";
        String textShape = "Recent B-1971 flights";

        assertSame(contentShape, DynamicLabelTranslation.translateText(contentShape));
        assertSame(
                textShape,
                DynamicLabelTranslation.translateContentDescription(textShape));
    }

    @Test
    public void rejectsWhitespaceNewlinesUnicodeOverlengthAndExtraTextByIdentity() {
        String[] rejectedText = {
                "Recent  flights",
                "Recent B 1971 flights",
                "Recent B-1971\nflights",
                "Recent 中国 flights",
                "Recent ABCDEFGHIJKLMNOPQRSTU flights",
                "x Recent B-1971 flights",
                "Recent B-1971 flights x"
        };
        for (String value : rejectedText) {
            assertSame(value, DynamicLabelTranslation.translateText(value));
        }

        String[] rejectedDescriptions = {
                "Flight: ",
                "Flight: HBH 8040",
                "Flight: HBH8040\n",
                "Flight: 航班",
                "Flight: ABCDEFGHIJKLMNOPQRSTU",
                "x Flight: HBH8040",
                "Flight: HBH8040 x",
                "Unknown: HBH8040"
        };
        for (String value : rejectedDescriptions) {
            assertSame(value, DynamicLabelTranslation.translateContentDescription(value));
        }
    }

    @Test
    public void preservesNullAndEveryNonStringByIdentity() {
        StringBuilder mutableText = new StringBuilder("Recent B-1971 flights");
        SpannedString spannedText = new SpannedString("Recent B-1971 flights");
        StringBuilder mutableDescription = new StringBuilder("Flight: HBH8040");
        SpannedString spannedDescription = new SpannedString("Aircraft: B738");

        assertNull(DynamicLabelTranslation.translateText(null));
        assertSame(mutableText, DynamicLabelTranslation.translateText(mutableText));
        assertSame(spannedText, DynamicLabelTranslation.translateText(spannedText));
        assertNull(DynamicLabelTranslation.translateContentDescription(null));
        assertSame(
                mutableDescription,
                DynamicLabelTranslation.translateContentDescription(mutableDescription));
        assertSame(
                spannedDescription,
                DynamicLabelTranslation.translateContentDescription(spannedDescription));
    }

    @Test
    public void permanentlyExcludesMoreAndSpannedAircraftType() {
        String more = "More NS8040 information";
        SpannedString aircraftType = new SpannedString("AIRCRAFT TYPE  (B738)");

        assertSame(more, DynamicLabelTranslation.translateText(more));
        assertSame(aircraftType, DynamicLabelTranslation.translateText(aircraftType));
    }
}
