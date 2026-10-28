# FR24 Simplified Chinese Module

[简体中文](README.md) | **English** | [繁體中文](README.zh-TW.md) | [日本語](README.ja.md) | [한국어](README.ko.md)

This is an unofficial Simplified Chinese localization test module for the Flightradar24 Android app. It uses the standard Legacy Xposed API 82 to translate audited resource names and context-specific text inside the target process, with constrained hooks for text and content descriptions. Unmatched text and errors fall back to the original content. The original app package is not modified.

The current source contains 1,217 resource-name mappings and 65 exact-text mappings, plus constrained handling for dynamic text, arrays, and plurals. It covers native screens such as the map, search, most-tracked flights, disruptions, flight details, and airport details. **This is still a test project, not a complete localization; the number of mappings does not indicate page-level completion.** The public source and release packages do not include the original APK, decompiled files, or extracted images.

## Compatibility

- Target app package: `com.flightradar24free`
- Minimum Android version: 8.1 (API 27)
- Most recently verified device environment: Android 16, SukiSU Ultra, NeoZygisk, Vector/LSPosed, and Flightradar24 11.9.0 (110900000)
- Interface: standard Legacy Xposed API 82; no private APIs from Vector, NeoZygisk, or any specific LSPosed fork

Other framework forks, Android versions, and Flightradar24 versions have not yet been tested on devices and are not guaranteed to be compatible.

## Installation

