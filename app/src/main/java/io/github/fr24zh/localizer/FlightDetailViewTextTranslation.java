package io.github.fr24zh.localizer;

import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class FlightDetailViewTextTranslation {
    private static final Pattern MORE_INFORMATION = Pattern.compile(
            "\\AMore ([A-Z0-9-]{1,20}) information\\z");
    private static final Pattern AIRCRAFT_TYPE = Pattern.compile(
            "\\AAIRCRAFT TYPE  \\(([A-Z0-9-]{1,20})\\)\\z");
    private static final String DATA_SOURCE_PREFIX = "Data source — ";
    private static final String FLIGHT_IDENTIFIER = "[A-Z0-9-]{1,20}";
    private static final String AIRPORT_CODE = "[A-Z0-9]{2,8}";
    private static final String TIME = "[0-5][0-9]:[0-5][0-9]";
    private static final String DISTANCE = "(?:0|[1-9][0-9]*|[1-9][0-9]{0,2}(?:,[0-9]{3})+)";
    private static final Pattern FLIGHT_FROM_TO = Pattern.compile(
            "\\A(" + FLIGHT_IDENTIFIER + ") FLIGHT FROM (" + AIRPORT_CODE
                    + ") TO (" + AIRPORT_CODE + ")\\z");
    private static final Pattern FLIGHT_HISTORY_MORE = Pattern.compile(
            "\\AMore (" + FLIGHT_IDENTIFIER + ") flights\\z");
    private static final Pattern YEAR = Pattern.compile("\\AYEAR ([0-9]{4})\\z");
    private static final Pattern DEPARTURE_DATE = Pattern.compile(
            "\\A(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) "
                    + "([1-9]|[12][0-9]|3[01])\\z");
    private static final Pattern DISTANCE_LEFT = Pattern.compile(
            "\\A(" + DISTANCE + ") (NM|km|mi), (" + TIME + ") ago\\z");
    private static final Pattern DISTANCE_RIGHT = Pattern.compile(
            "\\A(" + DISTANCE + ") (NM|km|mi), in (" + TIME + ")\\z");
    private static final Pattern TIME_ARRIVING = Pattern.compile(
            "\\AArriving in (" + TIME + ")\\z");
    private static final Pattern TIME_DEPARTED = Pattern.compile(
            "\\ADeparted (" + TIME + ") ago\\z");
    private static final Pattern SEARCH_RESULT_COUNT = Pattern.compile(
            "\\A([0-9]{1,9}) of ([0-9]{1,9}) (airport|airports|airline|airlines|flight|flights)\\z");

    private FlightDetailViewTextTranslation() {
    }

    static Object translate(String viewEntryName, Object value) {
        if ("txtMoreInfoHeaderTitle".equals(viewEntryName) && value instanceof String) {
            Matcher matcher = MORE_INFORMATION.matcher((String) value);
            if (matcher.matches()) {
                return "更多 " + matcher.group(1) + " 信息";
            }
        }
        if ("txtAircraftType".equals(viewEntryName)
                && (value instanceof String || value instanceof Spanned)) {
            Matcher matcher = AIRCRAFT_TYPE.matcher(value.toString());
            if (matcher.matches()) {
                return "机型（" + matcher.group(1) + "）";
            }
        }
        if ("txtDataSourceHeader".equals(viewEntryName) && value instanceof String) {
            String source = (String) value;
            if (source.startsWith(DATA_SOURCE_PREFIX)) {
                String parameter = source.substring(DATA_SOURCE_PREFIX.length());
                if (parameter.length() >= 1
                        && parameter.length() <= 80
                        && parameter.indexOf('\r') < 0
                        && parameter.indexOf('\n') < 0) {
                    return "数据来源 — " + parameter;
                }
            }
        }
        if (!(value instanceof String)) {
            return value;
        }
        String source = (String) value;
        if ("txtTitleRight".equals(viewEntryName)) {
            Matcher matcher = SEARCH_RESULT_COUNT.matcher(source);
            if (matcher.matches()) {
                String category = matcher.group(3);
                String unit;
                if ("airport".equals(category) || "airports".equals(category)) {
                    unit = "个机场";
                } else if ("airline".equals(category) || "airlines".equals(category)) {
                    unit = "家航空公司";
                } else {
                    unit = "个航班";
                }
                return matcher.group(1) + "/" + matcher.group(2) + " " + unit;
            }
        }
        if ("txtFlightFromTo".equals(viewEntryName)) {
            Matcher matcher = FLIGHT_FROM_TO.matcher(source);
            if (matcher.matches()) {
                return matcher.group(1) + " 航班：" + matcher.group(2) + " → " + matcher.group(3);
            }
        }
        if ("txtFlightHistoryMore".equals(viewEntryName)) {
            Matcher matcher = FLIGHT_HISTORY_MORE.matcher(source);
            if (matcher.matches()) {
                return "更多 " + matcher.group(1) + " 航班";
            }
        }
        if ("txtTitle".equals(viewEntryName)) {
            Matcher matcher = YEAR.matcher(source);
            if (matcher.matches()) {
                return matcher.group(1) + " 年";
            }
        }
        if ("txtdepatureDate".equals(viewEntryName)) {
            Matcher matcher = DEPARTURE_DATE.matcher(source);
            if (matcher.matches()) {
                return monthNumber(matcher.group(1)) + "月" + matcher.group(2) + "日";
            }
        }
        if ("txtDistanceLeft".equals(viewEntryName)) {
            Matcher matcher = DISTANCE_LEFT.matcher(source);
            if (matcher.matches()) {
                return matcher.group(1) + " " + matcher.group(2) + "，" + matcher.group(3) + " 前";
            }
        }
        if ("txtDistanceRight".equals(viewEntryName)) {
            Matcher matcher = DISTANCE_RIGHT.matcher(source);
            if (matcher.matches()) {
                return matcher.group(1) + " " + matcher.group(2) + "，" + matcher.group(3) + " 后";
            }
        }
        if ("txtTimeArriving".equals(viewEntryName)) {
            Matcher matcher = TIME_ARRIVING.matcher(source);
            if (matcher.matches()) {
                return "将在 " + matcher.group(1) + " 后到达";
            }
        }
        if ("txtTimeDeparted".equals(viewEntryName)) {
            Matcher matcher = TIME_DEPARTED.matcher(source);
            if (matcher.matches()) {
                return "已于 " + matcher.group(1) + " 前起飞";
            }
        }
        return value;
    }

    private static String monthNumber(String month) {
        if ("Jan".equals(month)) {
            return "1";
        }
        if ("Feb".equals(month)) {
            return "2";
        }
        if ("Mar".equals(month)) {
            return "3";
        }
        if ("Apr".equals(month)) {
            return "4";
        }
        if ("May".equals(month)) {
            return "5";
        }
        if ("Jun".equals(month)) {
            return "6";
        }
        if ("Jul".equals(month)) {
            return "7";
        }
        if ("Aug".equals(month)) {
            return "8";
        }
        if ("Sep".equals(month)) {
            return "9";
        }
        if ("Oct".equals(month)) {
            return "10";
        }
        if ("Nov".equals(month)) {
            return "11";
        }
        return "12";
    }
}
