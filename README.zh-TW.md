# FR24 簡體中文模組

[简体中文](README.md) | [English](README.en.md) | **繁體中文** | [日本語](README.ja.md) | [한국어](README.ko.md)

這是一個面向 Flightradar24 Android 用戶端的非官方簡體中文本地化測試模組。它透過標準 Legacy Xposed API 82，在目標程序內依已稽核的資源名稱與語境進行翻譯，並使用受限的文字與內容描述 Hook；未命中或處理發生例外時會保留原文。不修改原版應用程式套件。

目前原始碼包含 1,217 項資源名稱對應與 65 項精確文字對應，另有受限的動態文字、陣列和複數處理，涵蓋地圖、搜尋、熱門航班、運行異常、航班與機場詳情等原生畫面。**本專案仍處於測試階段，並非完整本地化；對應數量不代表畫面完成率。** 公開原始碼與發布套件不包含原版 APK、反編譯檔案或擷取的圖片。

## 相容性

- 目標應用程式套件名稱：`com.flightradar24free`
- Android 最低版本：8.1（API 27）
- 最近實機驗證環境：Android 16、SukiSU Ultra、NeoZygisk、Vector/LSPosed、Flightradar24 11.9.0（110900000）
- 介面：標準 Legacy Xposed API 82，不呼叫 Vector、NeoZygisk 或特定 LSPosed 分支的私有 API

除上述已驗證環境外，尚未在其他框架分支、Android 版本或 Flightradar24 版本上完成實機測試，因此不保證相容性。

## 安裝

1. 從原始碼建置測試 APK，或在 GitHub Actions 成功建置後下載工作流程產物。目前尚未發布穩定版。這是一般 Android APK，請勿從 Magisk/SukiSU 的模組頁面刷入。
2. 開啟 Vector 或相容的 LSPosed 管理器，進入「模組」。
3. 啟用名為 `FR24 简体中文测试模块` 的模組。
4. 模組作用域僅勾選 Flightradar24，其套件名稱必須為 `com.flightradar24free`。
5. 強制停止 Flightradar24，然後重新開啟。

此模組沒有啟動圖示或設定畫面，這是正常現象。

測試套件使用 Android 偵錯憑證簽署。本機與 CI 的憑證可能不同，不同 CI 執行也不保證使用相同憑證，因此可能無法直接覆蓋安裝。如果發生簽章衝突，請勿解除安裝 FR24；只需解除安裝本地化模組、安裝新版，再重新檢查作用域。發布穩定版前必須確定固定的簽署方案，且私密金鑰不得提交至儲存庫。

## 如何確認是否生效

首次測試時，可查看地圖、搜尋、航班詳情、機場詳情與設定等常見文字是否已變成中文。框架日誌中的下列內容可協助確認 Hook 是否安裝，但不能證明已完整涵蓋：

```text
FR24ZH: resource-hook install method=getText
FR24ZH: text hook installed
FR24ZH: content-description hook installed
```

如果完全沒有翻譯，請先確認模組作用域並再次執行強制停止。若日誌顯示 Hook 已安裝但文字仍為英文，通常表示目前的 Flightradar24 版本未透過這些 Android 資源 API 提供該文字，或實際英文與字典內容並非完全一致；此時需要依據裝置日誌與畫面原文繼續調整。

## 解除安裝與還原

在 Vector/LSPosed 中移除本模組的 Flightradar24 作用域或直接停用模組，然後強制停止並重新開啟 Flightradar24。也可以解除安裝本 APK。模組不會修改 Flightradar24 的應用程式套件或使用者資料，因此無須還原原版 APK。

## 安全邊界

- Android 資訊清單不申請或使用網路、儲存空間、懸浮視窗、無障礙、Root 或查詢已安裝應用程式等權限；Hook 也不申請或使用 Android 無障礙權限。
- 程式碼只允許在 `com.flightradar24free` 及其冒號後綴子程序中安裝 Hook。
- 資源回呼依已稽核的資源項目名稱、語境及受控格式參數進行翻譯；未命中、格式化失敗與例外情況會保留原文。文字與描述 Hook 使用精確比對或受限的專用規則，不進行不受限制的全域取代。
- 航班號、機場代碼、註冊號、時間、數值及未知文字保持不變。
- 本模組不提供訂閱、付款、完整性檢查、反作弊或 Root 偵測繞過功能。

## 從原始碼建置

一般建置環境需要 JDK 17、Android SDK Platform 35、Build Tools 35.0.0 與 Python 3；Gradle Wrapper 使用 8.9。

```sh
# 設定 JAVA_HOME 與 ANDROID_HOME 後執行
bash ./gradlew :app:testDebugUnitTest :app:assembleDebug
python3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

Windows 環境可執行：

```powershell
powershell -ExecutionPolicy Bypass -File scripts/bootstrap-android.ps1
powershell -ExecutionPolicy Bypass -File scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:assembleDebug
py -3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

