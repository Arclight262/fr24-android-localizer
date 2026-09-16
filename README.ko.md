# FR24 중국어 간체 모듈

[简体中文](README.md) | [English](README.en.md) | [繁體中文](README.zh-TW.md) | [日本語](README.ja.md) | **한국어**

이 프로젝트는 Flightradar24 Android 앱을 중국어 간체로 현지화하는 비공식 테스트 모듈입니다. 표준 Legacy Xposed API 82를 사용하여 대상 프로세스 안에서 검토된 리소스 이름과 문맥을 기준으로 번역하며, 텍스트와 콘텐츠 설명에는 범위가 제한된 Hook을 적용합니다. 일치하지 않는 텍스트나 처리 중 오류가 발생한 경우 원문을 유지합니다. 원본 앱 패키지는 수정하지 않습니다.

현재 소스에는 리소스 이름 매핑 1,217개와 완전 일치 텍스트 매핑 65개가 있으며, 동적 텍스트, 배열, 복수형을 위한 제한된 처리도 포함되어 있습니다. 지도, 검색, 많이 추적되는 항공편, 운항 장애, 항공편 상세 정보, 공항 상세 정보 등의 네이티브 화면을 다룹니다. **아직 테스트 프로젝트이며 완전한 현지화가 아닙니다. 매핑 수는 화면별 완성도를 의미하지 않습니다.** 공개 소스와 릴리스 패키지에는 원본 APK, 디컴파일 파일 또는 추출한 이미지가 포함되지 않습니다.

## 호환성

- 대상 앱 패키지: `com.flightradar24free`
- 최소 Android 버전: 8.1(API 27)
- 최근 실기기 검증 환경: Android 16, SukiSU Ultra, NeoZygisk, Vector/LSPosed, Flightradar24 11.9.0(110900000)
- 인터페이스: 표준 Legacy Xposed API 82. Vector, NeoZygisk 또는 특정 LSPosed 포크의 비공개 API는 사용하지 않습니다

위 환경 외의 프레임워크 포크, Android 버전 또는 Flightradar24 버전에서는 아직 실기기 검증을 완료하지 않았으며 호환성을 보장하지 않습니다.

## 설치

