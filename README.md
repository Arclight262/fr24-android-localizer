# FR24 简体中文模块

**简体中文** | [English](README.en.md) | [繁體中文](README.zh-TW.md) | [日本語](README.ja.md) | [한국어](README.ko.md)

这是一个面向 Flightradar24 Android 客户端的非官方简体中文测试模块。它通过标准 Legacy Xposed API 82，在目标进程内按已审计资源名和语境翻译，并使用受约束的文本与描述 Hook；未命中或处理异常时保持原样。不修改原版安装包。

当前源码包含 1,217 条资源名映射和 65 条精确文本映射，另有受约束的动态文本、数组和复数处理，覆盖地图、搜索、热门航班、运行异常、航班与机场详情等原生界面。**仍为测试项目，不是完整汉化；映射数量不代表页面完成率。** 公开源码与发布包不包含原版 APK、反编译文件或提取图片。

## 兼容性

- 目标应用包名：`com.flightradar24free`
- Android 最低版本：8.1（API 27）
- 最近真机复核环境：Android 16、SukiSU Ultra、NeoZygisk、Vector/LSPosed、Flightradar24 11.9.0（110900000）
- 接口：标准 Legacy Xposed API 82，不调用 Vector、NeoZygisk 或某个 LSPosed 分支的私有 API

除上述已验证环境外，尚未完成其他框架分支、Android 版本或 Flightradar24 版本的设备验证，不对其兼容性作出保证。

## 安装

