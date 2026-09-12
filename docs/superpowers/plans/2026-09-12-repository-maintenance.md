# FR24 Android Localizer Repository Maintenance Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Reduce runtime hook overhead, remove redundant code and tests, strengthen format-resource regression coverage, and make installation and CI artifacts easier for users to understand and verify.

**Architecture:** Keep the existing Legacy Xposed hook architecture and compile-time Java dictionaries. Make resource callbacks quiet and fail-open, centralize duplicated parsing and version metadata, and improve delivery without changing the target process policy or styled-text behavior.

**Tech Stack:** Java 8 source compatibility, Android Gradle Plugin 8.7.3, Gradle 8.9, JUnit 4.13.2, PowerShell, GitHub Actions, Python 3 APK verifier.

**Spec:** `docs/superpowers/specs/2026-09-12-repository-maintenance-design.md`

## Global Constraints

- Target only `com.flightradar24free` and its existing colon child-process policy.
- Keep `minSdk = 27`, `compileSdk = 35`, and `targetSdk = 35`.
- Use only Legacy Xposed API 82; add no Android permissions or private framework API.
- Unknown values, unexpected types, formatting failures, and hook errors must preserve the original value.
- Do not translate `Spanned` values or narrow process scope in this pass.
- Do not add a signing key or claim stable in-place upgrade support.

---

### Task 1: Remove resource-callback diagnostics and gate stack capture

**Files:**
- Modify: `app/src/test/java/io/github/fr24zh/localizer/HookTranslationTest.java`
- Modify: `app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java`
- Modify: `app/src/main/java/io/github/fr24zh/localizer/Fr24LocalizationModule.java`
- Delete: `app/src/main/java/io/github/fr24zh/localizer/ResourceHookDiagnostics.java`
- Delete: `app/src/main/java/io/github/fr24zh/localizer/ResourceDiagnosticSink.java`
- Delete: `app/src/test/java/io/github/fr24zh/localizer/ResourceHookDiagnosticsTest.java`
- Delete: `app/src/test/java/io/github/fr24zh/localizer/ResourceDiagnosticSinkTest.java`

**Interfaces:**
- Produces: `HookTranslation.needsSelectedContextStack(String, Object, Object[])` for the resource hook to decide whether stack inspection is necessary.
- Preserves: existing `translateResourceCall` overloads and all fail-open results.

- [ ] **Step 1: Write the failing stack-gate test**

Add this focused test to `HookTranslationTest`:

```java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Test
public void selectedContextStackIsNeededOnlyForExactCandidateCall() {
    assertTrue(HookTranslation.needsSelectedContextStack(
            "selected", "%d selected", new Object[]{0x7f140321}));
    assertFalse(HookTranslation.needsSelectedContextStack(
            "other", "%d selected", new Object[]{0x7f140321}));
    assertFalse(HookTranslation.needsSelectedContextStack(
            "selected", "Selected", new Object[]{0x7f140321}));
    assertFalse(HookTranslation.needsSelectedContextStack(
            "selected", "%d selected", new Object[]{-1}));
    assertFalse(HookTranslation.needsSelectedContextStack(
            "selected", "%d selected", new Object[]{0x7f140321, 2}));
}
```

- [ ] **Step 2: Run the focused test and verify RED**

Run:

```powershell
scripts/run-gradle.ps1 :app:testDebugUnitTest --tests io.github.fr24zh.localizer.HookTranslationTest.selectedContextStackIsNeededOnlyForExactCandidateCall
```

Expected: compilation fails because `needsSelectedContextStack` does not exist.

- [ ] **Step 3: Implement the minimal stack gate**

Add to `HookTranslation` and reuse it from `translateSelectedResource`:

```java
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
```

In `Fr24LocalizationModule`, replace the resource-name-only branch with:

```java
} else if (HookTranslation.needsSelectedContextStack(
        resourceName, original, param.args)) {
    translated = HookTranslation.translateResourceCall(
            resourceName,
            original,
            param.args,
            Thread.currentThread().getStackTrace());
```

- [ ] **Step 4: Run the focused test and verify GREEN**

Run the same focused Gradle test. Expected: PASS.

- [ ] **Step 5: Remove per-callback diagnostic code**

Delete both diagnostic classes and their tests. Remove diagnostic fields, callback records, and `logResourceDiagnostic` from `Fr24LocalizationModule`. Keep one install line per hooked method:

