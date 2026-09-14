package io.github.fr24zh.localizer;

final class HookTranslation {
    private HookTranslation() {
    }

    static Object translateResourceResult(
            String resourceEntryName,
            Object value,
            Object[] formatArguments) {
        if (!(value instanceof String)) {
            return value;
        }
        String translated = ResourceTranslationDictionary.translate(
                resourceEntryName,
                formatArguments);
        if (translated != null) {
            return translated;
        }
        return value;
    }

    static Object translateRawResourceResult(String resourceEntryName, Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        String template = ResourceTranslationDictionary.template(resourceEntryName);
        if (template == null) {
            return value;
        }
        if (isAuditedRawTemplate(resourceEntryName, (String) value, template)) {
            return template;
        }
        if (JavaFormatSignature.hasArguments((String) value)
                || JavaFormatSignature.hasArguments(template)) {
            return value;
        }
        return template;
    }

    static Object translateTextArrayResult(
            String resourceEntryName,
            Object value,
            Object[] callArguments) {
        if (callArguments == null
                || callArguments.length != 1
                || HookArguments.typedArrayIndex(callArguments) == null) {
            return value;
        }
        return SettingsArrayTranslation.translate(resourceEntryName, value);
    }

    private static boolean isAuditedRawTemplate(
            String resourceEntryName,
            String source,
            String translatedTemplate) {
        if ("settings_aircraft_info_desc".equals(resourceEntryName)) {
            return "Labels travel with the aircraft and are shown when there are fewer than %d aircraft on the map."
                    .equals(source)
                    && "地图上的航空器少于 %d 架时，标签会随航空器移动并显示。"
                    .equals(translatedTemplate);
        }
        if ("bookmark_eta".equals(resourceEntryName)) {
            return "ETA %s".equals(source) && "预计到达 %s".equals(translatedTemplate);
        }
        if ("eta_ago".equals(resourceEntryName)) {
            return "%s ago".equals(source) && "%s 前".equals(translatedTemplate);
        }
        if ("eta_in".equals(resourceEntryName)) {
            return "in %s".equals(source) && "%s 后".equals(translatedTemplate);
        }
        if ("share_text".equals(resourceEntryName)) {
            return "Check out Flightradar24, the app that turns your phone into an air traffic radar. %s"
                    .equals(source)
                    && "试试 Flightradar24，这款应用能把手机变成空中交通雷达：%s"
                    .equals(translatedTemplate);
        }
        if ("geofence_notification_title".equals(resourceEntryName)) {
            return "Welcome to %s".equals(source) && "欢迎来到 %s".equals(translatedTemplate);
        }
        if ("settings_weather_winds_level_selected_value".equals(resourceEntryName)) {
            return "%s ft".equals(source) && "%s 英尺".equals(translatedTemplate);
        }
        if ("alert_condition_too_short".equals(resourceEntryName)) {
            return "Condition %d too short.".equals(source)
                    && "条件 %d 太短。".equals(translatedTemplate);
        }
        if ("alert_emergency_history_desc".equals(resourceEntryName)) {
            return "Emergency alert (squawk %s)".equals(source)
                    && "紧急提醒（应答机代码 %s）".equals(translatedTemplate);
        }
        if ("airport_diff_min".equals(resourceEntryName)) {
            return "%s min ago".equals(source) && "%s 分钟前".equals(translatedTemplate);
        }
        if ("airport_diff_hrs".equals(resourceEntryName)) {
            return "%s hrs ago".equals(source) && "%s 小时前".equals(translatedTemplate);
        }
        if ("airport_diff_days".equals(resourceEntryName)) {
            return "%s days ago".equals(source) && "%s 天前".equals(translatedTemplate);
        }
        if ("airport_diff_months".equals(resourceEntryName)) {
            return "%s months ago".equals(source) && "%s 个月前".equals(translatedTemplate);
        }
        if ("airport_diff_years".equals(resourceEntryName)) {
            return "%s years ago".equals(source) && "%s 年前".equals(translatedTemplate);
        }
        if ("search_status_landed".equals(resourceEntryName)) {
            return "Landed %s".equals(source) && "已于 %s 降落".equals(translatedTemplate);
        }
        if ("search_status_diverted_to".equals(resourceEntryName)) {
            return "Diverted to %s".equals(source) && "已备降至 %s".equals(translatedTemplate);
        }
        if ("search_status_diverting_to".equals(resourceEntryName)) {
            return "Diverting to %s".equals(source) && "正在备降至 %s".equals(translatedTemplate);
        }
        if ("search_status_estimated_arr_time".equals(resourceEntryName)) {
            return "Estimated arrival %s".equals(source) && "预计于 %s 到达".equals(translatedTemplate);
        }
        if ("cab_operated_by".equals(resourceEntryName)) {
            return "Operated by %s".equals(source) && "由 %s 执飞".equals(translatedTemplate);
        }
        if ("search_airline_msg".equals(resourceEntryName)) {
            return "Only %s flights currently in Flightradar24 coverage are listed.".equals(source)
                    && "仅列出当前在 Flightradar24 覆盖范围内的 %s 航班。".equals(translatedTemplate);
        }
        if ("search_found_aircraft".equals(resourceEntryName)) {
            return "%d of %d aircraft".equals(source)
                    && "%1$d/%2$d 架航空器".equals(translatedTemplate);
        }
        if ("search_nearby_away".equals(resourceEntryName)) {
            return "%s away".equals(source) && "距此 %s".equals(translatedTemplate);
        }
        return "search_status_estimated_dep_time".equals(resourceEntryName)
                && "Estimated departure %s".equals(source)
                && "预计于 %s 起飞".equals(translatedTemplate);
    }

