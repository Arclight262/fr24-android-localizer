package io.github.fr24zh.localizer;

import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

final class ResourceTranslationDictionary {
    private static final Map<String, String> TRANSLATIONS = createTranslations();

    private ResourceTranslationDictionary() {
    }

    static String translate(String resourceName, Object[] formatArguments) {
        if (resourceName == null) {
            return null;
        }
        String template = TRANSLATIONS.get(resourceName);
        if (template == null) {
            return null;
        }
        if (("accessibility_icao".equals(resourceName)
                || "accessibility_squawk".equals(resourceName)
                || "signup_privacy_policy_note".equals(resourceName)
                || "accessibility_weather".equals(resourceName)
                || "live_notifications_created_for".equals(resourceName)
                || "widget_last_update".equals(resourceName)
                || "flight_ended_description".equals(resourceName)
                || "flight_ended_description_history".equals(resourceName)
                || "flight_validation_found_aircraft".equals(resourceName)
                || "flight_validation_found_flight".equals(resourceName))
                && (formatArguments == null
                || formatArguments.length != 1
                || !(formatArguments[0] instanceof String))) {
            return null;
        }
        try {
            return String.format(
                    Locale.SIMPLIFIED_CHINESE,
                    template,
                    formatArguments == null ? new Object[]{} : formatArguments);
        } catch (IllegalFormatException ignored) {
            return null;
        }
    }

    static int size() {
        return TRANSLATIONS.size();
    }

    static String template(String resourceName) {
        return resourceName == null ? null : TRANSLATIONS.get(resourceName);
    }