```java
XposedBridge.log(LOG_PREFIX + "resource hook installed method="
        + methodName + " hookCount=" + (hooks == null ? 0 : hooks.size()));
```

Use the same concise form for `TypedArray` installation. Preserve `logInstallFailure` for installation exceptions and preserve silent fail-open behavior inside callbacks.

- [ ] **Step 6: Run the complete unit suite**

Run `scripts/run-gradle.ps1 :app:testDebugUnitTest`. Expected: all remaining tests pass.

- [ ] **Step 7: Commit**

```powershell
git add app/src/main/java app/src/test/java
git commit -m "perf: remove resource callback diagnostics"
```

### Task 2: Consolidate flight-description translation

**Files:**
- Modify: `app/src/test/java/io/github/fr24zh/localizer/DynamicLabelTranslationTest.java`
- Modify: `app/src/main/java/io/github/fr24zh/localizer/DynamicLabelTranslation.java`
- Modify: `app/src/main/java/io/github/fr24zh/localizer/Fr24LocalizationModule.java`
- Delete: `app/src/main/java/io/github/fr24zh/localizer/MapAccessibilityTranslation.java`
- Delete: `app/src/test/java/io/github/fr24zh/localizer/MapAccessibilityTranslationTest.java`

**Interfaces:**
- Produces: `DynamicLabelTranslation.translateFlightDescription(Object)` with the existing exact `Flight: [A-Z0-9-]{1,20}` contract.
- Consumes: the existing `FLIGHT` pattern and private `translate` helper.

- [ ] **Step 1: Write the failing direct flight-description test**

```java
@Test
public void translatesMapFlightDescriptionsThroughTheSharedEntryPoint() {
    assertEquals("航班：HBH8040",
            DynamicLabelTranslation.translateFlightDescription("Flight: HBH8040"));
    String nearMiss = "Flight: HBH 8040";
    assertSame(nearMiss, DynamicLabelTranslation.translateFlightDescription(nearMiss));
}
```

- [ ] **Step 2: Run the focused test and verify RED**

Run the new test only. Expected: compilation fails because the shared entry point does not exist.

- [ ] **Step 3: Add the shared method and route existing callers through it**

```java
static Object translateFlightDescription(Object value) {
    if (!(value instanceof String)) {
        return value;
    }
    return translate(FLIGHT, "航班：", (String) value);
}
```

Call this method first from `translateContentDescription`. Change `MAP_ACCESSIBILITY_TRANSLATOR` in `Fr24LocalizationModule` to call it directly. Delete `MapAccessibilityTranslation` and its duplicate test class.

- [ ] **Step 4: Run dynamic-label and accessibility tests**

Run:

```powershell
scripts/run-gradle.ps1 :app:testDebugUnitTest --tests io.github.fr24zh.localizer.DynamicLabelTranslationTest --tests io.github.fr24zh.localizer.AccessibilityNodeHookArgumentsTest
```

Expected: PASS.

- [ ] **Step 5: Commit**

```powershell
git add app/src/main/java app/src/test/java
git commit -m "refactor: share flight description translation"
```

### Task 3: Strengthen format tests and remove test-only production APIs

**Files:**
- Modify: `app/src/test/java/io/github/fr24zh/localizer/ResourceTranslationDictionaryTest.java`
- Modify: `app/src/test/java/io/github/fr24zh/localizer/HookTranslationTest.java`
- Modify: `app/src/test/java/io/github/fr24zh/localizer/TranslationDictionaryTest.java`
- Modify: `app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java`
- Modify: `app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java`

**Interfaces:**
- Removes: `HookTranslation.translateResult(Object)` and `ResourceTranslationDictionary.entriesForTest()`.
- Preserves: `ResourceTranslationDictionary.translate`, `template`, and `size`.

- [ ] **Step 1: Add the formatted-resource table test**

```java
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
```

- [ ] **Step 2: Verify the new test protects the table**

Temporarily use an intentionally wrong expected value for one case and run the focused test. Expected: assertion failure naming that case. Restore the correct expected value and rerun. Expected: PASS.

- [ ] **Step 3: Remove redundant production APIs and tests**

Delete `translateResult` and its four dedicated tests. Delete `entriesForTest` and `initialEntriesAreNonBlankAndImmutable`; dictionary construction already rejects blank keys/values and returns an unmodifiable map.

- [ ] **Step 4: Consolidate dictionary-size assertions**

Remove repeated `1217`, `65`, and `1282` assertions from per-feature tests. Keep one explicit baseline in `TranslationDictionaryTest`:

