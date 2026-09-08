package io.github.fr24zh.localizer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.text.SpannedString;

import org.junit.Test;

public final class SettingsArrayTranslationTest {
    @Test
    public void translatesOnlySixteenExactArraysIntoFreshCopies() {
        Object[][] cases = {
                {"alert_condition_aircraft_type",
                        strings("Airline is", "Airline is not", "Altitude less than", "Altitude more than",
                                "Destination is", "Destination is not"),
                        strings("航空公司是", "航空公司不是", "高度低于", "高度高于", "目的地是", "目的地不是")},
                {"alert_condition_airline",
                        strings("Aircraft type is", "Aircraft type is not", "Altitude less than", "Altitude more than",
                                "Destination is", "Destination is not"),
                        strings("机型是", "机型不是", "高度低于", "高度高于", "目的地是", "目的地不是")},
                {"alert_condition_flight",
                        strings("Altitude less than", "Altitude more than"),
                        strings("高度低于", "高度高于")},
                {"alert_condition_registration",
                        strings("Altitude less than", "Altitude more than", "Destination is", "Destination is not"),
                        strings("高度低于", "高度高于", "目的地是", "目的地不是")},
                {"alert_region_array", strings("Global", "Local"), strings("全球", "本地")},
                {"alert_type_array", strings("Flight", "Registration", "Airline", "Aircraft type"),
                        strings("航班", "注册号", "航空公司", "机型")},
                {"language_array_values",
                        strings("Device setting", "Deutsch", "English", "Español", "Français", "Indonesia",
                                "Italiano", "Polski", "Português", "Русский", "Türkçe", "日本語"),
                        strings("跟随设备设置", "Deutsch", "English", "Español", "Français", "Indonesia",
                                "Italiano", "Polski", "Português", "Русский", "Türkçe", "日本語")},
                {"settings_altitude_array", strings("Feet", "Meters"), strings("英尺", "米")},
                {"settings_distance_array", strings("Kilometers", "Imperial miles", "Nautical miles"),
                        strings("千米", "英制英里", "海里")},
                {"settings_speed_array", strings("Knots", "Km/h", "Mph"),
                        strings("节", "千米/小时", "英里/小时")},
                {"settings_temperature_array", strings("Celsius", "Fahrenheit"),
                        strings("摄氏度", "华氏度")},
                {"settings_time_format_array", strings("Device setting", "12-hour", "24-hour"),
                        strings("跟随设备设置", "12 小时制", "24 小时制")},
                {"settings_tz_array", strings("Airport", "Local", "UTC"),
                        strings("机场", "本地", "UTC")},
                {"settings_vertical_speed_array", strings("fpm", "m/s"),
                        strings("英尺/分钟", "米/秒")},
                {"settings_visibility_array", strings("Off", "15 min", "30 min", "60 min", "120 min", "240 min"),
                        strings("关闭", "15 分钟", "30 分钟", "60 分钟", "120 分钟", "240 分钟")},
                {"settings_wind_speed_array", strings("Knots", "Km/h", "Mph", "m/s"),
                        strings("节", "千米/小时", "英里/小时", "米/秒")}
        };

        int elementCount = 0;
        for (Object[] testCase : cases) {
            String resourceName = (String) testCase[0];
            CharSequence[] source = (CharSequence[]) testCase[1];
            CharSequence[] expected = (CharSequence[]) testCase[2];
            CharSequence[] originalSnapshot = source.clone();

            Object first = SettingsArrayTranslation.translate(resourceName, source);
            Object second = SettingsArrayTranslation.translate(resourceName, source);

            assertArrayEquals(expected, (Object[]) first);
            assertArrayEquals(originalSnapshot, source);
            assertNotSame(source, first);
            assertNotSame(first, second);
            elementCount += expected.length;
        }
        assertEquals(16, cases.length);
        assertEquals(64, elementCount);
    }

    @Test
    public void preservesUnknownExcludedAndNearMatchArrayNamesByIdentity() {
        CharSequence[] exactAltitude = strings("Feet", "Meters");
        String[] excludedNames = {
                "language_array_keys", "alert_type_arrays",
                "settings_alert_type_array", "environment_array", "device_model_array",
                "third_party_array", "settings_altitude_arrays", "Settings_altitude_array"
        };
        for (String resourceName : excludedNames) {
            assertSame(exactAltitude, SettingsArrayTranslation.translate(resourceName, exactAltitude));
        }
        assertSame(exactAltitude, SettingsArrayTranslation.translate(null, exactAltitude));
    }

    @Test
    public void preservesWrongLengthOrderCaseAndTextByIdentity() {
        CharSequence[] missing = strings("Feet");
        CharSequence[] extra = strings("Feet", "Meters", "Other");
        CharSequence[] reversed = strings("Meters", "Feet");
        CharSequence[] wrongCase = strings("feet", "Meters");
        CharSequence[] nearText = strings("Feet ", "Meters");
        CharSequence[] nullElement = new CharSequence[]{"Feet", null};

        assertSame(missing, SettingsArrayTranslation.translate("settings_altitude_array", missing));
        assertSame(extra, SettingsArrayTranslation.translate("settings_altitude_array", extra));
        assertSame(reversed, SettingsArrayTranslation.translate("settings_altitude_array", reversed));
        assertSame(wrongCase, SettingsArrayTranslation.translate("settings_altitude_array", wrongCase));
        assertSame(nearText, SettingsArrayTranslation.translate("settings_altitude_array", nearText));
        assertSame(nullElement, SettingsArrayTranslation.translate("settings_altitude_array", nullElement));

        CharSequence[] wrongLanguageValue = strings(
                "Device settings", "Deutsch", "English", "Español", "Français", "Indonesia",
                "Italiano", "Polski", "Português", "Русский", "Türkçe", "日本語");
        assertSame(wrongLanguageValue,
                SettingsArrayTranslation.translate("language_array_values", wrongLanguageValue));
    }

    @Test
    public void preservesNonStringElementsNullAndNonArraysByIdentity() {
        CharSequence[] mutableElement = new CharSequence[]{"Feet", new StringBuilder("Meters")};
        CharSequence[] spannedElement = new CharSequence[]{"Feet", new SpannedString("Meters")};
        Object object = new Object();
        Object[] objectArray = new Object[]{"Feet", "Meters"};
        int[] primitiveArray = new int[]{1, 2};

        assertSame(mutableElement, SettingsArrayTranslation.translate("settings_altitude_array", mutableElement));
        assertSame(spannedElement, SettingsArrayTranslation.translate("settings_altitude_array", spannedElement));
        assertNull(SettingsArrayTranslation.translate("settings_altitude_array", null));
        assertSame(object, SettingsArrayTranslation.translate("settings_altitude_array", object));
        assertSame(objectArray, SettingsArrayTranslation.translate("settings_altitude_array", objectArray));
        assertSame(primitiveArray, SettingsArrayTranslation.translate("settings_altitude_array", primitiveArray));
    }

    private static CharSequence[] strings(String... values) {
        return values;
    }

}
