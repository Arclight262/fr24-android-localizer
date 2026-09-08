package io.github.fr24zh.localizer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

final class TranslationDictionary {
    private static final Map<String, String> TRANSLATIONS = createTranslations();

    private TranslationDictionary() {
    }

    static String translate(String source) {
        if (source == null) {
            return null;
        }
        String translated = TRANSLATIONS.get(source);
        return translated == null ? source : translated;
    }

    static int size() {
        return TRANSLATIONS.size();
    }

    private static Map<String, String> createTranslations() {
        Map<String, String> translations = new LinkedHashMap<>();
        translations.put("Map", "地图");
        translations.put("Search", "搜索");
        translations.put("Settings", "设置");
        translations.put("More", "更多");
        translations.put("Filters", "筛选");
        translations.put("Weather", "天气");
        translations.put("Alerts", "提醒");
        translations.put("Layers", "图层");
        translations.put("Playback", "回放");
        translations.put("Scheduled", "计划中");
        translations.put("Landed", "已降落");
        translations.put("Delayed", "延误");
        translations.put("Cancelled", "已取消");
        translations.put("Arrivals", "到达");
        translations.put("Departures", "出发");
        translations.put("Airport", "机场");
        translations.put("Terminal", "航站楼");
        translations.put("Gate", "登机口");
        translations.put("Altitude", "高度");
        translations.put("Speed", "速度");
        translations.put("Heading", "航向");
        translations.put("Aircraft", "飞机");
        translations.put("Route", "航线");
        translations.put("Flight", "航班");
        translations.put("Follow", "关注");
        translations.put("Share", "分享");
        translations.put("Close", "关闭");
        translations.put("Cancel", "取消");
        translations.put("Apply", "应用");
        translations.put("Reset", "重置");
        translations.put("On ground", "地面");
        translations.put("Estimated", "预计");
        translations.put("Actual", "实际");
        translations.put("AR view", "AR 视图");
        translations.put("Log In", "登录");
        translations.put("Log in", "登录");
        translations.put("Go to my location", "定位到我的位置");
        translations.put("Unlock 60+ features", "解锁 60 多项功能");
        translations.put("3D view", "3D 视图");
        translations.put("More info", "更多信息");
        translations.put("Less info", "收起信息");
        translations.put("Unlock", "解锁");
        translations.put("Off", "关闭");
        translations.put("Date", "日期");
        translations.put("Time (UTC)", "时间（UTC）");
        translations.put("Start playback", "开始回放");
        translations.put("Subscribe now", "立即订阅");
        translations.put("Create account", "创建账户");
        translations.put("Categories", "分类");
        translations.put("Custom", "自定义");
        translations.put("All categories", "全部类别");
        translations.put("Passenger", "客机");
        translations.put("Cargo", "货机");
        translations.put("Military or government", "军用或政府");
        translations.put("Business jets", "公务机");
        translations.put(
                "An overview of current global precipitation overlaid on our live map. "
                        + "The total precipitation layer is refreshed 12 times a day.",
                "在实时地图上叠加显示当前全球降水量。总降水层每天刷新 12 次。");
        translations.put("Want to get rid of ads?", "想要去除广告吗？");
        translations.put(
                "Upgrade for a faster, ad-free Flightradar24 experience with more features "
                        + "& data. Start your FREE trial today.",
                "升级即可获得更快、无广告且功能和数据更丰富的 Flightradar24 体验。立即开始免费试用。");
        translations.put("BAROMETRIC ALT.", "气压高度");
        translations.put("REG", "注册号");
        translations.put("Barometric altitude", "气压高度");
        translations.put("Barometric alt.", "气压高度");
        translations.put("Reg", "注册号");
        translations.put("AGE", "机龄");
        translations.put("Bookmarks: locked", "收藏：已锁定");
        return Collections.unmodifiableMap(translations);
    }
}