1. 优先从 [GitHub Releases](https://github.com/Arclight262/fr24-android-localizer/releases) 获取 APK；也可从与所需提交匹配的成功 [GitHub Actions 运行](https://github.com/Arclight262/fr24-android-localizer/actions) 获取临时测试包。这是普通 Android APK，不要在 Magisk/SukiSU 的模块页面中刷入。
2. 若下载的是 Actions 产物，先解压该产物并取出 APK。
3. 使用 Android 套件安装程序安装 APK，再打开 Vector 或兼容的 LSPosed 管理器。
4. 在管理器中进入“模块”。
5. 启用 `FR24 中文化（非官方测试）`。
6. 模块作用域只勾选 Flightradar24（`com.flightradar24free`），然后强行停止并重新打开它。

每个 CI 产物均包含带版本号的 APK、`SHA256SUMS.txt` 和 `BUILD_INFO.txt`。请先计算 APK 的 SHA-256，并与 `SHA256SUMS.txt` 中对应条目比较；再核对 `BUILD_INFO.txt` 中的 versionName、versionCode、commit、workflowRun 与下载来源。

模块本身没有启动图标，也没有设置界面，这是正常现象。

测试包使用 Android 调试证书签名。本地与 CI 的证书可能不同，不同 CI 运行也不保证使用同一证书，因此不保证能够覆盖安装。发生签名冲突时不要卸载 FR24；可以卸载汉化模块后安装新版并重新检查作用域。不得假定可稳定签名升级，私钥不得提交仓库。

## 如何判断是否生效

首次测试可查看地图、搜索、航班详情、机场详情、设置等常见文本是否变成中文。框架日志中的以下内容可辅助确认 Hook 安装情况，但不能据此判断全量覆盖：

```text
FR24ZH: resource hook installed method=getText hookCount=<N>
FR24ZH: text hook installed
FR24ZH: content-description hook installed
```

其中 N 是运行时生成的 Hook 数量，实际值可能不同。

如果完全没有汉化，先确认作用域和强制停止步骤。若日志显示 Hook 已安装但文本仍是英文，通常意味着当前 Flightradar24 版本的文本不是通过这些 Android 资源接口提供，或实际英文与词典并不完全一致；此时需要依据设备日志和界面文本继续适配。

## 卸载与恢复

在 Vector/LSPosed 中取消本模块的 Flightradar24 作用域或直接禁用模块，然后强行停止并重新打开 Flightradar24。也可以卸载本 APK。模块不修改 Flightradar24 的安装包和用户数据，因此无需恢复原 APK。

## 安全边界

- Android 清单不申请或使用网络、存储、悬浮窗、无障碍、Root 或查询应用列表等权限；Hook 不申请也不使用 Android 无障碍权限。
- 代码只允许在 `com.flightradar24free` 及它的冒号子进程中安装 Hook。
- 资源回调按已审计资源条目名、语境和受控格式参数翻译；未命中、格式化失败和异常情况保持原样。文本和描述 Hook 使用精确匹配或受约束的专门规则，不作无约束的全局替换。
- 航班号、机场代码、注册号、时间、数值与未知文本保持原样。
- 不提供订阅、支付、完整性校验、反作弊或 Root 检测绕过。

## 从源码构建

通用环境需要 JDK 17、Android SDK Platform 35、Build Tools 35.0.0 和 Python 3；Gradle Wrapper 使用 8.9。

```sh
# 设置 JAVA_HOME 和 ANDROID_HOME 后执行
bash ./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
python3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

Windows 环境可执行：

```powershell
powershell -ExecutionPolicy Bypass -File scripts/bootstrap-android.ps1
powershell -ExecutionPolicy Bypass -File scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
py -3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

`bootstrap-android.ps1` 把构建工具下载到被 Git 忽略的项目本地 `.tools` 目录，不修改系统级安装；需要可访问下载源。Python 需另行安装，引导脚本不安装 Python。

[构建工作流](.github/workflows/build.yml) 运行单元测试、Lint、构建与 APK 校验。校验检查模块入口、Xposed 元数据、包名、版本、SDK、调试签名、未声明权限及未打包编译用桩；它不是完整安全审计，也不能代替真机测试。首次 GitHub Actions 构建已验证通过，后续仍应以每次提交的工作流结果为准。

## 移植到其他语言

当前源码把简体中文译文直接编译进 APK，不支持安装后切换语言，也不是可单独导入的语言包。移植其他语言时，建议先 Fork 仓库，再为每种语言建立独立分支，例如 `lang/ja-JP` 或 `lang/de-DE`。

1. 保留英文原文、资源条目名、匹配条件和正则表达式，只替换目标语言译文。主要入口如下：

   | 文件 | 内容 |
   | --- | --- |
   | [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) | 按 Android 资源条目名匹配的文本和格式模板 |
   | [`TranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/TranslationDictionary.java) | 英文原文与译文的精确映射 |
   | [`SettingsArrayTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/SettingsArrayTranslation.java) | 设置页数组选项 |
   | [`DynamicLabelTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/DynamicLabelTranslation.java) | 航班、呼号和机型等动态标签 |
   | [`FlightDetailViewTextTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/FlightDetailViewTextTranslation.java) | 航班详情中的受约束动态文本 |
   | [`Fr24LocalizationModule.java`](app/src/main/java/io/github/fr24zh/localizer/Fr24LocalizationModule.java) | 地图无障碍描述文本 Hook |
   | [`HookTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java) | 复数、数量和特殊格式文本，以及模板安全检查 |

2. 把 [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) 中用于格式化的 `Locale.SIMPLIFIED_CHINESE` 改为目标语言区域。测试代码中的相同区域设置也要同步调整。不要仅做全仓库机械替换：目标语言的单复数、词序和日期格式可能需要单独实现。
3. 完整保留 `%s`、`%d`、`%1$d`、`%%` 等格式占位符的数量、类型和位置索引。航班号、机场代码、注册号、时间及其他动态数据应继续由原参数提供，不应写死进译文。
4. 在 [`strings.xml`](app/src/main/res/values/strings.xml) 和 [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml) 中修改模块显示名称与说明。如果需要与中文版同时安装，还要在 [`app/build.gradle.kts`](app/build.gradle.kts) 中使用不同的 `applicationId`；不要修改目标应用包名 `com.flightradar24free`。只有在同时重命名 Java 包时，才需要同步修改 `namespace`、源码中的 `package` 声明和 [`assets/xposed_init`](app/src/main/assets/xposed_init) 入口类名。
5. 修改对应的 `app/src/test` 测试期望值，并运行上文的单元测试、APK 构建和 `verify-apk.py` 校验。随后在真机上至少复核地图、搜索、航班详情、机场详情、设置、复数和格式化文本。
6. 提交 Pull Request 时注明目标语言及区域代码、测试过的 FR24/Android/框架版本和仍未覆盖的页面。不要提交 Flightradar24 原版 APK、反编译产物、完整设备日志或密钥。

建议移植流程：`Fork → lang/<语言代码> 分支 → 翻译 → 自动化测试 → 真机复核 → Pull Request`。

## 已知边界与反馈

- 机场、城市、航空公司专名、航班号、注册号、单位以及 METAR/TAF 原文保留。
- `FREE`、`LIVE` 等图片内文字保持原样，本阶段不替换或分发原版图片。
- 地图底图、网页、支付 SDK 和 Android/OEM 系统界面不属于原生文字覆盖范围。
- 三处服务器连接错误提示已加入翻译，尚待未联网启动时真机复现验收；此前离线提示不证明服务器故障。
- 漏译反馈请附 FR24、Android 和框架版本、页面路径与原文。截图需遮盖个人信息，不上传设备序列号、原版 APK、完整日志或密钥。
- 模块只通过框架记录 Hook 安装成功或失败信息，不记录逐资源的名称、类型、命中状态或正文。框架本身权限较高，请只安装可信模块。

## 声明

本项目与 Flightradar24 AB 无关，也未获其认可。Flightradar24 名称及相关商标归其权利人所有。请只在你有权控制的设备和应用副本上测试，并自行遵守适用的服务条款和法律。

本项目自有代码采用 MIT License。
