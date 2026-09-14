package io.github.fr24zh.localizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

public final class ResourceTranslationDictionaryTest {
    @Test
    public void translatesNotificationGeofenceUpdateAndSessionResources() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("accessibility_filters_locked", "要解锁筛选器功能，您需要拥有一个账户");
        expected.put("app_update_continue_btn", "继续");
        expected.put("app_update_download_btn", "立即更新");
        expected.put("app_update_msg", "您正在使用不受支持的 Flightradar24 版本。请更新到最新版本，以获得更好的航班追踪体验。");
        expected.put("app_update_title", "更新 Flightradar24");
        expected.put("authenticate_logged_out_number_of_active_sessions_exceeded_description", "您可能在另一台设备上登录，导致超出当前订阅类型允许的活动会话数量。您可以在此设备上重新登录。");
        expected.put("authenticate_logged_out_number_of_active_sessions_exceeded_title", "您已退出登录");
        expected.put("authenticate_logged_out_session_expired_description", "您可以重新登录。如果无法登录或遇到其他问题，请联系支持团队。");
        expected.put("authenticate_logged_out_session_expired_title", "会话已过期");
        expected.put("geofence_notification_text", "获取最新抵港、离港及天气信息");
        expected.put("geofence_notification_title", "欢迎来到 %s");
        expected.put("geofence_popup_btn1", "好的，知道了");
        expected.put("geofence_popup_btn2", "更新设置");
        expected.put("geofence_popup_msg1", "使用 Flightradar24，及时了解您关注的航班动态。");
        expected.put("geofence_popup_msg2", "如果不想在到访机场时收到通知，请在“提醒”中更新机场通知偏好设置。");
        expected.put("notification_channel_live_notifications", "实时通知");
        expected.put("notification_channel_new_features", "新功能");
        expected.put("push_notification_aircraft", "航空器：");
        expected.put("push_notification_altitude", "高度：");
        expected.put("push_notification_callsign", "呼号：");
        expected.put("push_notification_route", "航线：");
        expected.put("push_notification_special_flight_title", "精选航班提醒");
        expected.put("push_notification_title", "航班提醒");
        expected.put("push_notification_title_7600", "无线电故障提醒");
        expected.put("push_notification_title_7700", "一般紧急情况提醒");
        expected.put("view_3d_error", "很抱歉，无法加载 3D 视图。");

