# FR24 简体中文测试模块

这是一个面向 Flightradar24 Android 客户端的非官方简体中文测试模块。它通过标准 Legacy Xposed API 82，在目标进程内按已审计资源名和语境翻译，并使用受约束的文本与描述 Hook；未命中或处理异常时保持原样。不修改原版安装包。

当前源码包含 1,217 条资源名映射和 65 条精确文本映射，另有受约束的动态文本、数组和复数处理，覆盖地图、搜索、热门航班、运行异常、航班与机场详情等原生界面。**仍为测试项目，不是完整汉化；映射数量不代表页面完成率。** 公开源码与发布包不应包含原版 APK、反编译文件或提取图片。

## 兼容性

- 目标应用包名：`com.flightradar24free`
- Android 最低版本：8.1（API 27）
- 最近真机复核环境：Android 16、SukiSU Ultra、NeoZygisk、Vector/LSPosed、Flightradar24 11.9.0（110900000）
- 接口：标准 Legacy Xposed API 82，不调用 Vector、NeoZygisk 或某个 LSPosed 分支的私有 API

除上述已验证环境外，尚未完成其他框架分支、Android 版本或 Flightradar24 版本的设备验证，不对其兼容性作出保证。

## 安装

1. 从源码构建测试 APK，或在 GitHub Actions 构建成功后下载工作流产物。当前尚未发布稳定版。这是普通 Android APK，不要在 Magisk/SukiSU 的模块页面中刷入。
2. 打开 Vector 或兼容的 LSPosed 管理器，进入“模块”。
3. 启用“FR24 简体中文测试模块”。
4. 模块作用域只勾选 Flightradar24，包名应为 `com.flightradar24free`。
5. 强行停止 Flightradar24，然后重新打开。

模块本身没有启动图标，也没有设置界面，这是正常现象。

测试包使用 Android 调试证书签名。本地与 CI 的证书可能不同，不同 CI 运行也不保证使用同一证书，因此不保证能够覆盖安装。发生签名冲突时不要卸载 FR24；可以卸载汉化模块后安装新版并重新检查作用域。正式发布前需确定固定签名方案，私钥不得提交仓库。

## 如何判断是否生效

首次测试可查看地图、搜索、航班详情、机场详情、设置等常见文本是否变成中文。框架日志中的以下内容可辅助确认 Hook 安装情况，但不能据此判断全量覆盖：

```text
FR24ZH: resource-hook install method=getText
FR24ZH: text hook installed
FR24ZH: content-description hook installed
```

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
bash ./gradlew :app:testDebugUnitTest :app:assembleDebug
python3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

Windows 环境可执行：

```powershell
powershell -ExecutionPolicy Bypass -File scripts/bootstrap-android.ps1
powershell -ExecutionPolicy Bypass -File scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:assembleDebug
py -3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

`bootstrap-android.ps1` 把构建工具下载到被 Git 忽略的项目本地 `.tools` 目录，不修改系统级安装；需要可访问下载源。Python 需另行安装，引导脚本不安装 Python。

[构建工作流](.github/workflows/build.yml) 运行单元测试、构建与 APK 校验。校验检查模块入口、Xposed 元数据、未声明权限及未打包编译用桩；它不是完整安全审计，也不能代替真机测试。首次 GitHub Actions 构建已验证通过，后续仍应以每次提交的工作流结果为准。

## 已知边界与反馈

- 机场、城市、航空公司专名、航班号、注册号、单位以及 METAR/TAF 原文保留。
- `FREE`、`LIVE` 等图片内文字保持原样，本阶段不替换或分发原版图片。
- 地图底图、网页、支付 SDK 和 Android/OEM 系统界面不属于原生文字覆盖范围。
- 三处服务器连接错误提示已加入翻译，尚待未联网启动时真机复现验收；此前离线提示不证明服务器故障。
- 漏译反馈请附 FR24、Android 和框架版本、页面路径与原文。截图需遮盖个人信息，不上传设备序列号、原版 APK、完整日志或密钥。
- 模块通过框架写入有限诊断日志；资源诊断记录资源名、类型和命中状态，不记录资源正文。框架本身权限较高，请只安装可信模块。

## 声明

本项目与 Flightradar24 AB 无关，也未获其认可。Flightradar24 名称及相关商标归其权利人所有。请只在你有权控制的设备和应用副本上测试，并自行遵守适用的服务条款和法律。

本项目自有代码采用 MIT License。