    private static Map<String, String> createTranslations() {
        Map<String, String> translations = new LinkedHashMap<>();
        add(translations, "cab_more_info", "更多 %s 信息");
        add(translations, "cab_more_flight_from_to", "%1$s 航班：%2$s → %3$s");
        add(translations, "cab_distance", "大圆距离");
        add(translations, "cab_flight_time", "平均飞行时间");
        add(translations, "cab_more_arrival_information", "到达信息");
        add(translations, "cab_baggage", "行李转盘");
        add(translations, "cab_history_history_more", "更多 %s 航班");
        add(translations, "label_year", "%s 年");
        add(translations, "cab_small_arriving", "将在 %s后到达");
        add(translations, "cab_small_arriving_ago", "已于 %s前到达");
        add(translations, "cab_small_departed", "已于 %s前起飞");
        add(translations, "cab_aircraft_type", "机型（%s）");
        add(translations, "cab_aircraft_age", "机龄（%s）");
        add(translations, "cab_aircraft_history", "最近的 %s 航班");
        add(translations, "cab_data_source", "数据来源 — %s");
        add(translations, "cab_diverting_to", "正在备降至 — %1$s（%2$s）");
        add(translations, "search_country_airports_title", "%s 的机场");
        add(translations, "search_headers", "%1$d/%2$d %3$s");
        add(translations, "settings_aircraft_info_desc",
                "地图上的航空器少于 %d 架时，标签会随航空器移动并显示。");
        add(translations, "settings_weather_winds_barbs_legend", "风羽（%s）");
        add(translations, "settings_weather_winds_level_legend", "风速（%s）");
        add(translations, "filters_delete_dialog_description", "确定要删除 %s 吗？");
        add(translations, "global_playback_timeline_upgrade_text",
                "升级后可查看超过 %d 天的回放历史");
        add(translations, "menu_ar", "AR 视图");
        add(translations, "my_account", "我的账户");
        add(translations, "login_email", "电子邮箱");
        add(translations, "login_create_password", "创建密码");
        add(translations, "login_logout", "退出登录");
        add(translations, "login_subscription", "订阅方案");
        add(translations, "subs_basic", "免费");
        add(translations, "login_available_features", "可用功能");
        add(translations, "login_upgrade_subscription", "升级订阅方案");
        add(translations, "delete_account", "删除账户");
        add(translations, "menu_log_in", "登录");
        add(translations, "menu_apply_for_receiver", "增加覆盖");
        add(translations, "menu_about", "关于");
        add(translations, "menu_commerical_services", "商业服务");
        add(translations, "menu_feedback", "反馈");
        add(translations, "menu_share", "分享");
        add(translations, "close", "关闭");
        add(translations, "menu_privacy_policy", "隐私政策");
        add(translations, "menu_subscription", "升级");
        add(translations, "menu_subscription_cta_50", "解锁 60 多项功能");
        add(translations, "menu_terms_and_conditions", "服务条款");
        add(translations, "search_404", "未找到结果");
        add(translations, "search_airborne_flights", "实时航班");
        add(translations, "search_airlines", "航空公司");
        add(translations, "search_airports", "机场");
        add(translations, "search_hint", "航班号、机场、航线或注册号");
        add(translations, "search_history_title", "搜索历史");
        add(translations, "search_clear_history_title", "╳ 清除历史");
        add(translations, "search_clear_history_confirmation", "确定要清除搜索历史吗？");
        add(translations, "search_shortcuts_to_find", "常用查找方式");
        add(translations, "search_shortcut_airline", "按航空公司查找实时航班");
        add(translations, "search_shortcut_airport", "按国家/地区查找机场");
        add(translations, "search_shortcut_airport_flight_history", "机场航班历史");
        add(translations, "search_shortcut_nearby", "附近");
        add(translations, "search_shortcut_route", "按航线查找航班");
        add(translations, "search_recent_flights", "最近或计划航班");
        add(translations, "cab_aircraft_registration", "注册号");
        add(translations, "cab_aircraft_serial_number", "序列号（MSN）");
        add(translations, "cab_aircraft_type_na", "机型");
        add(translations, "cab_btn_less", "收起信息");
        add(translations, "cab_btn_more_info", "更多信息");
        add(translations, "cab_calibrated_altitude", "气压高度");
        add(translations, "cab_ground_speed", "地速");
        add(translations, "cab_vertical_speed", "垂直速度");
        add(translations, "cab_gps_altitude", "GPS 高度");
        add(translations, "cab_track", "航迹");
        add(translations, "cab_speed_altitude_chart", "速度与高度图");
        add(translations, "cab_true_air_speed", "真空速");
        add(translations, "cab_indicated_air_speed", "指示空速");
        add(translations, "cab_mach", "马赫数");
        add(translations, "cab_weather_wind", "风");
        add(translations, "cab_weather_temperature", "温度");
        add(translations, "cab_radar_fir_uir", "飞行情报区/高空飞行情报区");
        add(translations, "cab_aircraft_mode_s_code", "ICAO 24 位地址");
        add(translations, "cab_squawk", "应答机代码");
        add(translations, "cab_latitude", "纬度");
        add(translations, "cab_longitude", "经度");
        add(translations, "cab_myfr24_title", "旅行者");
        add(translations, "cab_reg_country", "注册国");
        add(translations, "settings_menu_visibility", "显示");
        add(translations, "settings_menu_misc", "其他");
        add(translations, "settings_map_type", "地图类型");
        add(translations, "settings_map_type_map", "标准");
        add(translations, "settings_map_type_terrain", "地形");
        add(translations, "settings_map_type_satellite", "卫星");
        add(translations, "settings_map_type_hybrid", "混合");
        add(translations, "settings_map_type_map_style_1", "银色");
        add(translations, "settings_map_type_map_style_2", "深色");
        add(translations, "settings_map_type_map_style_3", "茄紫色");
        add(translations, "settings_map_type_map_style_4", "简洁");
        add(translations, "settings_map_type_map_style_5", "黑白");
        add(translations, "settings_aircraft_info", "航空器标签");
        add(translations, "settings_aircraft_info_logo", "标志");
        add(translations, "settings_aircraft_info_text", "文字标签");
        add(translations, "settings_dim_map", "地图亮度");
        add(translations, "settings_show_day_night", "昼夜分界线");
        add(translations, "settings_show_day_night_desc", "一眼查看地图上的昼夜区域。");
        add(translations, "settings_show_atc_boundaries", "空管边界");
        add(translations, "settings_show_atc_boundaries_desc",
                "全球飞行情报区（FIR）和高空飞行情报区（UIR）边界。");
        add(translations, "settings_show_atc_boundaries_red", "红色");
        add(translations, "settings_show_atc_boundaries_green", "绿色");
        add(translations, "settings_show_atc_boundaries_blue", "蓝色");
        add(translations, "settings_show_oceanic_tracks", "洋区航路");
        add(translations, "settings_show_oceanic_tracks_desc",
                "当前北大西洋、太平洋洋区航路及澳大利亚灵活航路。");
        add(translations, "settings_aeronatuical_charts", "航空图");
        add(translations, "settings_aeronatuical_charts_desc",
                "导航台是机组使用的无线电导航信标。低空和高空图层显示导航航路点和航路。");
        add(translations, "settings_aeronatuical_charts_navaids", "导航台");
        add(translations, "settings_aeronatuical_charts_low", "低空");
        add(translations, "settings_aeronatuical_charts_high", "高空");
        add(translations, "settings_show_airports_desc", "在地图上显示机场位置。放大可查看更多机场。");
        add(translations, "settings_show_my_location", "显示我的位置");
        add(translations, "settings_show_my_location_desc", "在地图上以圆点显示你的位置。");
        add(translations, "settings_screen_timeout", "屏幕休眠");
        add(translations, "settings_screen_timeout_desc", "启用后，屏幕会在无操作后休眠。");
        add(translations, "settings_show_photos", "航空器照片");
        add(translations, "settings_show_photos_summary",
                "有照片时显示航空器照片；关闭可减少数据流量。");
        add(translations, "settings_show_notification_bar", "显示系统栏");
        add(translations, "settings_show_notification_bar_summary",
                "在屏幕顶部显示系统通知栏。");
        add(translations, "settings_display_prompt", "退出时确认");
        add(translations, "settings_display_prompt_summary", "关闭应用前显示确认提示。");
        add(translations, "settings_live_notifications_title", "实时通知");
        add(translations, "settings_live_notifications_summary",
                "点击实时航班面板中的“关注”按钮时创建实时通知。");
        add(translations, "settings_tz", "时区");
        add(translations, "settings_tz_long_summary",
                "控制应用中的时间显示时区。“机场”会按相应出发或到达机场的当地时间显示；“本地”使用 Android 设备时区；“UTC”统一使用协调世界时（UTC），即航空领域采用的时间标准。");
        add(translations, "language", "语言");
        add(translations, "settings_time", "时间");
        add(translations, "settings_time_format", "时间格式");
        add(translations, "settings_temperature", "温度");
        add(translations, "settings_speed", "航空器速度");
        add(translations, "settings_speed_vertical", "垂直速度");
        add(translations, "settings_speed_wind", "风速");
        add(translations, "settings_distance_unit", "距离");
        add(translations, "settings_accessibility", "无障碍");
        add(translations, "settings_accessibility_map_controls", "地图控制");
        add(translations, "settings_accessibility_map_controls_desc", "使用按钮缩放和移动地图");
        add(translations, "settings_privacy", "隐私");
        add(translations, "settings_personalized_ads", "隐私偏好中心");
        add(translations, "settings_personalized_ads_button", "打开");
        add(translations, "settings_personalized_ads_summary", "管理你的个人数据和广告偏好");
        add(translations, "settings_analytics", "分析");
        add(translations, "settings_analytics_summary", "发送分析数据，帮助我们改进应用。");
        add(translations, "settings_crash_reporting", "崩溃报告");
        add(translations, "settings_crash_reporting_summary", "发送应用崩溃报告，帮助我们改进使用体验。");
        add(translations, "settings_performance_monitoring", "性能监控");
        add(translations, "settings_performance_monitoring_summary", "发送性能数据，帮助我们改进使用体验。");
        add(translations, "settings_read_tos", "阅读完整服务条款");
        add(translations, "settings_read_tos_fr24", "Flightradar24 服务条款");
        add(translations, "view_osl_title", "开源许可");
        add(translations, "view_osl_click", "开源软件许可详情");
        add(translations, "settings_weather_basic_weather", "当前天气");
        add(translations, "settings_weather_cloud", "云层");
        add(translations, "settings_weather_volcano", "火山喷发");
        add(translations, "settings_weather_basic_weather_desc", "全球 3,000 个机场的当前天气。");
        add(translations, "settings_weather_cloud_desc",
                "全球红外卫星云图覆盖世界各地，每 60 分钟刷新一次。");
        add(translations, "settings_weather_precipitation_total", "总降水量");
        add(translations, "settings_weather_precipitation_intense", "强降水");
        add(translations, "settings_weather_precipitation_intense_desc",
                "全球雷达系统在地图上显示各地降水区域和强度，每 30 分钟刷新一次。");
        add(translations, "settings_weather_usardr", "北美雷达");
        add(translations, "settings_weather_usardr_desc",
                "高分辨率北美雷达由多部雷达的图像拼接而成，覆盖美国本土、阿拉斯加、夏威夷、波多黎各及加拿大南部。其分辨率为 1 公里，每 2 分钟更新一次，可提供更详细、更及时的天气信息。");
        add(translations, "settings_weather_ausrdr", "澳大利亚雷达");
        add(translations, "settings_weather_ausrdr_desc",
                "澳大利亚雷达是覆盖全澳降水的合成图像，分辨率为 2 公里，每 7.5 分钟更新一次。");
        add(translations, "settings_weather_volcano_desc",
                "显示影响航空运行的火山喷发和火山灰云边界，仅在适用时显示。");
        add(translations, "free_trial", "免费试用");
        add(translations, "filters_aircraft_categories", "航空器类别");
        add(translations, "filters_categories", "分类");
        add(translations, "filters_custom", "自定义");
        add(translations, "filters_create_account_title", "使用筛选功能需要账户。");
        add(translations, "filters_continue_button", "继续");
        add(translations, "filters_custom_not_logged_in", "要解锁筛选功能，必须登录账户。");
        add(translations, "filters_search_clear_all", "全部清除");
        add(translations, "filters_selected_show_less", "收起");
        add(translations, "filters_num", "%d 个筛选条件");
        add(translations, "filters_unlock_categories_upsell_title",
                "要按航空器类别筛选，请升级至 Silver（%1$s）或 Gold（%2$d）。");
        add(translations, "filters_info_blog_title", "访问我们的博客，查看筛选功能的完整使用指南");
        add(translations, "unlock_learn_more", "了解更多");
        add(translations, "filter_category_all", "所有类别");
        add(translations, "filter_category_passenger", "客运");
        add(translations, "filter_category_tooltip_info", "更多信息");
        add(translations, "filter_category_cargo", "货运");
        add(translations, "filter_category_military_and_government", "军用或政府");
        add(translations, "filter_category_business_jets", "公务机");
        add(translations, "filter_category_general_aviation", "通用航空");
        add(translations, "filter_category_helicopters", "直升机");
        add(translations, "filter_category_lighter_than_air", "轻于空气航空器");
        add(translations, "filter_category_gliders", "滑翔机");
        add(translations, "filter_category_drones", "无人机");
        add(translations, "filter_category_ground_vehicles", "地面车辆");
        add(translations, "filter_category_other", "其他");
        add(translations, "filter_category_uncategorized", "未分类");
        add(translations, "filter_category_passenger_explainer", "主要用于载客的商业航空器。");
        add(translations, "filter_category_cargo_explainer", "仅运输货物的航空器。");
        add(translations, "filter_category_military_and_government_explainer",
                "由军方或政府机构运营的航空器。");
        add(translations, "filter_category_business_jets_explainer",
                "较大型的私人航空器，例如湾流、庞巴迪和皮拉图斯。");
        add(translations, "filter_category_general_aviation_explainer",
                "非商业运输飞行，包括私人飞行、空中救护、航空测绘、飞行训练，以及用于仪表校准的航空器。");
        add(translations, "filter_category_helicopters_explainer", "旋翼航空器。");
        add(translations, "filter_category_lighter_than_air_explainer",
                "轻于空气的航空器包括各类充气飞艇。");
        add(translations, "filter_category_gliders_explainer", "无动力航空器。");
        add(translations, "filter_category_drones_explainer",
                "无人航空器，涵盖从小型消费级无人机到较大型无人机（UAV）。");
        add(translations, "filter_category_ground_vehicles_explainer",
                "配备应答机的地面车辆，例如飞机推回牵引车、消防车和机场运行车辆。");
        add(translations, "filter_category_other_explainer",
                "显示在 Flightradar24 上、但未归入其他类别的航空器（国际空间站、不明飞行物、圣诞老人等）。");
        add(translations, "filter_category_uncategorized_explainer",
                "尚未在 Flightradar24 数据库中归入某个类别的航空器。");
        add(translations, "add_custom_filter_button_text", "添加自定义筛选");
        add(translations, "accessibility_collapse_panel", "收起面板");
        add(translations, "filters_custom_filters_section_header", "筛选");
        add(translations, "filters_custom_empty_header", "尚无已保存的筛选");
        add(translations, "filters_custom_empty_description",
                "点击下方“添加自定义筛选”按钮来添加筛选。");
        add(translations, "filter_route_visit_our_blog", "访问我们的博客，查看筛选功能的完整使用指南。");
        add(translations, "custom_filter_type_airline", "航空公司");
        add(translations, "custom_filter_type_aircraft", "航空器");
        add(translations, "custom_filter_type_airport", "机场");
        add(translations, "custom_filter_type_route", "航线");
        add(translations, "custom_filter_type_categories", "类别");
        add(translations, "custom_filter_type_advanced", "高级");
        add(translations, "add_filter_title", "新建筛选条件");
        add(translations, "add_filter_description", "选择要添加的筛选条件类型。");
        add(translations, "alert_custom", "自定义提醒");
        add(translations, "settings_notifcation_custom_summary",
                "根据你选择的航班事件接收推送通知。同时提醒上限：Silver 10 条｜Gold 25 条");
        add(translations, "settings_notification_special_flight_title", "特色航班");
        add(translations, "settings_notification_special_flight_summary",
                "接收由 Flightradar24 工作人员选出的独特或有趣航班通知。");
        add(translations, "settings_notification_7600_title", "应答机代码 7600");
        add(translations, "settings_notification_7600_summary",
                "航班使用应答机代码 7600（无线电或通信故障）时通知我。");
        add(translations, "settings_notification_7700_title", "应答机代码 7700");
        add(translations, "settings_notification_7700_summary",
                "航班使用应答机代码 7700（表示紧急情况）时通知我。");
        add(translations, "settings_notification_nearby_airports_title", "附近机场");
        add(translations, "settings_notification_channel_high_pie", "发出声音");
        add(translations, "settings_notification_nearby_airports_summary",
                "到访机场时通知我，以便快速查看航班信息。");
        add(translations, "settings_notifications_behavior", "行为：");
        add(translations, "global_playback_start_playback", "开始回放");
        add(translations, "global_playback_date_picker_upgrade_text",
                "如需查看 7 天以上的回放历史，请升级到 Silver（90 天回放）或 Gold（365 天回放）订阅。");
        add(translations, "airport_panel_airport_and_runway_details", "机场与跑道详情");
        add(translations, "airport_panel_latest_events", "最新动态");
        add(translations, "airport_panel_airport_facts", "机场资料");
        add(translations, "stats_tab_title0", "统计");
        add(translations, "stats_tab_title2", "运行异常");
        add(translations, "stats_tab_title3", "热门航班");
        add(translations, "stats_tab_title4", "收藏");
        add(translations, "stats_most_tracked_flights", "追踪人数最多的航班");
        add(translations, "stats_most_tracked_flights_footer",
                "查看其他 Flightradar24 用户当前正在关注的前 10 个航班。");
        add(translations, "accessibility_followers", "关注人数：%d");
        add(translations, "accessibility_disrupt_arrival_index", "到港运行异常指数：%s");
        add(translations, "accessibility_disrupt_departure_index", "离港运行异常指数：%s");
        add(translations, "accessibility_wind_vrb", "风向不定，风速 %1$d %2$s");
        add(translations, "unit_speed_kts_accessible", "节");
        add(translations, "airport_panel_arrival_information", "到达信息");
        add(translations, "airport_panel_departure_information", "出发信息");
        add(translations, "airport_panel_flight_history", "航班历史");
        add(translations, "airport_panel_landings", "降落");
        add(translations, "airport_panel_takeoffs", "起飞");
        add(translations, "airport_panel_runway", "跑道");
        add(translations, "airport_panel_time_zone", "时区");
        add(translations, "airport_panel_today", "今天");
        add(translations, "menu_faq", "常见问题");
        add(translations, "menu_onboarding", "使用教程");
        add(translations, "menu_newsletter", "新闻通讯");
        add(translations, "search_by_route_arr_airport", "到达机场");
        add(translations, "search_by_route_dep_airport", "出发机场");
        add(translations, "search_by_route_hint", "按城市、机场名称或 IATA/ICAO 代码搜索。");
        add(translations, "search_clear_history_acknowledgment", "搜索历史已清除");
        add(translations, "settings_data_sources", "数据来源");
        add(translations, "settings_measurement_units", "单位");
        add(translations, "settings_show_airports", "机场标记");
        add(translations, "settings_visibility_airtraffic", "空中航空器");
        add(translations, "settings_visibility_groundtraffic", "地面航空器");
        add(translations, "data_source_adsb_title", "地面 ADS-B");
        add(translations, "data_source_adsb_summary", "通过地面接收站，基于 GPS 跟踪航空器。");
        add(translations, "data_source_mlat_title", "地面 MLAT");
        add(translations, "data_source_mlat_summary", "通过地面接收站，基于到达时间差跟踪航空器。");
        add(translations, "data_source_aireon_title", "星基 ADS-B");
        add(translations, "data_source_aireon_summary", "通过卫星接收站，基于 GPS 跟踪航空器；数据由 Aireon 提供。");
        add(translations, "data_source_sat_title", "星基 ADS-C");
        add(translations, "data_source_sat_summary", "通过与航空器的卫星连接，基于 GPS 进行跟踪。");
        add(translations, "data_source_faa_summary", "结合雷达、多点定位和卫星技术，为美国主要机场提供地面目标跟踪。");
        add(translations, "data_source_uat_summary", "基于 GPS 的航空器跟踪，主要用于美国 18,000 英尺以下飞行的轻型航空器。");
        add(translations, "data_source_australia_title", "澳大利亚雷达");
        add(translations, "data_source_australia_summary", "官方雷达数据，覆盖澳大利亚及其周边部分洋区。");
        add(translations, "data_source_spidertracks_summary", "通过 Spidertracks 发射器，基于 GPS 对轻型航空器进行专有跟踪。");
        add(translations, "data_source_flarm_summary", "基于 GPS 的航空器跟踪技术，主要用于轻型航空器、直升机和滑翔机。");
        add(translations, "data_source_other_title", "其他");
        add(translations, "data_source_other_summary", "Flightradar24 使用的、未归入以上类别的其他跟踪技术。");
        add(translations, "data_source_est_title", "估算");
        add(translations, "data_source_est_summary", "航空器离开 Flightradar24 覆盖范围后，最多可继续估算其位置 240 分钟。");
        add(translations, "settings_types_of_traffic", "交通类型");
        add(translations, "filters_edit_list_title", "编辑筛选");
        add(translations, "filters_add_name_input_label", "筛选名称");
        add(translations, "filters_delete_dialog_title", "删除筛选");
        add(translations, "alert_add", "添加提醒");
        add(translations, "alert_history_title", "收到的提醒");
        add(translations, "alert_region", "提醒区域");
        add(translations, "airport_panel_airport_elevation", "机场海拔");
        add(translations, "airport_panel_runway_usage", "跑道使用情况");
        add(translations, "global_playback_loading_error", "与 FR24 通信失败。");
        add(translations, "accessibility_flight_no_callsign", "航班：无呼号");
        add(translations, "search_shortcut_airport_map", "在地图上显示");
        add(translations, "search_shortcut_airport_general", "显示概览");
        add(translations, "search_shortcut_airport_arr_board", "到达航班看板");
        add(translations, "search_shortcut_airport_dep_board", "出发航班看板");
        add(translations, "search_shortcut_airport_on_ground", "地面航空器");
        add(translations, "search_shortcut_airport_arr_flights", "查找抵达航班");
        add(translations, "search_shortcut_airport_dep_flights", "查找出发航班");
        add(translations, "accessibility_aircraft", "航空器：%s");
        add(translations, "accessibility_country", "国家/地区：%s");
        add(translations, "accessibility_iata", "IATA 代码：%s");
        add(translations, "accessibility_icao", "ICAO 代码：%s");
        add(translations, "accessibility_iata_and_icao", "IATA 代码：%1$s，ICAO 代码：%2$s");
        add(translations, "accessibility_registration", "注册号：%s");
        add(translations, "cab_airport_elev", "海拔 %s");
        add(translations, "cab_airport_elevation", "海拔：%s");
        add(translations, "timezone_local_time_accessibility", "当地时间：%s");
        add(translations, "accessibility_airport_photo", "照片");
        add(translations, "accessibility_wind", "风：%1$d 度，%2$d %3$s");
        add(translations, "cab_airport_wx_conditions", "天气状况");
        add(translations, "cab_airport_wx_temperature", "温度");
        add(translations, "cab_airport_wx_more_title", "更多天气与 METAR");
        add(translations, "weather_sky_condition_cloudy", "多云");
        add(translations, "cab_airport_delay_index", "运行异常指数");
        add(translations, "cab_airport_average_delay", "平均延误");
        add(translations, "cab_airport_delay_minutes", "分钟");
        add(translations, "cab_airport_canceled", "已取消航班");
        add(translations, "cab_airport_delayed", "延误航班");
        add(translations, "weather_sky_condition_calm", "无风");
        add(translations, "weather_sky_condition_clear", "晴");
        add(translations, "weather_sky_condition_drizzle", "毛毛雨");
        add(translations, "weather_sky_condition_fog", "雾");
        add(translations, "weather_sky_condition_overcast", "阴");
        add(translations, "weather_sky_condition_rain", "雨");
        add(translations, "weather_sky_condition_snow", "雪");
        add(translations, "weather_sky_condition_thunderstorm", "雷暴");
        add(translations, "airport_most_disruptions", "运行异常最严重的机场");
        add(translations, "stats_airport_disrupt_msg0",
                "0 到 5 之间的数值综合反映延误航班数量、平均延误时间和取消航班数量。数值越高，表示取消和/或延误情况越严重。");
        add(translations, "stats_airport_disrupt_msg1", "箭头显示当前运行异常指数的变化趋势。");
        add(translations, "stats_airport_disrupt_msg2", "约 300 个最繁忙机场提供运行异常指数。");
        add(translations, "stats_airport_disrupt_value0", "运行顺畅。");
        add(translations, "stats_airport_disrupt_value1", "存在轻微问题，部分航班延误或少量取消。");
        add(translations, "stats_airport_disrupt_value2", "存在严重问题，航班长时间延误且多班取消。");
        add(translations, "stats_disrupt_airport", "机场运行异常");
        add(translations, "disrupt_footer", "查看取消航班最多、延误时间最长的前 10 个机场。");
        add(translations, "disrupt_wx_not_available", "当前无天气数据");
        add(translations, "search_recent_footer", "更多搜索历史");
        add(translations, "tooltip_pull_down", "向下滑动即可查看热门航班、机场运行异常、收藏和追踪统计。");
        add(translations, "stats_aircraft_on_map", "地图上的航空器");
        add(translations, "stats_data_source", "数据来源");
        add(translations, "stats_view", "当前视图");
        add(translations, "stats_global", "全球");
        add(translations, "stats_total_title", "航空器总数");
        add(translations, "bookmarks_tab_aircraft", "航空器");
        add(translations, "bookmarks_tab_flights", "航班");
        add(translations, "bookmarks_tab_airports", "机场");
        add(translations, "bookmarks_tab_locations", "位置");
        add(translations, "bookmark_empty_header_aircraft", "添加航空器收藏！");
        add(translations, "bookmark_empty_header_flights", "添加航班收藏！");
        add(translations, "bookmark_empty_header_airports", "添加机场收藏！");
        add(translations, "bookmark_empty_header_locations", "添加位置收藏！");
        add(translations, "bookmark_empty_text_aircraft", "点击下方“添加收藏”按钮，或点击应用内任意航班号旁的星标，即可添加航空器。");
        add(translations, "bookmark_empty_text_flights", "点击下方“添加收藏”按钮，或点击应用内任意航班号旁的星标，即可添加航班。");
        add(translations, "bookmark_empty_text_airports", "点击下方“添加收藏”按钮，或点击任意机场面板顶部的星标图标，即可添加机场。");
        add(translations, "bookmark_empty_text_locations", "点击下方“添加收藏”按钮即可添加位置。保存的位置将包括当前地图位置和缩放级别。");
        add(translations, "bookmark_add", "添加收藏");
        add(translations, "bookmark_not_tracked", "当前未追踪");
        add(translations, "bookmark_sort_by", "排序方式：");
        add(translations, "bookmark_sort_last_added", "最近添加");
        add(translations, "edit", "编辑");
        add(translations, "bookmark_limit_reached_dropdown_basic",
                "如需添加多个收藏，请升级至 <b>Silver（%d）</b> 或 <b>Gold（%d）</b> 订阅方案。");
        add(translations, "bookmark_limit_reached_dropdown_gold", "已达到 %d 个收藏的上限。");
        add(translations, "bookmark_limit_reached_dropdown_silver",
                "如需添加 10 个以上收藏，请升级至 <b>Gold（%d）</b> 订阅方案。");
        add(translations, "bookmark_sort_airports_alphabetical", "机场名称 A-Z");
        add(translations, "bookmark_sort_airports_iata", "IATA 代码 A-Z");
        add(translations, "bookmark_sort_custom", "自定义");
        add(translations, "bookmark_sort_flight_number_alphabetical", "航班号 A-Z");
        add(translations, "bookmark_sort_location_alphabetical", "位置名称 A-Z");
        add(translations, "bookmark_sort_registration_alphabetical", "注册号 A-Z");
        add(translations, "bookmark_sort_status", "状态");
        add(translations, "accessibility_help", "帮助");
        add(translations, "bookmark_help_sort_alphabetical", "按字母顺序排列");
        add(translations, "bookmark_help_sort_alphabetical_airport_code", "按 IATA 代码字母顺序排列");
        add(translations, "bookmark_help_sort_alphabetical_airport_name", "按机场名称字母顺序排列");
        add(translations, "bookmark_help_sort_custom", "创建自定义顺序");
        add(translations, "bookmark_help_sort_last_added", "按添加时间排序");
        add(translations, "bookmark_help_sort_status", "按当前状态排序，例如“实时”");
        add(translations, "bookmark_accessibility_bookmarked_aircraft", "收藏：已收藏航空器");
        add(translations, "bookmark_accessibility_bookmarked_airport", "收藏：已收藏机场");
        add(translations, "bookmark_accessibility_bookmarked_both", "收藏：已收藏航班和航空器");
        add(translations, "bookmark_accessibility_bookmarked_flight", "收藏：已收藏航班");
        add(translations, "bookmark_accessibility_locked", "收藏：已锁定");
        add(translations, "bookmark_add_aircraft_hint", "例如 D–AIHV");
        add(translations, "bookmark_add_airport_hint", "例如 London Heathrow 或 LHR");
        add(translations, "bookmark_add_error", "无法添加收藏。");
        add(translations, "bookmark_add_flight_hint", "例如 SQ23 或 AM22");
        add(translations, "bookmark_add_location", "命名位置");
        add(translations, "bookmark_add_location_button", "将位置添加到收藏");
        add(translations, "bookmark_add_location_hint", "为位置创建名称");
        add(translations, "bookmark_add_location_hint2",
                "如需保存位置，请在地图上选择要保存的位置和缩放级别。默认位置为当前地图位置。为该位置创建名称，然后点击添加。");
        add(translations, "bookmark_added_aircraft", "航空器已成功收藏。");
        add(translations, "bookmark_added_airport", "机场已成功收藏。");
        add(translations, "bookmark_added_flight", "航班已成功收藏。");
        add(translations, "bookmark_added_location", "位置已成功收藏。");
        add(translations, "bookmark_already_added", "已添加");
        add(translations, "bookmark_create_account", "创建账户");
        add(translations, "bookmark_edit_aircraft", "编辑航空器");
        add(translations, "bookmark_edit_airports", "编辑机场");
        add(translations, "bookmark_edit_discard_changes_message", "如果关闭编辑页面而不保存，更改将会丢失。");
        add(translations, "bookmark_edit_discard_changes_title", "放弃更改？");
        add(translations, "bookmark_edit_flights", "编辑航班");
        add(translations, "bookmark_edit_locations", "编辑位置");
        add(translations, "bookmark_eta", "预计到达 %s");
        add(translations, "bookmark_flying_from_to", "正从 %1$s 飞往 %2$s");
        add(translations, "bookmark_go_to_bookmarks", "前往收藏");
        add(translations, "bookmark_limit_reached_msg", "如需添加更多收藏，请先删除一个收藏。");
        add(translations, "bookmark_limit_reached_promo_msg_basic",
                "升级至 <b>Silver（%1$d）</b> 或 <b>Gold（%2$d）</b> 即可提高收藏数量上限。");
        add(translations, "bookmark_limit_reached_promo_msg_silver",
                "升级至 <b>Gold（%d）</b> 即可提高收藏数量上限。");
        add(translations, "bookmark_limit_reached_promo_title", "已达到 %s 账户的收藏上限");
        add(translations, "bookmark_limit_reached_title", "已达到收藏数量上限");
        add(translations, "bookmark_locked_header", "需要账户才能解锁收藏功能");
        add(translations, "bookmark_locked_text",
                "创建免费账户可添加 1 个收藏；升级至 <b>Silver（%1$d）</b> 或 <b>Gold（%2$d）</b> 可添加更多收藏。");
        add(translations, "bookmark_locked_text_anonymous",
                "创建账户即可解锁收藏功能，并在所有 Flightradar24 平台查看收藏。");
        add(translations, "bookmark_on_ground_at", "地面停留于 %s");
        add(translations, "bookmark_promo_anonymous_msg",
                "使用收藏功能需要创建免费的 Flightradar24 账户。创建账户后，你可以在多个平台（iOS、Android 和 Flightradar24.com）使用与你的个人资料关联的订阅权益、功能和已保存设置。\n\n"
                        + "创建账户后，你可以在任意平台访问收藏。在主地图界面向下滑动即可打开收藏。");
        add(translations, "bookmark_promo_create_account", "创建免费的 Flightradar24 账户");
        add(translations, "bookmark_promo_msg",
                "你可以将多个航空器、航班、机场和位置添加到收藏，方便快速访问。在主地图界面向下滑动即可打开收藏。<br /><br />"
                        + "创建免费的 Flightradar24 账户可添加 1 个收藏；升级至 <b>Silver（%1$d）</b> 或 <b>Gold（%2$d）</b> 可添加更多收藏。");
        add(translations, "bookmark_promo_title", "收藏");
        add(translations, "bookmark_remove_error", "无法移除收藏。");
        add(translations, "bookmark_removed_aircraft", "已移除航空器收藏。");
        add(translations, "bookmark_removed_airport", "已移除机场收藏。");
        add(translations, "bookmark_removed_flight", "已移除航班收藏。");
        add(translations, "bookmark_save_error", "无法保存更改，请稍后重试。");
        add(translations, "bookmark_save_success", "更改已保存到收藏。");
        add(translations, "bookmark_tooltip", "点击航空器、机场或航班号旁的星标，即可将其添加到收藏。");
        add(translations, "bookmark_view", "查看收藏");
        add(translations, "bookmark_weather_not_available", "当前无天气数据");
        add(translations, "bookmarks_type_aircraft", "航空器");
        add(translations, "bookmarks_type_airport", "机场");
        add(translations, "bookmarks_type_flight", "航班");
        add(translations, "bookmarks_type_location", "位置");
        add(translations, "bookmarks_type_title", "选择要收藏的类型");
        add(translations, "airport_panel_current", "当前");
        add(translations, "live_tag", "实时");
        add(translations, "airport_panel_disruptions", "运行异常");
        add(translations, "airport_panel_more_disruptions", "更多运行异常");
        add(translations, "airport_panel_total", "总计");
        add(translations, "cab_airport_general", "概览");
        add(translations, "cab_airport_departures", "出发");
        add(translations, "cab_airport_arrivals", "到达");
        add(translations, "cab_airport_on_ground", "地面");
        add(translations, "airport_panel_airport_statistics", "机场统计");
        add(translations, "airport_panel_last_7_days_tag", "最近 7 天");
        add(translations, "airport_airports_served", "通航机场");
        add(translations, "airport_countries_served", "通航国家/地区");
        add(translations, "airport_panel_busiest_routes", "最繁忙航线");
        add(translations, "airport_panel_average_flights_per_day", "机场航班起降量");
        add(translations, "airport_panel_flights_per_day", "每日起降量");
        add(translations, "accessibility_locked_content", "内容已锁定。升级后查看。");
        add(translations, "airport_panel_airport_name", "机场名称");
        add(translations, "airport_panel_airport_satellite_photo", "机场卫星照片");
        add(translations, "airport_runway_title", "跑道详情");
        add(translations, "cab_airport_wx_sunrise", "日出");
        add(translations, "cab_airport_wx_sunset", "日落");
        add(translations, "airport_panel_travelers", "常旅客");
        add(translations, "do_you_work_at_an_airport", "您在机场工作吗？");
        add(translations, "help_us_improve_ground_coverage", "帮助我们改善地面覆盖");
        add(translations, "airport_flight_history_intro_tooltip", "查看所选日期的机场出发与到达历史。");
        add(translations, "airport_panel_earlier_flights", "更早的航班");
        add(translations, "cab_airport_status_estimated", "预计");
        add(translations, "cab_airport_status_landed", "已降落");
        add(translations, "cab_airport_status_canceled", "已取消");
        add(translations, "label_airline", "航空公司");
        add(translations, "label_aircraft", "航空器");
        add(translations, "airport_landed", "已降落");
        add(translations, "airport_landed_as", "以 %s 降落");
        add(translations, "airport_diff_min", "%s 分钟前");
        add(translations, "airport_diff_hrs", "%s 小时前");
        add(translations, "airport_diff_days", "%s 天前");
        add(translations, "airport_diff_months", "%s 个月前");
        add(translations, "airport_diff_years", "%s 年前");
        add(translations, "search_status_landed", "已于 %s 降落");
        add(translations, "search_airline_flights", "%s 个航班");
        add(translations, "airport_show_on_map", "在地图上显示");
        add(translations, "airport_aircraft_info", "航空器信息");
        add(translations, "airport_flight_info", "航班信息");
        add(translations, "airport_panel_bookmark_aircraft", "收藏航空器");
        add(translations, "airport_panel_bookmark_flight", "收藏航班");
        add(translations, "airport_playback", "回放");
        add(translations, "airport_panel_download_csv_kml", "下载 CSV/KML");
        add(translations, "cab_unlock_selected_feature", "解锁 %s");
        add(translations, "cab_unlock_feature", "解锁");
        add(translations, "label_scheduled", "计划出发");
        add(translations, "label_actual_departure", "实际出发");
        add(translations, "label_scheduled_arrival", "计划到达");
        add(translations, "label_status", "状态");
        add(translations, "label_flight_time", "飞行时间");
        add(translations, "label_equipment", "机型");
        add(translations, "label_call_sign", "呼号");
        add(translations, "label_flight_history", "航班");
        add(translations, "accessibility_not_available", "不可用");
        add(translations, "not_available", "不可用");
        add(translations, "search_status_estimated_dep_time", "预计于 %s 起飞");
        add(translations, "error_something_went_wrong_longer", "出了点问题，无法加载数据。");
        add(translations, "error_reload", "重新加载");
        add(translations, "airport_panel_remove_ads_desc", "移除广告 — 打开升级选项");
        add(translations, "cab_tracked_via_satellite_by", "卫星追踪数据提供方");
        add(translations, "remove_ads", "移除广告，获得更流畅的应用体验");
        add(translations, "search_status_diverted_to", "已备降至 %s");
        add(translations, "search_status_diverting_to", "正在备降至 %s");
        add(translations, "search_status_estimated_arr_time", "预计于 %s 到达");
        add(translations, "airport_panel_variable", "风向不定，%1$d %2$s");
        add(translations, "filters_edit_title", "编辑 — %s");
        add(translations, "cab_btn_share", "分享");
        add(translations, "cab_close", "关闭");
        add(translations, "settings_more", "更多");
        add(translations, "accessibility_expand_panel", "展开面板");
        add(translations, "bookmark_accessibility_not_bookmarked", "收藏：未收藏");
        add(translations, "settings_aeronatuical_charts_none", "关闭");
        add(translations, "settings_aircraft_info_ac_type", "机型");
        add(translations, "settings_aircraft_info_altitude", "高度");
        add(translations, "settings_aircraft_info_callsign", "呼号");
        add(translations, "settings_aircraft_info_flightnumber", "航班号");
        add(translations, "settings_aircraft_info_max", "已达到文字标签数量上限。");
        add(translations, "settings_aircraft_info_none", "关闭");
        add(translations, "settings_aircraft_info_registration", "注册号");
        add(translations, "settings_aircraft_info_route", "航线");
        add(translations, "settings_aircraft_info_speed", "速度");
        add(translations, "settings_altitude", "气压高度");
        add(translations, "settings_altitude_unit_ft", "英尺");
        add(translations, "settings_altitude_unit_m", "米");
        add(translations, "settings_menu_map", "地图");
        add(translations, "settings_menu_weather", "天气");
        add(translations, "settings_speed_unit_kmh", "千米/小时");
        add(translations, "settings_speed_unit_kts", "节");
        add(translations, "settings_speed_unit_mph", "英里/小时");
        add(translations, "settings_notifcation_custom_summary_with_sub", "根据你选择的航班事件接收推送通知。");
        add(translations, "settings_notification_channel_blocked", "已阻止");
        add(translations, "settings_notification_channel_low_pie", "静默显示并最小化");
        add(translations, "settings_notification_channel_medium_pie", "静默显示");
        add(translations, "settings_notification_channel_urgent_pie", "发出声音并在屏幕上弹出");
        add(translations, "settings_utc_warning", "UTC 使用 24 小时协调世界时显示时间。如需 12 小时制，请选择非 UTC 时区。");
        add(translations, "settings_visibility_limit_description", "设置地图上显示的航空器数量上限。");
        add(translations, "settings_visibility_limit_header", "航空器数量上限");
        add(translations, "settings_visibility_restricted_header", "受限航班");
        add(translations, "settings_visibility_restricted_included", "包含");
        add(translations, "settings_visibility_restricted_none", "无");
        add(translations, "settings_visibility_restricted_only", "仅");
        add(translations, "settings_weather_flight_level", "飞行高度层");
        add(translations, "settings_weather_airmet", "AIRMET / SIGMET");
        add(translations, "settings_weather_airmet_desc", "由相关机构发布的 AIRMET/SIGMET，用于预报发布区域内可能对航班造成危险的重要天气事件；每 30 分钟刷新。");
        add(translations, "settings_weather_high_level", "高空重要天气");
        add(translations, "settings_weather_high_level_desc", "高空重要天气预报区域，最长提供 24 小时预报，每 6 小时一个时段。");
        add(translations, "settings_weather_ice", "高分辨率结冰");
        add(translations, "settings_weather_ice_desc", "未来 36 小时飞行高度层 FL060 至 FL300 的结冰严重程度预报。信息由美国航空气象中心生成，等级包括无、微量、轻度、中度和严重。");
        add(translations, "settings_weather_ict", "高分辨率湍流");
        add(translations, "settings_weather_ict_desc", "未来 36 小时飞行高度层 FL100 至 FL450 的湍流预报。信息由美国航空气象中心生成，等级包括无、轻度、中度和严重。");
        add(translations, "settings_weather_lightning", "闪电");
        add(translations, "settings_weather_lightning_desc", "地图上显示记录到的闪电，每 15 分钟更新。");
        add(translations, "settings_weather_opacity", "不透明度");
        add(translations, "settings_weather_precipitation_total_desc", "在实时地图上叠加显示当前全球降水量。总降水层每天刷新 12 次。");
        add(translations, "settings_weather_winds", "风");
        add(translations, "settings_weather_winds_areas", "渐变");
        add(translations, "settings_weather_winds_arrows", "风羽");
        add(translations, "settings_weather_winds_barbs_combination_legend", "风羽组合");
        add(translations, "settings_weather_winds_barbs_legend_calm", "静风");
        add(translations, "settings_weather_winds_desc", "在实时地图上以 1,000 英尺为增量显示 1,000 至 51,000 英尺范围内的风速和风向。可使用风羽或颜色渐变显示。风层每天刷新 12 次。");
        add(translations, "settings_weather_winds_level_selected", "所选高度");
        add(translations, "settings_weather_winds_level_selected_value", "%s 英尺");
        add(translations, "menu_alerts", "提醒");
        add(translations, "menu_billing_details", "账单详情");
        add(translations, "menu_custom_fleets", "自定义机队");
        add(translations, "menu_data_sharing", "数据共享");
        add(translations, "menu_filter", "筛选");
        add(translations, "menu_global_playback", "回放");
        add(translations, "menu_more", "更多");
        add(translations, "menu_search", "搜索");
        add(translations, "menu_settings", "设置");
        add(translations, "global_playback_date_picker_gold_upgrade_text",
                "要查看超过 90 天的回放历史，请升级至 Gold 订阅（可查看 365 天回放）。");
        add(translations, "playback_loading", "正在加载回放数据…");
        add(translations, "playback_not_available", "无可用回放");
        add(translations, "playback_not_available_for_this_flight", "很遗憾，此航班没有可用的回放数据！");
        add(translations, "playback_share", "分享回放");
        add(translations, "playback_share_subject", "%1$s 航班 %2$s");
        add(translations, "playback_share_subject_aircraft", "航空器：%s");
        add(translations, "playback_share_text", "在 Flightradar24 上查看航班 %1$s 从 %2$s 飞往 %3$s 的回放。");
        add(translations, "playback_share_text_aircraft", "在 Flightradar24 上查看航空器 %1$s 从 %2$s 飞往 %3$s 的回放。");
        add(translations, "playback_share_text_aircraft_short", "在 Flightradar24 上查看航空器 %s 的回放。");
        add(translations, "playback_share_text_short", "在 Flightradar24 上查看航班 %s 的回放。");
        add(translations, "playback_tooltip", "点击图表或再次向上滑动可打开高度和速度图。向下滑动可隐藏时间轴以外的所有内容。");
        add(translations, "login_billing_details", "账单详情");
        add(translations, "login_cancel_subscription", "取消订阅");
        add(translations, "login_change_password", "更改密码");
        add(translations, "login_change_password_title", "更改密码");
        add(translations, "login_change_payment_method", "更改付款方式");
        add(translations, "login_continue", "继续");
        add(translations, "login_contributor_note",
                "我们已将你的订阅方案名称更新为 Contributor，所有原有功能保持不变。感谢你与我们共享数据，敬请期待 Contributor 专属功能。");
        add(translations, "login_create_password_title", "创建密码");
        add(translations, "login_dont_have_an_account", "还没有账户？");
        add(translations, "login_dont_have_an_account_sign_up", "注册");
        add(translations, "login_email_hint", "电子邮箱");
        add(translations, "login_error_email", "请输入有效的电子邮箱地址。");
        add(translations, "login_error_password", "密码必须至少包含 7 个字符。");
        add(translations, "login_forgot_password", "忘记密码？");
        add(translations, "login_forgot_password_subtitle", "输入你的电子邮箱以申请新密码。");
        add(translations, "login_forgot_password_title", "忘记密码？");
        add(translations, "login_generic_msg", "无法通过 FR24 服务器进行身份验证。");
        add(translations, "login_log_in", "登录");
        add(translations, "login_log_in_with_email", "使用电子邮箱登录");
        add(translations, "login_my_data_sharing", "我的数据共享");
        add(translations, "login_new_password", "新密码");
        add(translations, "login_new_password_again", "再次输入新密码");
        add(translations, "login_or_login_with", "或使用电子邮箱");
        add(translations, "login_password_hint", "密码");
        add(translations, "login_request_failed", "请求失败，请稍后重试。");
        add(translations, "login_request_new_password", "申请新密码");
        add(translations, "subs_already_owned_exception", "看起来你已购买此订阅。请重启应用以验证购买。");
        add(translations, "subs_annual", "年付");
        add(translations, "subs_annual_sub", "年度订阅");
        add(translations, "subs_annual_sub_with_plan", "%s 年度订阅");
        add(translations, "subs_backend_exception", "与 FR24 服务器通信失败。请重启应用以验证购买。");
        add(translations, "subs_backend_exception_logged_in", "与 FR24 服务器通信失败。请使用“%s”按钮将本次购买关联到此账户。");
        add(translations, "subs_continue_with_basic", "使用 Free 继续");
        add(translations, "subs_continue_with_sub", "使用 %1$s（%2$s）继续");
        add(translations, "subs_continue_with_sub2", "使用 %s 继续");
        add(translations, "subs_different_account_msg",
                "你当前有有效的 Flightradar24 订阅，但该订阅使用另一个 Google 账户购买。请登录该 Google Play 账户以更改订阅。");
        add(translations, "subs_enjoying", "你当前正在使用 Flightradar24 Free。");
        add(translations, "subs_free_trial_month", "免费试用 %1$d 天，之后仅需 %2$s/月。");
        add(translations, "subs_free_trial_year", "免费试用 %1$d 天，之后仅需 %2$s/年。");
        add(translations, "subs_level_annual", "%s 年付");
        add(translations, "subs_level_monthly", "%s 月付");
        add(translations, "subs_monthly", "月付");
        add(translations, "subs_monthly_sub", "月度订阅");
        add(translations, "subs_monthly_sub_with_plan", "%s 月度订阅");
        add(translations, "subs_native_dialog_logo_info", "广告帮助维持\nFlightradar24 免费服务");
        add(translations, "subs_native_dialog_upgrade", "升级以移除广告");
        add(translations, "subs_per_first_year_html", "首次支付 %1$s，之后每年以 %2$s 自动续订。");
        add(translations, "subs_per_month", "/月");
        add(translations, "subs_per_month_html", "仅需 %s/月");
        add(translations, "subs_per_year_html", "仅需 %s/年");
        add(translations, "subs_plan_auto_renews", "方案将自动续订，可随时取消，包括免费试用期间。");
        add(translations, "subs_save", "节省 %s");
        add(translations, "subs_save_annual",
                "<font color=#6ccb78><b>节省 %s</b></font>，相较于<font color=#327db6><b><u>月度订阅</u></b></font>");
        add(translations, "subs_save_monthly",
                "<font color=#6ccb78><b>节省 %s</b></font>，选择<font color=#327db6><b><u>年度订阅</u></b></font>");
        add(translations, "subs_start_free_trial2", "开始 7 天免费试用");
        add(translations, "subs_start_no_trial", "开始订阅");
        add(translations, "subs_upgrade_title", "升级选项");
        add(translations, "subs_upgrade_to_gold", "升级至 Gold");
        add(translations, "subs_upgrade_to_silver_or_gold", "可随时升级至 Silver 或 Gold。");
        add(translations, "subscription_linking_failed",
                "出了点问题，订阅未激活。请重试。如果问题仍然存在，请联系支持。");
        add(translations, "alert_add_condition", "添加条件");
        add(translations, "alert_basic", "设置提醒类型和条件");
        add(translations, "alert_condition_is", "是");
        add(translations, "alert_condition_is_not", "不是");
        add(translations, "alert_condition_less", "小于");
        add(translations, "alert_condition_more", "大于");
        add(translations, "alert_condition_too_short", "条件 %d 太短。");
        add(translations, "alert_custom_notfound", "未找到自定义提醒。点击 + 添加。");
        add(translations, "alert_discard", "删除");
        add(translations, "alert_done", "完成");
        add(translations, "alert_emergency_history_desc", "紧急提醒（应答机代码 %s）");
        add(translations, "alert_failed_to_save", "保存提醒失败，请重启应用后重试");
        add(translations, "alert_global", "全球");
        add(translations, "alert_hint_aircraft", "例如 A388 或 B73");
        add(translations, "alert_hint_airline", "ICAO 代码（例如 SAS）");
        add(translations, "alert_hint_airport", "IATA 代码（例如 LHR）");
        add(translations, "alert_hint_alt", "高度（英尺）");
        add(translations, "alert_hint_flight", "航班号或呼号");
        add(translations, "alert_hint_reg", "例如 D-AIHV");
        add(translations, "alert_history_log_deleted", "提醒记录已删除");
        add(translations, "alert_history_notfound", "未收到提醒。");
        add(translations, "alert_local", "本地");
        add(translations, "alert_options", "其他条件（可选）");
        add(translations, "alert_received", "收到时间：");
        add(translations, "alert_select_area", "选择区域");
        add(translations, "alert_select_area_error", "请选择区域");
        add(translations, "alert_triger_too_short", "提醒触发条件必须至少包含 3 个字符。");
        add(translations, "alert_triger_too_short_aircraft", "提醒触发条件必须至少包含 2 个字符。");
        add(translations, "alert_triger_too_short_airline", "提醒触发条件必须正好包含 3 个字符（ICAO 代码）。");
        add(translations, "alert_trigger", "提醒触发条件");
        add(translations, "alert_type", "提醒类型");
        add(translations, "alert_type_airline", "航空公司");
        add(translations, "alert_type_altitude", "高度");
        add(translations, "alert_type_destination", "目的地");
        add(translations, "alert_type_flight", "航班");
        add(translations, "alert_type_reg", "注册号");
        add(translations, "alert_type_type", "机型");
        add(translations, "filter_aircraft_full_selection_title", "选中的航空器");
        add(translations, "filter_aircraft_info",
                "请输入 ICAO 代码，或从下方列表中选择。筛选现会精确匹配，例如 C17 只会显示 C17，不会同时显示 C172。使用 * 可显示与部分 ICAO 代码匹配的所有航空器，例如 B77* 可显示所有波音 777 机型。多个条目请用逗号分隔，例如 A35*,B789 可显示所有空客 A350 和波音 787-9。");
        add(translations, "filter_aircraft_most_popular", "热门航空器");
        add(translations, "filter_aircraft_try_most_popular", "试试“热门航空器”列表。");
        add(translations, "filter_airline_full_selection_title", "选中的航空公司");
        add(translations, "filter_airline_hint", "输入 ICAO 代码");
        add(translations, "filter_airline_info", "输入航空公司名称或 ICAO 代码。多个航空公司 ICAO 代码请用逗号分隔，例如 BAW, UAL。");
        add(translations, "filter_airline_most_popular", "热门航空公司");
        add(translations, "filter_airline_try_most_popular", "试试“热门航空公司”列表。");
        add(translations, "filter_airport_country_prefix", "国家/地区：");
        add(translations, "filter_airport_full_selection_title", "选中的机场");
        add(translations, "filter_airport_info", "输入机场名称、IATA 代码，或从下方列表中选择。多个机场请用逗号分隔，例如 JFK, ARN。");
        add(translations, "filter_airport_most_popular", "热门机场");
        add(translations, "filter_airport_try_most_popular", "试试“热门机场”列表。");
        add(translations, "filter_by_advanced_limit_title", "要使用高级筛选，请升级至 Silver（%1$s）或 Gold（%2$d）。");
        add(translations, "filter_by_advanced_title", "添加高级筛选");
        add(translations, "filter_by_aircraft_description", "查找航空器");
        add(translations, "filter_by_aircraft_hint", "航空器 ICAO 代码");
        add(translations, "filter_by_aircraft_title", "按航空器添加筛选");
        add(translations, "filter_by_airline_description", "查找航空公司");
        add(translations, "filter_by_airline_hint", "航空公司名称或 ICAO 代码");
        add(translations, "filter_by_airline_title", "按航空公司添加筛选");
        add(translations, "filter_by_airport_description", "查找机场");
        add(translations, "filter_by_airport_hint", "机场名称或 IATA 代码");
        add(translations, "filter_by_airport_title", "按机场添加筛选");
        add(translations, "filter_by_category_limit_title", "要使用类别筛选，请升级至 Silver（%1$s）或 Gold（%2$d）。");
        add(translations, "filter_by_category_title", "按类别添加筛选");
        add(translations, "filter_by_route_title", "按航线添加筛选");
        add(translations, "filter_categories_full_selection_title", "选中的类别");
        add(translations, "filter_category_custom_filter_override_disable", "停用");
        add(translations, "filter_category_custom_filter_override_explainer",
                "为避免筛选冲突，使用类别面板前必须停用当前筛选，或编辑当前筛选并移除类别条件。");
        add(translations, "filter_category_custom_filter_override_title", "当前启用的筛选包含类别条件");
        add(translations, "filter_custom_iata_code", "IATA 代码：%s");
        add(translations, "filter_custom_iata_codes", "IATA 代码：%s");
        add(translations, "filter_custom_icao_code", "ICAO 代码：%s");
        add(translations, "filter_custom_icao_codes", "ICAO 代码：%s");
        add(translations, "filter_done_button", "添加");
        add(translations, "filter_remove", "移除此筛选");
        add(translations, "filter_route_full_selection_title", "选中的航线");
        add(translations, "filter_save", "保存");
        add(translations, "filter_search_cant_find_what_you_are_looking_for", "找不到你需要的内容？");
        add(translations, "filter_search_select_all", "全选");
        add(translations, "filter_search_visit_our_blog",
                "也可访问我们的博客，查看<font color=#327DB6>筛选功能完整使用指南。</font>");
        add(translations, "filter_toggle_description", "切换筛选/高亮模式");
        add(translations, "filters_add_clear_changes", "清除更改");
        add(translations, "filters_add_clear_title", "确定要清除所做的更改吗？");
        add(translations, "filters_add_name_error_empty", "请输入筛选名称。");
        add(translations, "filters_add_name_error_min_length", "筛选名称必须至少包含 %d 个字符");
        add(translations, "filters_add_navigate_away_body", "如果未保存就返回上一页，所做的更改将会丢失。");
        add(translations, "filters_add_navigate_away_title", "保存更改");
        add(translations, "filters_add_save_error_body", "请稍后重试。");
        add(translations, "filters_add_save_error_title", "无法保存更改。");
        add(translations, "filters_advanced_age_title", "机龄");
        add(translations, "filters_advanced_altitude_title", "气压高度");
        add(translations, "filters_advanced_call_sign_error", "呼号可由字母和数字组成，长度为 3 至 8 个字符。最多可添加 3 个不同的值。");
        add(translations, "filters_advanced_call_sign_title", "呼号");
        add(translations, "filters_advanced_call_sign_tooltip",
                "呼号可由字母和数字组成，长度为 3 至 8 个字符。在开头或结尾使用 * 可包含所有可能的组合，例如 UAL1* 会显示所有以 UAL1 开头的航班，*123 会显示所有以 123 结尾的航班。多个呼号请用逗号分隔，例如 CPA847,BA55W。");
        add(translations, "filters_advanced_input_example", "例如 %s");
        add(translations, "filters_advanced_radar_error", "雷达代码可由字母、数字和连字符组成，长度为 5 至 10 个字符，可使用 A-Z、0-9 和 -。");
        add(translations, "filters_advanced_radar_title", "雷达");
        add(translations, "filters_advanced_radar_tooltip",
                "雷达代码可由字母、数字和连字符组成，长度为 5 至 10 个字符，可使用 A-Z、0-9 和 -。多个雷达代码请用逗号分隔，例如 T-KJFK567,F-ESSB2。");
        add(translations, "filters_advanced_registration_error", "注册号可由字母和数字组成，长度为 2 至 12 个字符。");
        add(translations, "filters_advanced_registration_title", "注册号");
        add(translations, "filters_advanced_registration_tooltip",
                "注册号可由字母和数字组成，长度为 2 至 12 个字符。在开头或结尾使用 * 可包含所有可能的组合，例如 M-* 会显示所有以 M- 开头的航班，*HV 会显示所有以 HV 结尾的航班。多个注册号请用逗号分隔，例如 D-AIHV,M-ILAN。");
        add(translations, "filters_advanced_speed_title", "地速");
        add(translations, "filters_advanced_squawk_error", "应答机代码由 0 至 7 的 4 位数字组成，不能包含字母。");
        add(translations, "filters_advanced_squawk_title", "应答机代码");
        add(translations, "filters_advanced_squawk_tooltip",
                "应答机代码由 0 至 7 的 4 位数字组成，不能包含字母。\n多个应答机代码请用逗号分隔，例如 2000,7000。");
        add(translations, "filters_aircraft_operating_as_tooltip_info",
                "以某航空公司呼号运行的航空器，但并不一定属于该航空公司，例如从另一家航空公司租赁的航空器。");
        add(translations, "filters_aircraft_operating_as_tooltip_title", "航空器运营方");
        add(translations, "filters_aircraft_painted_as_tooltip_info",
                "采用某航空公司涂装的航空器，但并不一定由该航空公司运营，例如为大型航空公司执飞航班的支线航空公司航空器。");
        add(translations, "filters_aircraft_painted_as_tooltip_title", "航空器涂装");
        add(translations, "filters_both", "两者");
        add(translations, "filters_current_active_filters_disabled_warning", "创建或编辑筛选时，当前启用的筛选将被停用。");
        add(translations, "filters_data_sources_disabled_message", "所有数据来源均已停用，因此地图上没有可见的航空器。");
        add(translations, "filters_delete_success_snackbar_message", "已成功移除筛选。");
        add(translations, "filters_dismiss", "关闭");
        add(translations, "filters_dont_show_again", "不再显示");
        add(translations, "filters_edit_list_downgrade_to_basic_info",
                "Free 账户一次只能使用一个筛选。你可以删除筛选，但只能编辑当前启用的筛选。");
        add(translations, "filters_edit_list_downgrade_to_silver_or_gold_info",
                "已保存的筛选数量超过当前订阅允许的上限。你可以删除筛选，但只能编辑当前启用的筛选。");
        add(translations, "filters_edit_list_info", "对筛选所做的更改会同步到所有平台。");
        add(translations, "filters_edit_success_snackbar_message", "更改已保存。");
        add(translations, "filters_inbound", "入港");
        add(translations, "filters_invalid_route_body",
                "必须至少指定一个出发地和一个目的地条件。\n如果返回上一页，当前航线筛选的更改将会丢失。");
        add(translations, "filters_invalid_route_title", "无效航线");
        add(translations, "filters_no_aircraft_visible_message", "所有航空器类别均已停用，因此地图上没有可见的航空器。");
        add(translations, "filters_operating_as", "运营方为");
        add(translations, "filters_other_platform_ios",
                "你的订阅已关联到 iOS 上的 Apple ID。请在 Apple ID 的“订阅”中升级或更改方案。");
        add(translations, "filters_other_platform_web",
                "你的订阅已关联到网站账户。请前往 Flightradar24.com 的“我的账户”升级或更改方案。");
        add(translations, "filters_outbound", "出港");
        add(translations, "filters_painted_as", "涂装为");
        add(translations, "filters_restore", "恢复");
        add(translations, "filters_route_switch_from_to_action", "对调");
        add(translations, "filters_selected_show_more", "+ 另外 %s 项");
        add(translations, "filters_special", "特殊");
        add(translations, "filters_special_fleet", "自定义机队");
        add(translations, "filters_special_receiver", "接收器");
        add(translations, "filters_special_unblocked", "选择性解除屏蔽");
        add(translations, "filters_special_unblocked_fleet", "未屏蔽机队");
        add(translations, "filters_unable_to_load", "无法加载已保存的筛选。");
        add(translations, "airport_add_alert", "添加提醒");
        add(translations, "airport_downloads", "下载");
        add(translations, "airport_flight_history_broken_link_info", "请核对发送方并重试。");
        add(translations, "airport_flight_history_broken_link_title", "共享链接有问题");
        add(translations, "airport_flight_history_limit_description", "已达到机场航班历史可查看的最长天数。");
        add(translations, "airport_flight_history_link_beyond_limit", "无法访问所选日期");
        add(translations, "airport_flight_history_link_limit_upsell_title", "你尝试访问的链接所显示的日期超出当前方案限额");
        add(translations, "airport_flight_history_search_for_airport", "查找机场");
        add(translations, "airport_flight_history_title", "机场航班历史");
        add(translations, "airport_load_next_on_ground", "加载更多");
        add(translations, "airport_no_photo_msg", "如果你有照片并希望显示在 Flightradar24 上，请上传到 jetphotos.com");
        add(translations, "airport_no_photo_title", "暂无机场照片");
        add(translations, "airport_panel_ambiguous_arrival_description", "当前到达机场的时间已晚于预计到达时间，但我们尚无法确认航班已降落。这可能表示航班延误，也可能是到达信息不可用，或尚未传送至我们的某个数据源。");
        add(translations, "airport_panel_ambiguous_departure_description", "当前出发机场的时间已晚于预计出发时间，且航班尚未处于活动状态。这可能表示航班延误或取消，也可能是出发信息不可用，或尚未传送至我们的某个数据源。");
        add(translations, "airport_panel_arr_upsell_free", "如需查看超过 24 小时的到港航班，请升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        add(translations, "airport_panel_arr_upsell_limit", "已达到到港航班可查看的最长小时数。");
        add(translations, "airport_panel_arr_upsell_nli", "如需查看超过 12 小时的到港航班，请创建免费账户（24 小时）或升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        add(translations, "airport_panel_arr_upsell_silver", "如需查看超过 36 小时的到港航班，请升级至 Gold（48 小时）方案。");
        add(translations, "airport_panel_arrival_runway", "到达跑道");
        add(translations, "airport_panel_calm", "无风");
        add(translations, "airport_panel_category", "类别");
        add(translations, "airport_panel_check_in", "值机");
        add(translations, "airport_panel_dep_upsell_free", "如需查看超过 24 小时的离港航班，请升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        add(translations, "airport_panel_dep_upsell_limit", "已达到离港航班可查看的最长小时数。");
        add(translations, "airport_panel_dep_upsell_nli", "如需查看超过 12 小时的离港航班，请创建免费账户（24 小时）或升级至 Silver（36 小时）或 Gold（48 小时）方案。");
        add(translations, "airport_panel_dep_upsell_silver", "如需查看超过 36 小时的离港航班，请升级至 Gold（48 小时）方案。");
        add(translations, "airport_panel_departure_runway", "起飞跑道");
        add(translations, "airport_panel_faa_lid_code", "FAA LID 代码");
        add(translations, "airport_panel_flights_per_day_graph", "每日起降架次 — 图表");
        add(translations, "airport_panel_gnd_upsell_free", "如需查看超过 2 小时的地面航空器历史，请升级至 Silver（7 天）或 Gold（30 天）方案。");
        add(translations, "airport_panel_gnd_upsell_limit", "已达到地面航空器历史可查看的最长天数。");
        add(translations, "airport_panel_gnd_upsell_nli", "如需查看超过 60 分钟的地面航空器历史，请创建免费账户（2 小时）或升级至 Silver（7 天）或 Gold（30 天）方案。");
        add(translations, "airport_panel_gnd_upsell_silver", "如需查看超过 7 天的地面航空器历史，请升级至 Gold（30 天）方案。");
        add(translations, "airport_panel_graph_error", "此机场的图表数据不可用。");
        add(translations, "airport_panel_iata_code", "IATA 代码");
        add(translations, "airport_panel_icao_code", "ICAO 代码");
        add(translations, "airport_panel_later_flights", "稍后航班");
        add(translations, "airport_panel_latest_events_tooltip", "查看各机场的历史起飞和降落数据，包括航空公司、呼号、航班号、机型和飞行时间。还可查看所选机场最新的降落和起飞记录。可查看的历史范围取决于你的订阅等级。");
        add(translations, "airport_panel_length_m_ft", "长度（m/ft）");
        add(translations, "airport_panel_length_m_ft_accessibility", "长度（米/英尺）");
        add(translations, "airport_panel_more_landings", "更多降落");
        add(translations, "airport_panel_more_takeoffs", "更多起飞");
        add(translations, "airport_panel_no_recent_landing_events", "暂无近期降落记录");
        add(translations, "airport_panel_no_recent_takeoff_events", "暂无近期起飞记录");
        add(translations, "airport_panel_remove_ads", "移除广告");
        add(translations, "airport_panel_remove_bookmark", "移除收藏");
        add(translations, "airport_panel_runway_info", "只有在机场上空低空能够接收到应答机信号时，才会生成跑道使用统计数据。若应答机信号较弱或质量较差，跑道数据可能缺失、不正确，或显示为 N/A。");
        add(translations, "airport_panel_runway_usage_graph", "跑道使用情况 — 图表");
        add(translations, "airport_panel_state", "州/省");
        add(translations, "airport_panel_surface", "道面");
        add(translations, "airport_panel_tomorrow", "明天");
        add(translations, "airport_panel_travelers_empty", "此机场暂无常旅客。");
        add(translations, "airport_panel_yesterday", "昨天");
        add(translations, "airport_runway_surface_asgr", "沥青/草地");
        add(translations, "airport_runway_surface_asph", "沥青");
        add(translations, "airport_runway_surface_bitu", "沥青混合料");
        add(translations, "airport_runway_surface_brck", "砖块");
        add(translations, "airport_runway_surface_clay", "黏土");
        add(translations, "airport_runway_surface_coas", "混凝土/沥青");
        add(translations, "airport_runway_surface_cogs", "混凝土/草地");
        add(translations, "airport_runway_surface_conc", "混凝土");
        add(translations, "airport_runway_surface_corl", "珊瑚");
        add(translations, "airport_runway_surface_dirt", "泥土");
        add(translations, "airport_runway_surface_gras", "草地");
        add(translations, "airport_runway_surface_grvl", "碎石");
        add(translations, "airport_runway_surface_ice", "冰");
        add(translations, "airport_runway_surface_late", "红土");
        add(translations, "airport_runway_surface_maca", "碎石路面");
        add(translations, "airport_runway_surface_mats", "着陆垫");
        add(translations, "airport_runway_surface_meta", "金属");
        add(translations, "airport_runway_surface_mix", "非沥青混合料");
        add(translations, "airport_runway_surface_othr", "其他");
        add(translations, "airport_runway_surface_pavd", "铺装");
        add(translations, "airport_runway_surface_psp", "冲孔钢板");
        add(translations, "airport_runway_surface_sand", "沙地");
        add(translations, "airport_runway_surface_seld", "密封面");
        add(translations, "airport_runway_surface_silt", "淤泥");
        add(translations, "airport_runway_surface_snow", "雪");
        add(translations, "airport_runway_surface_soil", "土壤");
        add(translations, "airport_runway_surface_ston", "石材");
        add(translations, "airport_runway_surface_tarm", "柏油路面");
        add(translations, "airport_runway_surface_trtd", "处理面");
        add(translations, "airport_runway_surface_turf", "草皮");
        add(translations, "airport_runway_surface_unkn", "未知");
        add(translations, "airport_runway_surface_unpv", "未铺装");
        add(translations, "airport_runway_surface_wate", "水面");
        add(translations, "cab_actual", "实际");
        add(translations, "cab_aircraft_age_brand_new", "全新");
        add(translations, "cab_aircraft_age_na", "机龄");
        add(translations, "cab_aircraft_age_test_flight", "试飞");
        add(translations, "cab_airport_error", "服务器在处理请求时发生错误。请重试。");
        add(translations, "cab_airport_metar", "METAR 是机场向飞行员报告天气信息时使用的一种格式。");
        add(translations, "cab_airport_status_delayed", "延误");
        add(translations, "cab_airport_status_departed", "已起飞");
        add(translations, "cab_airport_status_diverted", "已备降");
        add(translations, "cab_airport_status_diverting", "正在备降");
        add(translations, "cab_airport_status_scheduled", "计划");
        add(translations, "cab_airport_status_unknown", "未知");
        add(translations, "cab_airport_wx_air_pressure", "气压");
        add(translations, "cab_airport_wx_dew_point", "露点");
        add(translations, "cab_airport_wx_humidity", "湿度");
        add(translations, "cab_airport_wx_metar", "最新 METAR");
        add(translations, "cab_airport_wx_wind", "风");
        add(translations, "cab_btn_3d", "3D 视图");
        add(translations, "cab_btn_follow", "关注");
        add(translations, "cab_btn_route", "航线");
        add(translations, "cab_calibrated_alt", "气压高度");
        add(translations, "cab_chart_altitude_title", "气压高度");
        add(translations, "cab_chart_altitude_title_marker", "气压高度：");
        add(translations, "cab_chart_speed_title", "地速");
        add(translations, "cab_chart_speed_title_marker", "地速：");
        add(translations, "cab_data_source_increase_coverage", "增加你所在地区的覆盖");
        add(translations, "cab_data_source_inv_summary", "该航空器因故障或错误编程而广播无效的应答机代码。应答机故障可能产生可通过多种方式显示的错误。由于 ICAO 24 位地址不正确，通常无法识别该航空器。");
        add(translations, "cab_data_source_inv_title", "无效应答机");
        add(translations, "cab_estimated", "预计");
        add(translations, "cab_gate", "登机口");
        add(translations, "cab_most_tracked_counter", "由 <b>%s</b> 人关注");
        add(translations, "cab_most_tracked_ranking", "全球 <b>#%s</b>");
        add(translations, "cab_myfr24_link", "开始记录你的航班");
        add(translations, "cab_myfr24_onboard_multiple", "本航班上的用户");
        add(translations, "cab_myfr24_onboard_single", "本航班上的用户");
        add(translations, "cab_myfr24_travellers_multiple", "以上是最常搭乘 %1$s（%2$s）至 %3$s（%4$s）并于 %5$s 出行的 myFlightradar24 用户。");
        add(translations, "cab_myfr24_travellers_multiple_airport", "以上是最常往返 %1$s（%2$s）的 myFlightradar24 用户。");
        add(translations, "cab_myfr24_travellers_single", "以上是一位最常搭乘 %1$s（%2$s）至 %3$s（%4$s）并于 %5$s 出行的 myFlightradar24 用户。");
        add(translations, "cab_myfr24_travellers_single_airport", "以上是一位最常往返 %1$s（%2$s）的 myFlightradar24 用户。");
        add(translations, "cab_operated_by", "由 %s 执飞");
        add(translations, "cab_scheduled", "计划");
        add(translations, "cab_share_flight", "分享航班");
        add(translations, "cab_small_departed_na", "已起飞 N/A");
        add(translations, "cab_small_reg", "注册号");
        add(translations, "cab_terminal", "航站楼");
        add(translations, "cab_tracked_via_satellite", "通过卫星追踪");
        add(translations, "search_aircraft", "航空器");
        add(translations, "search_airline_msg", "仅列出当前在 Flightradar24 覆盖范围内的 %s 航班。");
        add(translations, "search_by_route_arr_hint", "搜索到达机场");
        add(translations, "search_by_route_button", "搜索");
        add(translations, "search_by_route_dep_hint", "搜索出发机场");
        add(translations, "search_callsign", "呼号");
        add(translations, "search_error_msg", "服务器在处理请求时发生错误。请重试。");
        add(translations, "search_found_aircraft", "%1$d/%2$d 架航空器");
        add(translations, "search_hint_with_tooltip", "例如 BA112、Heathrow、LHR-JFK、BAW112");
        add(translations, "search_label", "搜索");
        add(translations, "search_menu_title", "搜索");
        add(translations, "search_nearby_away", "距此 %s");
        add(translations, "search_shortcut_airline_filter_hint", "航空公司名称或 ICAO 代码");
        add(translations, "search_shortcut_airport_filter_hint", "机场名称或 IATA 代码");
        add(translations, "search_shortcut_country_filter_hint", "国家/地区名称");
        add(translations, "search_status_canceled", "已取消");
        add(translations, "search_status_departed", "已起飞");
        add(translations, "search_status_scheduled", "计划");
        add(translations, "search_status_unknown", "未知");
        add(translations, "eta_ago", "%s 前");
        add(translations, "eta_in", "%s 后");
        add(translations, "share_text",
                "试试 Flightradar24，这款应用能把手机变成空中交通雷达：%s");
        add(translations, "accessibility_filters_locked", "要解锁筛选器功能，您需要拥有一个账户");
        add(translations, "app_update_continue_btn", "继续");
        add(translations, "app_update_download_btn", "立即更新");
        add(translations, "app_update_msg", "您正在使用不受支持的 Flightradar24 版本。请更新到最新版本，以获得更好的航班追踪体验。");
        add(translations, "app_update_title", "更新 Flightradar24");
        add(translations, "authenticate_logged_out_number_of_active_sessions_exceeded_description", "您可能在另一台设备上登录，导致超出当前订阅类型允许的活动会话数量。您可以在此设备上重新登录。");
        add(translations, "authenticate_logged_out_number_of_active_sessions_exceeded_title", "您已退出登录");
        add(translations, "authenticate_logged_out_session_expired_description", "您可以重新登录。如果无法登录或遇到其他问题，请联系支持团队。");
        add(translations, "authenticate_logged_out_session_expired_title", "会话已过期");
        add(translations, "geofence_notification_text", "获取最新抵港、离港及天气信息");
        add(translations, "geofence_notification_title", "欢迎来到 %s");
        add(translations, "geofence_popup_btn1", "好的，知道了");
        add(translations, "geofence_popup_btn2", "更新设置");
        add(translations, "geofence_popup_msg1", "使用 Flightradar24，及时了解您关注的航班动态。");
        add(translations, "geofence_popup_msg2", "如果不想在到访机场时收到通知，请在“提醒”中更新机场通知偏好设置。");
        add(translations, "notification_channel_live_notifications", "实时通知");
        add(translations, "notification_channel_new_features", "新功能");
        add(translations, "push_notification_aircraft", "航空器：");
        add(translations, "push_notification_altitude", "高度：");
        add(translations, "push_notification_callsign", "呼号：");
        add(translations, "push_notification_route", "航线：");
        add(translations, "push_notification_special_flight_title", "精选航班提醒");
        add(translations, "push_notification_title", "航班提醒");
        add(translations, "push_notification_title_7600", "无线电故障提醒");
        add(translations, "push_notification_title_7700", "一般紧急情况提醒");
        add(translations, "view_3d_error", "很抱歉，无法加载 3D 视图。");
        add(translations, "signup_account_linked_apple", "你的 Apple 账户已关联");
        add(translations, "signup_account_linked_facebook", "你的 Facebook 账户已关联");
        add(translations, "signup_account_linked_google", "你的 Google 账户已关联");
        add(translations, "signup_account_newsletter", "接收来自 Flightradar24 的邮件更新");
        add(translations, "signup_already_have", "已经有账户了？");
        add(translations, "signup_create_account", "创建账户");
        add(translations, "signup_log_in", "登录");
        add(translations, "signup_newsletter_error", "请选择你的邮件偏好设置。");
        add(translations, "signup_newsletter_no", "不用了，我不想接收来自 Flightradar24 的更新。");
        add(translations, "signup_newsletter_yes", "是的，我想接收来自 Flightradar24 的邮件更新：");
        add(translations, "signup_newsletter_yes_option1", "不定期接收最新功能和专属优惠信息");
        add(translations, "signup_newsletter_yes_option2", "每周五接收最新航空新闻周报");
        add(translations, "signup_nonsubscribed_header", "创建一个可在所有设备通用的 Flightradar24 账户");
        add(translations, "signup_or_with_email", "或使用电子邮箱");
        add(translations, "signup_password_hint", "设置密码");
        add(translations, "signup_privacy_policy_note", "我们不会与任何人分享你的个人信息。你可以随时更改邮件偏好设置。\n阅读我们的%s");
        add(translations, "signup_privacy_policy_note_link", "隐私政策");
        add(translations, "signup_with_apple", "使用 Apple 注册");
        add(translations, "signup_with_email", "使用电子邮箱注册");
        add(translations, "signup_with_facebook", "使用 Facebook 注册");
        add(translations, "signup_with_google", "使用 Google 注册");
        add(translations, "tooltip_ar_main_button", "启动 AR");
        add(translations, "tooltip_ar_main_description", "使用 AR 视图，将摄像头对准天空，即可查看头顶航班的详细信息。");
        add(translations, "tooltip_ar_main_title", "把手机举向天空！");
        add(translations, "tooltip_ar_main_title_tablet", "把平板举向天空！");
        add(translations, "tooltip_ar_range_description", "看不到飞机？试着增大搜索半径。");
        add(translations, "tooltip_ar_tabs_description", "点击这些标签页即可查看更多航班详情。");
        add(translations, "tooltip_bookmark_welcome_button", "打开收藏");
        add(translations, "tooltip_bookmark_welcome_description", "这是你第一次点击星标，我们想借此机会向你介绍它的用法。\n\n点击星标后，航班、飞机或机场会被加入你的收藏列表，方便你快速访问。你可以在地图界面向下滑动顶部栏打开收藏。");
        add(translations, "tooltip_bookmark_welcome_title", "很高兴你发现了收藏功能");
        add(translations, "tooltip_description", "工具提示");
        add(translations, "tooltip_label", "显示工具提示");
        add(translations, "tooltip_search_1", "搜索航班号、机场、航线、呼号或注册号。");
        add(translations, "tooltip_search_2", "如图所示，你可以直接在搜索栏中搜索航班号、机场、航线、呼号或注册号。");
        add(translations, "walkthrough_00_subtitle", "点击飞机图标即可查看包含飞机照片的航班概览。点击“更多信息”可查看更详细的内容。");
        add(translations, "walkthrough_00_title", "探索我们的精选功能");
        add(translations, "walkthrough_01_subtitle", "展开后可查看完整航班状态和飞机详情，还可以查看你正在追踪的飞机大图。");
        add(translations, "walkthrough_01_title", "获取完整航班与飞机信息");
        add(translations, "walkthrough_02_subtitle", "点击 AR 视图按钮并将设备指向天空，即可快速识别头顶航班。");
        add(translations, "walkthrough_02_title", "识别头顶航班");
        add(translations, "walkthrough_03_subtitle", "通过搜索航班号、航线或注册号查找航班，也可以仅按出发地或目的地查找航班。");
        add(translations, "walkthrough_03_title", "轻松查找航班");
        add(translations, "walkthrough_04_subtitle", "按名称或代码搜索机场，或直接点击地图上的机场标记。查看实时到港、离港以及当前天气。");
        add(translations, "walkthrough_04_title", "获取机场实时信息");
        add(translations, "walkthrough_05_subtitle", "查看航班历史并回放过往航班。查看该航班的航迹以及详细的速度和高度图表。你可以通过搜索进入历史记录，或在选中航班后点击“更多”或“最近”航班进入。");
        add(translations, "walkthrough_05_title", "回顾历史航班");
        add(translations, "walkthrough_06_charts", "航空图");
        add(translations, "walkthrough_06_cloud", "云层图层");
        add(translations, "walkthrough_06_precip", "降水图层");
        add(translations, "walkthrough_06_subtitle", "为主地图添加云层、降水、航空图、洋区航路及其他图层。点击“设置”即可查看所有可用图层。");
        add(translations, "walkthrough_06_title", "添加实用地图图层");
        add(translations, "walkthrough_06_tracks", "洋区航路");
        add(translations, "walkthrough_close", "关闭");
        add(translations, "walkthrough_new_3d_subtitle", "使用 3D 视图跟随航班，查看精细的飞机 3D 模型、高分辨率卫星与地形影像，以及附近其他航班。");
        add(translations, "walkthrough_new_3d_title", "在 3D 视图中跟随航班");
        add(translations, "walkthrough_next", "下一步");
        add(translations, "augmented_aircraft", "航空器");
        add(translations, "augmented_details", "详情");
        add(translations, "augmented_no_compass", "您的设备未配备指南针。");
        add(translations, "augmented_overview", "概览");
        add(translations, "augmented_reg", "注册号");
        add(translations, "volcano_area", "区域");
        add(translations, "volcano_current_ash_area", "当前火山灰区域");
        add(translations, "volcano_eruption", "喷发");
        add(translations, "volcano_forecast_12hrs", "第二预报火山灰区域（通常为 +12 小时）");
        add(translations, "volcano_forecast_18hrs", "第三预报火山灰区域（通常为 +18 小时）");
        add(translations, "volcano_forecast_6hrs", "第一预报火山灰区域（通常为 +6 小时）");
        add(translations, "volcano_forecasts", "预报");
        add(translations, "volcano_issue_time", "发布时间");
        add(translations, "volcano_observations", "观测");
        add(translations, "volcano_remarks", "备注");
        add(translations, "volcano_valid", "有效时间");
        add(translations, "whats_new", "更新内容");
        add(translations, "whats_new_action_view_afh", "查看机场航班历史");
        add(translations, "whats_new_feature_3d_view", "带逼真模型的 3D 视图");
        add(translations, "whats_new_feature_ad_free", "无广告应用体验");
        add(translations, "whats_new_feature_aeronautical_charts", "地图上的航空图");
        add(translations, "whats_new_feature_description_airport_history", "通过新的“机场航班历史”功能，可查看全球各机场最长 365 天的历史起降记录。");
        add(translations, "whats_new_feature_description_airport_movements", "增加了更多数据、表格和交互式图表，可查看机场过去 7 天的总起降架次、跑道使用情况，以及按天细分的 7 天明细。");
        add(translations, "whats_new_feature_description_airport_panel", "全新升级的机场面板让您比以往查看更多信息，并可自定义重要机场数据的展示方式。通过可展开和可折叠的分区，您可以控制查看内容，其中包括统计、跑道详情、航班历史和最新动态。");
        add(translations, "whats_new_feature_description_airport_statistics", "以交互方式查看更多所选机场的数据，包括航线、通航机场数量、通航国家数量，以及最繁忙航线的细分。");
        add(translations, "whats_new_feature_description_latest_events", "可从面板底部的“概览”标签页查看所选机场最新的起飞和降落，以及每个航班的详细信息。");
        add(translations, "whats_new_feature_filters", "按航空器类别筛选");
        add(translations, "whats_new_feature_flight_history", "查看更长时间的航班历史");
        add(translations, "whats_new_feature_map_layers", "航空气象地图图层");
        add(translations, "whats_new_feature_title_airport_history", "机场航班历史");
        add(translations, "whats_new_feature_title_airport_movements", "每日起降架次与跑道使用情况");
        add(translations, "whats_new_feature_title_airport_panel", "机场面板");
        add(translations, "whats_new_feature_title_airport_statistics", "机场统计");
        add(translations, "whats_new_feature_title_latest_events", "最新动态");
        add(translations, "allow", "允许");
        add(translations, "no_thanks", "不用了，谢谢");
        add(translations, "system_settings", "系统设置");
        add(translations, "perm_app_settings_button", "设置");
        add(translations, "perm_ar_location_description",
                "使用 AR 视图需要允许位置和相机权限。允许位置权限后，你还可以：");
        add(translations, "perm_ar_location_settings", "你随时可以在设备的“应用权限”设置中更改此偏好。");
        add(translations, "perm_ar_location_text_1", "• 查看附近所有航班和机场的列表");
        add(translations, "perm_ar_location_text_2", "• 一键将地图缩放到你的位置");
        add(translations, "perm_ar_location_title", "Flightradar24 在授予位置和相机权限后体验最佳");
        add(translations, "perm_background_location_description",
                "始终允许 Flightradar24 访问你的位置后，你可以：");
        add(translations, "perm_background_location_text",
                "• 当你到访机场时接收通知，并在后台使用你的位置以便快速查看航班信息。");
        add(translations, "perm_camera_ar", "AR 视图需要相机权限。");
        add(translations, "perm_camera_ar_settings",
                "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要相机权限。\n\n如需启用相机权限，请前往“应用设置 → 权限”，并开启相机权限。");
        add(translations, "perm_camera_location_ar", "AR 视图需要相机和位置权限。");
        add(translations, "perm_camera_location_ar_precise", "AR 视图需要相机和精确位置权限。");
        add(translations, "perm_camera_location_ar_settings",
                "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要相机和位置权限。\n\n如需启用相机和位置权限，请前往“应用设置 → 权限”，并开启相机和位置权限。");
        add(translations, "perm_camera_location_ar_settings_precise",
                "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要相机和精确位置权限。\n\n如需启用相机和精确位置权限，请前往“应用设置 → 权限”，并开启相机和精确位置权限。");
        add(translations, "perm_location", "需要位置权限。");
        add(translations, "perm_location_ar", "AR 视图需要位置权限。");
        add(translations, "perm_location_ar_precise", "AR 视图需要精确位置权限。");
        add(translations, "perm_location_ar_settings",
                "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要位置权限。\n\n如需启用位置权限，请前往“应用设置 → 权限”，并开启位置权限。");
        add(translations, "perm_location_ar_settings_precise",
                "AR 视图可让你将设备对准天空，以识别头顶飞过的航空器。要使用此功能，我们需要精确位置权限。\n\n如需启用精确位置权限，请前往“应用设置 → 权限”，并开启精确位置权限。");
        add(translations, "perm_location_description",
                "在你使用应用期间，允许 Flightradar24 访问你的位置后，你可以：");
        add(translations, "perm_location_myloc_settings",
                "定位按钮可让你一键将地图缩放到你的位置。要使用此功能，我们需要位置权限。\n\n如需使用定位按钮，请前往“应用设置 → 权限”，并开启位置权限。");
        add(translations, "perm_location_nearby_airports_background",
                "机场通知需要“始终允许”的位置权限。");
        add(translations, "perm_location_nearby_airports_background_precise",
                "机场通知需要“始终允许”的精确位置权限。");
        add(translations, "perm_location_nearby_settings",
                "“搜索附近的航空器”会使用你的位置来确定你附近的航空器列表。要使用此功能，我们需要位置权限。\n\n如需使用“搜索附近的航空器”，请前往“应用设置 → 权限”，并开启位置权限。");
        add(translations, "perm_location_precise", "需要精确位置权限。");
        add(translations, "perm_location_show_myloc_settings",
                "“显示我的位置”可让你在地图界面上以圆点查看当前位置。要使用此功能，我们需要位置权限。\n\n如需使用“显示我的位置”，请前往“应用设置 → 权限”，并开启位置权限。");
        add(translations, "perm_location_text_1",
                "• 只需将设备对准天空，即可通过 AR 视图查看附近有哪些航班，请见上图示例");
        add(translations, "perm_location_title", "Flightradar24 在授予位置权限后体验最佳");
        add(translations, "perm_notification", "需要通知权限。");
        add(translations, "accessibility_airport_photo_action", "在外部浏览器中打开图片");
        add(translations, "accessibility_drop_down_menu_toggle", "切换下拉面板");
        add(translations, "accessibility_map", "地图");
        add(translations, "accessibility_move_map_down", "向下移动地图");
        add(translations, "accessibility_move_map_left", "向左移动地图");
        add(translations, "accessibility_move_map_right", "向右移动地图");
        add(translations, "accessibility_move_map_up", "向上移动地图");
        add(translations, "accessibility_show_percentages", "显示百分比");
        add(translations, "accessibility_weather", "天气：%s");
        add(translations, "accessibility_zoom_in_map", "放大地图");
        add(translations, "accessibility_zoom_out_map", "缩小地图");
        add(translations, "back_button_content_description", "返回");
        add(translations, "log_in_screen_title", "登录");
        add(translations, "my_location_description", "前往我的位置");
        add(translations, "please_wait", "请稍候…");
        add(translations, "btn_could_be_better", "可以更好");
        add(translations, "btn_great", "很棒");
        add(translations, "btn_provide_feekback", "留下反馈");
        add(translations, "btn_rate_and_review", "评分/评价");
        add(translations, "cancel", "取消");
        add(translations, "clear", "清除");
        add(translations, "exit", "你确定要退出应用吗？");
        add(translations, "from", "从");
        add(translations, "loading", "正在加载");
        add(translations, "no", "否");
        add(translations, "ok", "确定");
        add(translations, "off_caps", "关");
        add(translations, "on_caps", "开");
        add(translations, "or", "或");
        add(translations, "select_all", "全选");
        add(translations, "to", "到");
        add(translations, "volcano", "火山");
        add(translations, "yes", "是");
        add(translations, "aircraft_no_photo_title", "航空器照片不可用");
        add(translations, "alerts_to_many_conditions", "最多只能添加 5 个条件");
        add(translations, "custom_filter_list_edit", "编辑");
        add(translations, "custom_filter_list_switch_enable", "启用筛选");
        add(translations, "delete", "删除");
        add(translations, "discard", "放弃");
        add(translations, "delete_account_code_description", "请输入下方代码，以确认您要删除账户。");
        add(translations, "delete_account_description_no_subscription",
                "点击“删除账户”即可删除您的 Flightradar24 账户，以及与其关联的所有个人数据和产品数据，其中包括所有已登录设备上的已启用且已保存的筛选、设置、提醒、收藏和 MyFR24 数据。部分存储在应用本地的产品数据仍会保留在您的 iOS/Android 设备上，可能需要手动删除。\n\n您可以随时创建新账户。");
        add(translations, "delete_account_enter_code", "输入代码");
        add(translations, "delete_account_error_message", "无法删除您的账户。请稍后重试。如果问题仍然存在，请联系支持团队。");
        add(translations, "delete_account_error_title", "发生错误");
        add(translations, "delete_account_got_it", "知道了");
        add(translations, "delete_account_group_error_message", "您的账户属于某个群组。请联系群组管理员删除您的账户。");
        add(translations, "delete_account_group_error_title", "无法删除账户");
        add(translations, "delete_account_success_message", "您的账户已永久删除。");
        add(translations, "delete_account_success_title", "账户已删除");
        add(translations, "download_csv", "下载 CSV");
        add(translations, "download_kml", "下载 KML");
        add(translations, "download_quota_reached", "您已达到下载限额。");
        add(translations, "download_text", "您可以将航班数据下载为 KML 或 CSV 文件。");
        add(translations, "download_title", "下载数据文件");
        add(translations, "error_something_went_wrong", "出了点问题。");
        add(translations, "location_error", "抱歉，无法确定您的位置。请确保已开启定位服务。");
        add(translations, "no_camera_error", "AR 视图需要设备配备后置摄像头。");
        add(translations, "no_connection_error_message", "请检查网络连接后重试。");
        add(translations, "fr24_not_available", "Flightradar24 服务器无响应");
        add(translations, "no_connection_error_details", "无法连接到服务器。");
        add(translations, "see_latest_update", "查看最新动态");
        add(translations, "stats_no_aircraft_error_msg", "您查看的区域可能不在覆盖范围内，或筛选条件过于严格。");
        add(translations, "unable_to_locate", "无法获取您的位置，请检查定位设置后重试。");
        add(translations, "widget_error", "出了点问题，无法加载数据。");
        add(translations, "technical_problems", "技术问题");
        add(translations, "try_again", "重试");
        add(translations, "no_aircraft_found", "未找到航空器。");
        add(translations, "no_callsign", "无呼号");
        add(translations, "flight_ended_title", "正在关注的航班已降落或超出覆盖范围");
        add(translations, "flight_info_load_more_flights", "加载更多航班");
        add(translations, "flight_validation_btn_error", "确定，返回实时地图");
        add(translations, "flight_validation_btn_no", "否，返回实时地图");
        add(translations, "flight_validation_btn_yes_aircraft", "是，查看航空器历史");
        add(translations, "flight_validation_btn_yes_flight", "是，查看航班历史");
        add(translations, "flight_validation_error_msg", "目前无法完成您的请求。请稍后重试。");
        add(translations, "flight_validation_error_title", "请求失败");
        add(translations, "flight_validation_not_found", "抱歉，找不到该航班的数据。");
        add(translations, "flight_validation_title", "未找到实时航班");
        add(translations, "label_flight_number", "航班号");
        add(translations, "no_flights_found", "未找到航班。");
        add(translations, "infinite_flight_error_crashed", "抱歉，3D 视图已停止运行。请重启应用后重试。");
        add(translations, "infinite_flight_error_title", "发生错误");
        add(translations, "jetphotos_error_msg", "无法加载图片。");
        add(translations, "jetphotos_error_title", "出了点问题");
        add(translations, "language_restart", "重启");
        add(translations, "language_restart_msg", "请重启 Flightradar24 以应用所选语言设置。");
        add(translations, "live_notifications_created_for", "已为 %s 创建实时通知");
        add(translations, "live_notifications_creation_error_description", "请稍后重试。");
        add(translations, "live_notifications_creation_error_title", "无法创建实时通知");
        add(translations, "live_notifications_permission_popup_description",
                "要使用实时通知，请在系统设置中启用通知。");
        add(translations, "live_notifications_permission_popup_title", "实时通知");
        add(translations, "live_notifications_stop_following", "停止关注");
        add(translations, "message_feedback_dailog",
                "很遗憾未能令您满意！\n希望您能告诉我们如何改进 Flightradar24。");
        add(translations, "message_rate_app",
                "很高兴您喜欢！\n如果您能在 Google Play 商店为 Flightradar24 评分或撰写评价，帮助更多用户发现这款应用，我们将不胜感激。");
        add(translations, "messages_rate_app_dialog", "到目前为止，您觉得 Flightradar24 怎么样？");
        add(translations, "title_rate_us", "Flightradar24 意见反馈");
        add(translations, "multi_select_popup_description", "请从下方列表选择一个航班或机场");
        add(translations, "multi_select_popup_title", "应用当前不支持多选");
        add(translations, "multi_select_unavailable_airport", "不可用的机场");
        add(translations, "multi_select_unavailable_flight", "不可用的航班");
        add(translations, "onground_disclaimer",
                "免责声明：在确认航空器已从机场起飞之前，该航空器将一直保留在列表中。请注意，数据可能存在误差。");
        add(translations, "widget_last_update", "上次更新于 %s");
        add(translations, "flight_ended_description",
                "呼号为 %s 的航班目前未被 Flightradar24 跟踪。该航班可能已超出覆盖范围或已降落。");
        add(translations, "flight_ended_description_history", "是否查看航空器 %s 的航班历史？");
        add(translations, "flight_validation_found_aircraft",
                "注册号为 %s 的航空器目前未被 Flightradar24 跟踪。该航空器可能已超出覆盖范围或已降落。\n\n是否查看该航班的历史记录？");
        add(translations, "flight_validation_found_flight",
                "航班 %s 目前未被 Flightradar24 跟踪。该航班可能已超出覆盖范围或已降落。\n\n是否查看该航班的历史记录？");
        add(translations, "unable_to_load_ad",
                "<font face=sans-serif-medium>无法加载广告，请重试。</font>");
        add(translations, "unable_to_load_ad_tip",
                "<font face=sans-serif-medium>无法加载广告，请重试。</font><br/>提示：为提高获得激励广告的概率，请在“隐私偏好中心”中调整广告偏好设置。");
        add(translations, "accessibility_reorder", "重新排序");
        add(translations, "accessibility_squawk", "应答机代码：%s");
        add(translations, "accessibility_wind_calm", "风：静风");
        add(translations, "oceanic_track", "洋区航路");
        add(translations, "oceanic_track_direction", "方向");
        add(translations, "oceanic_track_from", "自");
        add(translations, "oceanic_track_until", "至");
        add(translations, "oceanic_track_points", "航路点");
        add(translations, "dialog_message_calibration", "如果指南针表现不佳，请尝试重新校准。可按以下步骤操作：\n\n将手机前后倾斜\n\n左右移动手机\n\n然后向左、向右倾斜\n\n可能需要重复上述步骤，直到指南针校准完成。");
        add(translations, "label_aircraft_registration", "航空器注册号");
        add(translations, "label_airport_name_or_code", "机场名称、IATA 或 ICAO 代码");
        add(translations, "label_type_code", "机型代码");
        add(translations, "label_serial_number", "序列号（MSN）");
        add(translations, "label_code", "代码");
        add(translations, "label_operator", "运营方");
        add(translations, "label_country_of_registration", "注册国家/地区");
        add(translations, "label_center", "居中");
        add(translations, "label_speed", "速度");
        add(translations, "label_track", "航迹");
        add(translations, "live_aircraft_status_not_available", "状态 N/A");
        add(translations, "play_services_not_found", "Flightradar24 应用需要更新或重新安装 Google Play 服务。");
        add(translations, "signin_with_google", "使用 Google 登录");
        add(translations, "signin_with_facebook", "使用 Facebook 登录");
        add(translations, "signin_with_apple", "使用 Apple 登录");
        add(translations, "label_age", "机龄");
        add(translations, "label_date", "日期");
        return Collections.unmodifiableMap(translations);
    }

    private static void add(Map<String, String> translations, String key, String value) {
        if (key == null || key.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Translation keys and values must be non-blank");
        }
        if (translations.put(key, value) != null) {
            throw new IllegalStateException("Duplicate resource translation: " + key);
        }
    }
}