        assertEquals(26, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
        }
    }

    @Test
    public void exposesAuditedEtaAndShareRawTemplates() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("eta_ago", "%s 前");
        expected.put("eta_in", "%s 后");
        expected.put("share_text",
                "试试 Flightradar24，这款应用能把手机变成空中交通雷达：%s");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
        }
    }

    @Test
    public void translatesSearchStatsAndDisruptionResources() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("stats_tab_title0", "统计");
        expected.put("stats_tab_title2", "运行异常");
        expected.put("stats_tab_title3", "热门航班");
        expected.put("stats_tab_title4", "收藏");
        expected.put("stats_most_tracked_flights", "追踪人数最多的航班");
        expected.put("stats_most_tracked_flights_footer",
                "查看其他 Flightradar24 用户当前正在关注的前 10 个航班。");
        expected.put("unit_speed_kts_accessible", "节");
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertEquals("关注人数：964", ResourceTranslationDictionary.translate(
                "accessibility_followers", new Object[]{964}));
        assertEquals("到港运行异常指数：2.6", ResourceTranslationDictionary.translate(
                "accessibility_disrupt_arrival_index", new Object[]{"2.6"}));
        assertEquals("离港运行异常指数：2.6", ResourceTranslationDictionary.translate(
                "accessibility_disrupt_departure_index", new Object[]{"2.6"}));
        assertEquals("风向不定，风速 4 kt", ResourceTranslationDictionary.translate(
                "accessibility_wind_vrb", new Object[]{4, "kt"}));
    }

    @Test
    public void rejectsInvalidSearchStatsAndDisruptionArguments() {
        assertNull(ResourceTranslationDictionary.translate("missing_stats_key", null));
        assertNull(ResourceTranslationDictionary.translate("accessibility_followers", null));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_followers", new Object[]{"964"}));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_disrupt_arrival_index", null));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_disrupt_departure_index", null));
        assertNull(ResourceTranslationDictionary.translate("accessibility_wind_vrb", null));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_wind_vrb", new Object[]{"4", "kt"}));
    }

    @Test
    public void translatesRemainingAuditedNativeShellResources() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("airport_panel_latest_events", "最新动态");
        expected.put("login_email", "电子邮箱");
        expected.put("login_create_password", "创建密码");
        expected.put("login_logout", "退出登录");
        expected.put("login_subscription", "订阅方案");
        expected.put("subs_basic", "免费");
        expected.put("login_available_features", "可用功能");
        expected.put("login_upgrade_subscription", "升级订阅方案");
        expected.put("delete_account", "删除账户");
        expected.put("filter_route_visit_our_blog", "访问我们的博客，查看筛选功能的完整使用指南。");
        expected.put("custom_filter_type_airline", "航空公司");
        expected.put("custom_filter_type_aircraft", "航空器");
        expected.put("custom_filter_type_airport", "机场");
        expected.put("custom_filter_type_route", "航线");
        expected.put("custom_filter_type_categories", "类别");
        expected.put("custom_filter_type_advanced", "高级");
        expected.put("add_filter_title", "新建筛选条件");
        expected.put("add_filter_description", "选择要添加的筛选条件类型。");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
    }

    @Test
    public void translatesFlightDetailLabelsOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("cab_vertical_speed", "垂直速度");
        expected.put("cab_gps_altitude", "GPS 高度");
        expected.put("cab_track", "航迹");
        expected.put("cab_speed_altitude_chart", "速度与高度图");
        expected.put("cab_true_air_speed", "真空速");
        expected.put("cab_indicated_air_speed", "指示空速");
        expected.put("cab_mach", "马赫数");
        expected.put("cab_weather_wind", "风");
        expected.put("cab_weather_temperature", "温度");
        expected.put("cab_radar_fir_uir", "飞行情报区/高空飞行情报区");
        expected.put("cab_aircraft_mode_s_code", "ICAO 24 位地址");
        expected.put("cab_squawk", "应答机代码");
        expected.put("cab_latitude", "纬度");
        expected.put("cab_longitude", "经度");
        expected.put("cab_myfr24_title", "旅行者");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("cab_vertical_speed_missing", null));
        assertEquals("Vertical speed", TranslationDictionary.translate("Vertical speed"));
    }

    @Test
    public void translatesFlightDetailTopAndHistoryOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("cab_more_flight_from_to", "3U8732 航班：CAN → CTU");
        expected.put("cab_distance", "大圆距离");
        expected.put("cab_flight_time", "平均飞行时间");
        expected.put("cab_more_arrival_information", "到达信息");
        expected.put("cab_baggage", "行李转盘");
        expected.put("cab_history_history_more", "更多 3U8732 航班");
        expected.put("label_year", "2026 年");
        Map<String, String> actual = new LinkedHashMap<>();
        actual.put("cab_more_flight_from_to", ResourceTranslationDictionary.translate(
                "cab_more_flight_from_to", new Object[]{"3U8732", "CAN", "CTU"}));
        actual.put("cab_distance", ResourceTranslationDictionary.translate("cab_distance", null));
        actual.put("cab_flight_time", ResourceTranslationDictionary.translate("cab_flight_time", null));
        actual.put("cab_more_arrival_information", ResourceTranslationDictionary.translate(
                "cab_more_arrival_information", null));
        actual.put("cab_baggage", ResourceTranslationDictionary.translate("cab_baggage", null));
        actual.put("cab_history_history_more", ResourceTranslationDictionary.translate(
                "cab_history_history_more", new Object[]{"3U8732"}));
        actual.put("label_year", ResourceTranslationDictionary.translate(
                "label_year", new Object[]{"2026"}));
        assertEquals(expected, actual);
        assertNull(ResourceTranslationDictionary.translate("cab_more_flight_from_to_missing", null));
        assertEquals(
                "FLIGHT FROM",
                TranslationDictionary.translate("FLIGHT FROM"));
    }

    @Test
    public void formatsKnownResourceTemplates() {
        assertEquals(
                "更多 NS8035 信息",
                ResourceTranslationDictionary.translate(
                        "cab_more_info",
                        new Object[]{"NS8035"}));
        assertEquals(
                "正在备降至 — CKG（ZUCK）",
                ResourceTranslationDictionary.translate(
                        "cab_diverting_to",
                        new Object[]{"CKG", "ZUCK"}));
    }

    @Test
    public void formatsEarlyAuditedTemplatesAndRejectsMissingArguments() {
        Object[][] cases = {
                {"cab_small_arriving", new Object[]{"5 分钟"}, "将在 5 分钟后到达"},
                {"cab_small_arriving_ago", new Object[]{"5 分钟"}, "已于 5 分钟前到达"},
                {"cab_small_departed", new Object[]{"5 分钟"}, "已于 5 分钟前起飞"},
                {"cab_aircraft_type", new Object[]{"B738"}, "机型（B738）"},
                {"cab_aircraft_age", new Object[]{"8 年"}, "机龄（8 年）"},
                {"cab_aircraft_history", new Object[]{10}, "最近的 10 航班"},
                {"cab_data_source", new Object[]{"ADS-B"}, "数据来源 — ADS-B"},
                {"search_country_airports_title", new Object[]{"中国"}, "中国 的机场"},
                {"search_headers", new Object[]{1, 2, "航班"}, "1/2 航班"},
                {"settings_weather_winds_barbs_legend", new Object[]{"FL100"}, "风羽（FL100）"},
                {"settings_weather_winds_level_legend", new Object[]{"10000 ft"}, "风速（10000 ft）"},
                {"filters_delete_dialog_description", new Object[]{"筛选器"}, "确定要删除 筛选器 吗？"}
        };
        for (Object[] testCase : cases) {
            String key = (String) testCase[0];
            assertEquals(testCase[2], ResourceTranslationDictionary.translate(
                    key, (Object[]) testCase[1]));
            assertNull(ResourceTranslationDictionary.translate(key, new Object[]{}));
        }
        assertNull(ResourceTranslationDictionary.translate(
                "search_headers", new Object[]{"1", "2", "航班"}));
    }

    @Test
    public void returnsNullForUnknownOrInvalidTemplates() {
        assertNull(ResourceTranslationDictionary.translate("missing_name", null));
        assertNull(ResourceTranslationDictionary.translate(
                "cab_diverting_to",
                new Object[]{"CKG"}));
    }

    @Test
    public void returnsNullWhenDynamicTemplateHasNoFormatArguments() {
        assertNull(ResourceTranslationDictionary.translate("cab_more_info", null));
        assertNull(ResourceTranslationDictionary.translate("cab_more_info", new Object[]{}));
    }

    @Test
    public void staticTemplatesCanFormatWithoutArguments() {
        assertEquals(
                "static text",
                String.format(Locale.SIMPLIFIED_CHINESE, "static text", new Object[]{}));
    }

    @Test
    public void translatesMenuSearchAndFlightDetailResources() {
        assertEquals(
                "解锁 60 多项功能",
                ResourceTranslationDictionary.translate("menu_subscription_cta_50", null));
        assertEquals(
                "航班号、机场、航线或注册号",
                ResourceTranslationDictionary.translate("search_hint", null));
        assertEquals(
                "注册国",
                ResourceTranslationDictionary.translate("cab_reg_country", null));
        assertEquals(
                "AR 视图",
                ResourceTranslationDictionary.translate("menu_ar", new Object[]{}));
    }

    @Test
    public void translatesSettingsByResourceContext() {
        assertEquals(
                "标准",
                ResourceTranslationDictionary.translate("settings_map_type_map", null));
        assertEquals(
                "退出时确认",
                ResourceTranslationDictionary.translate("settings_display_prompt", null));
        assertEquals(
                "全球 3,000 个机场的当前天气。",
                ResourceTranslationDictionary.translate(
                        "settings_weather_basic_weather_desc", null));
    }

    @Test
    public void translatesWeatherLayerResourcesOnlyByExactResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("settings_weather_precipitation_intense", "强降水");
        expected.put("settings_weather_precipitation_intense_desc",
                "全球雷达系统在地图上显示各地降水区域和强度，每 30 分钟刷新一次。");
        expected.put("settings_weather_usardr", "北美雷达");
        expected.put("settings_weather_usardr_desc",
                "高分辨率北美雷达由多部雷达的图像拼接而成，覆盖美国本土、阿拉斯加、夏威夷、波多黎各及加拿大南部。其分辨率为 1 公里，每 2 分钟更新一次，可提供更详细、更及时的天气信息。");
        expected.put("settings_weather_ausrdr", "澳大利亚雷达");
        expected.put("settings_weather_ausrdr_desc",
                "澳大利亚雷达是覆盖全澳降水的合成图像，分辨率为 2 公里，每 7.5 分钟更新一次。");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("settings_weather_missing", null));
        assertEquals("Intense precipitation", TranslationDictionary.translate("Intense precipitation"));
        assertEquals("North American radar", TranslationDictionary.translate("North American radar"));
        assertEquals("Australian radar", TranslationDictionary.translate("Australian radar"));
    }

    @Test
    public void translatesMiscSettingsOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("language", "语言");
        expected.put("settings_time", "时间");
        expected.put("settings_time_format", "时间格式");
        expected.put("settings_temperature", "温度");
        expected.put("settings_speed", "航空器速度");
        expected.put("settings_speed_vertical", "垂直速度");
        expected.put("settings_speed_wind", "风速");
        expected.put("settings_distance_unit", "距离");
        expected.put("settings_accessibility", "无障碍");
        expected.put("settings_accessibility_map_controls", "地图控制");
        expected.put("settings_accessibility_map_controls_desc", "使用按钮缩放和移动地图");
        expected.put("settings_privacy", "隐私");
        expected.put("settings_personalized_ads", "隐私偏好中心");
        expected.put("settings_personalized_ads_button", "打开");
        expected.put("settings_personalized_ads_summary", "管理你的个人数据和广告偏好");
        expected.put("settings_analytics", "分析");
        expected.put("settings_analytics_summary", "发送分析数据，帮助我们改进应用。");
        expected.put("settings_crash_reporting", "崩溃报告");
        expected.put("settings_crash_reporting_summary", "发送应用崩溃报告，帮助我们改进使用体验。");
        expected.put("settings_performance_monitoring", "性能监控");
        expected.put("settings_performance_monitoring_summary", "发送性能数据，帮助我们改进使用体验。");
        expected.put("settings_read_tos", "阅读完整服务条款");
        expected.put("settings_read_tos_fr24", "Flightradar24 服务条款");
        expected.put("view_osl_title", "开源许可");
        expected.put("view_osl_click", "开源软件许可详情");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("settings_misc_missing", null));
        assertEquals("Language", TranslationDictionary.translate("Language"));
        assertEquals("Open", TranslationDictionary.translate("Open"));
        assertEquals("Analytics", TranslationDictionary.translate("Analytics"));
    }

    @Test
    public void translatesFiltersAlertsAirportsAndPlayback() {
        assertEquals(
                "使用筛选功能需要账户。",
                ResourceTranslationDictionary.translate("filters_create_account_title", null));
        assertEquals(
                "应答机代码 7700",
                ResourceTranslationDictionary.translate("settings_notification_7700_title", null));
        assertEquals(
                "机场与跑道详情",
                ResourceTranslationDictionary.translate(
                        "airport_panel_airport_and_runway_details", null));
        assertEquals(
                "开始回放",
                ResourceTranslationDictionary.translate("global_playback_start_playback", null));
    }

    @Test
    public void translatesReviewedFilterResourcesOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("unlock_learn_more", "了解更多");
        expected.put("filter_category_all", "所有类别");
        expected.put("filter_category_passenger", "客运");
        expected.put("filter_category_tooltip_info", "更多信息");
        expected.put("filter_category_cargo", "货运");
        expected.put("filter_category_military_and_government", "军用或政府");
        expected.put("filter_category_business_jets", "公务机");
        expected.put("filter_category_general_aviation", "通用航空");
        expected.put("filter_category_helicopters", "直升机");
        expected.put("filter_category_lighter_than_air", "轻于空气航空器");
        expected.put("add_custom_filter_button_text", "添加自定义筛选");
        expected.put("accessibility_collapse_panel", "收起面板");
        expected.put("filters_custom_filters_section_header", "筛选");
        expected.put("filters_custom_empty_header", "尚无已保存的筛选");
        expected.put("filters_custom_empty_description", "点击下方“添加自定义筛选”按钮来添加筛选。");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("filters_reviewed_missing", null));
        assertEquals("Learn more", TranslationDictionary.translate("Learn more"));
        assertEquals("More Info", TranslationDictionary.translate("More Info"));
        assertEquals("filters", TranslationDictionary.translate("filters"));
        assertEquals("更多信息", TranslationDictionary.translate("More info"));
        assertEquals("筛选", TranslationDictionary.translate("Filters"));
    }

    @Test
    public void translatesRemainingFilterCategoryTitlesOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("filter_category_gliders", "滑翔机");
        expected.put("filter_category_drones", "无人机");
        expected.put("filter_category_ground_vehicles", "地面车辆");
        expected.put("filter_category_other", "其他");
        expected.put("filter_category_uncategorized", "未分类");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("filter_category_remaining_missing", null));
        assertEquals("Other", TranslationDictionary.translate("Other"));
        assertEquals("Ground vehicle", TranslationDictionary.translate("Ground vehicle"));
        assertEquals("Non-categorised", TranslationDictionary.translate("Non-categorised"));
    }

    @Test
    public void translatesFilterCategoryExplainersOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("filter_category_passenger_explainer", "主要用于载客的商业航空器。");
        expected.put("filter_category_cargo_explainer", "仅运输货物的航空器。");
        expected.put("filter_category_military_and_government_explainer", "由军方或政府机构运营的航空器。");
        expected.put("filter_category_business_jets_explainer", "较大型的私人航空器，例如湾流、庞巴迪和皮拉图斯。");
        expected.put("filter_category_general_aviation_explainer", "非商业运输飞行，包括私人飞行、空中救护、航空测绘、飞行训练，以及用于仪表校准的航空器。");
        expected.put("filter_category_helicopters_explainer", "旋翼航空器。");
        expected.put("filter_category_lighter_than_air_explainer", "轻于空气的航空器包括各类充气飞艇。");
        expected.put("filter_category_gliders_explainer", "无动力航空器。");
        expected.put("filter_category_drones_explainer", "无人航空器，涵盖从小型消费级无人机到较大型无人机（UAV）。");
        expected.put("filter_category_ground_vehicles_explainer", "配备应答机的地面车辆，例如飞机推回牵引车、消防车和机场运行车辆。");
        expected.put("filter_category_other_explainer", "显示在 Flightradar24 上、但未归入其他类别的航空器（国际空间站、不明飞行物、圣诞老人等）。");
        expected.put("filter_category_uncategorized_explainer", "尚未在 Flightradar24 数据库中归入某个类别的航空器。");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("filter_category_explainer_missing", null));
        assertEquals("Unpowered aircraft.", TranslationDictionary.translate("Unpowered aircraft."));
        assertEquals("Aircraft that carry only cargo.", TranslationDictionary.translate(
                "Aircraft that carry only cargo."));
        assertEquals("Other", TranslationDictionary.translate("Other"));
    }

    @Test
    public void translatesFilterBlogPromptOnlyByResourceContext() {
        assertEquals(
                "访问我们的博客，查看筛选功能的完整使用指南",
                ResourceTranslationDictionary.translate("filters_info_blog_title", null));
        assertNull(ResourceTranslationDictionary.translate("filters_info_blog_title_missing", null));
        assertEquals(
                "Visit our blog for a complete guide on how to use filters",
                TranslationDictionary.translate(
                        "Visit our blog for a complete guide on how to use filters"));
        assertEquals(
                "Visit our blog for a complete guide on how to use filters >",
                TranslationDictionary.translate(
                        "Visit our blog for a complete guide on how to use filters >"));
    }

    @Test
    public void preservesReviewedNotificationAndPlaybackWording() {
        assertEquals(
                "根据你选择的航班事件接收推送通知。同时提醒上限：Silver 10 条｜Gold 25 条",
                ResourceTranslationDictionary.translate(
                        "settings_notifcation_custom_summary", null));
        assertEquals(
                "行为：",
                ResourceTranslationDictionary.translate("settings_notifications_behavior", null));
        assertEquals(
                "如需查看 7 天以上的回放历史，请升级到 Silver（90 天回放）或 Gold（365 天回放）订阅。",
                ResourceTranslationDictionary.translate(
                        "global_playback_date_picker_upgrade_text", null));
    }

    @Test
    public void translatesReviewedAlertSettingResourcesOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("settings_notification_channel_high_pie", "发出声音");
        expected.put("settings_notification_nearby_airports_summary",
                "到访机场时通知我，以便快速查看航班信息。");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("settings_notification_alerts_missing", null));
        assertEquals("Make Sound", TranslationDictionary.translate("Make Sound"));
        assertEquals("Make sound ", TranslationDictionary.translate("Make sound "));
        assertEquals(
                "Notify me when I visit an airport to easily access flight information.",
                TranslationDictionary.translate(
                        "Notify me when I visit an airport to easily access flight information."));
    }

    @Test
    public void translatesFirstElevenPointNineStaticCandidates() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("menu_faq", "常见问题");
        expected.put("menu_onboarding", "使用教程");
        expected.put("search_by_route_arr_airport", "到达机场");
        expected.put("search_by_route_dep_airport", "出发机场");
        expected.put("search_by_route_hint", "按城市、机场名称或 IATA/ICAO 代码搜索。");
        expected.put("search_clear_history_acknowledgment", "搜索历史已清除");
        expected.put("settings_data_sources", "数据来源");
        expected.put("settings_measurement_units", "单位");
        expected.put("settings_show_airports", "机场标记");
        expected.put("settings_visibility_airtraffic", "空中航空器");
        expected.put("settings_visibility_groundtraffic", "地面航空器");
        expected.put("filters_edit_list_title", "编辑筛选");
        expected.put("filters_add_name_input_label", "筛选名称");
        expected.put("filters_delete_dialog_title", "删除筛选");
        expected.put("alert_add", "添加提醒");
        expected.put("alert_history_title", "收到的提醒");
        expected.put("alert_region", "提醒区域");
        expected.put("airport_panel_airport_elevation", "机场海拔");
        expected.put("airport_panel_runway_usage", "跑道使用情况");
        expected.put("global_playback_loading_error", "与 FR24 通信失败。");
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
    }

    @Test
    public void translatesReviewedMapSettingsOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("settings_aircraft_info_logo", "标志");
        expected.put("settings_aircraft_info_text", "文字标签");
        expected.put("settings_dim_map", "地图亮度");
        expected.put("settings_show_day_night", "昼夜分界线");
        expected.put("settings_show_day_night_desc", "一眼查看地图上的昼夜区域。");
        expected.put("settings_show_atc_boundaries", "空管边界");
        expected.put("settings_show_atc_boundaries_desc", "全球飞行情报区（FIR）和高空飞行情报区（UIR）边界。");
        expected.put("settings_show_atc_boundaries_red", "红色");
        expected.put("settings_show_atc_boundaries_green", "绿色");
        expected.put("settings_show_atc_boundaries_blue", "蓝色");
        expected.put("settings_show_oceanic_tracks", "洋区航路");
        expected.put("settings_show_oceanic_tracks_desc", "当前北大西洋、太平洋洋区航路及澳大利亚灵活航路。");
        expected.put("settings_aeronatuical_charts", "航空图");
        expected.put("settings_aeronatuical_charts_desc",
                "导航台是机组使用的无线电导航信标。低空和高空图层显示导航航路点和航路。");
        expected.put("settings_aeronatuical_charts_navaids", "导航台");
        expected.put("settings_aeronatuical_charts_low", "低空");
        expected.put("settings_aeronatuical_charts_high", "高空");
        expected.put("settings_show_airports_desc", "在地图上显示机场位置。放大可查看更多机场。");
        expected.put("settings_show_my_location", "显示我的位置");
        expected.put("settings_show_my_location_desc", "在地图上以圆点显示你的位置。");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("settings_map_missing", null));
        assertEquals("Logo", TranslationDictionary.translate("Logo"));
        assertEquals("Red", TranslationDictionary.translate("Red"));
        assertEquals("Low altitude", TranslationDictionary.translate("Low altitude"));
    }

    @Test
    public void translatesVisibilityDataSourcesOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("data_source_adsb_title", "地面 ADS-B");
        expected.put("data_source_adsb_summary", "通过地面接收站，基于 GPS 跟踪航空器。");
        expected.put("data_source_mlat_title", "地面 MLAT");
        expected.put("data_source_mlat_summary", "通过地面接收站，基于到达时间差跟踪航空器。");
        expected.put("data_source_aireon_title", "星基 ADS-B");
        expected.put("data_source_aireon_summary", "通过卫星接收站，基于 GPS 跟踪航空器；数据由 Aireon 提供。");
        expected.put("data_source_sat_title", "星基 ADS-C");
        expected.put("data_source_sat_summary", "通过与航空器的卫星连接，基于 GPS 进行跟踪。");
        expected.put("data_source_faa_summary", "结合雷达、多点定位和卫星技术，为美国主要机场提供地面目标跟踪。");
        expected.put("data_source_uat_summary", "基于 GPS 的航空器跟踪，主要用于美国 18,000 英尺以下飞行的轻型航空器。");
        expected.put("data_source_australia_title", "澳大利亚雷达");
        expected.put("data_source_australia_summary", "官方雷达数据，覆盖澳大利亚及其周边部分洋区。");
        expected.put("data_source_spidertracks_summary", "通过 Spidertracks 发射器，基于 GPS 对轻型航空器进行专有跟踪。");
        expected.put("data_source_flarm_summary", "基于 GPS 的航空器跟踪技术，主要用于轻型航空器、直升机和滑翔机。");
        expected.put("data_source_other_title", "其他");
        expected.put("data_source_other_summary", "Flightradar24 使用的、未归入以上类别的其他跟踪技术。");
        expected.put("data_source_est_title", "估算");
        expected.put("data_source_est_summary", "航空器离开 Flightradar24 覆盖范围后，最多可继续估算其位置 240 分钟。");
        expected.put("settings_types_of_traffic", "交通类型");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("data_source_missing", null));
        assertEquals("Other", TranslationDictionary.translate("Other"));
        assertEquals("Estimations", TranslationDictionary.translate("Estimations"));
        assertEquals("Australia radar", TranslationDictionary.translate("Australia radar"));
    }

    @Test
    public void translatesMapAccountOnlyByResourceContext() {
        assertEquals("我的账户", ResourceTranslationDictionary.translate("my_account", null));
        assertEquals("My account", TranslationDictionary.translate("My account"));
        assertEquals("My Account", TranslationDictionary.translate("My Account"));
    }

    @Test
    public void translatesCommercialServicesOnlyByResourceContext() {
        assertEquals("商业服务", ResourceTranslationDictionary.translate(
                "menu_commerical_services", null));
        assertEquals("Commercial services", TranslationDictionary.translate("Commercial services"));
        assertEquals("Commercial Services", TranslationDictionary.translate("Commercial Services"));
    }

    @Test
    public void translatesNewsletterOnlyByResourceContext() {
        assertEquals("新闻通讯", ResourceTranslationDictionary.translate("menu_newsletter", null));
        assertEquals("Newsletter", TranslationDictionary.translate("Newsletter"));
        assertEquals("newsletter", TranslationDictionary.translate("newsletter"));
    }

    @Test
    public void translatesNoCallSignOnlyByResourceContext() {
        assertEquals("航班：无呼号", ResourceTranslationDictionary.translate(
                "accessibility_flight_no_callsign", null));
        assertEquals("Flight: no call sign", TranslationDictionary.translate("Flight: no call sign"));
        assertEquals("Flight: NO CALL SIGN", TranslationDictionary.translate("Flight: NO CALL SIGN"));
    }

    @Test
    public void translatesAuditedFilterUpsellTemplatesOnlyByResourceContext() {
        assertEquals("%d 个筛选条件", ResourceTranslationDictionary.template("filters_num"));
        assertEquals(
                "要按航空器类别筛选，请升级至 Silver（%1$s）或 Gold（%2$d）。",
                ResourceTranslationDictionary.template("filters_unlock_categories_upsell_title"));
        assertEquals(
                "10 个筛选条件",
                ResourceTranslationDictionary.translate("filters_num", new Object[]{10}));
        assertEquals(
                "1 个筛选条件",
                ResourceTranslationDictionary.translate("filters_num", new Object[]{1}));
        assertEquals(
                "要按航空器类别筛选，请升级至 Silver（10 个筛选条件）或 Gold（25）。",
                ResourceTranslationDictionary.translate(
                        "filters_unlock_categories_upsell_title",
                        new Object[]{"10 个筛选条件", 25}));
        assertNull(ResourceTranslationDictionary.translate("filters_num_missing", new Object[]{10}));
        assertEquals("10 filters", TranslationDictionary.translate("10 filters"));
    }

    @Test
    public void translatesAirportStaticResourcesOnlyByResourceContext() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("search_shortcut_airport_map", "在地图上显示");
        expected.put("search_shortcut_airport_general", "显示概览");
        expected.put("search_shortcut_airport_arr_board", "到达航班看板");
        expected.put("search_shortcut_airport_dep_board", "出发航班看板");
        expected.put("search_shortcut_airport_on_ground", "地面航空器");
        expected.put("search_shortcut_airport_arr_flights", "查找抵达航班");
        expected.put("search_shortcut_airport_dep_flights", "查找出发航班");
        expected.put("accessibility_airport_photo", "照片");
        expected.put("cab_airport_wx_conditions", "天气状况");
        expected.put("cab_airport_wx_temperature", "温度");
        expected.put("cab_airport_wx_more_title", "更多天气与 METAR");
        expected.put("weather_sky_condition_cloudy", "多云");
        expected.put("airport_panel_current", "当前");
        expected.put("live_tag", "实时");
        expected.put("airport_panel_disruptions", "运行异常");
        expected.put("airport_panel_more_disruptions", "更多运行异常");
        expected.put("airport_panel_total", "总计");
        expected.put("cab_airport_general", "概览");
        expected.put("cab_airport_departures", "出发");
        expected.put("cab_airport_arrivals", "到达");
        expected.put("cab_airport_on_ground", "地面");
        expected.put("airport_panel_airport_statistics", "机场统计");
        expected.put("airport_panel_last_7_days_tag", "最近 7 天");
        expected.put("airport_airports_served", "通航机场");
        expected.put("airport_countries_served", "通航国家/地区");
        expected.put("airport_panel_busiest_routes", "最繁忙航线");
        expected.put("airport_panel_average_flights_per_day", "机场航班起降量");
        expected.put("airport_panel_flights_per_day", "每日起降量");
        expected.put("accessibility_locked_content", "内容已锁定。升级后查看。");
        expected.put("airport_panel_airport_name", "机场名称");
        expected.put("airport_panel_airport_satellite_photo", "机场卫星照片");
        expected.put("airport_runway_title", "跑道详情");
        expected.put("cab_airport_wx_sunrise", "日出");
        expected.put("cab_airport_wx_sunset", "日落");
        expected.put("airport_panel_travelers", "常旅客");
        expected.put("do_you_work_at_an_airport", "您在机场工作吗？");
        expected.put("help_us_improve_ground_coverage", "帮助我们改善地面覆盖");
        expected.put("airport_flight_history_intro_tooltip", "查看所选日期的机场出发与到达历史。");
        expected.put("airport_panel_earlier_flights", "更早的航班");
        expected.put("cab_airport_status_estimated", "预计");
        expected.put("cab_airport_status_landed", "已降落");
        expected.put("cab_airport_status_canceled", "已取消");
        expected.put("label_airline", "航空公司");
        expected.put("label_aircraft", "航空器");
        expected.put("airport_landed", "已降落");
        expected.put("airport_show_on_map", "在地图上显示");
        expected.put("airport_aircraft_info", "航空器信息");
        expected.put("airport_flight_info", "航班信息");
        expected.put("airport_panel_bookmark_aircraft", "收藏航空器");
        expected.put("airport_panel_bookmark_flight", "收藏航班");
        expected.put("airport_playback", "回放");
        expected.put("airport_panel_download_csv_kml", "下载 CSV/KML");
        expected.put("label_scheduled", "计划出发");
        expected.put("label_actual_departure", "实际出发");
        expected.put("label_scheduled_arrival", "计划到达");
        expected.put("label_status", "状态");
        expected.put("label_flight_time", "飞行时间");
        expected.put("label_equipment", "机型");
        expected.put("label_call_sign", "呼号");
        expected.put("label_flight_history", "航班");
        expected.put("accessibility_not_available", "不可用");
        expected.put("error_something_went_wrong_longer", "出了点问题，无法加载数据。");
        expected.put("error_reload", "重新加载");
        expected.put("airport_panel_remove_ads_desc", "移除广告 — 打开升级选项");
        expected.put("cab_tracked_via_satellite_by", "卫星追踪数据提供方");
        expected.put("remove_ads", "移除广告，获得更流畅的应用体验");
        expected.put("cab_btn_share", "分享");
        expected.put("cab_close", "关闭");
        expected.put("accessibility_expand_panel", "展开面板");
        expected.put("bookmark_accessibility_not_bookmarked", "收藏：未收藏");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertNull(ResourceTranslationDictionary.translate("airport_static_missing", null));
        assertNull(ResourceTranslationDictionary.translate("N/A", null));
    }

    @Test
    public void translatesRemainingBookmarkResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("bookmark_accessibility_bookmarked_aircraft", "收藏：已收藏航空器");
        expected.put("bookmark_accessibility_bookmarked_airport", "收藏：已收藏机场");
        expected.put("bookmark_accessibility_bookmarked_both", "收藏：已收藏航班和航空器");
        expected.put("bookmark_accessibility_bookmarked_flight", "收藏：已收藏航班");
        expected.put("bookmark_accessibility_locked", "收藏：已锁定");
        expected.put("bookmark_add_aircraft_hint", "例如 D–AIHV");
        expected.put("bookmark_add_airport_hint", "例如 London Heathrow 或 LHR");
        expected.put("bookmark_add_error", "无法添加收藏。");
        expected.put("bookmark_add_flight_hint", "例如 SQ23 或 AM22");
        expected.put("bookmark_add_location", "命名位置");
        expected.put("bookmark_add_location_button", "将位置添加到收藏");
        expected.put("bookmark_add_location_hint", "为位置创建名称");
        expected.put("bookmark_add_location_hint2", "如需保存位置，请在地图上选择要保存的位置和缩放级别。默认位置为当前地图位置。为该位置创建名称，然后点击添加。");
        expected.put("bookmark_added_aircraft", "航空器已成功收藏。");
        expected.put("bookmark_added_airport", "机场已成功收藏。");
        expected.put("bookmark_added_flight", "航班已成功收藏。");
        expected.put("bookmark_added_location", "位置已成功收藏。");
        expected.put("bookmark_already_added", "已添加");
        expected.put("bookmark_create_account", "创建账户");
        expected.put("bookmark_edit_aircraft", "编辑航空器");
        expected.put("bookmark_edit_airports", "编辑机场");
        expected.put("bookmark_edit_discard_changes_message", "如果关闭编辑页面而不保存，更改将会丢失。");
        expected.put("bookmark_edit_discard_changes_title", "放弃更改？");
        expected.put("bookmark_edit_flights", "编辑航班");
        expected.put("bookmark_edit_locations", "编辑位置");
        expected.put("bookmark_go_to_bookmarks", "前往收藏");
        expected.put("bookmark_limit_reached_msg", "如需添加更多收藏，请先删除一个收藏。");
        expected.put("bookmark_limit_reached_title", "已达到收藏数量上限");
        expected.put("bookmark_locked_header", "需要账户才能解锁收藏功能");
        expected.put("bookmark_locked_text_anonymous", "创建账户即可解锁收藏功能，并在所有 Flightradar24 平台查看收藏。");
        expected.put("bookmark_promo_anonymous_msg", "使用收藏功能需要创建免费的 Flightradar24 账户。创建账户后，你可以在多个平台（iOS、Android 和 Flightradar24.com）使用与你的个人资料关联的订阅权益、功能和已保存设置。\n\n创建账户后，你可以在任意平台访问收藏。在主地图界面向下滑动即可打开收藏。");
        expected.put("bookmark_promo_create_account", "创建免费的 Flightradar24 账户");
        expected.put("bookmark_promo_title", "收藏");
        expected.put("bookmark_remove_error", "无法移除收藏。");
        expected.put("bookmark_removed_aircraft", "已移除航空器收藏。");
        expected.put("bookmark_removed_airport", "已移除机场收藏。");
        expected.put("bookmark_removed_flight", "已移除航班收藏。");
        expected.put("bookmark_save_error", "无法保存更改，请稍后重试。");
        expected.put("bookmark_save_success", "更改已保存到收藏。");
        expected.put("bookmark_tooltip", "点击航空器、机场或航班号旁的星标，即可将其添加到收藏。");
        expected.put("bookmark_view", "查看收藏");
        expected.put("bookmark_weather_not_available", "当前无天气数据");
        expected.put("bookmarks_type_aircraft", "航空器");
        expected.put("bookmarks_type_airport", "机场");
        expected.put("bookmarks_type_flight", "航班");
        expected.put("bookmarks_type_location", "位置");
        expected.put("bookmarks_type_title", "选择要收藏的类型");
        assertEquals(47, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(entry.getKey(), null));
        }
        assertEquals("预计到达 %s", ResourceTranslationDictionary.template("bookmark_eta"));
        assertEquals("正从 CKG 飞往 PEK", ResourceTranslationDictionary.translate(
                "bookmark_flying_from_to", new Object[]{"CKG", "PEK"}));
        assertEquals("已达到 Gold 账户的收藏上限", ResourceTranslationDictionary.translate(
                "bookmark_limit_reached_promo_title", new Object[]{"Gold"}));
        assertEquals("地面停留于 PEK", ResourceTranslationDictionary.translate(
                "bookmark_on_ground_at", new Object[]{"PEK"}));
        assertEquals("升级至 <b>Silver（10）</b> 或 <b>Gold（50）</b> 即可提高收藏数量上限。",
                ResourceTranslationDictionary.translate("bookmark_limit_reached_promo_msg_basic", new Object[]{10, 50}));
        assertEquals("升级至 <b>Gold（50）</b> 即可提高收藏数量上限。",
                ResourceTranslationDictionary.translate("bookmark_limit_reached_promo_msg_silver", new Object[]{50}));
        assertEquals("创建免费账户可添加 1 个收藏；升级至 <b>Silver（10）</b> 或 <b>Gold（50）</b> 可添加更多收藏。",
                ResourceTranslationDictionary.translate("bookmark_locked_text", new Object[]{10, 50}));
        assertEquals("你可以将多个航空器、航班、机场和位置添加到收藏，方便快速访问。在主地图界面向下滑动即可打开收藏。<br /><br />创建免费的 Flightradar24 账户可添加 1 个收藏；升级至 <b>Silver（10）</b> 或 <b>Gold（50）</b> 可添加更多收藏。",
                ResourceTranslationDictionary.translate("bookmark_promo_msg", new Object[]{10, 50}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_flying_from_to", new Object[]{"CKG"}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_limit_reached_promo_msg_basic", new Object[]{"10", 50}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_limit_reached_promo_msg_silver", new Object[]{"50"}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_limit_reached_promo_title", null));
        assertNull(ResourceTranslationDictionary.translate("bookmark_locked_text", new Object[]{10}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_on_ground_at", null));
        assertNull(ResourceTranslationDictionary.translate("bookmark_promo_msg", new Object[]{10}));
    }

    @Test
    public void translatesAirportDisruptionAndWeatherResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("cab_airport_delay_index", "运行异常指数");
        expected.put("cab_airport_average_delay", "平均延误");
        expected.put("cab_airport_delay_minutes", "分钟");
        expected.put("cab_airport_canceled", "已取消航班");
        expected.put("cab_airport_delayed", "延误航班");
        expected.put("weather_sky_condition_calm", "无风");
        expected.put("weather_sky_condition_clear", "晴");
        expected.put("weather_sky_condition_drizzle", "毛毛雨");
        expected.put("weather_sky_condition_fog", "雾");
        expected.put("weather_sky_condition_overcast", "阴");
        expected.put("weather_sky_condition_rain", "雨");
        expected.put("weather_sky_condition_snow", "雪");
        expected.put("weather_sky_condition_thunderstorm", "雷暴");
        expected.put("weather_sky_condition_cloudy", "多云");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
    }

    @Test
    public void airportDisruptionAndWeatherResourcesFailOpenForUnknownOrFormattedCalls() {
        assertNull(ResourceTranslationDictionary.translate("cab_airport_delay_index_missing", null));
        assertNull(ResourceTranslationDictionary.translate(
                "cab_airport_delay_index_missing", new Object[]{"unexpected"}));
        assertNull(ResourceTranslationDictionary.translate(
                "weather_sky_condition_missing", new Object[]{1}));
    }

    @Test
    public void translatesAirportDisruptionExplainerResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("airport_most_disruptions", "运行异常最严重的机场");
        expected.put("stats_airport_disrupt_msg0",
                "0 到 5 之间的数值综合反映延误航班数量、平均延误时间和取消航班数量。数值越高，表示取消和/或延误情况越严重。");
        expected.put("stats_airport_disrupt_msg1", "箭头显示当前运行异常指数的变化趋势。");
        expected.put("stats_airport_disrupt_msg2", "约 300 个最繁忙机场提供运行异常指数。");
        expected.put("stats_airport_disrupt_value0", "运行顺畅。");
        expected.put("stats_airport_disrupt_value1", "存在轻微问题，部分航班延误或少量取消。");
        expected.put("stats_airport_disrupt_value2", "存在严重问题，航班长时间延误且多班取消。");
        expected.put("stats_disrupt_airport", "机场运行异常");
        expected.put("disrupt_footer", "查看取消航班最多、延误时间最长的前 10 个机场。");
        expected.put("disrupt_wx_not_available", "当前无天气数据");

        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
    }

    @Test
    public void translatesDropdownStatsAndBookmarkResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("search_recent_footer", "更多搜索历史");
        expected.put("tooltip_pull_down", "向下滑动即可查看热门航班、机场运行异常、收藏和追踪统计。");
        expected.put("stats_aircraft_on_map", "地图上的航空器");
        expected.put("stats_data_source", "数据来源");
        expected.put("stats_view", "当前视图");
        expected.put("stats_global", "全球");
        expected.put("stats_total_title", "航空器总数");
        expected.put("bookmarks_tab_aircraft", "航空器");
        expected.put("bookmarks_tab_flights", "航班");
        expected.put("bookmarks_tab_airports", "机场");
        expected.put("bookmarks_tab_locations", "位置");
        expected.put("bookmark_empty_header_aircraft", "添加航空器收藏！");
        expected.put("bookmark_empty_header_flights", "添加航班收藏！");
        expected.put("bookmark_empty_header_airports", "添加机场收藏！");
        expected.put("bookmark_empty_header_locations", "添加位置收藏！");
        expected.put("bookmark_empty_text_aircraft", "点击下方“添加收藏”按钮，或点击应用内任意航班号旁的星标，即可添加航空器。");
        expected.put("bookmark_empty_text_flights", "点击下方“添加收藏”按钮，或点击应用内任意航班号旁的星标，即可添加航班。");
        expected.put("bookmark_empty_text_airports", "点击下方“添加收藏”按钮，或点击任意机场面板顶部的星标图标，即可添加机场。");
        expected.put("bookmark_empty_text_locations", "点击下方“添加收藏”按钮即可添加位置。保存的位置将包括当前地图位置和缩放级别。");
        expected.put("bookmark_add", "添加收藏");
        expected.put("bookmark_not_tracked", "当前未追踪");
        expected.put("bookmark_sort_by", "排序方式：");
        expected.put("bookmark_sort_last_added", "最近添加");
        expected.put("edit", "编辑");
        expected.put("bookmark_sort_airports_alphabetical", "机场名称 A-Z");
        expected.put("bookmark_sort_airports_iata", "IATA 代码 A-Z");
        expected.put("bookmark_sort_custom", "自定义");
        expected.put("bookmark_sort_flight_number_alphabetical", "航班号 A-Z");
        expected.put("bookmark_sort_location_alphabetical", "位置名称 A-Z");
        expected.put("bookmark_sort_registration_alphabetical", "注册号 A-Z");
        expected.put("bookmark_sort_status", "状态");
        expected.put("accessibility_help", "帮助");
        expected.put("bookmark_help_sort_alphabetical", "按字母顺序排列");
        expected.put("bookmark_help_sort_alphabetical_airport_code", "按 IATA 代码字母顺序排列");
        expected.put("bookmark_help_sort_alphabetical_airport_name", "按机场名称字母顺序排列");
        expected.put("bookmark_help_sort_custom", "创建自定义顺序");
        expected.put("bookmark_help_sort_last_added", "按添加时间排序");
        expected.put("bookmark_help_sort_status", "按当前状态排序，例如“实时”");
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(
                    entry.getKey(), null));
        }
        assertEquals("如需添加多个收藏，请升级至 <b>Silver（10）</b> 或 <b>Gold（100）</b> 订阅方案。",
                ResourceTranslationDictionary.translate("bookmark_limit_reached_dropdown_basic",
                        new Object[]{10, 100}));
        assertEquals("已达到 5 个收藏的上限。",
                ResourceTranslationDictionary.translate("bookmark_limit_reached_dropdown_gold",
                        new Object[]{5}));
        assertEquals("如需添加 10 个以上收藏，请升级至 <b>Gold（50）</b> 订阅方案。",
                ResourceTranslationDictionary.translate("bookmark_limit_reached_dropdown_silver",
                        new Object[]{50}));
    }

    @Test
    public void bookmarkDropdownTemplatesFailOpenForMissingOrWrongArguments() {
        assertNull(ResourceTranslationDictionary.translate("bookmark_limit_reached_dropdown_basic", null));
        assertNull(ResourceTranslationDictionary.translate(
                "bookmark_limit_reached_dropdown_basic", new Object[]{10}));
        assertNull(ResourceTranslationDictionary.translate(
                "bookmark_limit_reached_dropdown_basic", new Object[]{"10", 100}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_limit_reached_dropdown_gold", null));
        assertNull(ResourceTranslationDictionary.translate(
                "bookmark_limit_reached_dropdown_gold", new Object[]{"5"}));
        assertNull(ResourceTranslationDictionary.translate("bookmark_limit_reached_dropdown_silver", new Object[]{}));
    }

    @Test
    public void formatsAirportResourceTemplatesWithAuditedJavaSignatures() {
        assertEquals("航空器：Boeing 737", ResourceTranslationDictionary.translate(
                "accessibility_aircraft", new Object[]{"Boeing 737"}));
        assertEquals("国家/地区：CN", ResourceTranslationDictionary.translate(
                "accessibility_country", new Object[]{"CN"}));
        assertEquals("IATA 代码：PEK", ResourceTranslationDictionary.translate(
                "accessibility_iata", new Object[]{"PEK"}));
        assertEquals("IATA 代码：PEK，ICAO 代码：ZBAA", ResourceTranslationDictionary.translate(
                "accessibility_iata_and_icao", new Object[]{"PEK", "ZBAA"}));
        assertEquals("注册号：B-1234", ResourceTranslationDictionary.translate(
                "accessibility_registration", new Object[]{"B-1234"}));
        assertEquals("海拔 116 ft", ResourceTranslationDictionary.translate(
                "cab_airport_elev", new Object[]{"116 ft"}));
        assertEquals("海拔：116 ft", ResourceTranslationDictionary.translate(
                "cab_airport_elevation", new Object[]{"116 ft"}));
        assertEquals("当地时间：12:34", ResourceTranslationDictionary.translate(
                "timezone_local_time_accessibility", new Object[]{"12:34"}));
        assertEquals("风：180 度，12 kt", ResourceTranslationDictionary.translate(
                "accessibility_wind", new Object[]{180, 12, "kt"}));
        assertEquals("以 B-1234 降落", ResourceTranslationDictionary.translate(
                "airport_landed_as", new Object[]{"B-1234"}));
        assertEquals("15 分钟前", ResourceTranslationDictionary.translate(
                "airport_diff_min", new Object[]{"15"}));
        assertEquals("2 小时前", ResourceTranslationDictionary.translate(
                "airport_diff_hrs", new Object[]{"2"}));
        assertEquals("3 天前", ResourceTranslationDictionary.translate(
                "airport_diff_days", new Object[]{"3"}));
        assertEquals("4 个月前", ResourceTranslationDictionary.translate(
                "airport_diff_months", new Object[]{"4"}));
        assertEquals("5 年前", ResourceTranslationDictionary.translate(
                "airport_diff_years", new Object[]{"5"}));
        assertEquals("已于 13:38 降落", ResourceTranslationDictionary.translate(
                "search_status_landed", new Object[]{"13:38"}));
        assertEquals("3 个航班", ResourceTranslationDictionary.translate(
                "search_airline_flights", new Object[]{"3"}));
        assertEquals("解锁 航班历史", ResourceTranslationDictionary.translate(
                "cab_unlock_selected_feature", new Object[]{"航班历史"}));
        assertEquals("预计于 08:15 起飞", ResourceTranslationDictionary.translate(
                "search_status_estimated_dep_time", new Object[]{"08:15"}));
        assertEquals("已备降至 CKG", ResourceTranslationDictionary.translate(
                "search_status_diverted_to", new Object[]{"CKG"}));
        assertEquals("正在备降至 ZUCK", ResourceTranslationDictionary.translate(
                "search_status_diverting_to", new Object[]{"ZUCK"}));
        assertEquals("预计于 12:34 到达", ResourceTranslationDictionary.translate(
                "search_status_estimated_arr_time", new Object[]{"12:34"}));
        assertEquals("风向不定，180 12 kt", ResourceTranslationDictionary.translate(
                "airport_panel_variable", new Object[]{180, "12 kt"}));
        assertEquals("编辑 — 我的筛选", ResourceTranslationDictionary.translate(
                "filters_edit_title", new Object[]{"我的筛选"}));
    }

    @Test
    public void failsOpenForAirportResourceTemplateArgumentMismatches() {
        assertNull(ResourceTranslationDictionary.translate("accessibility_aircraft", null));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_iata_and_icao", new Object[]{"PEK"}));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_wind", new Object[]{"180", 12, "kt"}));
        assertNull(ResourceTranslationDictionary.translate(
                "airport_panel_variable", new Object[]{180}));
        assertNull(ResourceTranslationDictionary.translate("filters_edit_title", new Object[]{}));
        assertNull(ResourceTranslationDictionary.translate("airport_diff_hrs", new Object[]{}));
        assertNull(ResourceTranslationDictionary.translate("search_status_landed", new Object[]{}));
    }

    @Test
    public void translatesAirportAndSettingsAccessibilityResources() {
        assertEquals("分享", ResourceTranslationDictionary.translate("menu_share", null));
        assertEquals("关闭", ResourceTranslationDictionary.translate("close", null));
        assertEquals("ICAO 代码：ZUCK", ResourceTranslationDictionary.translate(
                "accessibility_icao", new Object[]{"ZUCK"}));
        assertEquals("不可用", ResourceTranslationDictionary.translate("not_available", null));
        assertEquals("解锁", ResourceTranslationDictionary.translate("cab_unlock_feature", null));
        assertEquals("更多", ResourceTranslationDictionary.translate("settings_more", null));
        assertNull(ResourceTranslationDictionary.translate("accessibility_icao", null));
        assertNull(ResourceTranslationDictionary.translate("accessibility_icao", new Object[]{}));
        assertNull(ResourceTranslationDictionary.translate("accessibility_icao", new Object[]{1}));
        assertNull(ResourceTranslationDictionary.translate("airport_accessibility_missing", null));
        assertEquals("ICAO code: ZUCK", TranslationDictionary.translate("ICAO code: ZUCK"));
    }

    @Test
    public void translatesWeatherFreeTrialResource() {
        assertEquals("免费试用", ResourceTranslationDictionary.translate("free_trial", null));
    }

    @Test
    public void translatesRemainingSettingsResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("settings_aeronatuical_charts_none", "关闭");
        expected.put("settings_aircraft_info_ac_type", "机型");
        expected.put("settings_aircraft_info_altitude", "高度");
        expected.put("settings_aircraft_info_callsign", "呼号");
        expected.put("settings_aircraft_info_flightnumber", "航班号");
        expected.put("settings_aircraft_info_max", "已达到文字标签数量上限。");
        expected.put("settings_aircraft_info_none", "关闭");
        expected.put("settings_aircraft_info_registration", "注册号");
        expected.put("settings_aircraft_info_route", "航线");
        expected.put("settings_aircraft_info_speed", "速度");
        expected.put("settings_altitude", "气压高度");
        expected.put("settings_altitude_unit_ft", "英尺");
        expected.put("settings_altitude_unit_m", "米");
        expected.put("settings_menu_map", "地图");
        expected.put("settings_menu_weather", "天气");
        expected.put("settings_speed_unit_kmh", "千米/小时");
        expected.put("settings_speed_unit_kts", "节");
        expected.put("settings_speed_unit_mph", "英里/小时");
        expected.put("settings_notifcation_custom_summary_with_sub", "根据你选择的航班事件接收推送通知。");
        expected.put("settings_notification_channel_blocked", "已阻止");
        expected.put("settings_notification_channel_low_pie", "静默显示并最小化");
        expected.put("settings_notification_channel_medium_pie", "静默显示");
        expected.put("settings_notification_channel_urgent_pie", "发出声音并在屏幕上弹出");
        expected.put("settings_utc_warning", "UTC 使用 24 小时协调世界时显示时间。如需 12 小时制，请选择非 UTC 时区。");
        expected.put("settings_visibility_limit_description", "设置地图上显示的航空器数量上限。");
        expected.put("settings_visibility_limit_header", "航空器数量上限");
        expected.put("settings_visibility_restricted_header", "受限航班");
        expected.put("settings_visibility_restricted_included", "包含");
        expected.put("settings_visibility_restricted_none", "无");
        expected.put("settings_visibility_restricted_only", "仅");
        expected.put("settings_weather_flight_level", "飞行高度层");
        expected.put("settings_weather_airmet", "AIRMET / SIGMET");
        expected.put("settings_weather_airmet_desc", "由相关机构发布的 AIRMET/SIGMET，用于预报发布区域内可能对航班造成危险的重要天气事件；每 30 分钟刷新。");
        expected.put("settings_weather_high_level", "高空重要天气");
        expected.put("settings_weather_high_level_desc", "高空重要天气预报区域，最长提供 24 小时预报，每 6 小时一个时段。");
        expected.put("settings_weather_ice", "高分辨率结冰");
        expected.put("settings_weather_ice_desc", "未来 36 小时飞行高度层 FL060 至 FL300 的结冰严重程度预报。信息由美国航空气象中心生成，等级包括无、微量、轻度、中度和严重。");
        expected.put("settings_weather_ict", "高分辨率湍流");
        expected.put("settings_weather_ict_desc", "未来 36 小时飞行高度层 FL100 至 FL450 的湍流预报。信息由美国航空气象中心生成，等级包括无、轻度、中度和严重。");
        expected.put("settings_weather_lightning", "闪电");
        expected.put("settings_weather_lightning_desc", "地图上显示记录到的闪电，每 15 分钟更新。");
        expected.put("settings_weather_opacity", "不透明度");
        expected.put("settings_weather_precipitation_total_desc", "在实时地图上叠加显示当前全球降水量。总降水层每天刷新 12 次。");
        expected.put("settings_weather_winds", "风");
        expected.put("settings_weather_winds_areas", "渐变");
        expected.put("settings_weather_winds_arrows", "风羽");
        expected.put("settings_weather_winds_barbs_combination_legend", "风羽组合");
        expected.put("settings_weather_winds_barbs_legend_calm", "静风");
        expected.put("settings_weather_winds_desc", "在实时地图上以 1,000 英尺为增量显示 1,000 至 51,000 英尺范围内的风速和风向。可使用风羽或颜色渐变显示。风层每天刷新 12 次。");
        expected.put("settings_weather_winds_level_selected", "所选高度");
        expected.put("settings_weather_winds_level_selected_value", "%s 英尺");

        assertEquals(51, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
            if (!entry.getValue().contains("%")) {
                assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(entry.getKey(), null));
            }
        }
        assertNull(ResourceTranslationDictionary.translate("settings_visibility_adsb_title", null));
    }

    @Test
    public void translatesRemainingMenuPlaybackLoginAndSubscriptionResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("menu_alerts", "提醒");
        expected.put("menu_billing_details", "账单详情");
        expected.put("menu_custom_fleets", "自定义机队");
        expected.put("menu_data_sharing", "数据共享");
        expected.put("menu_filter", "筛选");
        expected.put("menu_global_playback", "回放");
        expected.put("menu_more", "更多");
        expected.put("menu_search", "搜索");
        expected.put("menu_settings", "设置");
        expected.put("global_playback_date_picker_gold_upgrade_text",
                "要查看超过 90 天的回放历史，请升级至 Gold 订阅（可查看 365 天回放）。");
        expected.put("playback_loading", "正在加载回放数据…");
        expected.put("playback_not_available", "无可用回放");
        expected.put("playback_not_available_for_this_flight", "很遗憾，此航班没有可用的回放数据！");
        expected.put("playback_share", "分享回放");
        expected.put("playback_share_subject", "%1$s 航班 %2$s");
        expected.put("playback_share_subject_aircraft", "航空器：%s");
        expected.put("playback_share_text", "在 Flightradar24 上查看航班 %1$s 从 %2$s 飞往 %3$s 的回放。");
        expected.put("playback_share_text_aircraft", "在 Flightradar24 上查看航空器 %1$s 从 %2$s 飞往 %3$s 的回放。");
        expected.put("playback_share_text_aircraft_short", "在 Flightradar24 上查看航空器 %s 的回放。");
        expected.put("playback_share_text_short", "在 Flightradar24 上查看航班 %s 的回放。");
        expected.put("playback_tooltip", "点击图表或再次向上滑动可打开高度和速度图。向下滑动可隐藏时间轴以外的所有内容。");
        expected.put("login_billing_details", "账单详情");
        expected.put("login_cancel_subscription", "取消订阅");
        expected.put("login_change_password", "更改密码");
        expected.put("login_change_password_title", "更改密码");
        expected.put("login_change_payment_method", "更改付款方式");
        expected.put("login_continue", "继续");
        expected.put("login_contributor_note",
                "我们已将你的订阅方案名称更新为 Contributor，所有原有功能保持不变。感谢你与我们共享数据，敬请期待 Contributor 专属功能。");
        expected.put("login_create_password_title", "创建密码");
        expected.put("login_dont_have_an_account", "还没有账户？");
        expected.put("login_dont_have_an_account_sign_up", "注册");
        expected.put("login_email_hint", "电子邮箱");
        expected.put("login_error_email", "请输入有效的电子邮箱地址。");
        expected.put("login_error_password", "密码必须至少包含 7 个字符。");
        expected.put("login_forgot_password", "忘记密码？");
        expected.put("login_forgot_password_subtitle", "输入你的电子邮箱以申请新密码。");
        expected.put("login_forgot_password_title", "忘记密码？");
        expected.put("login_generic_msg", "无法通过 FR24 服务器进行身份验证。");
        expected.put("login_log_in", "登录");
        expected.put("login_log_in_with_email", "使用电子邮箱登录");
        expected.put("login_my_data_sharing", "我的数据共享");
        expected.put("login_new_password", "新密码");
        expected.put("login_new_password_again", "再次输入新密码");
        expected.put("login_or_login_with", "或使用电子邮箱");
        expected.put("login_password_hint", "密码");
        expected.put("login_request_failed", "请求失败，请稍后重试。");
        expected.put("login_request_new_password", "申请新密码");
        expected.put("subs_already_owned_exception", "看起来你已购买此订阅。请重启应用以验证购买。");
        expected.put("subs_annual", "年付");
        expected.put("subs_annual_sub", "年度订阅");
        expected.put("subs_annual_sub_with_plan", "%s 年度订阅");
        expected.put("subs_backend_exception", "与 FR24 服务器通信失败。请重启应用以验证购买。");
        expected.put("subs_backend_exception_logged_in", "与 FR24 服务器通信失败。请使用“%s”按钮将本次购买关联到此账户。");
        expected.put("subs_continue_with_basic", "使用 Free 继续");
        expected.put("subs_continue_with_sub", "使用 %1$s（%2$s）继续");
        expected.put("subs_continue_with_sub2", "使用 %s 继续");
        expected.put("subs_different_account_msg",
                "你当前有有效的 Flightradar24 订阅，但该订阅使用另一个 Google 账户购买。请登录该 Google Play 账户以更改订阅。");
        expected.put("subs_enjoying", "你当前正在使用 Flightradar24 Free。");
        expected.put("subs_free_trial_month", "免费试用 %1$d 天，之后仅需 %2$s/月。");
        expected.put("subs_free_trial_year", "免费试用 %1$d 天，之后仅需 %2$s/年。");
        expected.put("subs_level_annual", "%s 年付");
        expected.put("subs_level_monthly", "%s 月付");
        expected.put("subs_monthly", "月付");
        expected.put("subs_monthly_sub", "月度订阅");
        expected.put("subs_monthly_sub_with_plan", "%s 月度订阅");
        expected.put("subs_native_dialog_logo_info", "广告帮助维持\nFlightradar24 免费服务");
        expected.put("subs_native_dialog_upgrade", "升级以移除广告");
        expected.put("subs_per_first_year_html", "首次支付 %1$s，之后每年以 %2$s 自动续订。");
        expected.put("subs_per_month", "/月");
        expected.put("subs_per_month_html", "仅需 %s/月");
        expected.put("subs_per_year_html", "仅需 %s/年");
        expected.put("subs_plan_auto_renews", "方案将自动续订，可随时取消，包括免费试用期间。");
        expected.put("subs_save", "节省 %s");
        expected.put("subs_save_annual",
                "<font color=#6ccb78><b>节省 %s</b></font>，相较于<font color=#327db6><b><u>月度订阅</u></b></font>");
        expected.put("subs_save_monthly",
                "<font color=#6ccb78><b>节省 %s</b></font>，选择<font color=#327db6><b><u>年度订阅</u></b></font>");
        expected.put("subs_start_free_trial2", "开始 7 天免费试用");
        expected.put("subs_start_no_trial", "开始订阅");
        expected.put("subs_upgrade_title", "升级选项");
        expected.put("subs_upgrade_to_gold", "升级至 Gold");
        expected.put("subs_upgrade_to_silver_or_gold", "可随时升级至 Silver 或 Gold。");
        expected.put("subscription_linking_failed",
                "出了点问题，订阅未激活。请重试。如果问题仍然存在，请联系支持。");

        assertEquals(81, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
            if (!entry.getValue().contains("%")) {
                assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(entry.getKey(), null));
            }
        }

        assertEquals("MU123 航班 CKG", ResourceTranslationDictionary.translate(
                "playback_share_subject", new Object[]{"MU123", "CKG"}));
        assertEquals("在 Flightradar24 上查看航班 MU123 从 CKG 飞往 PEK 的回放。",
                ResourceTranslationDictionary.translate(
                        "playback_share_text", new Object[]{"MU123", "CKG", "PEK"}));
        assertEquals("免费试用 7 天，之后仅需 ¥28/月。", ResourceTranslationDictionary.translate(
                "subs_free_trial_month", new Object[]{7, "¥28"}));
        assertEquals("使用 Gold（年付）继续", ResourceTranslationDictionary.translate(
                "subs_continue_with_sub", new Object[]{"Gold", "年付"}));
        assertEquals("<font color=#6ccb78><b>节省 20%</b></font>，相较于"
                        + "<font color=#327db6><b><u>月度订阅</u></b></font>",
                ResourceTranslationDictionary.translate("subs_save_annual", new Object[]{"20%"}));
        assertNull(ResourceTranslationDictionary.translate("global_playback_speed_label", new Object[]{2}));
        assertNull(ResourceTranslationDictionary.translate("subs_gold", null));
        assertNull(ResourceTranslationDictionary.translate("subs_silver", null));
    }

    @Test
    public void translatesAllRemainingAlertStringResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("alert_add_condition", "添加条件");
        expected.put("alert_basic", "设置提醒类型和条件");
        expected.put("alert_condition_is", "是");
        expected.put("alert_condition_is_not", "不是");
        expected.put("alert_condition_less", "小于");
        expected.put("alert_condition_more", "大于");
        expected.put("alert_condition_too_short", "条件 %d 太短。");
        expected.put("alert_custom_notfound", "未找到自定义提醒。点击 + 添加。");
        expected.put("alert_discard", "删除");
        expected.put("alert_done", "完成");
        expected.put("alert_emergency_history_desc", "紧急提醒（应答机代码 %s）");
        expected.put("alert_failed_to_save", "保存提醒失败，请重启应用后重试");
        expected.put("alert_global", "全球");
        expected.put("alert_hint_aircraft", "例如 A388 或 B73");
        expected.put("alert_hint_airline", "ICAO 代码（例如 SAS）");
        expected.put("alert_hint_airport", "IATA 代码（例如 LHR）");
        expected.put("alert_hint_alt", "高度（英尺）");
        expected.put("alert_hint_flight", "航班号或呼号");
        expected.put("alert_hint_reg", "例如 D-AIHV");
        expected.put("alert_history_log_deleted", "提醒记录已删除");
        expected.put("alert_history_notfound", "未收到提醒。");
        expected.put("alert_local", "本地");
        expected.put("alert_options", "其他条件（可选）");
        expected.put("alert_received", "收到时间：");
        expected.put("alert_select_area", "选择区域");
        expected.put("alert_select_area_error", "请选择区域");
        expected.put("alert_triger_too_short", "提醒触发条件必须至少包含 3 个字符。");
        expected.put("alert_triger_too_short_aircraft", "提醒触发条件必须至少包含 2 个字符。");
        expected.put("alert_triger_too_short_airline", "提醒触发条件必须正好包含 3 个字符（ICAO 代码）。");
        expected.put("alert_trigger", "提醒触发条件");
        expected.put("alert_type", "提醒类型");
        expected.put("alert_type_airline", "航空公司");
        expected.put("alert_type_altitude", "高度");
        expected.put("alert_type_destination", "目的地");
        expected.put("alert_type_flight", "航班");
        expected.put("alert_type_reg", "注册号");
        expected.put("alert_type_type", "机型");

        assertEquals(37, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
            if (!entry.getValue().contains("%")) {
                assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(entry.getKey(), null));
            }
        }
        assertEquals("条件 2 太短。", ResourceTranslationDictionary.translate(
                "alert_condition_too_short", new Object[]{2}));
        assertEquals("紧急提醒（应答机代码 7700）", ResourceTranslationDictionary.translate(
                "alert_emergency_history_desc", new Object[]{"7700"}));
    }

    @Test
    public void translatesAllRemainingFilterStringResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("filter_aircraft_full_selection_title", "选中的航空器");
        expected.put("filter_aircraft_info",
                "请输入 ICAO 代码，或从下方列表中选择。筛选现会精确匹配，例如 C17 只会显示 C17，不会同时显示 C172。使用 * 可显示与部分 ICAO 代码匹配的所有航空器，例如 B77* 可显示所有波音 777 机型。多个条目请用逗号分隔，例如 A35*,B789 可显示所有空客 A350 和波音 787-9。");
        expected.put("filter_aircraft_most_popular", "热门航空器");
        expected.put("filter_aircraft_try_most_popular", "试试“热门航空器”列表。");
        expected.put("filter_airline_full_selection_title", "选中的航空公司");
        expected.put("filter_airline_hint", "输入 ICAO 代码");
        expected.put("filter_airline_info", "输入航空公司名称或 ICAO 代码。多个航空公司 ICAO 代码请用逗号分隔，例如 BAW, UAL。");
        expected.put("filter_airline_most_popular", "热门航空公司");
        expected.put("filter_airline_try_most_popular", "试试“热门航空公司”列表。");
        expected.put("filter_airport_country_prefix", "国家/地区：");
        expected.put("filter_airport_full_selection_title", "选中的机场");
        expected.put("filter_airport_info", "输入机场名称、IATA 代码，或从下方列表中选择。多个机场请用逗号分隔，例如 JFK, ARN。");
        expected.put("filter_airport_most_popular", "热门机场");
        expected.put("filter_airport_try_most_popular", "试试“热门机场”列表。");
        expected.put("filter_by_advanced_limit_title", "要使用高级筛选，请升级至 Silver（%1$s）或 Gold（%2$d）。");
        expected.put("filter_by_advanced_title", "添加高级筛选");
        expected.put("filter_by_aircraft_description", "查找航空器");
        expected.put("filter_by_aircraft_hint", "航空器 ICAO 代码");
        expected.put("filter_by_aircraft_title", "按航空器添加筛选");
        expected.put("filter_by_airline_description", "查找航空公司");
        expected.put("filter_by_airline_hint", "航空公司名称或 ICAO 代码");
        expected.put("filter_by_airline_title", "按航空公司添加筛选");
        expected.put("filter_by_airport_description", "查找机场");
        expected.put("filter_by_airport_hint", "机场名称或 IATA 代码");
        expected.put("filter_by_airport_title", "按机场添加筛选");
        expected.put("filter_by_category_limit_title", "要使用类别筛选，请升级至 Silver（%1$s）或 Gold（%2$d）。");
        expected.put("filter_by_category_title", "按类别添加筛选");
        expected.put("filter_by_route_title", "按航线添加筛选");
        expected.put("filter_categories_full_selection_title", "选中的类别");
        expected.put("filter_category_custom_filter_override_disable", "停用");
        expected.put("filter_category_custom_filter_override_explainer",
                "为避免筛选冲突，使用类别面板前必须停用当前筛选，或编辑当前筛选并移除类别条件。");
        expected.put("filter_category_custom_filter_override_title", "当前启用的筛选包含类别条件");
        expected.put("filter_custom_iata_code", "IATA 代码：%s");
        expected.put("filter_custom_iata_codes", "IATA 代码：%s");
        expected.put("filter_custom_icao_code", "ICAO 代码：%s");
        expected.put("filter_custom_icao_codes", "ICAO 代码：%s");
        expected.put("filter_done_button", "添加");
        expected.put("filter_remove", "移除此筛选");
        expected.put("filter_route_full_selection_title", "选中的航线");
        expected.put("filter_save", "保存");
        expected.put("filter_search_cant_find_what_you_are_looking_for", "找不到你需要的内容？");
        expected.put("filter_search_select_all", "全选");
        expected.put("filter_search_visit_our_blog",
                "也可访问我们的博客，查看<font color=#327DB6>筛选功能完整使用指南。</font>");
        expected.put("filter_toggle_description", "切换筛选/高亮模式");
        expected.put("filters_add_clear_changes", "清除更改");
        expected.put("filters_add_clear_title", "确定要清除所做的更改吗？");
        expected.put("filters_add_name_error_empty", "请输入筛选名称。");
        expected.put("filters_add_name_error_min_length", "筛选名称必须至少包含 %d 个字符");
        expected.put("filters_add_navigate_away_body", "如果未保存就返回上一页，所做的更改将会丢失。");
        expected.put("filters_add_navigate_away_title", "保存更改");
        expected.put("filters_add_save_error_body", "请稍后重试。");
        expected.put("filters_add_save_error_title", "无法保存更改。");
        expected.put("filters_advanced_age_title", "机龄");
        expected.put("filters_advanced_altitude_title", "气压高度");
        expected.put("filters_advanced_call_sign_error", "呼号可由字母和数字组成，长度为 3 至 8 个字符。最多可添加 3 个不同的值。");
        expected.put("filters_advanced_call_sign_title", "呼号");
        expected.put("filters_advanced_call_sign_tooltip",
                "呼号可由字母和数字组成，长度为 3 至 8 个字符。在开头或结尾使用 * 可包含所有可能的组合，例如 UAL1* 会显示所有以 UAL1 开头的航班，*123 会显示所有以 123 结尾的航班。多个呼号请用逗号分隔，例如 CPA847,BA55W。");
        expected.put("filters_advanced_input_example", "例如 %s");
        expected.put("filters_advanced_radar_error", "雷达代码可由字母、数字和连字符组成，长度为 5 至 10 个字符，可使用 A-Z、0-9 和 -。");
        expected.put("filters_advanced_radar_title", "雷达");
        expected.put("filters_advanced_radar_tooltip",
                "雷达代码可由字母、数字和连字符组成，长度为 5 至 10 个字符，可使用 A-Z、0-9 和 -。多个雷达代码请用逗号分隔，例如 T-KJFK567,F-ESSB2。");
        expected.put("filters_advanced_registration_error", "注册号可由字母和数字组成，长度为 2 至 12 个字符。");
        expected.put("filters_advanced_registration_title", "注册号");
        expected.put("filters_advanced_registration_tooltip",
                "注册号可由字母和数字组成，长度为 2 至 12 个字符。在开头或结尾使用 * 可包含所有可能的组合，例如 M-* 会显示所有以 M- 开头的航班，*HV 会显示所有以 HV 结尾的航班。多个注册号请用逗号分隔，例如 D-AIHV,M-ILAN。");
        expected.put("filters_advanced_speed_title", "地速");
        expected.put("filters_advanced_squawk_error", "应答机代码由 0 至 7 的 4 位数字组成，不能包含字母。");
        expected.put("filters_advanced_squawk_title", "应答机代码");
        expected.put("filters_advanced_squawk_tooltip",
                "应答机代码由 0 至 7 的 4 位数字组成，不能包含字母。\n多个应答机代码请用逗号分隔，例如 2000,7000。");
        expected.put("filters_aircraft_operating_as_tooltip_info",
                "以某航空公司呼号运行的航空器，但并不一定属于该航空公司，例如从另一家航空公司租赁的航空器。");
        expected.put("filters_aircraft_operating_as_tooltip_title", "航空器运营方");
        expected.put("filters_aircraft_painted_as_tooltip_info",
                "采用某航空公司涂装的航空器，但并不一定由该航空公司运营，例如为大型航空公司执飞航班的支线航空公司航空器。");
        expected.put("filters_aircraft_painted_as_tooltip_title", "航空器涂装");
        expected.put("filters_both", "两者");
        expected.put("filters_current_active_filters_disabled_warning", "创建或编辑筛选时，当前启用的筛选将被停用。");
        expected.put("filters_data_sources_disabled_message", "所有数据来源均已停用，因此地图上没有可见的航空器。");
        expected.put("filters_delete_success_snackbar_message", "已成功移除筛选。");
        expected.put("filters_dismiss", "关闭");
        expected.put("filters_dont_show_again", "不再显示");
        expected.put("filters_edit_list_downgrade_to_basic_info",
                "Free 账户一次只能使用一个筛选。你可以删除筛选，但只能编辑当前启用的筛选。");
        expected.put("filters_edit_list_downgrade_to_silver_or_gold_info",
                "已保存的筛选数量超过当前订阅允许的上限。你可以删除筛选，但只能编辑当前启用的筛选。");
        expected.put("filters_edit_list_info", "对筛选所做的更改会同步到所有平台。");
        expected.put("filters_edit_success_snackbar_message", "更改已保存。");
        expected.put("filters_inbound", "入港");
        expected.put("filters_invalid_route_body",
                "必须至少指定一个出发地和一个目的地条件。\n如果返回上一页，当前航线筛选的更改将会丢失。");
        expected.put("filters_invalid_route_title", "无效航线");
        expected.put("filters_no_aircraft_visible_message", "所有航空器类别均已停用，因此地图上没有可见的航空器。");
        expected.put("filters_operating_as", "运营方为");
        expected.put("filters_other_platform_ios",
                "你的订阅已关联到 iOS 上的 Apple ID。请在 Apple ID 的“订阅”中升级或更改方案。");
        expected.put("filters_other_platform_web",
                "你的订阅已关联到网站账户。请前往 Flightradar24.com 的“我的账户”升级或更改方案。");
        expected.put("filters_outbound", "出港");
        expected.put("filters_painted_as", "涂装为");
        expected.put("filters_restore", "恢复");
        expected.put("filters_route_switch_from_to_action", "对调");
        expected.put("filters_selected_show_more", "+ 另外 %s 项");
        expected.put("filters_special", "特殊");
        expected.put("filters_special_fleet", "自定义机队");
        expected.put("filters_special_receiver", "接收器");
        expected.put("filters_special_unblocked", "选择性解除屏蔽");
        expected.put("filters_special_unblocked_fleet", "未屏蔽机队");
        expected.put("filters_unable_to_load", "无法加载已保存的筛选。");

        assertEquals(100, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
            if (!entry.getValue().contains("%")) {
                assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(entry.getKey(), null));
            }
        }
        assertEquals("要使用高级筛选，请升级至 Silver（10 个筛选条件）或 Gold（25）。",
                ResourceTranslationDictionary.translate(
                        "filter_by_advanced_limit_title", new Object[]{"10 个筛选条件", 25}));
        assertEquals("ICAO 代码：ZUCK", ResourceTranslationDictionary.translate(
                "filter_custom_icao_code", new Object[]{"ZUCK"}));
        assertEquals("筛选名称必须至少包含 3 个字符", ResourceTranslationDictionary.translate(
                "filters_add_name_error_min_length", new Object[]{3}));
        assertEquals("例如 B77*", ResourceTranslationDictionary.translate(
                "filters_advanced_input_example", new Object[]{"B77*"}));
        assertEquals("+ 另外 4 项", ResourceTranslationDictionary.translate(
                "filters_selected_show_more", new Object[]{4}));
    }

    @Test
    public void translatesAllRemainingAirportCabAndSearchResources() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("airport_add_alert", "添加提醒");
        expected.put("airport_downloads", "下载");
        expected.put("airport_flight_history_broken_link_info", "请核对发送方并重试。");
        expected.put("airport_flight_history_broken_link_title", "共享链接有问题");
        expected.put("airport_flight_history_limit_description", "已达到机场航班历史可查看的最长天数。");
        expected.put("airport_flight_history_link_beyond_limit", "无法访问所选日期");
        expected.put("airport_flight_history_link_limit_upsell_title", "你尝试访问的链接所显示的日期超出当前方案限额");
        expected.put("airport_flight_history_search_for_airport", "查找机场");
        expected.put("airport_flight_history_title", "机场航班历史");
        expected.put("airport_load_next_on_ground", "加载更多");
        expected.put("airport_no_photo_msg", "如果你有照片并希望显示在 Flightradar24 上，请上传到 jetphotos.com");
        expected.put("airport_no_photo_title", "暂无机场照片");
        expected.put("airport_panel_ambiguous_arrival_description", "当前到达机场的时间已晚于预计到达时间，但我们尚无法确认航班已降落。这可能表示航班延误，也可能是到达信息不可用，或尚未传送至我们的某个数据源。");
        expected.put("airport_panel_ambiguous_departure_description", "当前出发机场的时间已晚于预计出发时间，且航班尚未处于活动状态。这可能表示航班延误或取消，也可能是出发信息不可用，或尚未传送至我们的某个数据源。");
        expected.put("airport_panel_arr_upsell_free", "如需查看超过 24 小时的到港航班，请升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        expected.put("airport_panel_arr_upsell_limit", "已达到到港航班可查看的最长小时数。");
        expected.put("airport_panel_arr_upsell_nli", "如需查看超过 12 小时的到港航班，请创建免费账户（24 小时）或升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        expected.put("airport_panel_arr_upsell_silver", "如需查看超过 36 小时的到港航班，请升级至 Gold（48 小时）方案。");
        expected.put("airport_panel_arrival_runway", "到达跑道");
        expected.put("airport_panel_calm", "无风");
        expected.put("airport_panel_category", "类别");
        expected.put("airport_panel_check_in", "值机");
        expected.put("airport_panel_dep_upsell_free", "如需查看超过 24 小时的离港航班，请升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        expected.put("airport_panel_dep_upsell_limit", "已达到离港航班可查看的最长小时数。");
        expected.put("airport_panel_dep_upsell_nli", "如需查看超过 12 小时的离港航班，请创建免费账户（24 小时）或升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        expected.put("airport_panel_dep_upsell_silver", "如需查看超过 36 小时的离港航班，请升级至 Gold（48 小时）方案。");
        expected.put("airport_panel_departure_runway", "起飞跑道");
        expected.put("airport_panel_faa_lid_code", "FAA LID 代码");
        expected.put("airport_panel_flights_per_day_graph", "每日起降架次 — 图表");
        expected.put("airport_panel_gnd_upsell_free", "如需查看超过 2 小时的地面航空器历史，请升级至 Silver（7 天）或 Gold（30 天）方案。");
        expected.put("airport_panel_gnd_upsell_limit", "已达到地面航空器历史可查看的最长天数。");
        expected.put("airport_panel_gnd_upsell_nli", "如需查看超过 60 分钟的地面航空器历史，请创建免费账户（2 小时）或升级至 Silver（7 天）或 Gold（30 天）方案。");
        expected.put("airport_panel_gnd_upsell_silver", "如需查看超过 7 天的地面航空器历史，请升级至 Gold（30 天）方案。");
        expected.put("airport_panel_graph_error", "此机场的图表数据不可用。");
        expected.put("airport_panel_iata_code", "IATA 代码");
        expected.put("airport_panel_icao_code", "ICAO 代码");
        expected.put("airport_panel_later_flights", "稍后航班");
        expected.put("airport_panel_latest_events_tooltip", "查看各机场的历史起飞和降落数据，包括航空公司、呼号、航班号、机型和飞行时间。还可查看所选机场最新的降落和起飞记录。可查看的历史范围取决于你的订阅等级。");
        expected.put("airport_panel_length_m_ft", "长度（m/ft）");
        expected.put("airport_panel_length_m_ft_accessibility", "长度（米/英尺）");
        expected.put("airport_panel_more_landings", "更多降落");
        expected.put("airport_panel_more_takeoffs", "更多起飞");
        expected.put("airport_panel_no_recent_landing_events", "暂无近期降落记录");
        expected.put("airport_panel_no_recent_takeoff_events", "暂无近期起飞记录");
        expected.put("airport_panel_remove_ads", "移除广告");
        expected.put("airport_panel_remove_bookmark", "移除收藏");
        expected.put("airport_panel_runway_info", "只有在机场上空低空能够接收到应答机信号时，才会生成跑道使用统计数据。若应答机信号较弱或质量较差，跑道数据可能缺失、不正确，或显示为 N/A。");
        expected.put("airport_panel_runway_usage_graph", "跑道使用情况 — 图表");
        expected.put("airport_panel_state", "州/省"); expected.put("airport_panel_surface", "道面"); expected.put("airport_panel_tomorrow", "明天"); expected.put("airport_panel_travelers_empty", "此机场暂无常旅客。"); expected.put("airport_panel_yesterday", "昨天");
        expected.put("airport_runway_surface_asgr", "沥青/草地"); expected.put("airport_runway_surface_asph", "沥青"); expected.put("airport_runway_surface_bitu", "沥青混合料"); expected.put("airport_runway_surface_brck", "砖块"); expected.put("airport_runway_surface_clay", "黏土"); expected.put("airport_runway_surface_coas", "混凝土/沥青"); expected.put("airport_runway_surface_cogs", "混凝土/草地"); expected.put("airport_runway_surface_conc", "混凝土"); expected.put("airport_runway_surface_corl", "珊瑚"); expected.put("airport_runway_surface_dirt", "泥土"); expected.put("airport_runway_surface_gras", "草地"); expected.put("airport_runway_surface_grvl", "碎石"); expected.put("airport_runway_surface_ice", "冰"); expected.put("airport_runway_surface_late", "红土"); expected.put("airport_runway_surface_maca", "碎石路面"); expected.put("airport_runway_surface_mats", "着陆垫"); expected.put("airport_runway_surface_meta", "金属"); expected.put("airport_runway_surface_mix", "非沥青混合料"); expected.put("airport_runway_surface_othr", "其他"); expected.put("airport_runway_surface_pavd", "铺装"); expected.put("airport_runway_surface_psp", "冲孔钢板"); expected.put("airport_runway_surface_sand", "沙地"); expected.put("airport_runway_surface_seld", "密封面"); expected.put("airport_runway_surface_silt", "淤泥"); expected.put("airport_runway_surface_snow", "雪"); expected.put("airport_runway_surface_soil", "土壤"); expected.put("airport_runway_surface_ston", "石材"); expected.put("airport_runway_surface_tarm", "柏油路面"); expected.put("airport_runway_surface_trtd", "处理面"); expected.put("airport_runway_surface_turf", "草皮"); expected.put("airport_runway_surface_unkn", "未知"); expected.put("airport_runway_surface_unpv", "未铺装"); expected.put("airport_runway_surface_wate", "水面");
        expected.put("cab_actual", "实际"); expected.put("cab_aircraft_age_brand_new", "全新"); expected.put("cab_aircraft_age_na", "机龄"); expected.put("cab_aircraft_age_test_flight", "试飞"); expected.put("cab_airport_error", "服务器在处理请求时发生错误。请重试。"); expected.put("cab_airport_metar", "METAR 是机场向飞行员报告天气信息时使用的一种格式。"); expected.put("cab_airport_status_delayed", "延误"); expected.put("cab_airport_status_departed", "已起飞"); expected.put("cab_airport_status_diverted", "已备降"); expected.put("cab_airport_status_diverting", "正在备降"); expected.put("cab_airport_status_scheduled", "计划"); expected.put("cab_airport_status_unknown", "未知"); expected.put("cab_airport_wx_air_pressure", "气压"); expected.put("cab_airport_wx_dew_point", "露点"); expected.put("cab_airport_wx_humidity", "湿度"); expected.put("cab_airport_wx_metar", "最新 METAR"); expected.put("cab_airport_wx_wind", "风"); expected.put("cab_btn_3d", "3D 视图"); expected.put("cab_btn_follow", "关注"); expected.put("cab_btn_route", "航线"); expected.put("cab_calibrated_alt", "气压高度"); expected.put("cab_chart_altitude_title", "气压高度"); expected.put("cab_chart_altitude_title_marker", "气压高度："); expected.put("cab_chart_speed_title", "地速"); expected.put("cab_chart_speed_title_marker", "地速："); expected.put("cab_data_source_increase_coverage", "增加你所在地区的覆盖"); expected.put("cab_data_source_inv_summary", "该航空器因故障或错误编程而广播无效的应答机代码。应答机故障可能产生可通过多种方式显示的错误。由于 ICAO 24 位地址不正确，通常无法识别该航空器。"); expected.put("cab_data_source_inv_title", "无效应答机"); expected.put("cab_estimated", "预计"); expected.put("cab_gate", "登机口"); expected.put("cab_most_tracked_counter", "由 <b>%s</b> 人关注"); expected.put("cab_most_tracked_ranking", "全球 <b>#%s</b>"); expected.put("cab_myfr24_link", "开始记录你的航班"); expected.put("cab_myfr24_onboard_multiple", "本航班上的用户"); expected.put("cab_myfr24_onboard_single", "本航班上的用户"); expected.put("cab_myfr24_travellers_multiple", "以上是最常搭乘 %1$s（%2$s）至 %3$s（%4$s）并于 %5$s 出行的 myFlightradar24 用户。"); expected.put("cab_myfr24_travellers_multiple_airport", "以上是最常往返 %1$s（%2$s）的 myFlightradar24 用户。"); expected.put("cab_myfr24_travellers_single", "以上是一位最常搭乘 %1$s（%2$s）至 %3$s（%4$s）并于 %5$s 出行的 myFlightradar24 用户。"); expected.put("cab_myfr24_travellers_single_airport", "以上是一位最常往返 %1$s（%2$s）的 myFlightradar24 用户。"); expected.put("cab_operated_by", "由 %s 执飞"); expected.put("cab_scheduled", "计划"); expected.put("cab_share_flight", "分享航班"); expected.put("cab_small_departed_na", "已起飞 N/A"); expected.put("cab_small_reg", "注册号"); expected.put("cab_terminal", "航站楼"); expected.put("cab_tracked_via_satellite", "通过卫星追踪");
        expected.put("search_aircraft", "航空器"); expected.put("search_airline_msg", "仅列出当前在 Flightradar24 覆盖范围内的 %s 航班。"); expected.put("search_by_route_arr_hint", "搜索到达机场"); expected.put("search_by_route_button", "搜索"); expected.put("search_by_route_dep_hint", "搜索出发机场"); expected.put("search_callsign", "呼号"); expected.put("search_error_msg", "服务器在处理请求时发生错误。请重试。"); expected.put("search_found_aircraft", "%1$d/%2$d 架航空器"); expected.put("search_hint_with_tooltip", "例如 BA112、Heathrow、LHR-JFK、BAW112"); expected.put("search_label", "搜索"); expected.put("search_menu_title", "搜索"); expected.put("search_nearby_away", "距此 %s"); expected.put("search_shortcut_airline_filter_hint", "航空公司名称或 ICAO 代码"); expected.put("search_shortcut_airport_filter_hint", "机场名称或 IATA 代码"); expected.put("search_shortcut_country_filter_hint", "国家/地区名称"); expected.put("search_status_canceled", "已取消"); expected.put("search_status_departed", "已起飞"); expected.put("search_status_scheduled", "计划"); expected.put("search_status_unknown", "未知");
        assertEquals(151, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
        assertNull(ResourceTranslationDictionary.template("airport_hpa"));
        assertEquals("由 Air China 执飞", ResourceTranslationDictionary.translate("cab_operated_by", new Object[]{"Air China"}));
        assertEquals("由 <b>42</b> 人关注", ResourceTranslationDictionary.translate("cab_most_tracked_counter", new Object[]{42}));
        assertEquals("全球 <b>#7</b>", ResourceTranslationDictionary.translate("cab_most_tracked_ranking", new Object[]{7}));
        assertEquals("5/12 架航空器", ResourceTranslationDictionary.translate("search_found_aircraft", new Object[]{5, 12}));
    }

    @Test
    public void translatesSignupTooltipAndWalkthroughResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("signup_account_linked_apple", "你的 Apple 账户已关联");
        expected.put("signup_account_linked_facebook", "你的 Facebook 账户已关联");
        expected.put("signup_account_linked_google", "你的 Google 账户已关联");
        expected.put("signup_account_newsletter", "接收来自 Flightradar24 的邮件更新");
        expected.put("signup_already_have", "已经有账户了？");
        expected.put("signup_create_account", "创建账户");
        expected.put("signup_log_in", "登录");
        expected.put("signup_newsletter_error", "请选择你的邮件偏好设置。");
        expected.put("signup_newsletter_no", "不用了，我不想接收来自 Flightradar24 的更新。");
        expected.put("signup_newsletter_yes", "是的，我想接收来自 Flightradar24 的邮件更新：");
        expected.put("signup_newsletter_yes_option1", "不定期接收最新功能和专属优惠信息");
        expected.put("signup_newsletter_yes_option2", "每周五接收最新航空新闻周报");
        expected.put("signup_nonsubscribed_header", "创建一个可在所有设备通用的 Flightradar24 账户");
        expected.put("signup_or_with_email", "或使用电子邮箱");
        expected.put("signup_password_hint", "设置密码");
        expected.put("signup_privacy_policy_note", "我们不会与任何人分享你的个人信息。你可以随时更改邮件偏好设置。\n阅读我们的%s");
        expected.put("signup_privacy_policy_note_link", "隐私政策");
        expected.put("signup_with_apple", "使用 Apple 注册");
        expected.put("signup_with_email", "使用电子邮箱注册");
        expected.put("signup_with_facebook", "使用 Facebook 注册");
        expected.put("signup_with_google", "使用 Google 注册");
        expected.put("tooltip_ar_main_button", "启动 AR");
        expected.put("tooltip_ar_main_description", "使用 AR 视图，将摄像头对准天空，即可查看头顶航班的详细信息。");
        expected.put("tooltip_ar_main_title", "把手机举向天空！");
        expected.put("tooltip_ar_main_title_tablet", "把平板举向天空！");
        expected.put("tooltip_ar_range_description", "看不到飞机？试着增大搜索半径。");
        expected.put("tooltip_ar_tabs_description", "点击这些标签页即可查看更多航班详情。");
        expected.put("tooltip_bookmark_welcome_button", "打开收藏");
        expected.put("tooltip_bookmark_welcome_description", "这是你第一次点击星标，我们想借此机会向你介绍它的用法。\n\n点击星标后，航班、飞机或机场会被加入你的收藏列表，方便你快速访问。你可以在地图界面向下滑动顶部栏打开收藏。");
        expected.put("tooltip_bookmark_welcome_title", "很高兴你发现了收藏功能");
        expected.put("tooltip_description", "工具提示");
        expected.put("tooltip_label", "显示工具提示");
        expected.put("tooltip_search_1", "搜索航班号、机场、航线、呼号或注册号。");
        expected.put("tooltip_search_2", "如图所示，你可以直接在搜索栏中搜索航班号、机场、航线、呼号或注册号。");
        expected.put("walkthrough_00_subtitle", "点击飞机图标即可查看包含飞机照片的航班概览。点击“更多信息”可查看更详细的内容。");
        expected.put("walkthrough_00_title", "探索我们的精选功能");
        expected.put("walkthrough_01_subtitle", "展开后可查看完整航班状态和飞机详情，还可以查看你正在追踪的飞机大图。");
        expected.put("walkthrough_01_title", "获取完整航班与飞机信息");
        expected.put("walkthrough_02_subtitle", "点击 AR 视图按钮并将设备指向天空，即可快速识别头顶航班。");
        expected.put("walkthrough_02_title", "识别头顶航班");
        expected.put("walkthrough_03_subtitle", "通过搜索航班号、航线或注册号查找航班，也可以仅按出发地或目的地查找航班。");
        expected.put("walkthrough_03_title", "轻松查找航班");
        expected.put("walkthrough_04_subtitle", "按名称或代码搜索机场，或直接点击地图上的机场标记。查看实时到港、离港以及当前天气。");
        expected.put("walkthrough_04_title", "获取机场实时信息");
        expected.put("walkthrough_05_subtitle", "查看航班历史并回放过往航班。查看该航班的航迹以及详细的速度和高度图表。你可以通过搜索进入历史记录，或在选中航班后点击“更多”或“最近”航班进入。");
        expected.put("walkthrough_05_title", "回顾历史航班");
        expected.put("walkthrough_06_charts", "航空图");
        expected.put("walkthrough_06_cloud", "云层图层");
        expected.put("walkthrough_06_precip", "降水图层");
        expected.put("walkthrough_06_subtitle", "为主地图添加云层、降水、航空图、洋区航路及其他图层。点击“设置”即可查看所有可用图层。");
        expected.put("walkthrough_06_title", "添加实用地图图层");
        expected.put("walkthrough_06_tracks", "洋区航路");
        expected.put("walkthrough_close", "关闭");
        expected.put("walkthrough_new_3d_subtitle", "使用 3D 视图跟随航班，查看精细的飞机 3D 模型、高分辨率卫星与地形影像，以及附近其他航班。");
        expected.put("walkthrough_new_3d_title", "在 3D 视图中跟随航班");
        expected.put("walkthrough_next", "下一步");

        assertEquals(56, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
        }
    }

    @Test
    public void translatesArVolcanoAndWhatsNewShellsExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("augmented_aircraft", "航空器");
        expected.put("augmented_details", "详情");
        expected.put("augmented_no_compass", "您的设备未配备指南针。");
        expected.put("augmented_overview", "概览");
        expected.put("augmented_reg", "注册号");
        expected.put("volcano_area", "区域");
        expected.put("volcano_current_ash_area", "当前火山灰区域");
        expected.put("volcano_eruption", "喷发");
        expected.put("volcano_forecast_12hrs", "第二预报火山灰区域（通常为 +12 小时）");
        expected.put("volcano_forecast_18hrs", "第三预报火山灰区域（通常为 +18 小时）");
        expected.put("volcano_forecast_6hrs", "第一预报火山灰区域（通常为 +6 小时）");
        expected.put("volcano_forecasts", "预报");
        expected.put("volcano_issue_time", "发布时间");
        expected.put("volcano_observations", "观测");
        expected.put("volcano_remarks", "备注");
        expected.put("volcano_valid", "有效时间");
        expected.put("whats_new", "更新内容");
        expected.put("whats_new_action_view_afh", "查看机场航班历史");
        expected.put("whats_new_feature_3d_view", "带逼真模型的 3D 视图");
        expected.put("whats_new_feature_ad_free", "无广告应用体验");
        expected.put("whats_new_feature_aeronautical_charts", "地图上的航空图");
        expected.put("whats_new_feature_description_airport_history", "通过新的“机场航班历史”功能，可查看全球各机场最长 365 天的历史起降记录。");
        expected.put("whats_new_feature_description_airport_movements", "增加了更多数据、表格和交互式图表，可查看机场过去 7 天的总起降架次、跑道使用情况，以及按天细分的 7 天明细。");
        expected.put("whats_new_feature_description_airport_panel", "全新升级的机场面板让您比以往查看更多信息，并可自定义重要机场数据的展示方式。通过可展开和可折叠的分区，您可以控制查看内容，其中包括统计、跑道详情、航班历史和最新动态。");
        expected.put("whats_new_feature_description_airport_statistics", "以交互方式查看更多所选机场的数据，包括航线、通航机场数量、通航国家数量，以及最繁忙航线的细分。");
        expected.put("whats_new_feature_description_latest_events", "可从面板底部的“概览”标签页查看所选机场最新的起飞和降落，以及每个航班的详细信息。");
        expected.put("whats_new_feature_filters", "按航空器类别筛选");
        expected.put("whats_new_feature_flight_history", "查看更长时间的航班历史");
        expected.put("whats_new_feature_map_layers", "航空气象地图图层");
        expected.put("whats_new_feature_title_airport_history", "机场航班历史");
        expected.put("whats_new_feature_title_airport_movements", "每日起降架次与跑道使用情况");
        expected.put("whats_new_feature_title_airport_panel", "机场面板");
        expected.put("whats_new_feature_title_airport_statistics", "机场统计");
        expected.put("whats_new_feature_title_latest_events", "最新动态");

        assertEquals(34, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String actual = ResourceTranslationDictionary.template(entry.getKey());
            assertEquals(entry.getValue(), actual);
            assertFalse(actual.contains("%"));
            assertFalse(actual.contains("<"));
            assertFalse(actual.contains(">"));
            assertFalse(actual.contains("\n"));
        }
        assertNull(ResourceTranslationDictionary.template("whats_new_with_gold"));
        assertNull(ResourceTranslationDictionary.template("whats_new_unlock_subtitle"));
        assertNull(ResourceTranslationDictionary.template("ar_volcano_whats_new_missing"));
    }

    @Test
    public void formatsSignupPrivacyPolicyOnlyWithOneLinkTextArgument() {
        assertEquals("我们不会与任何人分享你的个人信息。你可以随时更改邮件偏好设置。\n阅读我们的隐私政策",
                ResourceTranslationDictionary.translate(
                        "signup_privacy_policy_note", new Object[]{"隐私政策"}));
        assertNull(ResourceTranslationDictionary.translate("signup_privacy_policy_note", null));
        assertNull(ResourceTranslationDictionary.translate(
                "signup_privacy_policy_note", new Object[]{}));
        assertNull(ResourceTranslationDictionary.translate(
                "signup_privacy_policy_note", new Object[]{"隐私政策", "额外参数"}));
        assertNull(ResourceTranslationDictionary.translate(
                "signup_privacy_policy_note", new Object[]{42}));
        assertNull(ResourceTranslationDictionary.translate("signup_shell_missing", null));
    }

    @Test
    public void preservesSignupAndBookmarkNewlineContracts() {
        assertEquals("我们不会与任何人分享你的个人信息。你可以随时更改邮件偏好设置。\n阅读我们的%s",
                ResourceTranslationDictionary.template("signup_privacy_policy_note"));
        assertEquals("这是你第一次点击星标，我们想借此机会向你介绍它的用法。\n\n点击星标后，航班、飞机或机场会被加入你的收藏列表，方便你快速访问。你可以在地图界面向下滑动顶部栏打开收藏。",
                ResourceTranslationDictionary.template("tooltip_bookmark_welcome_description"));
    }

    @Test
    public void translatesPermissionGuidanceResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("allow", "允许");
        expected.put("no_thanks", "不用了，谢谢");
        expected.put("system_settings", "系统设置");
        expected.put("perm_app_settings_button", "设置");
        expected.put("perm_ar_location_description", "使用 AR 视图需要允许位置和相机权限。允许位置权限后，你还可以：");
        expected.put("perm_ar_location_settings", "你随时可以在设备的“应用权限”设置中更改此偏好。");
        expected.put("perm_ar_location_text_1", "• 查看附近所有航班和机场的列表");
        expected.put("perm_ar_location_text_2", "• 一键将地图缩放到你的位置");
        expected.put("perm_ar_location_title", "Flightradar24 在授予位置和相机权限后体验最佳");
        expected.put("perm_background_location_description", "始终允许 Flightradar24 访问你的位置后，你可以：");
        expected.put("perm_background_location_text", "• 当你到访机场时接收通知，并在后台使用你的位置以便快速查看航班信息。");
        expected.put("perm_camera_ar", "AR 视图需要相机权限。");
        expected.put("perm_camera_ar_settings", "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要相机权限。\n\n如需启用相机权限，请前往“应用设置 → 权限”，并开启相机权限。");
        expected.put("perm_camera_location_ar", "AR 视图需要相机和位置权限。");
        expected.put("perm_camera_location_ar_precise", "AR 视图需要相机和精确位置权限。");
        expected.put("perm_camera_location_ar_settings", "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要相机和位置权限。\n\n如需启用相机和位置权限，请前往“应用设置 → 权限”，并开启相机和位置权限。");
        expected.put("perm_camera_location_ar_settings_precise", "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要相机和精确位置权限。\n\n如需启用相机和精确位置权限，请前往“应用设置 → 权限”，并开启相机和精确位置权限。");
        expected.put("perm_location", "需要位置权限。");
        expected.put("perm_location_ar", "AR 视图需要位置权限。");
        expected.put("perm_location_ar_precise", "AR 视图需要精确位置权限。");
        expected.put("perm_location_ar_settings", "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要位置权限。\n\n如需启用位置权限，请前往“应用设置 → 权限”，并开启位置权限。");
        expected.put("perm_location_ar_settings_precise", "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要精确位置权限。\n\n如需启用精确位置权限，请前往“应用设置 → 权限”，并开启精确位置权限。");
        expected.put("perm_location_description", "在你使用应用期间，允许 Flightradar24 访问你的位置后，你可以：");
        expected.put("perm_location_myloc_settings", "定位按钮可让你一键将地图缩放到你的位置。要使用此功能，我们需要位置权限。\n\n如需使用定位按钮，请前往“应用设置 → 权限”，并开启位置权限。");
        expected.put("perm_location_nearby_airports_background", "机场通知需要“始终允许”的位置权限。");
        expected.put("perm_location_nearby_airports_background_precise", "机场通知需要“始终允许”的精确位置权限。");
        expected.put("perm_location_nearby_settings", "“搜索附近的航空器”会使用你的位置来确定你附近的航空器列表。要使用此功能，我们需要位置权限。\n\n如需使用“搜索附近的航空器”，请前往“应用设置 → 权限”，并开启位置权限。");
        expected.put("perm_location_precise", "需要精确位置权限。");
        expected.put("perm_location_show_myloc_settings", "“显示我的位置”可让你在地图界面上以圆点查看当前位置。要使用此功能，我们需要位置权限。\n\n如需使用“显示我的位置”，请前往“应用设置 → 权限”，并开启位置权限。");
        expected.put("perm_location_text_1", "• 只需将设备对准天空，即可通过 AR 视图查看附近有哪些航班，请见上图示例");
        expected.put("perm_location_title", "Flightradar24 在授予位置权限后体验最佳");
        expected.put("perm_notification", "需要通知权限。");

        assertEquals(32, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String actual = ResourceTranslationDictionary.template(entry.getKey());
            assertEquals(entry.getValue(), actual);
            assertEquals(entry.getValue(), ResourceTranslationDictionary.translate(entry.getKey(), null));
            assertFalse(actual.contains("%"));
            assertFalse(actual.contains("<"));
            assertFalse(actual.contains(">"));
        }
    }

    @Test
    public void preservesPermissionGuidanceBulletsParagraphsAndLabels() {
        String[] bulletResources = {
                "perm_ar_location_text_1",
                "perm_ar_location_text_2",
                "perm_background_location_text",
                "perm_location_text_1"
        };
        for (String resource : bulletResources) {
            String actual = ResourceTranslationDictionary.template(resource);
            assertNotNull(actual);
            assertTrue(actual.startsWith("• "));
        }

        String[] twoParagraphResources = {
                "perm_camera_ar_settings",
                "perm_camera_location_ar_settings",
                "perm_camera_location_ar_settings_precise",
                "perm_location_ar_settings",
                "perm_location_ar_settings_precise",
                "perm_location_myloc_settings",
                "perm_location_nearby_settings",
                "perm_location_show_myloc_settings"
        };
        for (String resource : twoParagraphResources) {
            String actual = ResourceTranslationDictionary.template(resource);
            assertNotNull(actual);
            assertEquals(2, actual.split("\\n\\n", -1).length);
            assertFalse(actual.replace("\n\n", "").contains("\n"));
            assertTrue(actual.contains("“应用设置 → 权限”"));
        }

        String arTitle = ResourceTranslationDictionary.template("perm_ar_location_title");
        String locationTitle = ResourceTranslationDictionary.template("perm_location_title");
        String cameraAr = ResourceTranslationDictionary.template("perm_camera_ar");
        String appPermissions = ResourceTranslationDictionary.template("perm_ar_location_settings");
        String alwaysAllow = ResourceTranslationDictionary.template(
                "perm_location_nearby_airports_background");
        String nearbyAircraft = ResourceTranslationDictionary.template("perm_location_nearby_settings");
        String showMyLocation = ResourceTranslationDictionary.template(
                "perm_location_show_myloc_settings");
        assertNotNull(arTitle);
        assertNotNull(locationTitle);
        assertNotNull(cameraAr);
        assertNotNull(appPermissions);
        assertNotNull(alwaysAllow);
        assertNotNull(nearbyAircraft);
        assertNotNull(showMyLocation);
        assertTrue(arTitle.contains("Flightradar24"));
        assertTrue(locationTitle.contains("Flightradar24"));
        assertTrue(cameraAr.contains("AR"));
        assertTrue(appPermissions.contains("“应用权限”"));
        assertTrue(alwaysAllow.contains("“始终允许”"));
        assertTrue(nearbyAircraft.contains("“搜索附近的航空器”"));
        assertTrue(showMyLocation.contains("“显示我的位置”"));
    }

    @Test
    public void permissionGuidanceFailsOpenAndExcludesThirdPartyResources() {
        assertNull(ResourceTranslationDictionary.template("permission_guidance_missing"));
        assertNull(ResourceTranslationDictionary.translate("permission_guidance_missing", null));

        String[] googleAdsPermissionResources = {
                "notifications_permission_confirm",
                "notifications_permission_decline",
                "notifications_permission_title"
        };
        for (String resource : googleAdsPermissionResources) {
            assertNull(ResourceTranslationDictionary.template(resource));
        }

        assertNull(ResourceTranslationDictionary.template("com_facebook_internet_permission_error_message"));
        assertNull(ResourceTranslationDictionary.template("com_facebook_internet_permission_error_title"));
        assertNull(ResourceTranslationDictionary.template("material_timepicker_select_time"));
        assertNull(ResourceTranslationDictionary.template("androidx_compose_ui_autofill"));
    }

    @Test
    public void translatesAccessibilityAndMapControlsExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("accessibility_airport_photo_action", "在外部浏览器中打开图片");
        expected.put("accessibility_drop_down_menu_toggle", "切换下拉面板");
        expected.put("accessibility_map", "地图");
        expected.put("accessibility_move_map_down", "向下移动地图");
        expected.put("accessibility_move_map_left", "向左移动地图");
        expected.put("accessibility_move_map_right", "向右移动地图");
        expected.put("accessibility_move_map_up", "向上移动地图");
        expected.put("accessibility_show_percentages", "显示百分比");
        expected.put("accessibility_weather", "天气：%s");
        expected.put("accessibility_zoom_in_map", "放大地图");
        expected.put("accessibility_zoom_out_map", "缩小地图");
        expected.put("back_button_content_description", "返回");
        expected.put("log_in_screen_title", "登录");
        expected.put("my_location_description", "前往我的位置");
        expected.put("please_wait", "请稍候…");

        assertEquals(15, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
            if (!"accessibility_weather".equals(entry.getKey())) {
                assertEquals(entry.getValue(),
                        ResourceTranslationDictionary.translate(entry.getKey(), null));
                assertFalse(entry.getValue().contains("%"));
                assertFalse(entry.getValue().contains("<"));
                assertFalse(entry.getValue().contains(">"));
                assertFalse(entry.getValue().contains("\n"));
            }
        }
    }

    @Test
    public void formatsAccessibilityWeatherOnlyWithOneStringArgument() {
        String template = ResourceTranslationDictionary.template("accessibility_weather");
        assertEquals("天气：%s", template);
        assertEquals(1, template.split("%s", -1).length - 1);
        assertEquals("天气：晴", ResourceTranslationDictionary.translate(
                "accessibility_weather", new Object[]{"晴"}));
        assertNull(ResourceTranslationDictionary.translate("accessibility_weather", null));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_weather", new Object[]{}));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_weather", new Object[]{"晴", "额外参数"}));
        assertNull(ResourceTranslationDictionary.translate(
                "accessibility_weather", new Object[]{42}));
        assertTrue(ResourceTranslationDictionary.template("please_wait").endsWith("…"));
    }

    @Test
    public void accessibilityControlsExcludeUnsafeSelectedAndFailOpen() {
        assertNull(ResourceTranslationDictionary.template("selected"));
        assertNull(ResourceTranslationDictionary.translate("selected", new Object[]{2}));
        assertNull(ResourceTranslationDictionary.template("accessibility_controls_missing"));
        assertNull(ResourceTranslationDictionary.translate("accessibility_controls_missing", null));
    }

    @Test
    public void translatesGenericActionsAndSharedLabelsExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("btn_could_be_better", "可以更好");
        expected.put("btn_great", "很棒");
        expected.put("btn_provide_feekback", "留下反馈");
        expected.put("btn_rate_and_review", "评分/评价");
        expected.put("cancel", "取消");
        expected.put("clear", "清除");
        expected.put("exit", "你确定要退出应用吗？");
        expected.put("from", "从");
        expected.put("loading", "正在加载");
        expected.put("no", "否");
        expected.put("ok", "确定");
        expected.put("off_caps", "关");
        expected.put("on_caps", "开");
        expected.put("or", "或");
        expected.put("select_all", "全选");
        expected.put("to", "到");
        expected.put("volcano", "火山");
        expected.put("yes", "是");

        assertEquals(18, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String actual = ResourceTranslationDictionary.template(entry.getKey());
            assertEquals(entry.getValue(), actual);
            assertEquals(entry.getValue(),
                    ResourceTranslationDictionary.translate(entry.getKey(), null));
            assertFalse(actual.contains("%"));
            assertFalse(actual.contains("<"));
            assertFalse(actual.contains(">"));
            assertFalse(actual.contains("\n"));
            assertFalse(actual.contains("\r"));
        }
    }

    @Test
    public void genericActionsKeepMisspelledKeyAndFailOpenForExcludedKeys() {
        assertEquals("留下反馈",
                ResourceTranslationDictionary.template("btn_provide_feekback"));
        assertNull(ResourceTranslationDictionary.template("btn_provide_feedback"));
        assertNull(ResourceTranslationDictionary.translate("btn_provide_feedback", null));
        assertNull(ResourceTranslationDictionary.template("selected"));
        assertNull(ResourceTranslationDictionary.translate("selected", new Object[]{2}));
        assertNull(ResourceTranslationDictionary.template("generic_actions_missing"));
        assertNull(ResourceTranslationDictionary.translate("generic_actions_missing", null));
    }

    @Test
    public void translatesAccountDownloadAndSharedActionResourcesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("aircraft_no_photo_title", "航空器照片不可用");
        expected.put("alerts_to_many_conditions", "最多只能添加 5 个条件");
        expected.put("custom_filter_list_edit", "编辑");
        expected.put("custom_filter_list_switch_enable", "启用筛选");
        expected.put("delete", "删除");
        expected.put("discard", "放弃");
        expected.put("delete_account_code_description", "请输入下方代码，以确认您要删除账户。");
        expected.put("delete_account_description_no_subscription",
                "点击“删除账户”即可删除您的 Flightradar24 账户，以及与其关联的所有个人数据和产品数据，其中包括所有已登录设备上的已启用且已保存的筛选、设置、提醒、收藏和 MyFR24 数据。部分存储在应用本地的产品数据仍会保留在您的 iOS/Android 设备上，可能需要手动删除。\n\n您可以随时创建新账户。");
        expected.put("delete_account_enter_code", "输入代码");
        expected.put("delete_account_error_message", "无法删除您的账户。请稍后重试。如果问题仍然存在，请联系支持团队。");
        expected.put("delete_account_error_title", "发生错误");
        expected.put("delete_account_got_it", "知道了");
        expected.put("delete_account_group_error_message", "您的账户属于某个群组。请联系群组管理员删除您的账户。");
        expected.put("delete_account_group_error_title", "无法删除账户");
        expected.put("delete_account_success_message", "您的账户已永久删除。");
        expected.put("delete_account_success_title", "账户已删除");
        expected.put("download_csv", "下载 CSV");
        expected.put("download_kml", "下载 KML");
        expected.put("download_quota_reached", "您已达到下载限额。");
        expected.put("download_text", "您可以将航班数据下载为 KML 或 CSV 文件。");
        expected.put("download_title", "下载数据文件");

        assertEquals(21, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
            assertEquals(entry.getValue(),
                    ResourceTranslationDictionary.translate(entry.getKey(), null));
        }
    }

    @Test
    public void preservesAccountDescriptionParagraphAndStaticResourceContracts() {
        String description = ResourceTranslationDictionary.template(
                "delete_account_description_no_subscription");
        assertNotNull(description);
        assertEquals(2, description.split("\\n\\n", -1).length);
        assertFalse(description.replace("\n\n", "").contains("\n"));
        assertFalse(description.contains("\r"));

        String[] staticResources = {
                "aircraft_no_photo_title",
                "alerts_to_many_conditions",
                "custom_filter_list_edit",
                "custom_filter_list_switch_enable",
                "delete",
                "discard",
                "delete_account_code_description",
                "delete_account_enter_code",
                "delete_account_error_message",
                "delete_account_error_title",
                "delete_account_got_it",
                "delete_account_group_error_message",
                "delete_account_group_error_title",
                "delete_account_success_message",
                "delete_account_success_title",
                "download_csv",
                "download_kml",
                "download_quota_reached",
                "download_text",
                "download_title"
        };
        assertEquals(20, staticResources.length);
        for (String resource : staticResources) {
            String actual = ResourceTranslationDictionary.template(resource);
            assertNotNull(actual);
            assertFalse(actual.contains("%"));
            assertFalse(actual.contains("<"));
            assertFalse(actual.contains(">"));
            assertFalse(actual.contains("\n"));
            assertFalse(actual.contains("\r"));
        }

        assertTrue(description.contains("Flightradar24"));
        assertTrue(description.contains("MyFR24"));
        assertTrue(description.contains("所有已登录设备"));
        assertTrue(description.contains("iOS/Android"));
        assertTrue(ResourceTranslationDictionary.template("download_csv").contains("CSV"));
        assertTrue(ResourceTranslationDictionary.template("download_kml").contains("KML"));
        assertTrue(ResourceTranslationDictionary.template("download_text").contains("CSV"));
        assertTrue(ResourceTranslationDictionary.template("download_text").contains("KML"));
        assertTrue(ResourceTranslationDictionary.template("alerts_to_many_conditions").contains("5"));
    }

    @Test
    public void accountDownloadBatchExcludesSubscriptionTextAndFailsOpen() {
        assertNull(ResourceTranslationDictionary.template(
                "delete_account_description_subscription_on_android"));
        assertNull(ResourceTranslationDictionary.translate(
                "delete_account_description_subscription_on_android", null));
        assertNull(ResourceTranslationDictionary.template("account_download_actions_missing"));
        assertNull(ResourceTranslationDictionary.translate("account_download_actions_missing", null));
    }

    @Test
    public void translatesErrorsFlightValidationPhotosAndLanguageExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("error_something_went_wrong", "出了点问题。");
        expected.put("location_error", "抱歉，无法确定您的位置。请确保已开启定位服务。");
        expected.put("no_camera_error", "AR 视图需要设备配备后置摄像头。");
        expected.put("no_connection_error_message", "请检查网络连接后重试。");
        expected.put("stats_no_aircraft_error_msg", "您查看的区域可能不在覆盖范围内，或筛选条件过于严格。");
        expected.put("unable_to_locate", "无法获取您的位置，请检查定位设置后重试。");
        expected.put("widget_error", "出了点问题，无法加载数据。");
        expected.put("technical_problems", "技术问题");
        expected.put("try_again", "重试");
        expected.put("no_aircraft_found", "未找到航空器。");
        expected.put("no_callsign", "无呼号");
        expected.put("flight_ended_title", "正在关注的航班已降落或超出覆盖范围");
        expected.put("flight_info_load_more_flights", "加载更多航班");
        expected.put("flight_validation_btn_error", "确定，返回实时地图");
        expected.put("flight_validation_btn_no", "否，返回实时地图");
        expected.put("flight_validation_btn_yes_aircraft", "是，查看航空器历史");
        expected.put("flight_validation_btn_yes_flight", "是，查看航班历史");
        expected.put("flight_validation_error_msg", "目前无法完成您的请求。请稍后重试。");
        expected.put("flight_validation_error_title", "请求失败");
        expected.put("flight_validation_not_found", "抱歉，找不到该航班的数据。");
        expected.put("flight_validation_title", "未找到实时航班");
        expected.put("label_flight_number", "航班号");
        expected.put("no_flights_found", "未找到航班。");
        expected.put("infinite_flight_error_crashed", "抱歉，3D 视图已停止运行。请重启应用后重试。");
        expected.put("infinite_flight_error_title", "发生错误");
        expected.put("jetphotos_error_msg", "无法加载图片。");
        expected.put("jetphotos_error_title", "出了点问题");
        expected.put("language_restart", "重启");
        expected.put("language_restart_msg", "请重启 Flightradar24 以应用所选语言设置。");

        assertEquals(29, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String actual = ResourceTranslationDictionary.template(entry.getKey());
            assertEquals(entry.getValue(), actual);
            assertEquals(entry.getValue(),
                    ResourceTranslationDictionary.translate(entry.getKey(), null));
        }
    }

    @Test
    public void preservesErrorsFlightPhotosAndLanguageStaticContracts() {
        String[] staticResources = {
                "error_something_went_wrong",
                "location_error",
                "no_camera_error",
                "no_connection_error_message",
                "stats_no_aircraft_error_msg",
                "unable_to_locate",
                "widget_error",
                "technical_problems",
                "try_again",
                "no_aircraft_found",
                "no_callsign",
                "flight_ended_title",
                "flight_info_load_more_flights",
                "flight_validation_btn_error",
                "flight_validation_btn_no",
                "flight_validation_btn_yes_aircraft",
                "flight_validation_btn_yes_flight",
                "flight_validation_error_msg",
                "flight_validation_error_title",
                "flight_validation_not_found",
                "flight_validation_title",
                "label_flight_number",
                "no_flights_found",
                "infinite_flight_error_crashed",
                "infinite_flight_error_title",
                "jetphotos_error_msg",
                "jetphotos_error_title",
                "language_restart",
                "language_restart_msg"
        };
        assertEquals(29, staticResources.length);
        for (String resource : staticResources) {
            String actual = ResourceTranslationDictionary.template(resource);
            assertNotNull(actual);
            assertFalse(actual.contains("%"));
            assertFalse(actual.contains("<"));
            assertFalse(actual.contains(">"));
            assertFalse(actual.contains("\n"));
            assertFalse(actual.contains("\r"));
        }

        assertTrue(ResourceTranslationDictionary.template("no_camera_error").contains("AR"));
        assertTrue(ResourceTranslationDictionary.template(
                "infinite_flight_error_crashed").contains("3D"));
        assertTrue(ResourceTranslationDictionary.template(
                "language_restart_msg").contains("Flightradar24"));
        assertTrue(ResourceTranslationDictionary.template("no_aircraft_found").contains("航空器"));
        assertTrue(ResourceTranslationDictionary.template("no_callsign").contains("呼号"));
        assertEquals("出了点问题，无法加载数据。",
                ResourceTranslationDictionary.template("error_something_went_wrong_longer"));
    }

    @Test
    public void errorsFlightPhotosAndLanguageExcludeUnreviewedShellsAndFailOpen() {
        String[] excludedResources = {
                "dialog_3d_description_infinite_flight",
                "dialog_3d_disclaimer_infinite_flight",
                "dialog_3d_used_all_sessions_rewarded",
                "dialog_3d_used_all_sessions_rewarded2",
                "jetphotos_promo_msg",
                "jetphotos_promo_title",
                "com_facebook_image_download_unknown_error",
                "fingerprint_error_hw_not_available",
                "generic_error_no_device_credential",
                "material_timepicker_hour_error",
                "license_content_error",
                "default_error_msg"
        };
        for (String resource : excludedResources) {
            assertNull(ResourceTranslationDictionary.template(resource));
            assertNull(ResourceTranslationDictionary.translate(resource, null));
        }
        assertNull(ResourceTranslationDictionary.template("errors_flight_language_missing"));
        assertNull(ResourceTranslationDictionary.translate("errors_flight_language_missing", null));
    }

    @Test
    public void translatesLiveNotificationsRatingsMultiSelectAndWidgetExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("live_notifications_created_for", "已为 %s 创建实时通知");
        expected.put("live_notifications_creation_error_description", "请稍后重试。");
        expected.put("live_notifications_creation_error_title", "无法创建实时通知");
        expected.put("live_notifications_permission_popup_description",
                "要使用实时通知，请在系统设置中启用通知。");
        expected.put("live_notifications_permission_popup_title", "实时通知");
        expected.put("live_notifications_stop_following", "停止关注");
        expected.put("message_feedback_dailog",
                "很遗憾未能令您满意！\n希望您能告诉我们如何改进 Flightradar24。");
        expected.put("message_rate_app",
                "很高兴您喜欢！\n如果您能在 Google Play 商店为 Flightradar24 评分或撰写评价，帮助更多用户发现这款应用，我们将不胜感激。");
        expected.put("messages_rate_app_dialog", "到目前为止，您觉得 Flightradar24 怎么样？");
        expected.put("title_rate_us", "Flightradar24 意见反馈");
        expected.put("multi_select_popup_description", "请从下方列表选择一个航班或机场");
        expected.put("multi_select_popup_title", "应用当前不支持多选");
        expected.put("multi_select_unavailable_airport", "不可用的机场");
        expected.put("multi_select_unavailable_flight", "不可用的航班");
        expected.put("onground_disclaimer",
                "免责声明：在确认航空器已从机场起飞之前，该航空器将一直保留在列表中。请注意，数据可能存在误差。");
        expected.put("widget_last_update", "上次更新于 %s");

        assertEquals(16, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String actual = ResourceTranslationDictionary.template(entry.getKey());
            assertEquals(entry.getValue(), actual);
            assertFalse(actual.contains("<"));
            assertFalse(actual.contains(">"));
        }

        String[] staticResources = {
                "live_notifications_creation_error_description",
                "live_notifications_creation_error_title",
                "live_notifications_permission_popup_description",
                "live_notifications_permission_popup_title",
                "live_notifications_stop_following",
                "message_feedback_dailog",
                "message_rate_app",
                "messages_rate_app_dialog",
                "title_rate_us",
                "multi_select_popup_description",
                "multi_select_popup_title",
                "multi_select_unavailable_airport",
                "multi_select_unavailable_flight",
                "onground_disclaimer"
        };
        assertEquals(14, staticResources.length);
        for (String resource : staticResources) {
            assertEquals(ResourceTranslationDictionary.template(resource),
                    ResourceTranslationDictionary.translate(resource, null));
        }
    }

    @Test
    public void formatsLiveNotificationAndWidgetOnlyWithOneStringArgument() {
        String liveTemplate = ResourceTranslationDictionary.template(
                "live_notifications_created_for");
        String widgetTemplate = ResourceTranslationDictionary.template("widget_last_update");
        assertEquals("已为 %s 创建实时通知", liveTemplate);
        assertEquals("上次更新于 %s", widgetTemplate);
        assertEquals(1, liveTemplate.split("%s", -1).length - 1);
        assertEquals(1, widgetTemplate.split("%s", -1).length - 1);
        assertFalse(liveTemplate.replace("%s", "").contains("%"));
        assertFalse(widgetTemplate.replace("%s", "").contains("%"));

        assertEquals("已为 MU5103 创建实时通知", ResourceTranslationDictionary.translate(
                "live_notifications_created_for", new Object[]{"MU5103"}));
        assertEquals("上次更新于 12:30", ResourceTranslationDictionary.translate(
                "widget_last_update", new Object[]{"12:30"}));

        String[] formattedResources = {
                "live_notifications_created_for",
                "widget_last_update"
        };
        for (String resource : formattedResources) {
            assertNull(ResourceTranslationDictionary.translate(resource, null));
            assertNull(ResourceTranslationDictionary.translate(resource, new Object[]{}));
            assertNull(ResourceTranslationDictionary.translate(resource, new Object[]{null}));
            assertNull(ResourceTranslationDictionary.translate(
                    resource, new Object[]{"有效", "额外参数"}));
            assertNull(ResourceTranslationDictionary.translate(resource, new Object[]{42}));
        }
    }

    @Test
    public void preservesRatingNewlinesBrandsAndPlainTextContracts() {
        String feedback = ResourceTranslationDictionary.template("message_feedback_dailog");
        String rateApp = ResourceTranslationDictionary.template("message_rate_app");
        assertNotNull(feedback);
        assertNotNull(rateApp);
        assertEquals(1, feedback.split("\n", -1).length - 1);
        assertEquals(1, rateApp.split("\n", -1).length - 1);
        assertFalse(feedback.contains("\r"));
        assertFalse(rateApp.contains("\r"));

        String[] noNewlineResources = {
                "live_notifications_created_for",
                "live_notifications_creation_error_description",
                "live_notifications_creation_error_title",
                "live_notifications_permission_popup_description",
                "live_notifications_permission_popup_title",
                "live_notifications_stop_following",
                "messages_rate_app_dialog",
                "title_rate_us",
                "multi_select_popup_description",
                "multi_select_popup_title",
                "multi_select_unavailable_airport",
                "multi_select_unavailable_flight",
                "onground_disclaimer",
                "widget_last_update"
        };
        assertEquals(14, noNewlineResources.length);
        for (String resource : noNewlineResources) {
            String actual = ResourceTranslationDictionary.template(resource);
            assertNotNull(actual);
            assertFalse(actual.contains("\n"));
            assertFalse(actual.contains("\r"));
        }

        assertTrue(feedback.contains("Flightradar24"));
        assertTrue(rateApp.contains("Flightradar24"));
        assertTrue(rateApp.contains("Google Play"));
        assertTrue(ResourceTranslationDictionary.template(
                "messages_rate_app_dialog").contains("Flightradar24"));
        assertTrue(ResourceTranslationDictionary.template("title_rate_us")
                .contains("Flightradar24"));
    }

    @Test
    public void liveNotificationBatchExcludesUnverifiedPromoResourcesAndFailsOpen() {
        String[] excludedResources = {
                "live_notifications_creation_error_not_compatible",
                "live_notifications_promo_badge",
                "live_notifications_promo_create_account_hint",
                "live_notifications_promo_create_free_account",
                "live_notifications_promo_description",
                "live_notifications_promo_dont_show_again",
                "live_notifications_promo_free_trial_hint",
                "live_notifications_promo_hint",
                "live_notifications_promo_title"
        };
        assertEquals(9, excludedResources.length);
        for (String resource : excludedResources) {
            assertNull(ResourceTranslationDictionary.template(resource));
            assertNull(ResourceTranslationDictionary.translate(resource, null));
        }
        assertNull(ResourceTranslationDictionary.template(
                "notifications_ratings_widget_missing"));
        assertNull(ResourceTranslationDictionary.translate(
                "notifications_ratings_widget_missing", null));
    }

    @Test
    public void translatesFlightStateAndAdErrorHtmlTemplatesExactly() {
        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("flight_ended_description",
                "呼号为 %s 的航班目前未被 Flightradar24 跟踪。该航班可能已超出覆盖范围或已降落。");
        expected.put("flight_ended_description_history", "是否查看航空器 %s 的航班历史？");
        expected.put("flight_validation_found_aircraft",
                "注册号为 %s 的航空器目前未被 Flightradar24 跟踪。该航空器可能已超出覆盖范围或已降落。\n\n是否查看该航班的历史记录？");
        expected.put("flight_validation_found_flight",
                "航班 %s 目前未被 Flightradar24 跟踪。该航班可能已超出覆盖范围或已降落。\n\n是否查看该航班的历史记录？");
        expected.put("unable_to_load_ad",
                "<font face=sans-serif-medium>无法加载广告，请重试。</font>");
        expected.put("unable_to_load_ad_tip",
                "<font face=sans-serif-medium>无法加载广告，请重试。</font><br/>提示：为提高获得激励广告的概率，请在“隐私偏好中心”中调整广告偏好设置。");

        assertEquals(6, expected.size());
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), ResourceTranslationDictionary.template(entry.getKey()));
        }
    }

    @Test
    public void formatsFlightStateHtmlOnlyWithOneStringArgument() {
        String[] formattedResources = {
                "flight_ended_description",
                "flight_ended_description_history",
                "flight_validation_found_aircraft",
                "flight_validation_found_flight"
        };
        for (String resource : formattedResources) {
            String template = ResourceTranslationDictionary.template(resource);
            assertNotNull(template);
            assertEquals(1, template.split("%s", -1).length - 1);
            assertFalse(template.replace("%s", "").contains("%"));
            assertNull(ResourceTranslationDictionary.translate(resource, null));
            assertNull(ResourceTranslationDictionary.translate(resource, new Object[]{}));
            assertNull(ResourceTranslationDictionary.translate(resource, new Object[]{null}));
            assertNull(ResourceTranslationDictionary.translate(
                    resource, new Object[]{"<b>MU5103</b>", "额外参数"}));
            assertNull(ResourceTranslationDictionary.translate(resource, new Object[]{5103}));
        }

        String wrapped = "<b>MU5103</b>";
        String ended = ResourceTranslationDictionary.translate(
                "flight_ended_description", new Object[]{wrapped});
        String history = ResourceTranslationDictionary.translate(
                "flight_ended_description_history", new Object[]{wrapped});
        String foundAircraft = ResourceTranslationDictionary.translate(
                "flight_validation_found_aircraft", new Object[]{wrapped});
        String foundFlight = ResourceTranslationDictionary.translate(
                "flight_validation_found_flight", new Object[]{wrapped});
        assertEquals("呼号为 <b>MU5103</b> 的航班目前未被 Flightradar24 跟踪。该航班可能已超出覆盖范围或已降落。", ended);
        assertEquals("是否查看航空器 <b>MU5103</b> 的航班历史？", history);
        assertTrue(foundAircraft.contains(wrapped));
        assertTrue(foundFlight.contains(wrapped));
        assertFalse(ended.contains("%s"));
        assertFalse(history.contains("%s"));
        assertFalse(foundAircraft.contains("%s"));
        assertFalse(foundFlight.contains("%s"));
    }

    @Test
    public void preservesFlightParagraphsAndAdHtmlContracts() {
        String foundAircraft = ResourceTranslationDictionary.template(
                "flight_validation_found_aircraft");
        String foundFlight = ResourceTranslationDictionary.template(
                "flight_validation_found_flight");
        assertNotNull(foundAircraft);
        assertNotNull(foundFlight);
        assertEquals(1, foundAircraft.split("\n\n", -1).length - 1);
        assertEquals(1, foundFlight.split("\n\n", -1).length - 1);
        assertFalse(foundAircraft.replace("\n\n", "").contains("\n"));
        assertFalse(foundFlight.replace("\n\n", "").contains("\n"));

        String ad = ResourceTranslationDictionary.template("unable_to_load_ad");
        String tip = ResourceTranslationDictionary.template("unable_to_load_ad_tip");
        assertNotNull(ad);
        assertNotNull(tip);
        assertEquals("<font face=sans-serif-medium>无法加载广告，请重试。</font>", ad);
        assertEquals("<font face=sans-serif-medium>无法加载广告，请重试。</font><br/>提示：为提高获得激励广告的概率，请在“隐私偏好中心”中调整广告偏好设置。", tip);
        assertEquals(ad, ResourceTranslationDictionary.translate("unable_to_load_ad", null));
        assertEquals(tip, ResourceTranslationDictionary.translate("unable_to_load_ad_tip", null));
        assertTrue(ad.startsWith("<font face=sans-serif-medium>"));
        assertTrue(ad.endsWith("</font>"));
        assertTrue(tip.startsWith("<font face=sans-serif-medium>"));
        assertTrue(tip.contains("</font><br/>"));
        assertFalse(ad.contains("%"));
        assertFalse(tip.contains("%"));
        assertNull(ResourceTranslationDictionary.template("flight_html_ad_missing"));
        assertNull(ResourceTranslationDictionary.translate("flight_html_ad_missing", null));
    }

}