```java
@Test
public void auditedDictionarySizesRemainExplicit() {
    assertEquals(1217, ResourceTranslationDictionary.size());
    assertEquals(65, TranslationDictionary.size());
    assertEquals(1282,
            ResourceTranslationDictionary.size() + TranslationDictionary.size());
}
```

- [ ] **Step 5: Run all dictionary and hook tests**

Run the three modified test classes. Expected: PASS with only one occurrence of each repository-wide size baseline.

- [ ] **Step 6: Commit**

```powershell
git add app/src/main/java app/src/test/java
git commit -m "test: centralize translation invariants"
```

### Task 4: Centralize version metadata and improve CI artifacts

**Files:**
- Modify: `gradle.properties`
- Modify: `app/build.gradle.kts`
- Modify: `app/src/main/AndroidManifest.xml`
- Modify: `.github/workflows/build.yml`

**Interfaces:**
- Produces: `moduleVersionCode` and `moduleVersionName` as the single repository version source.
- Produces: versioned APK, `SHA256SUMS.txt`, and `BUILD_INFO.txt` in the CI artifact.

- [ ] **Step 1: Move version values into Gradle properties**

Add:

```properties
moduleVersionCode=3
moduleVersionName=0.2.0-test
```

At the top of `app/build.gradle.kts`, bind and consume them:

```kotlin
val moduleVersionCode: String by project
val moduleVersionName: String by project
```

```kotlin
versionCode = moduleVersionCode.toInt()
versionName = moduleVersionName
```

- [ ] **Step 2: Resolve intentional Lint warnings narrowly**

Add the tools namespace and one application-level suppression with an explanation:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <!-- Xposed-only module: no launcher surface and no user data to back up. -->
    <application
        android:allowBackup="false"
        android:label="@string/app_name"
        android:supportsRtl="true"
        tools:ignore="DataExtractionRules,MissingApplicationIcon">
```

- [ ] **Step 3: Extend the CI build and packaging steps**

Run Lint in the build command:

```yaml
run: bash ./gradlew clean :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

Add this bash packaging step. It reads the two Gradle properties, copies the APK to a versioned path, writes a checksum, and exports the artifact name:

```yaml
- name: Package identifiable artifact
  id: package
  shell: bash
  run: |
    version="$(sed -n 's/^moduleVersionName=//p' gradle.properties)"
    version_code="$(sed -n 's/^moduleVersionCode=//p' gradle.properties)"
    short_sha="${GITHUB_SHA::7}"
    artifact_name="fr24-localizer-${version}-${short_sha}"
    distribution_dir="app/build/distributions"
    mkdir -p "$distribution_dir"
    cp app/build/outputs/apk/debug/app-debug.apk \
      "$distribution_dir/${artifact_name}.apk"
    cd "$distribution_dir"
    sha256sum "${artifact_name}.apk" > SHA256SUMS.txt
    {
      echo "versionName=${version}"
      echo "versionCode=${version_code}"
      echo "commit=${GITHUB_SHA}"
      echo "workflowRun=${GITHUB_SERVER_URL}/${GITHUB_REPOSITORY}/actions/runs/${GITHUB_RUN_ID}"
      echo "signature=Android debug certificate; replacement install is not guaranteed"
    } > BUILD_INFO.txt
    echo "artifact_name=${artifact_name}" >> "$GITHUB_OUTPUT"
```

Upload `app/build/distributions/*` with `name: ${{ steps.package.outputs.artifact_name }}`.

- [ ] **Step 4: Verify configuration locally**

Run `scripts/run-gradle.ps1 :app:testDebugUnitTest :app:lintDebug :app:assembleDebug`. Expected: build success and no Lint warnings.

- [ ] **Step 5: Commit**

```powershell
git add gradle.properties app/build.gradle.kts app/src/main/AndroidManifest.xml .github/workflows/build.yml
git commit -m "ci: publish identifiable test artifacts"
```

### Task 5: Simplify Windows bootstrap and correct user documentation

**Files:**
- Modify: `scripts/bootstrap-android.ps1`
- Modify: `README.md`
- Modify: `README.en.md`
- Modify: `README.zh-TW.md`
- Modify: `README.ja.md`
- Modify: `README.ko.md`

**Interfaces:**
- Preserves: `scripts/bootstrap-android.ps1` prepares the local JDK and Android SDK under `.tools`.
- Removes: redundant standalone Gradle distribution download and wrapper generation.