    static Object translateResourceCall(
            String resourceEntryName,
            Object value,
            Object[] callArguments) {
        if (HookArguments.hasFormatArguments(callArguments)) {
            return translateResourceResult(
                    resourceEntryName,
                    value,
                    HookArguments.formatArguments(callArguments));
        }
        return translateRawResourceResult(resourceEntryName, value);
    }

    static Object translateResourceCall(
            String resourceEntryName,
            Object value,
            Object[] callArguments,
            StackTraceElement[] stackTrace) {
        if ("selected".equals(resourceEntryName)) {
            return translateSelectedResource(value, callArguments, stackTrace);
        }
        return translateResourceCall(resourceEntryName, value, callArguments);
    }

    static boolean needsSelectedContextStack(
            String resourceEntryName,
            Object value,
            Object[] callArguments) {
        return "selected".equals(resourceEntryName)
                && "%d selected".equals(value)
                && callArguments != null
                && callArguments.length == 1
                && callArguments[0] instanceof Integer
                && ((Integer) callArguments[0]) >= 0;
    }

    private static Object translateSelectedResource(
            Object value,
            Object[] callArguments,
            StackTraceElement[] stackTrace) {
        if (!needsSelectedContextStack("selected", value, callArguments)
                || stackTrace == null
                || stackTrace.length == 0) {
            return value;
        }

        boolean composeAccessibilityContext = false;
        for (StackTraceElement frame : stackTrace) {
            if (frame == null) {
                continue;
            }
            String className = frame.getClassName();
            if ("onItemCheckedStateChanged".equals(frame.getMethodName())
                    && (className.startsWith(
                            "com.flightradar24free.SettingsAlertsHistoryActivity$")
                    || className.startsWith(
                            "com.flightradar24free.feature.alerts.view.CustomAlertsFragment$"))) {
                return "已选择 %d 项";
            }
            if ("AndroidComposeViewAccessibilityDelegateCompat.android.kt"
                    .equals(frame.getFileName())) {
                composeAccessibilityContext = true;
            }
        }
        return composeAccessibilityContext ? "已选择" : value;
    }

    static Object translateQuantityResourceResult(
            String resourceEntryName,
            Object value,
            Object[] callArguments) {
        if (!(value instanceof String)) {
            return value;
        }
        if ("alert_are_you_sure_alerts".equals(resourceEntryName)
                || "alert_history_are_you_sure".equals(resourceEntryName)) {
            return translateAlertDeletionPlural(
                    resourceEntryName,
                    (String) value,
                    callArguments);
        }
        if ("filters_add_limit_error".equals(resourceEntryName)) {
            return translateFilterLimitPlural((String) value, callArguments);
        }
        if ("filters_num".equals(resourceEntryName)) {
            return translateFiltersNumPlural((String) value, callArguments);
        }
        return translateRemainingFirstPartyPlural(resourceEntryName, (String) value, callArguments);
    }