1. APK는 먼저 [GitHub Releases](https://github.com/Arclight262/fr24-android-localizer/releases)에서 받으십시오. 임시 테스트 빌드가 필요하면 필요한 커밋과 일치하는 성공한 [GitHub Actions 실행](https://github.com/Arclight262/fr24-android-localizer/actions)을 사용하십시오. 일반 Android APK이므로 Magisk/SukiSU 모듈 화면에서 플래시하지 마십시오.
2. Actions 아티팩트를 받았다면 압축을 풀어 APK를 꺼냅니다.
3. Xposed 관리자를 열기 전에 Android 패키지 설치 프로그램으로 APK를 설치합니다.
4. Vector 또는 호환되는 LSPosed 관리자를 열고 **모듈**로 이동합니다.
5. `FR24 中文化（非官方测试）` 모듈을 활성화합니다.
6. 모듈 범위에는 Flightradar24(`com.flightradar24free`)만 선택한 뒤 강제 종료하고 다시 실행합니다.

각 CI 아티팩트에는 버전이 포함된 APK, `SHA256SUMS.txt`, `BUILD_INFO.txt`가 들어 있습니다. 먼저 APK의 SHA-256을 계산하여 `SHA256SUMS.txt`의 해당 항목과 비교하십시오. 그런 다음 `BUILD_INFO.txt`의 versionName, versionCode, commit, workflowRun을 다운로드 출처와 대조하십시오.

이 모듈에는 런처 아이콘이나 설정 화면이 없습니다. 이는 정상입니다.

테스트 패키지는 Android 디버그 인증서로 서명됩니다. 로컬과 CI의 인증서가 다를 수 있고 CI 실행마다 같은 인증서를 사용한다는 보장도 없으므로 덮어쓰기 설치가 실패할 수 있습니다. 서명 충돌이 발생해도 FR24를 제거하지 마십시오. 현지화 모듈만 제거한 뒤 새 버전을 설치하고 범위를 다시 확인하면 됩니다. 안정적인 서명 업그레이드 경로를 가정하지 마십시오. 비공개 키를 저장소에 커밋해서는 안 됩니다.

## 작동 여부 확인

처음 확인할 때는 지도, 검색, 항공편 상세 정보, 공항 상세 정보, 설정 등의 일반 화면이 중국어로 표시되는지 확인합니다. 다음 프레임워크 로그는 Hook 설치 여부를 확인하는 데 도움이 되지만 전체 번역 범위를 보장하지는 않습니다.

```text
FR24ZH: resource hook installed method=getText hookCount=<N>
FR24ZH: text hook installed
FR24ZH: content-description hook installed
```

여기서 N은 런타임에 생성되는 Hook 개수이므로 실제 값은 다를 수 있습니다.

번역이 전혀 적용되지 않으면 먼저 모듈 범위를 확인하고 강제 종료 단계를 다시 수행하십시오. 로그에 Hook이 설치되었다고 표시되지만 텍스트가 영어로 남아 있다면 해당 Flightradar24 버전이 이 Android 리소스 API를 통해 텍스트를 제공하지 않거나 실제 영어 문구가 사전과 완전히 일치하지 않는 경우가 많습니다. 이때는 기기 로그와 화면의 원문을 기준으로 추가 대응해야 합니다.

## 제거 및 복원

Vector/LSPosed에서 이 모듈의 Flightradar24 범위를 해제하거나 모듈을 비활성화한 뒤 Flightradar24를 강제 종료하고 다시 실행합니다. 이 APK를 제거해도 됩니다. 모듈은 Flightradar24 패키지와 사용자 데이터를 수정하지 않으므로 원본 APK를 복원할 필요가 없습니다.

## 보안 경계

- Android 매니페스트는 네트워크, 저장소, 오버레이, 접근성, Root 또는 설치된 앱 조회 권한을 요청하거나 사용하지 않습니다. Hook도 Android 접근성 권한을 요청하거나 사용하지 않습니다.
- Hook은 `com.flightradar24free` 및 콜론 접미사가 붙은 하위 프로세스에서만 설치됩니다.
- 리소스 콜백은 검토된 리소스 항목 이름, 문맥, 제한된 형식 인수를 기준으로 번역합니다. 일치하지 않는 텍스트, 형식 지정 실패, 예외가 발생하면 원문을 유지합니다. 텍스트와 설명 Hook은 제한 없는 전역 치환 대신 완전 일치 또는 용도가 제한된 규칙을 사용합니다.
- 항공편 번호, 공항 코드, 등록 번호, 시간, 수치, 알 수 없는 텍스트는 변경하지 않습니다.
- 구독, 결제, 무결성 검사, 부정행위 방지 또는 Root 감지를 우회하는 기능은 제공하지 않습니다.

## 소스에서 빌드

일반적인 빌드 환경에는 JDK 17, Android SDK Platform 35, Build Tools 35.0.0, Python 3가 필요합니다. Gradle Wrapper는 8.9를 사용합니다.

```sh
# JAVA_HOME 및 ANDROID_HOME을 설정한 뒤 실행
bash ./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
python3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

Windows에서는 다음을 실행합니다.

```powershell
powershell -ExecutionPolicy Bypass -File scripts/bootstrap-android.ps1
powershell -ExecutionPolicy Bypass -File scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
py -3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

`bootstrap-android.ps1`은 빌드 도구를 Git에서 무시되는 프로젝트 내부의 `.tools` 디렉터리에 다운로드하며 시스템 전체 설치는 수행하지 않습니다. 다운로드 원본에 접근할 수 있어야 합니다. Python은 별도로 설치해야 하며 부트스트랩 스크립트는 Python을 설치하지 않습니다.

[빌드 워크플로](.github/workflows/build.yml)는 단위 테스트, Lint, APK 빌드 및 패키지 검증을 실행합니다. 검증 항목에는 모듈 진입점, Xposed 메타데이터, 패키지 이름, 버전, SDK, 디버그 서명, 선언된 권한이 없는지 여부, 컴파일 전용 스텁이 패키지에 포함되지 않았는지 여부가 포함됩니다. 이는 완전한 보안 감사가 아니며 실기기 테스트를 대신할 수 없습니다. 첫 GitHub Actions 빌드는 성공했지만 이후 커밋은 각 워크플로 결과를 기준으로 확인해야 합니다.

## 다른 언어로 포팅

현재 소스는 중국어 간체 번역을 APK에 직접 컴파일합니다. 설치 후 언어 전환을 지원하지 않으며 별도로 가져올 수 있는 언어 팩도 아닙니다. 다른 언어로 포팅하려면 먼저 저장소를 Fork하고 `lang/ja-JP` 또는 `lang/de-DE`처럼 언어별 브랜치를 만드십시오.

1. 영어 원문, 리소스 항목 이름, 일치 조건, 정규식은 그대로 두고 대상 언어 번역만 바꿉니다. 주요 진입점은 다음과 같습니다.

   | 파일 | 역할 |
   | --- | --- |
   | [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) | Android 리소스 항목 이름으로 일치시키는 텍스트 및 형식 템플릿 |
   | [`TranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/TranslationDictionary.java) | 영어 원문에서 번역문으로의 완전 일치 매핑 |
   | [`SettingsArrayTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/SettingsArrayTranslation.java) | 설정 화면의 배열 옵션 |
   | [`DynamicLabelTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/DynamicLabelTranslation.java) | 항공편, 호출 부호, 항공기 유형 등의 동적 레이블 |
   | [`FlightDetailViewTextTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/FlightDetailViewTextTranslation.java) | 항공편 상세 정보의 범위가 제한된 동적 텍스트 |
   | [`Fr24LocalizationModule.java`](app/src/main/java/io/github/fr24zh/localizer/Fr24LocalizationModule.java) | 지도 접근성 설명 Hook |
   | [`HookTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java) | 복수형, 수량, 특수 형식 텍스트 및 템플릿 안전 검사 |

2. [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java)에서 형식 지정에 사용되는 `Locale.SIMPLIFIED_CHINESE`를 대상 언어 로캘로 바꿉니다. 테스트 코드의 같은 로캘도 함께 수정해야 합니다. 저장소 전체를 기계적으로 치환해서는 안 됩니다. 대상 언어의 복수형, 어순, 날짜 형식에는 별도 구현이 필요할 수 있습니다.
3. `%s`, `%d`, `%1$d`, `%%` 같은 형식 자리표시자의 개수, 유형, 위치 인덱스를 모두 유지합니다. 항공편 번호, 공항 코드, 등록 번호, 시간 및 기타 동적 값은 번역문에 하드코딩하지 말고 원래 인수에서 계속 받아야 합니다.
4. [`strings.xml`](app/src/main/res/values/strings.xml)과 [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml)에서 모듈 표시 이름과 설명을 바꿉니다. 중국어 빌드와 동시에 설치하려면 [`app/build.gradle.kts`](app/build.gradle.kts)에서 다른 `applicationId`도 사용해야 합니다. 대상 앱 패키지 `com.flightradar24free`는 변경하지 마십시오. Java 패키지도 함께 바꾸는 경우에만 `namespace`, 소스의 `package` 선언, [`assets/xposed_init`](app/src/main/assets/xposed_init)의 진입 클래스 이름을 같이 수정합니다.
5. 해당 `app/src/test` 테스트의 예상값을 수정하고 위에 설명된 단위 테스트, APK 빌드, `verify-apk.py` 검증을 실행합니다. 그런 다음 실기기에서 최소한 지도, 검색, 항공편 상세 정보, 공항 상세 정보, 설정, 복수형, 형식 지정 텍스트를 확인하십시오.
6. Pull Request에는 대상 언어 및 로캘 코드, 테스트한 FR24/Android/프레임워크 버전, 아직 번역되지 않은 화면을 명시하십시오. Flightradar24 원본 APK, 디컴파일 산출물, 전체 기기 로그 또는 키를 커밋하지 마십시오.

권장 포팅 절차: `Fork → lang/<로캘 코드> 브랜치 → 번역 → 자동화 테스트 → 실기기 검증 → Pull Request`

## 알려진 범위 및 피드백

- 공항, 도시, 항공사 고유명, 항공편 번호, 등록 번호, 단위 및 원본 METAR/TAF 텍스트는 변경하지 않습니다.
- `FREE`, `LIVE`처럼 이미지에 포함된 텍스트는 변경하지 않습니다. 현재 단계에서는 원본 이미지를 교체하거나 배포하지 않습니다.
- 지도 타일, 웹 페이지, 결제 SDK, Android/OEM 시스템 UI는 네이티브 텍스트 적용 범위에 포함되지 않습니다.
- 서버 연결 오류 메시지 3개에는 번역을 추가했지만 네트워크가 없는 상태에서 앱을 실행하는 실기기 검증이 아직 필요합니다. 이전의 오프라인 안내만으로 서버 장애를 증명할 수는 없습니다.
- 미번역 텍스트를 신고할 때는 FR24, Android, 프레임워크 버전, 화면 경로, 원문을 포함하십시오. 스크린샷의 개인 정보는 가리고 기기 일련번호, 원본 APK, 전체 로그 또는 키는 업로드하지 마십시오.
- 모듈은 프레임워크에 Hook 설치 성공 또는 실패 정보만 기록하며 리소스별 이름, 유형, 일치 상태 또는 본문은 기록하지 않습니다. 프레임워크 자체의 권한이 강력하므로 신뢰할 수 있는 모듈만 설치하십시오.

## 고지 사항

이 프로젝트는 Flightradar24 AB와 관련이 없으며 해당 회사의 승인을 받지 않았습니다. Flightradar24 이름과 관련 상표는 각 권리자에게 귀속됩니다. 사용자가 제어할 권한이 있는 기기와 앱 사본에서만 테스트하고 적용되는 서비스 약관 및 법률을 준수하십시오.

이 프로젝트의 자체 코드는 MIT License로 제공됩니다.