1. Get the APK from [GitHub Releases](https://github.com/Arclight262/fr24-android-localizer/releases) first, or use the matching successful [GitHub Actions run](https://github.com/Arclight262/fr24-android-localizer/actions) for a temporary test build. This is a regular Android APK; do not flash it from the Magisk/SukiSU modules page.
2. If you download an Actions artifact, extract it and take out the APK.
3. Install the APK with Android's package installer before opening the Xposed manager.
4. Open Vector or a compatible LSPosed manager and go to **Modules**.
5. Enable the module named `FR24 中文化（非官方测试）`.
6. Select only Flightradar24 for the module scope (`com.flightradar24free`), then force-stop and reopen it.

Each CI artifact contains a versioned APK, `SHA256SUMS.txt`, and `BUILD_INFO.txt`. First calculate the APK SHA-256 and compare it with the corresponding entry in `SHA256SUMS.txt`; then check the `versionName`, `versionCode`, `commit`, and `workflowRun` in `BUILD_INFO.txt` against the download source.

The module has no launcher icon or settings screen. This is expected.

Test builds are signed with an Android debug certificate. Local and CI certificates may differ, and even separate CI runs are not guaranteed to use the same certificate, so an in-place update may fail. If a signature conflict occurs, do not uninstall FR24. Uninstall only the localization module, install the new build, and check the scope again. Do not assume a stable signing upgrade path; the private key must never be committed to the repository.

## How to verify that it works

For an initial check, look for Chinese text on common screens such as the map, search, flight details, airport details, and settings. The following framework log entries can help confirm that the hooks were installed, but they do not prove complete coverage:

```text
FR24ZH: resource hook installed method=getText hookCount=<N>
FR24ZH: text hook installed
FR24ZH: content-description hook installed
```

Here, N is the hook count generated at runtime, so the actual value may differ.

If nothing is translated, first check the module scope and repeat the force-stop step. If the logs show that the hooks were installed but text remains in English, that Flightradar24 version may not expose the text through these Android resource APIs, or the actual English text may not exactly match the dictionary. Device logs and the on-screen source text are then needed for further adaptation.

## Uninstalling and restoring

In Vector/LSPosed, remove Flightradar24 from this module's scope or disable the module, then force-stop and reopen Flightradar24. You can also uninstall this APK. The module does not modify the Flightradar24 package or its user data, so the original APK does not need to be restored.

## Security boundaries

- The Android manifest neither requests nor uses network, storage, overlay, accessibility, root, or installed-app query permissions. The hooks do not request or use Android accessibility permissions.
- Hooks are installed only in `com.flightradar24free` and its colon-suffixed subprocesses.
- Resource callbacks translate audited resource-entry names, contexts, and controlled format arguments. Unmatched text, formatting failures, and exceptions fall back to the original content. Text and description hooks use exact matches or constrained, purpose-built rules rather than unrestricted global replacement.
- Flight numbers, airport codes, registrations, times, numerical values, and unknown text remain unchanged.
- The module does not bypass subscriptions, payments, integrity checks, anti-cheat systems, or root detection.

## Building from source

A general build environment requires JDK 17, Android SDK Platform 35, Build Tools 35.0.0, and Python 3. The Gradle Wrapper uses version 8.9.

```sh
# Run after setting JAVA_HOME and ANDROID_HOME
bash ./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
python3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

On Windows, run:

```powershell
powershell -ExecutionPolicy Bypass -File scripts/bootstrap-android.ps1
powershell -ExecutionPolicy Bypass -File scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
py -3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

`bootstrap-android.ps1` downloads the build tools into the project-local, Git-ignored `.tools` directory and does not perform a system-wide installation. It requires access to the download sources. Python must be installed separately; the bootstrap script does not install it.

The [build workflow](.github/workflows/build.yml) runs unit tests, Lint, the APK build, and package verification. Verification checks the module entry point, Xposed metadata, package name, version, SDK levels, debug signature, absence of declared permissions, and absence of packaged compile-time stubs. It is not a complete security audit and does not replace device testing. The first GitHub Actions build completed successfully; each later commit must be evaluated using its own workflow result.

## Porting to another language

The current source compiles the Simplified Chinese translations directly into the APK. It does not support changing languages after installation and is not an independently importable language pack. To port another language, first fork the repository and create a separate branch for each language, such as `lang/ja-JP` or `lang/de-DE`.

1. Keep the English source text, resource-entry names, matching conditions, and regular expressions unchanged. Replace only the target-language translations. The main entry points are:

   | File | Purpose |
   | --- | --- |
   | [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) | Text and format templates matched by Android resource-entry name |
   | [`TranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/TranslationDictionary.java) | Exact mappings from English source text to translated text |
   | [`SettingsArrayTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/SettingsArrayTranslation.java) | Settings-page array options |
   | [`DynamicLabelTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/DynamicLabelTranslation.java) | Dynamic labels for flights, call signs, and aircraft types |
   | [`FlightDetailViewTextTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/FlightDetailViewTextTranslation.java) | Constrained dynamic text in flight details |
   | [`Fr24LocalizationModule.java`](app/src/main/java/io/github/fr24zh/localizer/Fr24LocalizationModule.java) | Map accessibility-description hook |
   | [`HookTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java) | Plural, quantity, and special-format text, plus template safety checks |

2. In [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java), replace the `Locale.SIMPLIFIED_CHINESE` used for formatting with the target locale. Update the same locale in the tests. Do not perform a blind repository-wide replacement: plural rules, word order, and date formats may require language-specific implementations.
3. Preserve the number, type, and positional indexes of format placeholders such as `%s`, `%d`, `%1$d`, and `%%`. Flight numbers, airport codes, registrations, times, and other dynamic values must continue to come from the original arguments rather than being hard-coded into translations.
4. Update the module display name and description in [`strings.xml`](app/src/main/res/values/strings.xml) and [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml). To install the new language build alongside the Chinese build, also use a different `applicationId` in [`app/build.gradle.kts`](app/build.gradle.kts). Do not change the target app package `com.flightradar24free`. Update `namespace`, the source `package` declarations, and the entry class in [`assets/xposed_init`](app/src/main/assets/xposed_init) only if you also rename the Java package.
5. Update the expected values in the corresponding `app/src/test` tests, then run the unit tests, APK build, and `verify-apk.py` verification described above. On a real device, check at least the map, search, flight details, airport details, settings, plurals, and formatted text.
6. In a pull request, state the target language and locale code, the tested FR24/Android/framework versions, and any screens that remain untranslated. Do not commit the original Flightradar24 APK, decompiled artifacts, complete device logs, or keys.

Recommended workflow: `Fork → lang/<locale-code> branch → translate → automated tests → device verification → pull request`.

## Known limitations and feedback

- Airport, city, and airline names; flight numbers; registrations; units; and raw METAR/TAF text remain unchanged.
- Text embedded in images, such as `FREE` and `LIVE`, remains unchanged. This project does not replace or distribute original images at this stage.
- Map tiles, web pages, payment SDKs, and Android/OEM system UI are outside the native-text coverage.
- Three server-connection error messages have been translated but still need device validation by launching the app without a network connection. A previous offline prompt did not prove a server failure.
- Reports of untranslated text should include the FR24, Android, and framework versions, the page path, and the original text. Redact personal information from screenshots. Do not upload device serial numbers, the original APK, complete logs, or keys.
- The module logs only hook installation success or failure through the framework. It does not log per-resource names, types, match status, or text. The framework itself has elevated privileges; install only modules you trust.

## Disclaimer

This project is not affiliated with or endorsed by Flightradar24 AB. The Flightradar24 name and related trademarks belong to their respective owner. Test only on devices and app copies that you are authorized to control, and comply with applicable terms of service and laws.

The project's original code is licensed under the MIT License.