    private static Object translateFiltersNumPlural(String value, Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantity(callArguments);
        Object[] formatArguments = HookArguments.quantityStringFormatArguments(callArguments);
        if (quantity == null
                || formatArguments == null
                || formatArguments.length != 1
                || !(formatArguments[0] instanceof Number)
                || ((Number) formatArguments[0]).intValue() != quantity) {
            return value;
        }
        String expectedSource = quantity == 1
                ? "1 filter"
                : quantity + " filters";
        if (!expectedSource.equals(value)) {
            return value;
        }
        String translated = ResourceTranslationDictionary.translate(
                "filters_num",
                new Object[]{quantity});
        return translated == null ? value : translated;
    }

    private static Object translateRemainingFirstPartyPlural(
            String resourceEntryName,
            String value,
            Object[] callArguments) {
        if ("cab_aircraft_age_value".equals(resourceEntryName)) {
            return translateAircraftAgePlural(value, callArguments);
        }
        if ("bookmark_char_counter".equals(resourceEntryName)
                || "custom_filter_list_limit_remaining".equals(resourceEntryName)
                || "filter_search_results".equals(resourceEntryName)
                || "session_sessions".equals(resourceEntryName)
                || "search_found_airport_airports".equals(resourceEntryName)
                || "search_found_flight_airline".equals(resourceEntryName)) {
            return translateNumericFormatPlural(resourceEntryName, value, callArguments);
        }
        if ("cab_myfr24_travellers_total_airport_plurals".equals(resourceEntryName)
                || "cab_myfr24_travellers_total_plurals".equals(resourceEntryName)) {
            return translateDisplayCountPlural(resourceEntryName, value, callArguments);
        }
        if ("day_days".equals(resourceEntryName)
                || "hour_hours".equals(resourceEntryName)
                || "search_found_airline".equals(resourceEntryName)
                || "search_found_airport".equals(resourceEntryName)
                || "search_found_flight".equals(resourceEntryName)
                || "dialog_3d_sessions_remaining".equals(resourceEntryName)) {
            return translateUnformattedPlural(resourceEntryName, value, callArguments);
        }
        return value;
    }

    private static Object translateAircraftAgePlural(String value, Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantity(callArguments);
        Object[] formatArguments = HookArguments.quantityStringFormatArguments(callArguments);
        if (quantity != null
                && formatArguments != null
                && formatArguments.length == 1
                && formatArguments[0] instanceof Number
                && ((Number) formatArguments[0]).intValue() == quantity) {
            String expectedSource = quantity == 1 ? "1 year" : quantity + " years";
            return expectedSource.equals(value) ? quantity + " 年" : value;
        }
        Integer rawQuantity = HookArguments.quantityStringQuantityWithoutFormatArguments(callArguments);
        if (rawQuantity == null) {
            return value;
        }
        String expectedSource = rawQuantity == 1 ? "%d year" : "%d years";
        return expectedSource.equals(value) ? "%d 年" : value;
    }

    private static Object translateNumericFormatPlural(
            String resourceEntryName,
            String value,
            Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantity(callArguments);
        Object[] formatArguments = HookArguments.quantityStringFormatArguments(callArguments);
        if (quantity == null
                || formatArguments == null
                || formatArguments.length != 1
                || !(formatArguments[0] instanceof Number)
                || ((Number) formatArguments[0]).intValue() != quantity) {
            return value;
        }
        String expectedSource;
        String translated;
        if ("bookmark_char_counter".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "1 character left" : quantity + " characters left";
            translated = "还可输入 " + quantity + " 个字符";
        } else if ("custom_filter_list_limit_remaining".equals(resourceEntryName)) {
            expectedSource = quantity + " remaining";
            translated = "剩余 " + quantity + " 个";
        } else if ("filter_search_results".equals(resourceEntryName)) {
            expectedSource = quantity + " Results";
            translated = quantity + " 个结果";
        } else if ("session_sessions".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "1 session" : quantity + " sessions";
            translated = quantity + " 个会话";
        } else if ("search_found_airport_airports".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "airport found" : "airports found";
            translated = "个机场";
        } else {
            expectedSource = quantity == 1 ? "flight found" : "flights found";
            translated = "个航班";
        }
        return expectedSource.equals(value) ? translated : value;
    }

