package io.github.fr24zh.localizer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

final class SettingsArrayTranslation {
    private static final Map<String, Entry> TRANSLATIONS = createTranslations();

    private SettingsArrayTranslation() {
    }

    static Object translate(String resourceEntryName, Object value) {
        if (!(value instanceof CharSequence[])) {
            return value;
        }
        Entry entry = TRANSLATIONS.get(resourceEntryName);
        if (entry == null) {
            return value;
        }
        CharSequence[] source = (CharSequence[]) value;
        if (source.length != entry.source.length) {
            return value;
        }
        for (int index = 0; index < source.length; index++) {
            if (!(source[index] instanceof String)
                    || !entry.source[index].equals(source[index])) {
                return value;
            }
        }
        return entry.translation.clone();
    }

    private static Map<String, Entry> createTranslations() {
        Map<String, Entry> translations = new LinkedHashMap<>();
        add(translations, "alert_condition_aircraft_type",
                new String[]{"Airline is", "Airline is not", "Altitude less than", "Altitude more than",
                        "Destination is", "Destination is not"},
                new String[]{"航空公司是", "航空公司不是", "高度低于", "高度高于", "目的地是", "目的地不是"});
        add(translations, "alert_condition_airline",
                new String[]{"Aircraft type is", "Aircraft type is not", "Altitude less than", "Altitude more than",
                        "Destination is", "Destination is not"},
                new String[]{"机型是", "机型不是", "高度低于", "高度高于", "目的地是", "目的地不是"});
        add(translations, "alert_condition_flight",
                new String[]{"Altitude less than", "Altitude more than"},
                new String[]{"高度低于", "高度高于"});
        add(translations, "alert_condition_registration",
                new String[]{"Altitude less than", "Altitude more than", "Destination is", "Destination is not"},
                new String[]{"高度低于", "高度高于", "目的地是", "目的地不是"});
        add(translations, "alert_region_array",
                new String[]{"Global", "Local"},
                new String[]{"全球", "本地"});
        add(translations, "alert_type_array",
                new String[]{"Flight", "Registration", "Airline", "Aircraft type"},
                new String[]{"航班", "注册号", "航空公司", "机型"});
        add(translations, "language_array_values",
                new String[]{"Device setting", "Deutsch", "English", "Español", "Français", "Indonesia",
                        "Italiano", "Polski", "Português", "Русский", "Türkçe", "日本語"},
                new String[]{"跟随设备设置", "Deutsch", "English", "Español", "Français", "Indonesia",
                        "Italiano", "Polski", "Português", "Русский", "Türkçe", "日本語"});
        add(translations, "settings_altitude_array",
                new String[]{"Feet", "Meters"},
                new String[]{"英尺", "米"});
        add(translations, "settings_distance_array",
                new String[]{"Kilometers", "Imperial miles", "Nautical miles"},
                new String[]{"千米", "英制英里", "海里"});
        add(translations, "settings_speed_array",
                new String[]{"Knots", "Km/h", "Mph"},
                new String[]{"节", "千米/小时", "英里/小时"});
        add(translations, "settings_temperature_array",
                new String[]{"Celsius", "Fahrenheit"},
                new String[]{"摄氏度", "华氏度"});
        add(translations, "settings_time_format_array",
                new String[]{"Device setting", "12-hour", "24-hour"},
                new String[]{"跟随设备设置", "12 小时制", "24 小时制"});
        add(translations, "settings_tz_array",
                new String[]{"Airport", "Local", "UTC"},
                new String[]{"机场", "本地", "UTC"});
        add(translations, "settings_vertical_speed_array",
                new String[]{"fpm", "m/s"},
                new String[]{"英尺/分钟", "米/秒"});
        add(translations, "settings_visibility_array",
                new String[]{"Off", "15 min", "30 min", "60 min", "120 min", "240 min"},
                new String[]{"关闭", "15 分钟", "30 分钟", "60 分钟", "120 分钟", "240 分钟"});
        add(translations, "settings_wind_speed_array",
                new String[]{"Knots", "Km/h", "Mph", "m/s"},
                new String[]{"节", "千米/小时", "英里/小时", "米/秒"});
        return Collections.unmodifiableMap(translations);
    }

    private static void add(
            Map<String, Entry> translations,
            String resourceEntryName,
            String[] source,
            String[] translation) {
        translations.put(resourceEntryName, new Entry(source, translation));
    }

    private static final class Entry {
        private final String[] source;
        private final String[] translation;

        private Entry(String[] source, String[] translation) {
            this.source = source;
            this.translation = translation;
        }
    }
}