- [ ] **Step 1: Pin the Windows JDK archive**

Use the official Temurin 17.0.20+8 archive and verified SHA-256:

```powershell
$jdkArchive = Join-Path $downloadsRoot 'OpenJDK17U-jdk_x64_windows_hotspot_17.0.20_8.zip'
Get-Download `
    -Uri 'https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.20%2B8/OpenJDK17U-jdk_x64_windows_hotspot_17.0.20_8.zip' `
    -Destination $jdkArchive `
    -ExpectedSha256 '418497BE5CF585BDD2203D6486A565D66D3F5E992D5630D45104CB873FAB8122'
```

Delete `$gradleRoot`, the Gradle archive download/extraction block, and the wrapper-generation fallback. The tracked `gradlew.bat` remains the only Gradle entry point.

- [ ] **Step 2: Validate the PowerShell script structure**

Run:

```powershell
[scriptblock]::Create((Get-Content scripts/bootstrap-android.ps1 -Raw)) | Out-Null
rg -n "gradleRoot|gradle-8.9-bin|wrapper --gradle-version|binary/latest/17" scripts/bootstrap-android.ps1
```

Expected: parsing succeeds and `rg` returns no matches.

- [ ] **Step 3: Correct the Simplified Chinese and English installation paths**

Use six explicit steps in `README.md`: obtain the APK from GitHub Releases or a matching successful Actions run; extract an Actions artifact; install the APK with Android's package installer; open Vector/LSPosed; enable `FR24 中文化（非官方测试）`; select only FR24 and force-stop/reopen it.

Use the equivalent explicit English steps in `README.en.md`, including: “Install the APK with Android's package installer before opening the Xposed manager” and the exact displayed Chinese module label.

- [ ] **Step 4: Correct the other three localized installation paths**

Use these exact user-facing installation sentences while preserving each README's existing surrounding language:

```text
繁體中文：先使用 Android 套件安裝程式安裝 APK，再開啟 Vector 或相容的 LSPosed 管理器。
日本語：Xposed マネージャーを開く前に、Android のパッケージインストーラーで APK をインストールします。
한국어：Xposed 관리자를 열기 전에 Android 패키지 설치 프로그램으로 APK를 설치합니다.
```

All three must identify the module as `FR24 中文化（非官方测试）`, link GitHub Releases as the primary download location, and describe Actions artifacts as temporary test builds.

- [ ] **Step 5: Update artifact and compatibility wording in every README**

Explain that each CI artifact contains a versioned APK, `SHA256SUMS.txt`, and `BUILD_INFO.txt`; users should compare the checksum and commit with the page they downloaded from. Keep the debug-signature warning and the existing supported/verified-environment distinction.

- [ ] **Step 6: Verify documentation links and terms**

Run searches proving the obsolete module name and floating JDK wording are gone, and verify every local Markdown link resolves to a tracked path.

- [ ] **Step 7: Commit**

```powershell
git add scripts/bootstrap-android.ps1 README.md README.en.md README.zh-TW.md README.ja.md README.ko.md
git commit -m "docs: clarify installation and reproducible setup"
```

### Task 6: Final repository and APK verification

**Files:**
- Verify: all modified source, tests, scripts, workflow, and documentation.

**Interfaces:**
- Consumes: the built debug APK and all repository verification commands.
- Produces: a clean, reviewable branch ready for device verification and publication.

- [ ] **Step 1: Run a fresh complete build**

```powershell
scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
```

Expected: build success, all tests passing, zero Lint errors, and zero unexplained warnings.

- [ ] **Step 2: Run the APK verifier**

Use the available Python 3 runtime:

```powershell
python scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

Expected: `APK verification passed`.

- [ ] **Step 3: Verify signature, permissions, and checksum**

Use Android Build Tools `apksigner` and `aapt2` to confirm a valid v2 debug signature and no `uses-permission` entries. Generate SHA-256 with `Get-FileHash` and compare it with the distribution checksum format produced by CI.

- [ ] **Step 4: Review the final diff**

Run:

```powershell
git diff main...HEAD --check
git diff main...HEAD --stat
git status --short
```

Expected: no whitespace errors, only intentional files changed, and a clean worktree after commits.

- [ ] **Step 5: Record deferred device checks in the handoff**

State explicitly that main-process-only hooks, `Spanned` translation, stable signing, and measured performance remain deferred. Do not claim those items are fixed.