    private static Object translateDisplayCountPlural(
            String resourceEntryName,
            String value,
            Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantity(callArguments);
        Object[] formatArguments = HookArguments.quantityStringFormatArguments(callArguments);
        if (quantity == null
                || formatArguments == null
                || formatArguments.length != 1
                || !(formatArguments[0] instanceof String)
                || ((String) formatArguments[0]).isEmpty()) {
            return value;
        }
        String displayCount = (String) formatArguments[0];
        if ("cab_myfr24_travellers_total_airport_plurals".equals(resourceEntryName)) {
            String expectedSource = quantity == 1
                    ? "A total of " + displayCount + " user have visited this airport."
                    : "A total of " + displayCount + " users have visited this airport.";
            return expectedSource.equals(value) ? "共有 " + displayCount + " 位用户到访过该机场。" : value;
        }
        String expectedSource = "A total of " + displayCount + " users have taken this flight.";
        return expectedSource.equals(value) ? "共有 " + displayCount + " 位用户乘坐过该航班。" : value;
    }

    private static Object translateUnformattedPlural(
            String resourceEntryName,
            String value,
            Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantityWithoutFormatArguments(callArguments);
        if (quantity == null) {
            return value;
        }
        String expectedSource;
        String translated;
        if ("day_days".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "day" : "days";
            translated = "天";
        } else if ("hour_hours".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "hour" : "hours";
            translated = "小时";
        } else if ("search_found_airline".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "airline" : "airlines";
            translated = "家航空公司";
        } else if ("search_found_airport".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "airport" : "airports";
            translated = "个机场";
        } else if ("search_found_flight".equals(resourceEntryName)) {
            expectedSource = quantity == 1 ? "flight" : "flights";
            translated = "个航班";
        } else {
            expectedSource = quantity == 1
                    ? "Watch a video | Get %d session"
                    : "Watch a video | Get %d sessions";
            translated = "观看视频 | 获得 %d 次 3D 体验";
        }
        return expectedSource.equals(value) ? translated : value;
    }

    private static Object translateAlertDeletionPlural(
            String resourceEntryName,
            String value,
            Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantityWithoutFormatArguments(callArguments);
        if (quantity == null) {
            return value;
        }
        boolean history = "alert_history_are_you_sure".equals(resourceEntryName);
        String expectedSource;
        if (history) {
            expectedSource = quantity == 1
                    ? "Are you sure you want to delete selected alert log?"
                    : "Are you sure you want to delete selected alert logs?";
        } else {
            expectedSource = quantity == 1
                    ? "Are you sure you want to delete selected alert?"
                    : "Are you sure you want to delete selected alerts?";
        }
        if (!expectedSource.equals(value)) {
            return value;
        }
        return history
                ? "确定要删除所选提醒记录吗？"
                : "确定要删除所选提醒吗？";
    }

    private static Object translateFilterLimitPlural(String value, Object[] callArguments) {
        Integer quantity = HookArguments.quantityStringQuantity(callArguments);
        Object[] formatArguments = HookArguments.quantityStringFormatArguments(callArguments);
        if (quantity == null
                || formatArguments == null
                || formatArguments.length != 2
                || !(formatArguments[0] instanceof Number)
                || !(formatArguments[1] instanceof Number)) {
            return value;
        }
        int limit = ((Number) formatArguments[0]).intValue();
        int remove = ((Number) formatArguments[1]).intValue();
        if (limit < 0 || remove < 0 || remove != quantity) {
            return value;
        }
        String expectedSource = "Filters are limited to " + limit
                + " values. Please remove " + remove
                + (quantity == 1 ? " value" : " values")
                + " to continue.";
        if (!expectedSource.equals(value)) {
            return value;
        }
        return "筛选值最多为 " + limit + " 个。请移除 " + remove + " 个值后继续。";
    }

    static Object translateTextArgument(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        String source = (String) value;
        String translated = TranslationDictionary.translate(source);
        if (!source.equals(translated)) {
            return translated;
        }
        return DynamicLabelTranslation.translateText(value);
    }

    static Object translateTextArgument(String viewEntryName, Object value) {
        if ("txtLanguage".equals(viewEntryName)
                && value instanceof String
                && "语言 - Language".equals(value)) {
            return "语言";
        }
        Object translated = translateTextArgument(value);
        if (translated != value) {
            return translated;
        }
        return FlightDetailViewTextTranslation.translate(viewEntryName, value);
    }

    static Object translateContentDescriptionArgument(Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        String source = (String) value;
        String translated = TranslationDictionary.translate(source);
        if (!source.equals(translated)) {
            return translated;
        }
        return DynamicLabelTranslation.translateContentDescription(value);
    }
}
