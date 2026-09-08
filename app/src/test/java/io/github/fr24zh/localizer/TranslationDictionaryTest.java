package io.github.fr24zh.localizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public final class TranslationDictionaryTest {
    @Test
    public void translatesKnownExactText() {
        assertEquals("\u641c\u7d22", TranslationDictionary.translate("Search"));
        assertEquals("\u5730\u56fe", TranslationDictionary.translate("Map"));
        assertEquals("\u63d0\u9192", TranslationDictionary.translate("Alerts"));
    }

    @Test
    public void preservesUnknownAndNearMatches() {
        assertEquals("Search flight", TranslationDictionary.translate("Search flight"));
        assertEquals("search", TranslationDictionary.translate("search"));
        assertEquals("Most tracked", TranslationDictionary.translate("Most tracked"));
        assertEquals("Followers: 964", TranslationDictionary.translate("Followers: 964"));
        assertEquals("Arrival disruption index: 2.6",
                TranslationDictionary.translate("Arrival disruption index: 2.6"));
    }

    @Test
    public void preservesAirportWeatherTermsOutsideResourceContext() {
        assertEquals("min", TranslationDictionary.translate("min"));
        assertEquals("Thunderstorm", TranslationDictionary.translate("Thunderstorm"));
        assertEquals("Disruption index", TranslationDictionary.translate("Disruption index"));
        assertEquals("Flights canceled", TranslationDictionary.translate("Flights canceled"));
    }

    @Test
    public void preservesAirportDisruptionExplainerTextOutsideResourceContext() {
        assertEquals("Most disrupted airport", TranslationDictionary.translate("Most disrupted airport"));
        assertEquals(
                "The value between 0 and 5 is a balanced value that includes the number of delayed flights, average delay, and number of canceled flights. The higher the value, the higher the number of cancelations and/or delays.",
                TranslationDictionary.translate(
                        "The value between 0 and 5 is a balanced value that includes the number of delayed flights, average delay, and number of canceled flights. The higher the value, the higher the number of cancelations and/or delays."));
        assertEquals("The arrows display the current disruption index trend.",
                TranslationDictionary.translate("The arrows display the current disruption index trend."));
        assertEquals("Disruption index is available for about 300 of the busiest airports.",
                TranslationDictionary.translate("Disruption index is available for about 300 of the busiest airports."));
        assertEquals("Good traffic flow.", TranslationDictionary.translate("Good traffic flow."));
        assertEquals("Minor problems with some delays or few cancellations.",
                TranslationDictionary.translate("Minor problems with some delays or few cancellations."));
        assertEquals("Major problems with long delays and several canceled flights.",
                TranslationDictionary.translate("Major problems with long delays and several canceled flights."));
        assertEquals("Airport disruptions", TranslationDictionary.translate("Airport disruptions"));
        assertEquals("See the top 10 airports with the most cancellations and longest delays.",
                TranslationDictionary.translate(
                        "See the top 10 airports with the most cancellations and longest delays."));
        assertEquals("Weather currently N/A", TranslationDictionary.translate("Weather currently N/A"));
    }

    @Test
    public void preservesSearchResultCountAsGlobalText() {
        assertEquals("1 of 1 airports", TranslationDictionary.translate("1 of 1 airports"));
    }

    @Test
    public void preservesDropdownStatsAndBookmarkTextAsGlobalText() {
        String[] sources = {
                "More search history", "Swipe down to see the most tracked flights, airport disruptions, saved bookmarks, and tracking statistics.",
                "Aircraft on map", "DATA SOURCE", "VIEW", "GLOBAL", "TOTAL AIRCRAFT",
                "Airports", "Flights", "Locations", "Add an aircraft to Bookmarks!", "Add a flight to Bookmarks!",
                "Add an airport to Bookmarks!", "Add a location to Bookmarks!",
                "Add an aircraft by tapping the Add bookmark button below or by clicking the star next to a flight number anywhere in the app.",
                "Add a flight by tapping the Add bookmark button below or by clicking the star next to a flight number anywhere in the app.",
                "Add an airport by tapping the Add bookmark button below or by clicking the star icon at the top of an airport panel anywhere in the app.",
                "Add a location by tapping the Add bookmark button below. The saved location will be your current position on the map, including zoom.",
                "Add bookmark", "Currently not tracked", "Sort by:", "Last added", "Edit",
                "Airport name A-Z", "IATA code A-Z", "Flight number A-Z", "Location A-Z", "Registration A-Z", "Status", "Help",
                "Sort by alphabetical order", "Sort by alphabetically by IATA code", "Sort alphabetically by airport name",
                "Create your own order", "Sort by when item was added", "Sort by current status, e.g. Live"
        };
        for (String source : sources) {
            assertEquals(source, TranslationDictionary.translate(source));
        }
        assertEquals(
                "To get more than one bookmark, please upgrade to a <b>Silver (%1$d)</b> or <b>Gold (%2$d)</b> subscription.",
                TranslationDictionary.translate(
                        "To get more than one bookmark, please upgrade to a <b>Silver (%1$d)</b> or <b>Gold (%2$d)</b> subscription."));
        assertEquals("You have reached the bookmark limit of %d.",
                TranslationDictionary.translate("You have reached the bookmark limit of %d."));
        assertEquals(
                "To get more than ten bookmarks, please upgrade to a <b>Gold (%d)</b> subscription.",
                TranslationDictionary.translate(
                        "To get more than ten bookmarks, please upgrade to a <b>Gold (%d)</b> subscription."));
    }

    @Test
    public void preservesRemainingBookmarkSourcesAsGlobalText() {
        String[] sources = {
                "Bookmarks: aircraft bookmarked",
                "Bookmarks: airport bookmarked",
                "Bookmarks: flight and aircraft bookmarked",
                "Bookmarks: flight bookmarked",
                "e.g. D–AIHV",
                "e.g. London Heathrow or LHR",
                "Unable to add bookmark.",
                "e.g. SQ23 or AM22",
                "Name location",
                "Add location to bookmarks",
                "Create name for location",
                "To save a location, select the position you wish on the map, including zoom. "
                        + "Your default location is your current location on the map. "
                        + "Create a name for your location and select to add.",
                "Aircraft bookmark successfully added.",
                "Airport bookmark successfully added.",
                "Flight bookmark successfully added.",
                "Location bookmark successfully added.",
                "Already added",
                "Edit aircraft",
                "Edit airports",
                "If you close Edit without saving you will lose your changes.",
                "Discard changes?",
                "Edit flights",
                "Edit locations",
                "ETA %s",
                "Flying %1$s — %2$s",
                "Go to bookmarks",
                "Please delete a bookmark if you wish to add more.",
                "Upgrade to <b>Silver (%1$d)</b> or <b>Gold (%2$d)</b> to add a higher number.",
                "Upgrade to <b>Gold (%d)</b> to add a higher number.",
                "You have reached the bookmark limit for a %s account",
                "You’ve reached your bookmarks limit",
                "To unlock the Bookmarks feature you must have an account",
                "Create a free account to add one bookmark or upgrade to <b>Silver (%1$d)</b> "
                        + "or <b>Gold (%2$d)</b> to add a higher number of bookmarks.",
                "By creating an account you can unlock the bookmarks feature and view your "
                        + "bookmarks across all Flightradar24 platforms.",
                "On ground at %s",
                "To use Bookmarks you are required to create a free Flightradar24 account. "
                        + "By creating an account you’ll be able to access all the benefits, features and saved "
                        + "settings of your subscription on multiple platforms (iOS, Android and Flightradar24.com) "
                        + "matched to your profile.\n\nAfter creating an account you can access your bookmarks "
                        + "across any platform. Access Bookmarks by swiping down on the main map screen.",
                "Create free Flightradar24 account",
                "You can add multiple aircraft, flights, airports and locations to your own bookmarks for easy "
                        + "access. Access Bookmarks by swiping down on the main map screen.<br /><br />Create a free "
                        + "Flightradar24 account to add one bookmark or upgrade to <b>Silver (%1$d)</b> or "
                        + "<b>Gold (%2$d)</b> to add a higher number.",
                "Bookmarks",
                "Unable to remove bookmark.",
                "Aircraft bookmark removed.",
                "Airport bookmark removed.",
                "Flight bookmark removed.",
                "Unable to save changes. Try again later.",
                "Changes have been saved to Bookmarks.",
                "Click the star next to the aircraft, airport or flight number to add the item to your bookmarks.",
                "View bookmarks",
                "Weather is currently not available",
                "Location",
                "Select what you want to bookmark"
        };
        assertEquals(50, sources.length);
        for (String source : sources) {
            assertEquals(source, TranslationDictionary.translate(source));
        }
        assertEquals("收藏：已锁定", TranslationDictionary.translate("Bookmarks: locked"));
        assertEquals("创建账户", TranslationDictionary.translate("Create account"));
        assertEquals("飞机", TranslationDictionary.translate("Aircraft"));
        assertEquals("机场", TranslationDictionary.translate("Airport"));
        assertEquals("航班", TranslationDictionary.translate("Flight"));
        assertEquals(65, TranslationDictionary.size());
    }

    @Test
    public void preservesRemainingSettingsSourcesOutsideResourceContext() {
        String[] identitySources = {
                "Aircraft type", "Call sign", "Flight number",
                "Maximum number of text labels reached.", "Registration", "Feet", "Meters",
                "Km/h", "Knots", "Mph",
                "Get push notifications based on flight events of your choosing.",
                "Blocked", "Show silently and minimize", "Show silently",
                "Make sound and pop on screen",
                "UTC displays all time in 24-hour Coordinated Universal Time (UTC). To see 12-hour time, please choose a non-UTC timezone.",
                "Set the maximum number of aircraft you will see on the map.",
                "Aircraft Limit", "Restricted Flights", "Included", "None", "Only", "Flight level",
                "AIRMETS / SIGMETS",
                "AIRMETS/SIGMETS issued by authorities forecasting significant weather events that are potentially hazardous to flights in the area for which they are issued, refreshed every 30 minutes.",
                "High level significant weather",
                "Forecasted areas of high level significant weather, available for up to 24-hour period in six hour increments.",
                "Hi-res icing",
                "Forecast of icing severity for 36 hours into the future at flight levels FL060 through FL300. Forecast information is generated by the U.S. Aviation Weather Center and categorized as none, trace, light, moderate, or severe.",
                "Hi-res turbulence",
                "Forecast of turbulence for 36 hours into the future at flight levels FL100 through FL450. Forecast information is generated by the U.S. Aviation Weather Center and categorized as none, light, moderate, or severe.",
                "Lightning", "Recorded lightning strikes shown on the map, updated every 15 minutes.",
                "Opacity", "Winds", "Gradient", "Wind barbs", "Wind barb combinations", "Calm",
                "See wind speed and direction on our live map in increments of 1,000 feet from 1,000 to 51,000 feet. Displayed in either wind barbs or a color gradient. The winds layer is refreshed 12 times a day.",
                "Selected altitude", "%s ft"
        };
        for (String source : identitySources) {
            assertEquals(source, TranslationDictionary.translate(source));
        }

        assertEquals("关闭", TranslationDictionary.translate("Off"));
        assertEquals("高度", TranslationDictionary.translate("Altitude"));
        assertEquals("航线", TranslationDictionary.translate("Route"));
        assertEquals("速度", TranslationDictionary.translate("Speed"));
        assertEquals("气压高度", TranslationDictionary.translate("Barometric altitude"));
        assertEquals("地图", TranslationDictionary.translate("Map"));
        assertEquals("天气", TranslationDictionary.translate("Weather"));
        assertEquals("在实时地图上叠加显示当前全球降水量。总降水层每天刷新 12 次。",
                TranslationDictionary.translate(
                        "An overview of current global precipitation overlaid on our live map. "
                                + "The total precipitation layer is refreshed 12 times a day."));
        assertEquals(65, TranslationDictionary.size());
    }

    @Test
    public void preservesNullAndEmptyValues() {
        assertNull(TranslationDictionary.translate(null));
        assertEquals("", TranslationDictionary.translate(""));
    }

    @Test
    public void translatesAuditedLabelsAndContentDescriptions() {
        assertEquals("AR 视图", TranslationDictionary.translate("AR view"));
        assertEquals("定位到我的位置", TranslationDictionary.translate("Go to my location"));
        assertEquals("创建账户", TranslationDictionary.translate("Create account"));
        assertEquals("公务机", TranslationDictionary.translate("Business jets"));
    }

    @Test
    public void translatesAuditedStaticFinalTexts() {
        assertEquals(
                "在实时地图上叠加显示当前全球降水量。总降水层每天刷新 12 次。",
                TranslationDictionary.translate(
                        "An overview of current global precipitation overlaid on our live map. "
                                + "The total precipitation layer is refreshed 12 times a day."));
        assertEquals("想要去除广告吗？", TranslationDictionary.translate("Want to get rid of ads?"));
        assertEquals(
                "升级即可获得更快、无广告且功能和数据更丰富的 Flightradar24 体验。立即开始免费试用。",
                TranslationDictionary.translate(
                        "Upgrade for a faster, ad-free Flightradar24 experience with more features "
                                + "& data. Start your FREE trial today."));
        assertEquals("气压高度", TranslationDictionary.translate("BAROMETRIC ALT."));
        assertEquals("注册号", TranslationDictionary.translate("REG"));
        assertEquals("机龄", TranslationDictionary.translate("AGE"));
        assertEquals("收藏：已锁定", TranslationDictionary.translate("Bookmarks: locked"));
    }

    @Test
    public void translatesAuditedPreTransformLabels() {
        assertEquals("气压高度", TranslationDictionary.translate("Barometric altitude"));
        assertEquals("注册号", TranslationDictionary.translate("Reg"));
        assertEquals("气压高度", TranslationDictionary.translate("Barometric alt."));
    }

    @Test
    public void exactDictionaryHasAuditedSize() {
        assertEquals(65, TranslationDictionary.size());
    }

    @Test
    public void combinedCatalogTracksReviewedElevenPointNineBatch() {
        int total = TranslationDictionary.size() + ResourceTranslationDictionary.size();
        assertEquals(1282, total);
    }
}