`bootstrap-android.ps1` 會將建置工具下載至專案本機且已被 Git 忽略的 `.tools` 目錄，不會進行系統層級安裝；需要能夠存取下載來源。Python 必須另外安裝，引導指令碼不會安裝 Python。

[建置工作流程](.github/workflows/build.yml) 會執行單元測試、建置 APK 並驗證套件。驗證項目包括模組進入點、Xposed 中繼資料、未宣告權限，以及未封裝僅供編譯使用的樁程式碼；這並非完整安全稽核，也無法取代實機測試。第一次 GitHub Actions 建置已驗證成功，後續仍應以每次提交各自的工作流程結果為準。

## 移植到其他語言

目前原始碼會把簡體中文譯文直接編譯進 APK，不支援安裝後切換語言，也不是可獨立匯入的語言套件。移植其他語言時，建議先 Fork 儲存庫，再為每種語言建立獨立分支，例如 `lang/ja-JP` 或 `lang/de-DE`。

1. 保留英文原文、資源項目名稱、比對條件與規則運算式，只替換目標語言譯文。主要進入點如下：

   | 檔案 | 內容 |
   | --- | --- |
   | [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) | 依 Android 資源項目名稱比對的文字與格式範本 |
   | [`TranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/TranslationDictionary.java) | 英文原文與譯文的精確對應 |
   | [`SettingsArrayTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/SettingsArrayTranslation.java) | 設定頁面的陣列選項 |
   | [`DynamicLabelTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/DynamicLabelTranslation.java) | 航班、呼號及機型等動態標籤 |
   | [`FlightDetailViewTextTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/FlightDetailViewTextTranslation.java) | 航班詳情中的受限動態文字 |
   | [`MapAccessibilityTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/MapAccessibilityTranslation.java) | 地圖無障礙描述文字 |
   | [`HookTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java) | 複數、數量與特殊格式文字，以及範本安全檢查 |

2. 將 [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) 中用於格式化的 `Locale.SIMPLIFIED_CHINESE` 改為目標語言地區設定。測試程式碼中的相同地區設定也必須同步調整。不要只進行全儲存庫機械式取代：目標語言的單複數、詞序與日期格式可能需要個別實作。
3. 完整保留 `%s`、`%d`、`%1$d`、`%%` 等格式預留位置的數量、型別和位置索引。航班號、機場代碼、註冊號、時間及其他動態資料應繼續由原始參數提供，不應硬編碼於譯文中。
4. 在 [`strings.xml`](app/src/main/res/values/strings.xml) 與 [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml) 中修改模組顯示名稱與說明。如需與中文版同時安裝，還必須在 [`app/build.gradle.kts`](app/build.gradle.kts) 中使用不同的 `applicationId`；請勿修改目標應用程式套件名稱 `com.flightradar24free`。只有在同時重新命名 Java 套件時，才需要同步修改 `namespace`、原始碼中的 `package` 宣告與 [`assets/xposed_init`](app/src/main/assets/xposed_init) 進入點類別名稱。
5. 修改對應的 `app/src/test` 測試預期值，並執行上文的單元測試、APK 建置及 `verify-apk.py` 驗證。接著在實機上至少檢查地圖、搜尋、航班詳情、機場詳情、設定、複數與格式化文字。
6. 提交 Pull Request 時，請註明目標語言與地區代碼、已測試的 FR24/Android/框架版本，以及仍未翻譯的畫面。不要提交 Flightradar24 原版 APK、反編譯產物、完整裝置日誌或金鑰。

建議移植流程：`Fork → lang/<語言代碼> 分支 → 翻譯 → 自動化測試 → 實機複核 → Pull Request`。

## 已知邊界與意見回饋

- 機場、城市、航空公司專名、航班號、註冊號、單位及 METAR/TAF 原文保持不變。
- `FREE`、`LIVE` 等圖片內文字保持不變；本階段不替換或散布原版圖片。
- 地圖圖磚、網頁、付款 SDK 及 Android/OEM 系統介面不在原生文字涵蓋範圍內。
- 三則伺服器連線錯誤提示已加入翻譯，但仍需透過無網路啟動應用程式進行實機驗證；先前的離線提示不能證明伺服器故障。
- 回報未翻譯文字時，請附上 FR24、Android 與框架版本、頁面路徑及原文。截圖必須遮蔽個人資訊；請勿上傳裝置序號、原版 APK、完整日誌或金鑰。
- 模組會透過框架寫入有限的診斷日誌；資源診斷只記錄資源名稱、類型與命中狀態，不記錄資源正文。框架本身具有較高權限，請只安裝可信任的模組。

## 聲明

本專案與 Flightradar24 AB 無關，也未獲其認可。Flightradar24 名稱與相關商標均屬其權利人所有。請只在您有權控制的裝置與應用程式副本上測試，並自行遵守適用的服務條款與法律。

本專案原創程式碼採用 MIT License。
