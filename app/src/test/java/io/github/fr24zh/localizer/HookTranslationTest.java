package io.github.fr24zh.localizer;

import android.text.Spanned;
import android.text.SpannedString;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class HookTranslationTest {
    @Test
    public void selectedContextStackIsNeededOnlyForExactCandidateCall() {
        assertTrue(HookTranslation.needsSelectedContextStack(
                "selected", "%d selected", new Object[]{0x7f140321}));
        assertFalse(HookTranslation.needsSelectedContextStack(
                "other", "%d selected", new Object[]{0x7f140321}));
        assertFalse(HookTranslation.needsSelectedContextStack(
                "selected", "Selected", new Object[]{0x7f140321}));
        assertFalse(HookTranslation.needsSelectedContextStack(
                "selected", "%d selected", new Object[]{-1}));
        assertFalse(HookTranslation.needsSelectedContextStack(
                "selected", "%d selected", new Object[]{0x7f140321, 2}));
    }

    @Test
    public void languageSuffixIsRemovedOnlyFromItsExactSettingsLabel() {
        assertEquals("语言", HookTranslation.translateTextArgument(
                "txtLanguage", "语言 - Language"));
        String source = "语言 - Language";
        assertSame(source, HookTranslation.translateTextArgument("txtOther", source));
        assertSame(source, HookTranslation.translateTextArgument(null, source));
        String different = "Deutsch - Language";
        assertSame(different, HookTranslation.translateTextArgument("txtLanguage", different));
        String longer = "语言 - Language ";
        assertSame(longer, HookTranslation.translateTextArgument("txtLanguage", longer));
        String translated = "语言";
        assertSame(translated, HookTranslation.translateTextArgument("txtLanguage", translated));
        StringBuilder mutable = new StringBuilder(source);
        assertSame(mutable, HookTranslation.translateTextArgument("txtLanguage", mutable));
    }

    @Test
    public void squawkResourceFormattingPreservesTheCodeAndRejectsInvalidArguments() {
        assertEquals("应答机代码：7700", HookTranslation.translateResourceCall(
                "accessibility_squawk", "Squawk: 7700",
                new Object[]{0x7f140001, new Object[]{"7700"}}));
        String source = "Squawk: 7700";
        for (Object[] arguments : new Object[][]{
                {}, {null}, {7700}, {"7700", "7600"}}) {
            assertSame(source, HookTranslation.translateResourceCall(
                    "accessibility_squawk", source, new Object[]{0x7f140001, arguments}));
        }
        String raw = "Squawk: %s";
        assertSame(raw, HookTranslation.translateResourceCall(
                "accessibility_squawk", raw, new Object[]{0x7f140001}));
    }

    @Test
    public void translatesSearchResultCountsOnlyForTitleRight() {
        assertEquals("1/1 个机场", HookTranslation.translateTextArgument(
                "txtTitleRight", "1 of 1 airports"));
        assertEquals("1/1 个机场", HookTranslation.translateTextArgument(
                "txtTitleRight", "1 of 1 airport"));
        assertEquals("1/1 家航空公司", HookTranslation.translateTextArgument(
                "txtTitleRight", "1 of 1 airlines"));
        assertEquals("1/1 家航空公司", HookTranslation.translateTextArgument(
                "txtTitleRight", "1 of 1 airline"));
        assertEquals("2/2 个航班", HookTranslation.translateTextArgument(
                "txtTitleRight", "2 of 2 flights"));
        assertEquals("2/2 个航班", HookTranslation.translateTextArgument(
                "txtTitleRight", "2 of 2 flight"));
        assertEquals("21/52 个航班", HookTranslation.translateTextArgument(
                "txtTitleRight", "21 of 52 flights"));
        assertEquals("000000001/000000002 个航班", HookTranslation.translateTextArgument(
                "txtTitleRight", "000000001 of 000000002 flights"));
    }

    @Test
    public void searchResultCountsFailOpenOutsideExactShape() {
        String source = "1 of 1 airports";
        String aircraft = "1 of 1 aircraft";
        String upperCase = "1 OF 1 airports";
        String leadingSpace = " 1 of 1 airports";
        String trailingSpace = "1 of 1 airports ";
        String newline = "1 of 1 airports\n";
        String prefix = "Result: 1 of 1 airports";
        String suffix = "1 of 1 airports total";
        String missingField = "1 of airports";
        String punctuation = "1 of 1 airports,";
        String longFirst = "1234567890 of 1 airports";
        String longSecond = "1 of 1234567890 airports";
        StringBuilder mutable = new StringBuilder(source);
        SpannedString spanned = new SpannedString(source);

        assertSame(source, HookTranslation.translateTextArgument("txtTitle", source));
        assertSame(source, HookTranslation.translateTextArgument(null, source));
        assertNull(HookTranslation.translateTextArgument("txtTitleRight", null));
        Integer nonString = Integer.valueOf(7);
        assertSame(nonString, HookTranslation.translateTextArgument("txtTitleRight", nonString));
        assertSame(aircraft, HookTranslation.translateTextArgument("txtTitleRight", aircraft));
        assertSame(upperCase, HookTranslation.translateTextArgument("txtTitleRight", upperCase));
        assertSame(leadingSpace, HookTranslation.translateTextArgument("txtTitleRight", leadingSpace));
        assertSame(trailingSpace, HookTranslation.translateTextArgument("txtTitleRight", trailingSpace));
        assertSame(newline, HookTranslation.translateTextArgument("txtTitleRight", newline));
        assertSame(prefix, HookTranslation.translateTextArgument("txtTitleRight", prefix));
        assertSame(suffix, HookTranslation.translateTextArgument("txtTitleRight", suffix));
        assertSame(missingField, HookTranslation.translateTextArgument("txtTitleRight", missingField));
        assertSame(punctuation, HookTranslation.translateTextArgument("txtTitleRight", punctuation));
        assertSame(longFirst, HookTranslation.translateTextArgument("txtTitleRight", longFirst));
        assertSame(longSecond, HookTranslation.translateTextArgument("txtTitleRight", longSecond));
        assertSame(mutable, HookTranslation.translateTextArgument("txtTitleRight", mutable));
        assertSame(spanned, HookTranslation.translateTextArgument("txtTitleRight", spanned));
        assertEquals("1/1 个机场", HookTranslation.translateTextArgument("txtTitleRight", source));
    }

    @Test
    public void translatesKnownPlainStringTextAssignments() {
        assertEquals("设置", HookTranslation.translateTextArgument("Settings"));
    }

    @Test
    public void translatesKnownPlainStringPreTransformLabels() {
        assertEquals(
                "气压高度",
                HookTranslation.translateTextArgument("Barometric altitude"));
        assertEquals("注册号", HookTranslation.translateTextArgument("Reg"));
        assertEquals("气压高度", HookTranslation.translateTextArgument("Barometric alt."));
    }

    @Test
    public void textArgumentPreservesNonStringPreTransformLabelByIdentity() {
        StringBuilder mutable = new StringBuilder("Barometric alt.");
        SpannedString spanned = new SpannedString("Barometric alt.");

        assertSame(mutable, HookTranslation.translateTextArgument(mutable));
        assertSame(spanned, HookTranslation.translateTextArgument(spanned));
    }

    @Test
    public void textArgumentPreservesNonStringCharSequencesByIdentity() {
        StringBuilder mutable = new StringBuilder("Settings");
        SpannedString spanned = new SpannedString("Settings");
        CharSequence custom = new CharSequence() {
            @Override
            public int length() {
                return 8;
            }

            @Override
            public char charAt(int index) {
                return "Settings".charAt(index);
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return "Settings".subSequence(start, end);
            }

            @Override
            public String toString() {
                return "Settings";
            }
        };

        assertSame(mutable, HookTranslation.translateTextArgument(mutable));
        assertSame(spanned, HookTranslation.translateTextArgument(spanned));
        assertSame(custom, HookTranslation.translateTextArgument(custom));
    }

    @Test
    public void textArgumentPreservesNullAndUnknownStringsByIdentity() {
        String unknown = "Unknown";

        assertNull(HookTranslation.translateTextArgument(null));
        assertSame(unknown, HookTranslation.translateTextArgument(unknown));
    }

    @Test
    public void textAndContentDescriptionArgumentsUseTheirAuditedDynamicBoundaries() {
        String contentShape = "Flight: HBH8040";
        String textShape = "Recent B-1971 flights";

        assertEquals(
                "最近的 B-1971 航班",
                HookTranslation.translateTextArgument(textShape));
        assertSame(contentShape, HookTranslation.translateTextArgument(contentShape));
        assertEquals(
                "航班：HBH8040",
                HookTranslation.translateContentDescriptionArgument(contentShape));
        assertSame(
                textShape,
                HookTranslation.translateContentDescriptionArgument(textShape));
    }

    @Test
    public void flightDetailFormattedTitlesTranslateOnlyWithTheirStrictTextViewContext() {
        assertEquals(
                "更多 CSN5939 信息",
                HookTranslation.translateTextArgument(
                        "txtMoreInfoHeaderTitle", "More CSN5939 information"));
        assertEquals(
                "机型（A320）",
                HookTranslation.translateTextArgument(
                        "txtAircraftType", "AIRCRAFT TYPE  (A320)"));
        assertEquals(
                "数据来源 — ADS-B",
                HookTranslation.translateTextArgument(
                        "txtDataSourceHeader", "Data source — ADS-B"));
        Spanned aircraftType = spanned("AIRCRAFT TYPE  (A320)");
        assertEquals(
                "机型（A320）",
                HookTranslation.translateTextArgument("txtAircraftType", aircraftType));
    }

    @Test
    public void flightDetailContextTranslationFailsOpenForEveryNearMatchAndOtherTextTypes() {
        String more = "More CSN5939 information";
        String aircraft = "AIRCRAFT TYPE  (A320)";
        String dataSource = "Data source — ADS-B";
        String longIdentifier = "ABCDEFGHIJKLMNOPQRSTU";
        String longDataSource = new String(new char[81]).replace('\0', 'A');
        String badMoreWithSpace = "More CSN 5939 information";
        String badMoreUnicode = "More 航班 information";
        String badMoreLong = "More " + longIdentifier + " information";
        String badMoreNewline = "More CSN5939 information\n";
        String badAircraftSpacing = "AIRCRAFT TYPE (A320)";
        String badAircraftSpace = "AIRCRAFT TYPE  (A 320)";
        String badAircraftUnicode = "AIRCRAFT TYPE  (空客A320)";
        String badAircraftLong = "AIRCRAFT TYPE  (" + longIdentifier + ")";
        String emptyDataSource = "Data source — ";
        String multilineDataSource = "Data source — ADS-B\nextra";
        String longDataSourceValue = "Data source — " + longDataSource;
        StringBuilder mutableAircraft = new StringBuilder(aircraft);
        Spanned spannedMore = spanned(more);
        Spanned spannedDataSource = spanned(dataSource);

        assertSame(more, HookTranslation.translateTextArgument(null, more));
        assertSame(more, HookTranslation.translateTextArgument("txtmoreinfoheadertitle", more));
        assertSame(badMoreWithSpace, HookTranslation.translateTextArgument(
                "txtMoreInfoHeaderTitle", badMoreWithSpace));
        assertSame(badMoreUnicode, HookTranslation.translateTextArgument(
                "txtMoreInfoHeaderTitle", badMoreUnicode));
        assertSame(badMoreLong, HookTranslation.translateTextArgument(
                "txtMoreInfoHeaderTitle", badMoreLong));
        assertSame(badMoreNewline, HookTranslation.translateTextArgument(
                "txtMoreInfoHeaderTitle", badMoreNewline));
        assertSame(spannedMore, HookTranslation.translateTextArgument(
                "txtMoreInfoHeaderTitle", spannedMore));

        assertSame(aircraft, HookTranslation.translateTextArgument(null, aircraft));
        assertSame(badAircraftSpacing, HookTranslation.translateTextArgument(
                "txtAircraftType", badAircraftSpacing));
        assertSame(badAircraftSpace, HookTranslation.translateTextArgument(
                "txtAircraftType", badAircraftSpace));
        assertSame(badAircraftUnicode, HookTranslation.translateTextArgument(
                "txtAircraftType", badAircraftUnicode));
        assertSame(badAircraftLong, HookTranslation.translateTextArgument(
                "txtAircraftType", badAircraftLong));
        assertSame(mutableAircraft, HookTranslation.translateTextArgument(
                "txtAircraftType", mutableAircraft));

        assertSame(dataSource, HookTranslation.translateTextArgument(null, dataSource));
        assertSame(emptyDataSource, HookTranslation.translateTextArgument(
                "txtDataSourceHeader", emptyDataSource));
        assertSame(multilineDataSource, HookTranslation.translateTextArgument(
                "txtDataSourceHeader", multilineDataSource));
        assertSame(longDataSourceValue, HookTranslation.translateTextArgument(
                "txtDataSourceHeader", longDataSourceValue));
        assertSame(spannedDataSource, HookTranslation.translateTextArgument(
                "txtDataSourceHeader", spannedDataSource));
        assertSame(dataSource, HookTranslation.translateTextArgument(
                "txtAircraftType", dataSource));
        assertSame(dataSource, HookTranslation.translateTextArgument(dataSource));
    }

    @Test
    public void flightDetailTopAndHistoryTextTranslateOnlyWithExactTextViewContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("flightFromTo", "3U8732 航班：CAN → CTU");
        expected.put("flightHistory", "更多 3U8732 航班");
        expected.put("year", "2026 年");
        expected.put("date", "8月24日");
        expected.put("distanceLeft", "633 NM，01:35 前");
        expected.put("distanceRight", "50 NM，00:13 后");
        expected.put("arriving", "将在 00:13 后到达");
        expected.put("departed", "已于 01:35 前起飞");
        Map<String, String> actual = new LinkedHashMap<>();
        actual.put("flightFromTo", (String) HookTranslation.translateTextArgument(
                "txtFlightFromTo", "3U8732 FLIGHT FROM CAN TO CTU"));
        actual.put("flightHistory", (String) HookTranslation.translateTextArgument(
                "txtFlightHistoryMore", "More 3U8732 flights"));
        actual.put("year", (String) HookTranslation.translateTextArgument("txtTitle", "YEAR 2026"));
        actual.put("date", (String) HookTranslation.translateTextArgument("txtdepatureDate", "Aug 24"));
        actual.put("distanceLeft", (String) HookTranslation.translateTextArgument(
                "txtDistanceLeft", "633 NM, 01:35 ago"));
        actual.put("distanceRight", (String) HookTranslation.translateTextArgument(
                "txtDistanceRight", "50 NM, in 00:13"));
        actual.put("arriving", (String) HookTranslation.translateTextArgument(
                "txtTimeArriving", "Arriving in 00:13"));
        actual.put("departed", (String) HookTranslation.translateTextArgument(
                "txtTimeDeparted", "Departed 01:35 ago"));
        assertEquals(expected, actual);
    }

    @Test
    public void flightDetailValidSamplesFailOpenForMissingEmptyAndWrongViewIds() {
        String[][] samples = {
                {"txtMoreInfoHeaderTitle", "More CSN5939 information"},
                {"txtAircraftType", "AIRCRAFT TYPE  (A320)"},
                {"txtDataSourceHeader", "Data source — ADS-B"},
                {"txtFlightFromTo", "3U8732 FLIGHT FROM CAN TO CTU"},
                {"txtFlightHistoryMore", "More 3U8732 flights"},
                {"txtTitle", "YEAR 2026"},
                {"txtdepatureDate", "Aug 24"},
                {"txtDistanceLeft", "633 NM, 01:35 ago"},
                {"txtDistanceRight", "50 NM, in 00:13"},
                {"txtTimeArriving", "Arriving in 00:13"},
                {"txtTimeDeparted", "Departed 01:35 ago"}
        };
        String[] invalidViewIds = {null, "", "wrongViewId"};

        for (String[] sample : samples) {
            for (String invalidViewId : invalidViewIds) {
                String original = new String(sample[1]);
                assertSame(
                        original,
                        HookTranslation.translateTextArgument(invalidViewId, original));
            }
        }
    }

    @Test
    public void flightDetailDepartureDatesTranslateAllMonthsAtTheirBoundaries() {
        String[][] dates = {
                {"Jan 1", "1月1日"},
                {"Feb 1", "2月1日"},
                {"Mar 1", "3月1日"},
                {"Apr 1", "4月1日"},
                {"May 1", "5月1日"},
                {"Jun 1", "6月1日"},
                {"Jul 1", "7月1日"},
                {"Aug 1", "8月1日"},
                {"Sep 1", "9月1日"},
                {"Oct 1", "10月1日"},
                {"Nov 1", "11月1日"},
                {"Dec 31", "12月31日"}
        };

        for (String[] date : dates) {
            assertEquals(
                    date[1],
                    HookTranslation.translateTextArgument("txtdepatureDate", date[0]));
        }
    }

    @Test
    public void flightDetailDistancesAndTimesPreserveBoundaryCapturesVerbatim() {
        String[][] cases = {
                {"txtDistanceLeft", "1,234 km, 00:00 ago", "1,234 km，00:00 前"},
                {"txtDistanceRight", "9,876 mi, in 59:59", "9,876 mi，59:59 后"},
                {"txtTimeArriving", "Arriving in 00:00", "将在 00:00 后到达"},
                {"txtTimeDeparted", "Departed 59:59 ago", "已于 59:59 前起飞"}
        };

        for (String[] translation : cases) {
            assertEquals(
                    translation[2],
                    HookTranslation.translateTextArgument(translation[0], translation[1]));
        }
    }

    @Test
    public void flightDetailContextRulesPreserveNonStringValuesByIdentity() {
        StringBuilder mutableDistance = new StringBuilder("1,234 km, 00:00 ago");
        Spanned date = spanned("Dec 31");

        assertSame(
                mutableDistance,
                HookTranslation.translateTextArgument("txtDistanceLeft", mutableDistance));
        assertSame(date, HookTranslation.translateTextArgument("txtdepatureDate", date));
    }

    @Test
    public void flightDetailTopAndHistoryTextFailsOpenForNearMatchesAndNonStrings() {
        String flightFromTo = "3U8732 FLIGHT FROM CAN TO CTU";
        String flightHistory = "More 3U8732 flights";
        String year = "YEAR 2026";
        String date = "Aug 24";
        String distanceLeft = "633 NM, 01:35 ago";
        String distanceRight = "50 NM, in 00:13";
        String arriving = "Arriving in 00:13";
        String departed = "Departed 01:35 ago";
        StringBuilder builder = new StringBuilder(flightFromTo);
        CharSequence custom = new CharSequence() {
            @Override
            public int length() {
                return flightHistory.length();
            }

            @Override
            public char charAt(int index) {
                return flightHistory.charAt(index);
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return flightHistory.subSequence(start, end);
            }

            @Override
            public String toString() {
                return flightHistory;
            }
        };
        Spanned spanned = spanned(date);

        assertNull(HookTranslation.translateTextArgument("txtFlightFromTo", null));
        assertSame("", HookTranslation.translateTextArgument("txtFlightFromTo", ""));
        assertSame(flightFromTo, HookTranslation.translateTextArgument("txtflightfromto", flightFromTo));
        assertSame("3U8732 FLIGHT FROM CAN TO CTU extra", HookTranslation.translateTextArgument(
                "txtFlightFromTo", "3U8732 FLIGHT FROM CAN TO CTU extra"));
        assertSame("3U8732 FLIGHT FROM CAN TO CTU\n", HookTranslation.translateTextArgument(
                "txtFlightFromTo", "3U8732 FLIGHT FROM CAN TO CTU\n"));
        assertSame("3U 8732 FLIGHT FROM CAN TO CTU", HookTranslation.translateTextArgument(
                "txtFlightFromTo", "3U 8732 FLIGHT FROM CAN TO CTU"));
        assertSame("航班 FLIGHT FROM CAN TO CTU", HookTranslation.translateTextArgument(
                "txtFlightFromTo", "航班 FLIGHT FROM CAN TO CTU"));
        assertSame("ABCDEFGHIJKLMNOPQRSTU FLIGHT FROM CAN TO CTU",
                HookTranslation.translateTextArgument(
                        "txtFlightFromTo", "ABCDEFGHIJKLMNOPQRSTU FLIGHT FROM CAN TO CTU"));
        assertSame("3U8732 FLIGHT FROM C TO CTU", HookTranslation.translateTextArgument(
                "txtFlightFromTo", "3U8732 FLIGHT FROM C TO CTU"));
        assertSame("3U8732 FLIGHT FROM CAN TO CTU!", HookTranslation.translateTextArgument(
                "txtFlightFromTo", "3U8732 FLIGHT FROM CAN TO CTU!"));

        assertSame(flightHistory, HookTranslation.translateTextArgument("txtFlightHistorymore", flightHistory));
        assertSame("More 3U8732 flight", HookTranslation.translateTextArgument(
                "txtFlightHistoryMore", "More 3U8732 flight"));
        assertSame("More 3U 8732 flights", HookTranslation.translateTextArgument(
                "txtFlightHistoryMore", "More 3U 8732 flights"));
        assertSame("More 航班 flights", HookTranslation.translateTextArgument(
                "txtFlightHistoryMore", "More 航班 flights"));
        assertSame("More ABCDEFGHIJKLMNOPQRSTU flights", HookTranslation.translateTextArgument(
                "txtFlightHistoryMore", "More ABCDEFGHIJKLMNOPQRSTU flights"));

        assertSame("YEAR 26", HookTranslation.translateTextArgument("txtTitle", "YEAR 26"));
        assertSame("Year 2026", HookTranslation.translateTextArgument("txtTitle", "Year 2026"));
        assertSame("YEAR 2026 extra", HookTranslation.translateTextArgument(
                "txtTitle", "YEAR 2026 extra"));
        assertSame(year, HookTranslation.translateTextArgument("txtOtherTitle", year));

        assertSame("Foo 24", HookTranslation.translateTextArgument("txtdepatureDate", "Foo 24"));
        assertSame("Aug 0", HookTranslation.translateTextArgument("txtdepatureDate", "Aug 0"));
        assertSame("Aug 32", HookTranslation.translateTextArgument("txtdepatureDate", "Aug 32"));
        assertSame("aug 24", HookTranslation.translateTextArgument("txtdepatureDate", "aug 24"));

        assertSame("633 nmi, 01:35 ago", HookTranslation.translateTextArgument(
                "txtDistanceLeft", "633 nmi, 01:35 ago"));
        assertSame("633 NM, 01:60 ago", HookTranslation.translateTextArgument(
                "txtDistanceLeft", "633 NM, 01:60 ago"));
        assertSame("633 NM, 60:35 ago", HookTranslation.translateTextArgument(
                "txtDistanceLeft", "633 NM, 60:35 ago"));
        assertSame("01,23 NM, 01:35 ago", HookTranslation.translateTextArgument(
                "txtDistanceLeft", "01,23 NM, 01:35 ago"));
        assertSame("1,2345 NM, 01:35 ago", HookTranslation.translateTextArgument(
                "txtDistanceLeft", "1,2345 NM, 01:35 ago"));
        assertSame("633 NM, in 01:35", HookTranslation.translateTextArgument(
                "txtDistanceLeft", "633 NM, in 01:35"));

        assertSame("50 miles, in 00:13", HookTranslation.translateTextArgument(
                "txtDistanceRight", "50 miles, in 00:13"));
        assertSame("50 NM, in 00:60", HookTranslation.translateTextArgument(
                "txtDistanceRight", "50 NM, in 00:60"));
        assertSame("50 NM, in 00:13 extra", HookTranslation.translateTextArgument(
                "txtDistanceRight", "50 NM, in 00:13 extra"));

        assertSame("Arriving in 00:60", HookTranslation.translateTextArgument(
                "txtTimeArriving", "Arriving in 00:60"));
        assertSame("arriving in 00:13", HookTranslation.translateTextArgument(
                "txtTimeArriving", "arriving in 00:13"));
        assertSame("Departed 01:60 ago", HookTranslation.translateTextArgument(
                "txtTimeDeparted", "Departed 01:60 ago"));
        assertSame("Departed 01:35 ago\n", HookTranslation.translateTextArgument(
                "txtTimeDeparted", "Departed 01:35 ago\n"));

        assertSame(builder, HookTranslation.translateTextArgument("txtFlightFromTo", builder));
        assertSame(custom, HookTranslation.translateTextArgument("txtFlightHistoryMore", custom));
        assertSame(spanned, HookTranslation.translateTextArgument("txtdepatureDate", spanned));
        assertSame(distanceLeft, HookTranslation.translateTextArgument("txtDistanceRight", distanceLeft));
        assertSame(distanceRight, HookTranslation.translateTextArgument("txtDistanceLeft", distanceRight));
        assertSame(arriving, HookTranslation.translateTextArgument("txtTimeDeparted", arriving));
        assertSame(departed, HookTranslation.translateTextArgument("txtTimeArriving", departed));
    }

    @Test
    public void resourceNameTranslationPreservesFormatArguments() {
        assertEquals(
                "\u66f4\u591a NS8035 \u4fe1\u606f",
                HookTranslation.translateResourceResult(
                        "cab_more_info",
                        "More NS8035 information",
                        new Object[]{"NS8035"}));
    }

    @Test
    public void unknownResourcePreservesOriginalEvenWhenExactTextIsKnown() {
        String original = "Search";
        assertSame(
                original,
                HookTranslation.translateResourceResult(
                        "unknown_resource",
                        original,
                        null));
    }

    @Test
    public void invalidResourceFormatPreservesOriginal() {
        String original = "Search";
        assertSame(
                original,
                HookTranslation.translateResourceResult(
                        "cab_diverting_to",
                        original,
                        new Object[]{"CKG"}));
    }

    @Test
    public void dynamicResourceWithNullFormatArgumentsPreservesOriginal() {
        String original = "Search";
        assertSame(
                original,
                HookTranslation.translateResourceResult("cab_more_info", original, null));
    }

    @Test
    public void dynamicResourceWithEmptyFormatArgumentsPreservesOriginal() {
        String original = "Search";
        assertSame(
                original,
                HookTranslation.translateResourceResult(
                        "cab_more_info",
                        original,
                        new Object[]{}));
    }

    @Test
    public void resourceTranslationPreservesNonStringByIdentity() {
        StringBuilder styledText = new StringBuilder("More NS8035 information");
        assertSame(
                styledText,
                HookTranslation.translateResourceResult(
                        "cab_more_info",
                        styledText,
                        new Object[]{"NS8035"}));
    }

    @Test
    public void rawResourceTemplateAllowsOnlyFourExactAirportCabAndSearchTemplates() {
        String[][] cases = {
                {"cab_operated_by", "Operated by %s", "由 %s 执飞", "Operated by Air China"},
                {"search_airline_msg", "Only %s flights currently in Flightradar24 coverage are listed.", "仅列出当前在 Flightradar24 覆盖范围内的 %s 航班。", "Only 12 flights currently in Flightradar24 coverage are listed."},
                {"search_found_aircraft", "%d of %d aircraft", "%1$d/%2$d 架航空器", "5 of 12 aircraft"},
                {"search_nearby_away", "%s away", "距此 %s", "3 km away"}
        };
        for (String[] testCase : cases) {
            assertEquals(testCase[2], HookTranslation.translateRawResourceResult(testCase[0], testCase[1]));
            String wrongSource = testCase[1] + "!";
            StringBuilder mutable = new StringBuilder(testCase[1]);
            SpannedString spanned = new SpannedString(testCase[1]);
            assertSame(wrongSource, HookTranslation.translateRawResourceResult(testCase[0], wrongSource));
            assertSame(testCase[3], HookTranslation.translateRawResourceResult(testCase[0], testCase[3]));
            assertSame(testCase[1], HookTranslation.translateRawResourceResult(testCase[0] + "_other", testCase[1]));
            assertSame(mutable, HookTranslation.translateRawResourceResult(testCase[0], mutable));
            assertSame(spanned, HookTranslation.translateRawResourceResult(testCase[0], spanned));
        }
        assertEquals("由 Air China 执飞", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) HookTranslation.translateRawResourceResult("cab_operated_by", "Operated by %s"), "Air China"));
        assertEquals("仅列出当前在 Flightradar24 覆盖范围内的 12 航班。", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) HookTranslation.translateRawResourceResult("search_airline_msg", "Only %s flights currently in Flightradar24 coverage are listed."), 12));
        assertEquals("5/12 架航空器", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) HookTranslation.translateRawResourceResult("search_found_aircraft", "%d of %d aircraft"), 5, 12));
        assertEquals("距此 3 km", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) HookTranslation.translateRawResourceResult("search_nearby_away", "%s away"), "3 km"));
        assertSame(12, HookTranslation.translateRawResourceResult("search_airline_msg", 12));
        assertSame("%d of %d aircraft", HookTranslation.translateRawResourceResult("search_airline_msg", "%d of %d aircraft"));
        assertSame("%s Away", HookTranslation.translateRawResourceResult("search_nearby_away", "%s Away"));
    }

    @Test
    public void rawResourceTemplateAllowsOnlyExactEtaAndShareTemplates() {
        String[][] cases = {
                {"eta_ago", "%s ago", "%s 前", "5 min ago", "5 min", "%s AGO"},
                {"eta_in", "in %s", "%s 后", "in 5 min", "5 min", "IN %s"},
                {"share_text",
                        "Check out Flightradar24, the app that turns your phone into an air traffic radar. %s",
                        "试试 Flightradar24，这款应用能把手机变成空中交通雷达：%s",
                        "Check out Flightradar24, the app that turns your phone into an air traffic radar. https://www.flightradar24.com",
                        "https://www.flightradar24.com",
                        "check out Flightradar24, the app that turns your phone into an air traffic radar. %s"}
        };
        for (String[] testCase : cases) {
            String source = testCase[1];
            assertEquals(testCase[2], HookTranslation.translateRawResourceResult(testCase[0], source));
            assertEquals(testCase[2].replace("%s", testCase[4]), String.format(Locale.US,
                    (String) HookTranslation.translateRawResourceResult(testCase[0], source), testCase[4]));

            String wrongKey = source;
            String wrongCase = testCase[5];
            String wrongPunctuation = source + "!";
            String missingPlaceholder = source.replace("%s", "");
            String extraPlaceholder = source + " %s";
            StringBuilder mutable = new StringBuilder(source);
            SpannedString spanned = new SpannedString(source);
            assertSame(wrongKey, HookTranslation.translateRawResourceResult(testCase[0] + "_other", wrongKey));
            assertSame(wrongCase, HookTranslation.translateRawResourceResult(testCase[0], wrongCase));
            assertSame(wrongPunctuation, HookTranslation.translateRawResourceResult(testCase[0], wrongPunctuation));
            assertSame(testCase[3], HookTranslation.translateRawResourceResult(testCase[0], testCase[3]));
            assertSame(missingPlaceholder, HookTranslation.translateRawResourceResult(testCase[0], missingPlaceholder));
            assertSame(extraPlaceholder, HookTranslation.translateRawResourceResult(testCase[0], extraPlaceholder));
            assertSame(mutable, HookTranslation.translateRawResourceResult(testCase[0], mutable));
            assertSame(spanned, HookTranslation.translateRawResourceResult(testCase[0], spanned));
            assertSame(null, HookTranslation.translateRawResourceResult(testCase[0], null));
        }
    }

    @Test
    public void rawResourceTemplateTranslatesStaticResources() {
        assertEquals(
                "气压高度",
                HookTranslation.translateRawResourceResult(
                        "cab_calibrated_altitude",
                        "Barometric altitude"));
        assertEquals(
                "当前天气",
                HookTranslation.translateRawResourceResult(
                        "settings_weather_basic_weather",
                        "Current weather"));
    }

    @Test
    public void rawResourceTemplateTranslatesOnlyExactAirportTemplates() {
        String[][] cases = {
                {"airport_diff_min", "%s min ago", "%s 分钟前", "15"},
                {"airport_diff_hrs", "%s hrs ago", "%s 小时前", "2"},
                {"airport_diff_days", "%s days ago", "%s 天前", "3"},
                {"airport_diff_months", "%s months ago", "%s 个月前", "4"},
                {"airport_diff_years", "%s years ago", "%s 年前", "5"},
                {"search_status_landed", "Landed %s", "已于 %s 降落", "13:38"},
                {"search_status_diverted_to", "Diverted to %s", "已备降至 %s", "CKG"},
                {"search_status_diverting_to", "Diverting to %s", "正在备降至 %s", "ZUCK"},
                {"search_status_estimated_arr_time", "Estimated arrival %s", "预计于 %s 到达", "12:34"},
                {"search_status_estimated_dep_time", "Estimated departure %s", "预计于 %s 起飞", "08:15"}
        };
        for (String[] testCase : cases) {
            Object translated = HookTranslation.translateRawResourceResult(testCase[0], testCase[1]);
            assertEquals(testCase[2], translated);
            assertEquals(testCase[2].replace("%s", testCase[3]),
                    String.format(Locale.SIMPLIFIED_CHINESE, (String) translated, testCase[3]));
        }
    }

    @Test
    public void rawResourceTemplateAllowsOnlyExactBookmarkEtaTemplate() {
        String source = "ETA %s";
        Object translated = HookTranslation.translateRawResourceResult("bookmark_eta", source);
        assertEquals("预计到达 %s", translated);
        assertEquals("预计到达 13:40", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) translated, "13:40"));
        String wrongPlaceholder = "ETA %d";
        String alreadyFormatted = "ETA 13:40";
        assertSame(wrongPlaceholder,
                HookTranslation.translateRawResourceResult("bookmark_eta", wrongPlaceholder));
        assertSame(alreadyFormatted,
                HookTranslation.translateRawResourceResult("bookmark_eta", alreadyFormatted));
        assertSame(source, HookTranslation.translateRawResourceResult("wrong_key", source));
        StringBuilder mutable = new StringBuilder(source);
        SpannedString spanned = new SpannedString(source);
        assertSame(mutable, HookTranslation.translateRawResourceResult("bookmark_eta", mutable));
        assertSame(spanned, HookTranslation.translateRawResourceResult("bookmark_eta", spanned));
    }

    @Test
    public void rawResourceTemplateAllowsOnlyExactWeatherWindLevelTemplate() {
        String source = "%s ft";
        Object translated = HookTranslation.translateRawResourceResult(
                "settings_weather_winds_level_selected_value", source);

        assertEquals("%s 英尺", translated);
        assertEquals("10,000 英尺", String.format(Locale.US, (String) translated, "10,000"));

        String wrongPlaceholder = "%d ft";
        String wrongCase = "%s FT";
        String alreadyFormatted = "10,000 ft";
        assertSame(wrongPlaceholder, HookTranslation.translateRawResourceResult(
                "settings_weather_winds_level_selected_value", wrongPlaceholder));
        assertSame(wrongCase, HookTranslation.translateRawResourceResult(
                "settings_weather_winds_level_selected_value", wrongCase));
        assertSame(alreadyFormatted, HookTranslation.translateRawResourceResult(
                "settings_weather_winds_level_selected_value", alreadyFormatted));
        assertSame(source, HookTranslation.translateRawResourceResult("wrong_key", source));
        StringBuilder mutable = new StringBuilder(source);
        SpannedString spanned = new SpannedString(source);
        assertSame(mutable, HookTranslation.translateRawResourceResult(
                "settings_weather_winds_level_selected_value", mutable));
        assertSame(spanned, HookTranslation.translateRawResourceResult(
                "settings_weather_winds_level_selected_value", spanned));
    }

    @Test
    public void rawResourceTemplateAllowsOnlyTwoExactAlertTemplates() {
        String conditionSource = "Condition %d too short.";
        String emergencySource = "Emergency alert (squawk %s)";

        assertEquals("条件 %d 太短。", HookTranslation.translateRawResourceResult(
                "alert_condition_too_short", conditionSource));
        assertEquals("紧急提醒（应答机代码 %s）", HookTranslation.translateRawResourceResult(
                "alert_emergency_history_desc", emergencySource));
        assertEquals("条件 2 太短。", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) HookTranslation.translateRawResourceResult(
                        "alert_condition_too_short", conditionSource), 2));
        assertEquals("紧急提醒（应答机代码 7700）", String.format(Locale.SIMPLIFIED_CHINESE,
                (String) HookTranslation.translateRawResourceResult(
                        "alert_emergency_history_desc", emergencySource), "7700"));

        String wrongCondition = "Condition %s too short.";
        String wrongEmergency = "Emergency alert (Squawk %s)";
        assertSame(wrongCondition, HookTranslation.translateRawResourceResult(
                "alert_condition_too_short", wrongCondition));
        assertSame(wrongEmergency, HookTranslation.translateRawResourceResult(
                "alert_emergency_history_desc", wrongEmergency));
        assertSame(conditionSource, HookTranslation.translateRawResourceResult(
                "alert_condition_too_short_other", conditionSource));
    }

    @Test
    public void textArrayHookPathTranslatesOnlyWithExactIndexArguments() {
        CharSequence[] source = new CharSequence[]{"Feet", "Meters"};
        Object translated = HookTranslation.translateTextArrayResult(
                "settings_altitude_array", source, new Object[]{0});

        assertArrayEquals(new String[]{"英尺", "米"}, (Object[]) translated);
        assertNotSame(source, translated);
        assertArrayEquals(new String[]{"Feet", "Meters"}, source);

        String[] alertSource = new String[]{"Flight", "Registration", "Airline", "Aircraft type"};
        Object translatedAlert = HookTranslation.translateTextArrayResult(
                "alert_type_array", alertSource, new Object[]{0x7f030005});
        assertArrayEquals(new String[]{"航班", "注册号", "航空公司", "机型"},
                (Object[]) translatedAlert);
        assertNotSame(alertSource, translatedAlert);

        assertSame(source, HookTranslation.translateTextArrayResult("settings_altitude_array", source, null));
        assertSame(source, HookTranslation.translateTextArrayResult(
                "settings_altitude_array", source, new Object[]{}));
        assertSame(source, HookTranslation.translateTextArrayResult(
                "settings_altitude_array", source, new Object[]{-1}));
        assertSame(source, HookTranslation.translateTextArrayResult(
                "settings_altitude_array", source, new Object[]{"0"}));
        assertSame(source, HookTranslation.translateTextArrayResult(
                "settings_altitude_array", source, new Object[]{0, "extra"}));
    }

    @Test
    public void textArrayHookPathFailsOpenForUnknownAndNonArrayResults() {
        CharSequence[] source = new CharSequence[]{"Feet", "Meters"};
        String scalar = "Feet";
        Object[] objectArray = new Object[]{"Feet", "Meters"};

        assertSame(source, HookTranslation.translateTextArrayResult(
                "language_array_values", source, new Object[]{0}));
        assertSame(source, HookTranslation.translateTextArrayResult(
                "unknown_array", source, new Object[]{0}));
        assertSame(scalar, HookTranslation.translateTextArrayResult(
                "settings_altitude_array", scalar, new Object[]{0}));
        assertSame(objectArray, HookTranslation.translateTextArrayResult(
                "settings_altitude_array", objectArray, new Object[]{0}));
        assertNull(HookTranslation.translateTextArrayResult(
                "settings_altitude_array", null, new Object[]{0}));
    }

    @Test
    public void rawAirportTemplatesFailOpenForNearMatchesAndNonStringIdentity() {
        String source = "%s min ago";
        String wrongPlaceholder = "%d min ago";
        String alreadyFormatted = "15 min ago";
        String unknown = "Unknown %s";
        String nearMatch = "%s minutes ago";
        StringBuilder mutable = new StringBuilder(source);
        SpannedString spanned = new SpannedString(source);

        assertSame(source, HookTranslation.translateRawResourceResult("wrong_key", source));
        assertSame(wrongPlaceholder, HookTranslation.translateRawResourceResult("airport_diff_min", wrongPlaceholder));
        assertSame(alreadyFormatted, HookTranslation.translateRawResourceResult("airport_diff_min", alreadyFormatted));
        assertSame(unknown, HookTranslation.translateRawResourceResult("airport_diff_min", unknown));
        assertSame(mutable, HookTranslation.translateRawResourceResult("airport_diff_min", mutable));
        assertSame(spanned, HookTranslation.translateRawResourceResult("airport_diff_min", spanned));
        assertEquals("%s 分钟前", HookTranslation.translateRawResourceResult("airport_diff_min", source));
        assertSame(nearMatch, HookTranslation.translateRawResourceResult("airport_diff_min", nearMatch));
    }

    @Test
    public void rawResourceTemplatePreservesDynamicFormatterTemplatesByIdentity() {
        String moreInfo = "More %s information";
        String diverting = "Diverting to — %1$s (%2$s)";

        assertSame(
                moreInfo,
                HookTranslation.translateRawResourceResult("cab_more_info", moreInfo));
        assertSame(
                diverting,
                HookTranslation.translateRawResourceResult("cab_diverting_to", diverting));
    }

    @Test
    public void rawResourceTemplateAllowsOnlyAuditedAircraftInfoTemplate() {
        String source = "Labels travel with the aircraft and are shown when there are fewer than %d aircraft on the map.";
        Object translatedTemplate = HookTranslation.translateRawResourceResult(
                "settings_aircraft_info_desc",
                source);

        assertEquals(
                "地图上的航空器少于 %d 架时，标签会随航空器移动并显示。",
                translatedTemplate);
        assertEquals(
                "地图上的航空器少于 250 架时，标签会随航空器移动并显示。",
                String.format(Locale.SIMPLIFIED_CHINESE, (String) translatedTemplate, 250));

        String otherResourceTemplate = "More %s information";
        String wrongPlaceholder = "Labels travel with the aircraft and are shown when there are fewer than %s aircraft on the map.";
        String alreadyFormatted = "Labels travel with the aircraft and are shown when there are fewer than 250 aircraft on the map.";
        SpannedString spannedSource = new SpannedString(source);
        StringBuilder mutableSource = new StringBuilder(source);

        assertSame(
                otherResourceTemplate,
                HookTranslation.translateRawResourceResult(
                        "cab_more_info",
                        otherResourceTemplate));
        assertSame(
                wrongPlaceholder,
                HookTranslation.translateRawResourceResult(
                        "settings_aircraft_info_desc",
                        wrongPlaceholder));
        assertSame(
                alreadyFormatted,
                HookTranslation.translateRawResourceResult(
                        "settings_aircraft_info_desc",
                        alreadyFormatted));
        assertSame(
                spannedSource,
                HookTranslation.translateRawResourceResult(
                        "settings_aircraft_info_desc",
                        spannedSource));
        assertSame(
                mutableSource,
                HookTranslation.translateRawResourceResult(
                        "settings_aircraft_info_desc",
                        mutableSource));
    }

    @Test
    public void rawResourceTemplatePreservesDynamicOrUncertainSourceByIdentity() {
        String implicitString = "More %s information";
        String implicitInteger = "Count %d";
        String explicitIndex = "Flight %1$s";
        String malformed = "Broken %";

        assertSame(
                implicitString,
                HookTranslation.translateRawResourceResult(
                        "settings_weather_basic_weather",
                        implicitString));
        assertSame(
                implicitInteger,
                HookTranslation.translateRawResourceResult(
                        "settings_weather_basic_weather",
                        implicitInteger));
        assertSame(
                explicitIndex,
                HookTranslation.translateRawResourceResult(
                        "settings_weather_basic_weather",
                        explicitIndex));
        assertSame(
                malformed,
                HookTranslation.translateRawResourceResult(
                        "settings_weather_basic_weather",
                        malformed));
    }

    @Test
    public void rawResourceTemplateDoesNotInjectPlaceholderIntoFormattedText() {
        String original = "More MU2845 information";
        assertSame(
                original,
                HookTranslation.translateRawResourceResult("cab_more_info", original));
    }

    @Test
    public void rawResourceTemplatePreservesUnknownAndNonStringResultsByIdentity() {
        String unknown = "Search";
        StringBuilder styled = new StringBuilder("More %s information");

        assertSame(
                unknown,
                HookTranslation.translateRawResourceResult("unknown_resource", unknown));
        assertSame(
                styled,
                HookTranslation.translateRawResourceResult("cab_more_info", styled));
    }

    @Test
    public void resourceCallUsesRawPathWhenNoFormatArrayWasPassed() {
        String getTextOriginal = "More %s information";
        String getStringOriginal = "More %s information";

        assertSame(
                getTextOriginal,
                HookTranslation.translateResourceCall(
                        "cab_more_info",
                        getTextOriginal,
                        new Object[]{0x7f1401fe}));
        assertSame(
                getStringOriginal,
                HookTranslation.translateResourceCall(
                        "cab_more_info",
                        getStringOriginal,
                        new Object[]{0x7f1401fe, "default text"}));
    }

    @Test
    public void resourceCallKeepsStrictFormattingForExplicitEmptyVarargs() {
        String original = "More %s information";
        assertSame(
                original,
                HookTranslation.translateResourceCall(
                        "cab_more_info",
                        original,
                        new Object[]{0x7f1401fe, new Object[]{}}));
    }

    @Test
    public void selectedActionModeContextsReturnOneExternallyFormattableTemplate() {
        StackTraceElement settingsAlerts = new StackTraceElement(
                "com.flightradar24free.SettingsAlertsHistoryActivity$1",
                "onItemCheckedStateChanged",
                "SettingsAlertsHistoryActivity.java",
                42);
        StackTraceElement customAlerts = new StackTraceElement(
                "com.flightradar24free.feature.alerts.view.CustomAlertsFragment$5",
                "onItemCheckedStateChanged",
                "CustomAlertsFragment.java",
                84);

        for (StackTraceElement frame : new StackTraceElement[]{settingsAlerts, customAlerts}) {
            Object translated = HookTranslation.translateResourceCall(
                    "selected",
                    "%d selected",
                    new Object[]{0x7f140321},
                    new StackTraceElement[]{frame});
            assertEquals("已选择 %d 项", translated);
            assertEquals(1, ((String) translated).split("%d", -1).length - 1);
            assertEquals("已选择 0 项", String.format(
                    Locale.SIMPLIFIED_CHINESE, (String) translated, 0));
            assertEquals("已选择 1 项", String.format(
                    Locale.SIMPLIFIED_CHINESE, (String) translated, 1));
            assertEquals("已选择 37 项", String.format(
                    Locale.SIMPLIFIED_CHINESE, (String) translated, 37));
        }

        Object actionPreferred = HookTranslation.translateResourceCall(
                "selected",
                "%d selected",
                new Object[]{0x7f140321},
                new StackTraceElement[]{
                        new StackTraceElement(
                                "bn0.c",
                                "a",
                                "AndroidComposeViewAccessibilityDelegateCompat.android.kt",
                                100),
                        settingsAlerts
                });
        assertEquals("已选择 %d 项", actionPreferred);
    }

    @Test
    public void selectedComposeAccessibilityContextsReturnFinalPlaceholderFreeText() {
        StackTraceElement composeElevenPointNine = new StackTraceElement(
                "bn0.c",
                "a",
                "AndroidComposeViewAccessibilityDelegateCompat.android.kt",
                100);
        StackTraceElement composeElevenPointEight = new StackTraceElement(
                "qm0.d",
                "b",
                "AndroidComposeViewAccessibilityDelegateCompat.android.kt",
                200);

        assertEquals("已选择", HookTranslation.translateResourceCall(
                "selected",
                "%d selected",
                new Object[]{0x7f140321},
                new StackTraceElement[]{composeElevenPointNine}));
        assertEquals("已选择", HookTranslation.translateResourceCall(
                "selected",
                "%d selected",
                new Object[]{0},
                new StackTraceElement[]{composeElevenPointEight}));
    }

    @Test
    public void selectedRoutingFailsOpenForContextNearMisses() {
        StackTraceElement[] wrongOuterClass = {new StackTraceElement(
                "com.flightradar24free.SettingsAlertsHistoryActivity",
                "onItemCheckedStateChanged",
                "SettingsAlertsHistoryActivity.java",
                42)};
        StackTraceElement[] wrongActionMethod = {new StackTraceElement(
                "com.flightradar24free.SettingsAlertsHistoryActivity$1",
                "onItemCheckedStatechanged",
                "SettingsAlertsHistoryActivity.java",
                42)};
        StackTraceElement[] wrongComposeSourceCase = {new StackTraceElement(
                "bn0.c",
                "a",
                "androidComposeViewAccessibilityDelegateCompat.android.kt",
                100)};
        StackTraceElement[] wrongComposeSourcePath = {new StackTraceElement(
                "bn0.c",
                "a",
                "androidx/compose/ui/platform/AndroidComposeViewAccessibilityDelegateCompat.android.kt",
                100)};

        for (StackTraceElement[] stack : new StackTraceElement[][]{
                wrongOuterClass,
                wrongActionMethod,
                wrongComposeSourceCase,
                wrongComposeSourcePath,
                new StackTraceElement[]{null},
                new StackTraceElement[]{}
        }) {
            String original = "%d selected";
            assertSame(original, HookTranslation.translateResourceCall(
                    "selected", original, new Object[]{0x7f140321}, stack));
        }
        String original = "%d selected";
        assertSame(original, HookTranslation.translateResourceCall(
                "selected", original, new Object[]{0x7f140321}, null));
    }

    @Test
    public void selectedRoutingFailsOpenForInvalidResourceValueAndCallShapes() {
        StackTraceElement[] actionStack = {new StackTraceElement(
                "com.flightradar24free.feature.alerts.view.CustomAlertsFragment$5",
                "onItemCheckedStateChanged",
                "CustomAlertsFragment.java",
                84)};
        String wrongName = "%d selected";
        String wrongCase = "%d Selected";
        String wrongPunctuation = "%d selected.";
        String alreadyActionTranslated = "已选择 %d 项";
        String alreadyComposeTranslated = "已选择";
        String nullArguments = "%d selected";
        String emptyArguments = "%d selected";
        String defaultValueOverload = "%d selected";
        String explicitVarargs = "%d selected";
        String negativeId = "%d selected";
        String longId = "%d selected";
        StringBuilder mutable = new StringBuilder("%d selected");

        assertSame(wrongName, HookTranslation.translateResourceCall(
                "selections", wrongName, new Object[]{0x7f140321}, actionStack));
        assertSame(wrongCase, HookTranslation.translateResourceCall(
                "selected", wrongCase, new Object[]{0x7f140321}, actionStack));
        assertSame(wrongPunctuation, HookTranslation.translateResourceCall(
                "selected", wrongPunctuation, new Object[]{0x7f140321}, actionStack));
        assertSame(alreadyActionTranslated, HookTranslation.translateResourceCall(
                "selected", alreadyActionTranslated, new Object[]{0x7f140321}, actionStack));
        assertSame(alreadyComposeTranslated, HookTranslation.translateResourceCall(
                "selected", alreadyComposeTranslated, new Object[]{0x7f140321}, actionStack));
        assertSame(nullArguments, HookTranslation.translateResourceCall(
                "selected", nullArguments, null, actionStack));
        assertSame(emptyArguments, HookTranslation.translateResourceCall(
                "selected", emptyArguments, new Object[]{}, actionStack));
        assertSame(defaultValueOverload, HookTranslation.translateResourceCall(
                "selected", defaultValueOverload,
                new Object[]{0x7f140321, "default"}, actionStack));
        assertSame(explicitVarargs, HookTranslation.translateResourceCall(
                "selected", explicitVarargs,
                new Object[]{0x7f140321, new Object[]{}}, actionStack));
        assertSame(negativeId, HookTranslation.translateResourceCall(
                "selected", negativeId, new Object[]{-1}, actionStack));
        assertSame(longId, HookTranslation.translateResourceCall(
                "selected", longId, new Object[]{1L}, actionStack));
        assertSame(mutable, HookTranslation.translateResourceCall(
                "selected", mutable, new Object[]{0x7f140321}, actionStack));
        assertNull(HookTranslation.translateResourceCall(
                "selected", null, new Object[]{0x7f140321}, actionStack));
    }

    @Test
    public void injectedSelectedStacksDoNotChangeOrdinaryResourceRouting() {
        StackTraceElement[] composeStack = {new StackTraceElement(
                "bn0.c",
                "a",
                "AndroidComposeViewAccessibilityDelegateCompat.android.kt",
                100)};
        StackTraceElement[] actionStack = {new StackTraceElement(
                "com.flightradar24free.SettingsAlertsHistoryActivity$1",
                "onItemCheckedStateChanged",
                "SettingsAlertsHistoryActivity.java",
                42)};

        assertEquals("我的账户", HookTranslation.translateResourceCall(
                "my_account", "My account", new Object[]{0x7f140001}, composeStack));
        assertEquals("更多 NS8035 信息", HookTranslation.translateResourceCall(
                "cab_more_info",
                "More NS8035 information",
                new Object[]{0x7f140002, new Object[]{"NS8035"}},
                actionStack));

        String original = "%d selected";
        assertSame(original, HookTranslation.translateResourceCall(
                "selected", original, new Object[]{0x7f140321}));
    }

    @Test
    public void translatesOnlyAuditedFiltersNumPluralResults() {
        assertEquals(
                "10 个筛选条件",
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        "10 filters",
                        new Object[]{0x7f1401fe, 10, new Object[]{10}}));
        assertEquals(
                "1 个筛选条件",
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        "1 filter",
                        new Object[]{0x7f1401fe, 1, new Object[]{1}}));
    }

    @Test
    public void translatesOnlyTwoExactAlertDeletionPluralsWithoutFormattingArguments() {
        assertEquals("确定要删除所选提醒吗？", HookTranslation.translateQuantityResourceResult(
                "alert_are_you_sure_alerts",
                "Are you sure you want to delete selected alert?",
                new Object[]{0x7f120000, 1}));
        assertEquals("确定要删除所选提醒吗？", HookTranslation.translateQuantityResourceResult(
                "alert_are_you_sure_alerts",
                "Are you sure you want to delete selected alerts?",
                new Object[]{0x7f120000, 2}));
        assertEquals("确定要删除所选提醒记录吗？", HookTranslation.translateQuantityResourceResult(
                "alert_history_are_you_sure",
                "Are you sure you want to delete selected alert log?",
                new Object[]{0x7f120001, 1}));
        assertEquals("确定要删除所选提醒记录吗？", HookTranslation.translateQuantityResourceResult(
                "alert_history_are_you_sure",
                "Are you sure you want to delete selected alert logs?",
                new Object[]{0x7f120001, 3}));

        String wrongSource = "Are you sure you want to delete selected Alert?";
        String wrongShape = "Are you sure you want to delete selected alerts?";
        String wrongKey = "Are you sure you want to delete selected alert?";
        assertSame(wrongSource, HookTranslation.translateQuantityResourceResult(
                "alert_are_you_sure_alerts", wrongSource, new Object[]{0x7f120000, 1}));
        assertSame(wrongShape, HookTranslation.translateQuantityResourceResult(
                "alert_are_you_sure_alerts", wrongShape,
                new Object[]{0x7f120000, 2, new Object[]{2}}));
        assertSame(wrongKey, HookTranslation.translateQuantityResourceResult(
                "alert_are_you_sure_alert", wrongKey, new Object[]{0x7f120000, 1}));
    }

    @Test
    public void translatesOnlyExactFilterLimitPluralWithTwoNumericArguments() {
        assertEquals("筛选值最多为 10 个。请移除 1 个值后继续。",
                HookTranslation.translateQuantityResourceResult(
                        "filters_add_limit_error",
                        "Filters are limited to 10 values. Please remove 1 value to continue.",
                        new Object[]{0x7f12000a, 1, new Object[]{10, 1}}));
        assertEquals("筛选值最多为 10 个。请移除 3 个值后继续。",
                HookTranslation.translateQuantityResourceResult(
                        "filters_add_limit_error",
                        "Filters are limited to 10 values. Please remove 3 values to continue.",
                        new Object[]{0x7f12000a, 3, new Object[]{10, 3}}));

        String wrongSource = "Filters are limited to 10 values. Please remove 3 value to continue.";
        String wrongQuantity = "Filters are limited to 10 values. Please remove 3 values to continue.";
        String wrongArgs = "Filters are limited to 10 values. Please remove 3 values to continue.";
        assertSame(wrongSource, HookTranslation.translateQuantityResourceResult(
                "filters_add_limit_error", wrongSource,
                new Object[]{0x7f12000a, 3, new Object[]{10, 3}}));
        assertSame(wrongQuantity, HookTranslation.translateQuantityResourceResult(
                "filters_add_limit_error", wrongQuantity,
                new Object[]{0x7f12000a, 2, new Object[]{10, 3}}));
        assertSame(wrongArgs, HookTranslation.translateQuantityResourceResult(
                "filters_add_limit_error", wrongArgs,
                new Object[]{0x7f12000a, 3, new Object[]{10, "3"}}));
    }

    @Test
    public void quantityPluralTranslationPreservesAllRejectedShapesByIdentity() {
        String otherPlural = "2 hours";
        String unknownPlural = "10 filters";
        String wrongEnglishCapitalization = "10 Filter";
        String wrongResourceName = "10 filters";
        String rawTwoArgumentShape = "%d filters";
        String emptyVarargs = "10 filters";
        String multipleVarargs = "10 filters";
        String nonNumberVararg = "10 filters";
        String mismatchedNumber = "10 filters";
        String negativeQuantity = "-1 filters";
        StringBuilder mutable = new StringBuilder("10 filters");
        SpannedString spanned = new SpannedString("10 filters");

        assertSame(
                otherPlural,
                HookTranslation.translateQuantityResourceResult(
                        "hour_hours",
                        otherPlural,
                        new Object[]{0x7f1401fe, 2, new Object[]{2}}));
        assertSame(
                unknownPlural,
                HookTranslation.translateQuantityResourceResult(
                        "unknown_plural",
                        unknownPlural,
                        new Object[]{0x7f1401fe, 10, new Object[]{10}}));
        assertSame(
                wrongEnglishCapitalization,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        wrongEnglishCapitalization,
                        new Object[]{0x7f1401fe, 10, new Object[]{10}}));
        assertSame(
                wrongResourceName,
                HookTranslation.translateQuantityResourceResult(
                        "filters_number",
                        wrongResourceName,
                        new Object[]{0x7f1401fe, 10, new Object[]{10}}));
        assertSame(
                rawTwoArgumentShape,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        rawTwoArgumentShape,
                        new Object[]{0x7f1401fe, 10}));
        assertSame(
                emptyVarargs,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        emptyVarargs,
                        new Object[]{0x7f1401fe, 10, new Object[]{}}));
        assertSame(
                multipleVarargs,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        multipleVarargs,
                        new Object[]{0x7f1401fe, 10, new Object[]{10, 10}}));
        assertSame(
                nonNumberVararg,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        nonNumberVararg,
                        new Object[]{0x7f1401fe, 10, new Object[]{"10"}}));
        assertSame(
                mismatchedNumber,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        mismatchedNumber,
                        new Object[]{0x7f1401fe, 10, new Object[]{11}}));
        assertSame(
                negativeQuantity,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        negativeQuantity,
                        new Object[]{0x7f1401fe, -1, new Object[]{-1}}));
        assertSame(
                mutable,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        mutable,
                        new Object[]{0x7f1401fe, 10, new Object[]{10}}));
        assertSame(
                spanned,
                HookTranslation.translateQuantityResourceResult(
                        "filters_num",
                        spanned,
                        new Object[]{0x7f1401fe, 10, new Object[]{10}}));
    }

    @Test
    public void rawGeofenceNotificationTitleAllowsOnlyItsAuditedStringTemplate() {
        String source = "Welcome to %s";
        Object translatedTemplate = HookTranslation.translateRawResourceResult(
                "geofence_notification_title",
                source);

        assertEquals("欢迎来到 %s", translatedTemplate);
        assertEquals(
                "欢迎来到 Beijing Capital",
                String.format(Locale.SIMPLIFIED_CHINESE, (String) translatedTemplate, "Beijing Capital"));

        String wrongResourceName = "Welcome to %s";
        String wrongSource = "Welcome at %s";
        String wrongCase = "welcome to %s";
        String wrongPunctuation = "Welcome to %s!";
        String formattedResult = "Welcome to Beijing Capital";
        String missingPlaceholder = "Welcome to";
        String extraPlaceholder = "Welcome to %s %s";
        StringBuilder mutableSource = new StringBuilder(source);
        SpannedString spannedSource = new SpannedString(source);

        assertSame(
                wrongResourceName,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_titles", wrongResourceName));
        assertSame(
                wrongSource,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", wrongSource));
        assertSame(
                wrongCase,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", wrongCase));
        assertSame(
                wrongPunctuation,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", wrongPunctuation));
        assertSame(
                formattedResult,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", formattedResult));
        assertSame(
                missingPlaceholder,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", missingPlaceholder));
        assertSame(
                extraPlaceholder,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", extraPlaceholder));
        assertSame(
                mutableSource,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", mutableSource));
        assertSame(
                spannedSource,
                HookTranslation.translateRawResourceResult(
                        "geofence_notification_title", spannedSource));
        assertSame(
                null,
                HookTranslation.translateRawResourceResult("geofence_notification_title", null));
    }

    @Test
    public void translatesRemainingNumericFormatPluralsOnlyWithMatchingQuantity() {
        assertEquals("还可输入 1 个字符", HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", "1 character left",
                new Object[]{0x7f120100, 1, new Object[]{1}}));
        assertEquals("还可输入 2 个字符", HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", "2 characters left",
                new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertEquals("1 年", HookTranslation.translateQuantityResourceResult(
                "cab_aircraft_age_value", "1 year",
                new Object[]{0x7f120101, 1, new Object[]{1}}));
        assertEquals("2 年", HookTranslation.translateQuantityResourceResult(
                "cab_aircraft_age_value", "2 years",
                new Object[]{0x7f120101, 2, new Object[]{2}}));
        assertEquals("%d 年", HookTranslation.translateQuantityResourceResult(
                "cab_aircraft_age_value", "%d year", new Object[]{0x7f120101, 1}));
        assertEquals("%d 年", HookTranslation.translateQuantityResourceResult(
                "cab_aircraft_age_value", "%d years", new Object[]{0x7f120101, 2}));
        assertEquals("剩余 1 个", HookTranslation.translateQuantityResourceResult(
                "custom_filter_list_limit_remaining", "1 remaining",
                new Object[]{0x7f120102, 1, new Object[]{1}}));
        assertEquals("剩余 2 个", HookTranslation.translateQuantityResourceResult(
                "custom_filter_list_limit_remaining", "2 remaining",
                new Object[]{0x7f120102, 2, new Object[]{2}}));
        assertEquals("1 个结果", HookTranslation.translateQuantityResourceResult(
                "filter_search_results", "1 Results",
                new Object[]{0x7f120103, 1, new Object[]{1}}));
        assertEquals("2 个结果", HookTranslation.translateQuantityResourceResult(
                "filter_search_results", "2 Results",
                new Object[]{0x7f120103, 2, new Object[]{2}}));
        assertEquals("1 个会话", HookTranslation.translateQuantityResourceResult(
                "session_sessions", "1 session",
                new Object[]{0x7f120104, 1, new Object[]{1}}));
        assertEquals("2 个会话", HookTranslation.translateQuantityResourceResult(
                "session_sessions", "2 sessions",
                new Object[]{0x7f120104, 2, new Object[]{2}}));
        assertEquals("个机场", HookTranslation.translateQuantityResourceResult(
                "search_found_airport_airports", "airport found",
                new Object[]{0x7f120105, 1, new Object[]{1}}));
        assertEquals("个机场", HookTranslation.translateQuantityResourceResult(
                "search_found_airport_airports", "airports found",
                new Object[]{0x7f120105, 2, new Object[]{2}}));
        assertEquals("个航班", HookTranslation.translateQuantityResourceResult(
                "search_found_flight_airline", "flight found",
                new Object[]{0x7f120106, 1, new Object[]{1}}));
        assertEquals("个航班", HookTranslation.translateQuantityResourceResult(
                "search_found_flight_airline", "flights found",
                new Object[]{0x7f120106, 2, new Object[]{2}}));
    }

    @Test
    public void translatesRemainingDisplayCountPluralsOnlyWithOneNonemptyString() {
        assertEquals("共有 1 位用户到访过该机场。", HookTranslation.translateQuantityResourceResult(
                "cab_myfr24_travellers_total_airport_plurals",
                "A total of 1 user have visited this airport.",
                new Object[]{0x7f120107, 1, new Object[]{"1"}}));
        assertEquals("共有 2 位用户到访过该机场。", HookTranslation.translateQuantityResourceResult(
                "cab_myfr24_travellers_total_airport_plurals",
                "A total of 2 users have visited this airport.",
                new Object[]{0x7f120107, 2, new Object[]{"2"}}));
        assertEquals("共有 1 位用户乘坐过该航班。", HookTranslation.translateQuantityResourceResult(
                "cab_myfr24_travellers_total_plurals",
                "A total of 1 users have taken this flight.",
                new Object[]{0x7f120108, 1, new Object[]{"1"}}));
        assertEquals("共有 2 位用户乘坐过该航班。", HookTranslation.translateQuantityResourceResult(
                "cab_myfr24_travellers_total_plurals",
                "A total of 2 users have taken this flight.",
                new Object[]{0x7f120108, 2, new Object[]{"2"}}));
    }

    @Test
    public void translatesRemainingUnformattedPluralsOnlyWithExactRawSources() {
        assertEquals("天", HookTranslation.translateQuantityResourceResult(
                "day_days", "day", new Object[]{0x7f120109, 1}));
        assertEquals("天", HookTranslation.translateQuantityResourceResult(
                "day_days", "days", new Object[]{0x7f120109, 2}));
        assertEquals("小时", HookTranslation.translateQuantityResourceResult(
                "hour_hours", "hour", new Object[]{0x7f12010a, 1}));
        assertEquals("小时", HookTranslation.translateQuantityResourceResult(
                "hour_hours", "hours", new Object[]{0x7f12010a, 2}));
        assertEquals("家航空公司", HookTranslation.translateQuantityResourceResult(
                "search_found_airline", "airline", new Object[]{0x7f12010b, 1}));
        assertEquals("家航空公司", HookTranslation.translateQuantityResourceResult(
                "search_found_airline", "airlines", new Object[]{0x7f12010b, 2}));
        assertEquals("个机场", HookTranslation.translateQuantityResourceResult(
                "search_found_airport", "airport", new Object[]{0x7f12010c, 1}));
        assertEquals("个机场", HookTranslation.translateQuantityResourceResult(
                "search_found_airport", "airports", new Object[]{0x7f12010c, 2}));
        assertEquals("个航班", HookTranslation.translateQuantityResourceResult(
                "search_found_flight", "flight", new Object[]{0x7f12010d, 1}));
        assertEquals("个航班", HookTranslation.translateQuantityResourceResult(
                "search_found_flight", "flights", new Object[]{0x7f12010d, 2}));
        assertEquals("观看视频 | 获得 %d 次 3D 体验", HookTranslation.translateQuantityResourceResult(
                "dialog_3d_sessions_remaining", "Watch a video | Get %d session",
                new Object[]{0x7f12010e, 1}));
        assertEquals("观看视频 | 获得 %d 次 3D 体验", HookTranslation.translateQuantityResourceResult(
                "dialog_3d_sessions_remaining", "Watch a video | Get %d sessions",
                new Object[]{0x7f12010e, 2}));
    }

    @Test
    public void remainingPluralTranslationsFailOpenForEveryStrictBoundary() {
        String wrongCapitalization = "2 Characters left";
        String wrongPunctuation = "2 characters left.";
        String wrongPlural = "2 character left";
        String wrongKey = "2 characters left";
        String wrongArity = "2 characters left";
        String emptyVarargs = "2 characters left";
        String multipleVarargs = "2 characters left";
        String wrongType = "2 characters left";
        String numericMismatch = "2 characters left";
        String emptyDisplayCount = "A total of 2 users have taken this flight.";
        String negativeQuantity = "days";
        String material = "2 new notifications";
        StringBuilder mutable = new StringBuilder("2 characters left");
        SpannedString spanned = new SpannedString("2 characters left");

        assertSame(wrongCapitalization, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", wrongCapitalization,
                new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertSame(wrongPunctuation, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", wrongPunctuation,
                new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertSame(wrongPlural, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", wrongPlural,
                new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertSame(wrongKey, HookTranslation.translateQuantityResourceResult(
                "bookmark_counter", wrongKey, new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertSame(wrongArity, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", wrongArity, new Object[]{0x7f120100, 2}));
        assertSame(emptyVarargs, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", emptyVarargs,
                new Object[]{0x7f120100, 2, new Object[]{}}));
        assertSame(multipleVarargs, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", multipleVarargs,
                new Object[]{0x7f120100, 2, new Object[]{2, 2}}));
        assertSame(wrongType, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", wrongType,
                new Object[]{0x7f120100, 2, new Object[]{"2"}}));
        assertSame(numericMismatch, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", numericMismatch,
                new Object[]{0x7f120100, 2, new Object[]{3}}));
        assertSame(emptyDisplayCount, HookTranslation.translateQuantityResourceResult(
                "cab_myfr24_travellers_total_plurals", emptyDisplayCount,
                new Object[]{0x7f120108, 2, new Object[]{""}}));
        assertSame(negativeQuantity, HookTranslation.translateQuantityResourceResult(
                "day_days", negativeQuantity, new Object[]{0x7f120109, -1}));
        assertSame(material, HookTranslation.translateQuantityResourceResult(
                "mtrl_badge_content_description", material,
                new Object[]{0x7f12010f, 2, new Object[]{2}}));
        assertSame(mutable, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", mutable,
                new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertSame(spanned, HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", spanned,
                new Object[]{0x7f120100, 2, new Object[]{2}}));
        assertNull(HookTranslation.translateQuantityResourceResult(
                "bookmark_char_counter", null, new Object[]{0x7f120100, 2, new Object[]{2}}));
    }

    @Test
    public void formatsOuterFiltersUpsellOnlyThroughExistingFormattedResourcePath() {
        assertEquals(
                "要按航空器类别筛选，请升级至 Silver（10 个筛选条件）或 Gold（25）。",
                HookTranslation.translateResourceResult(
                        "filters_unlock_categories_upsell_title",
                        "To filter by aircraft category, please upgrade to Silver (10 filters) or Gold (25).",
                        new Object[]{"10 个筛选条件", 25}));
    }

    @Test
    public void quantityPluralRouteDoesNotChangeExistingRawOrFormattedStringCalls() {
        String rawTemplate = "More %s information";
        String formattedValue = "More NS8035 information";

        assertSame(
                rawTemplate,
                HookTranslation.translateResourceCall(
                        "cab_more_info",
                        rawTemplate,
                        new Object[]{0x7f1401fe}));
        assertEquals(
                "更多 NS8035 信息",
                HookTranslation.translateResourceCall(
                        "cab_more_info",
                        formattedValue,
                        new Object[]{0x7f1401fe, new Object[]{"NS8035"}}));
    }

    private static Spanned spanned(final String value) {
        return new Spanned() {
            @Override
            public int length() {
                return value.length();
            }

            @Override
            public char charAt(int index) {
                return value.charAt(index);
            }

            @Override
            public CharSequence subSequence(int start, int end) {
                return value.subSequence(start, end);
            }

            @Override
            public String toString() {
                return value;
            }

            @Override
            public <T> T[] getSpans(int start, int end, Class<T> type) {
                return null;
            }

            @Override
            public int getSpanStart(Object tag) {
                return -1;
            }

            @Override
            public int getSpanEnd(Object tag) {
                return -1;
            }

            @Override
            public int getSpanFlags(Object tag) {
                return 0;
            }

            @Override
            public int nextSpanTransition(int start, int limit, Class type) {
                return limit;
            }
        };
    }

}
