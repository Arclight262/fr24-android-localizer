# FR24 簡体字中国語モジュール

[简体中文](README.md) | [English](README.en.md) | [繁體中文](README.zh-TW.md) | **日本語** | [한국어](README.ko.md)

これは Flightradar24 Android アプリ向けの非公式な簡体字中国語ローカライズ用テストモジュールです。標準の Legacy Xposed API 82 を使用し、監査済みのリソース名と文脈に基づいて対象プロセス内のテキストを翻訳します。テキストおよびコンテンツ説明には対象を限定した Hook を使用し、一致しない場合や処理中に例外が発生した場合は原文を維持します。公式アプリのパッケージは変更しません。

現在のソースには、1,217 件のリソース名マッピングと 65 件の完全一致テキストマッピングに加え、動的テキスト、配列、複数形を対象とした限定的な処理が含まれます。地図、検索、注目度の高いフライト、運航障害、フライト詳細、空港詳細などのネイティブ画面を対象としています。**現在もテストプロジェクトであり、完全なローカライズではありません。マッピング数は画面単位の完成度を示すものではありません。** 公開ソースとリリースパッケージには、公式 APK、逆コンパイルしたファイル、抽出した画像を含めません。

## 互換性

- 対象アプリのパッケージ名：`com.flightradar24free`
- 最小 Android バージョン：8.1（API 27）
- 直近の実機確認環境：Android 16、SukiSU Ultra、NeoZygisk、Vector/LSPosed、Flightradar24 11.9.0（110900000）
- インターフェース：標準 Legacy Xposed API 82。Vector、NeoZygisk、特定の LSPosed フォークの非公開 API は使用しません

上記以外のフレームワークのフォーク、Android バージョン、Flightradar24 バージョンでは実機検証が完了していないため、互換性は保証されません。

## インストール

1. ソースからテスト APK をビルドするか、GitHub Actions のビルド成功後にワークフロー成果物をダウンロードします。現在、安定版は公開されていません。これは通常の Android APK です。Magisk/SukiSU のモジュール画面からフラッシュしないでください。
2. Vector または互換性のある LSPosed マネージャーを開き、「モジュール」に移動します。
3. `FR24 简体中文测试模块` という名前のモジュールを有効にします。
4. モジュールのスコープでは Flightradar24 のみを選択します。パッケージ名は `com.flightradar24free` です。
5. Flightradar24 を強制停止し、再度起動します。

このモジュールにはランチャーアイコンや設定画面がありません。これは正常な動作です。

テスト APK は Android のデバッグ証明書で署名されています。ローカルと CI では証明書が異なる場合があり、CI の実行ごとに同じ証明書が使われる保証もないため、上書きインストールできないことがあります。署名が競合した場合も FR24 はアンインストールしないでください。ローカライズモジュールだけをアンインストールして新版を導入し、スコープを再確認してください。安定版を公開する前に固定の署名方式を決定し、秘密鍵はリポジトリへコミットしないでください。

## 動作確認

最初の確認では、地図、検索、フライト詳細、空港詳細、設定などの一般的な画面が中国語になっているか確認します。フレームワークのログにある次の項目は Hook の導入確認に役立ちますが、すべての画面が翻訳されたことを示すものではありません。

```text
FR24ZH: resource-hook install method=getText
FR24ZH: text hook installed
FR24ZH: content-description hook installed
```

まったく翻訳されない場合は、まずモジュールのスコープを確認し、もう一度強制停止してください。ログ上では Hook が導入済みでも英語のままの場合、その Flightradar24 バージョンでは対象テキストがこれらの Android リソース API を通して提供されていないか、実際の英文が辞書と完全には一致していない可能性があります。その場合は、端末ログと画面上の原文を基に追加対応が必要です。

## アンインストールと復元

Vector/LSPosed で本モジュールのスコープから Flightradar24 を外すか、モジュール自体を無効にしてから、Flightradar24 を強制停止して再度起動します。この APK をアンインストールすることもできます。本モジュールは Flightradar24 のパッケージやユーザーデータを変更しないため、公式 APK を復元する必要はありません。

## セキュリティ上の境界

- Android マニフェストは、ネットワーク、ストレージ、オーバーレイ、ユーザー補助、Root、インストール済みアプリの照会に関する権限を要求または使用しません。Hook も Android のユーザー補助権限を要求、使用しません。
- Hook は `com.flightradar24free` と、コロンが付いたそのサブプロセスだけに導入されます。
- リソースコールバックは、監査済みのリソース項目名、文脈、制御された書式引数に基づいて翻訳します。一致しないテキスト、書式化の失敗、例外では原文を維持します。テキストおよび説明用 Hook は、無制限の一括置換ではなく、完全一致または用途を限定した規則を使用します。
- 便名、空港コード、機体登録記号、時刻、数値、不明なテキストは変更しません。
- サブスクリプション、支払い、整合性チェック、チート対策、Root 検出の回避機能は提供しません。

## ソースからのビルド

一般的なビルド環境には、JDK 17、Android SDK Platform 35、Build Tools 35.0.0、Python 3 が必要です。Gradle Wrapper は 8.9 を使用します。

```sh
# JAVA_HOME と ANDROID_HOME を設定してから実行
bash ./gradlew :app:testDebugUnitTest :app:assembleDebug
python3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

Windows では次を実行します。

```powershell
powershell -ExecutionPolicy Bypass -File scripts/bootstrap-android.ps1
powershell -ExecutionPolicy Bypass -File scripts/run-gradle.ps1 clean :app:testDebugUnitTest :app:assembleDebug
py -3 scripts/verify-apk.py app/build/outputs/apk/debug/app-debug.apk
```

`bootstrap-android.ps1` は、ビルドツールを Git の追跡対象外であるプロジェクト内の `.tools` ディレクトリへダウンロードし、システム全体へのインストールは行いません。ダウンロード元へアクセスできる必要があります。Python は別途インストールしてください。このブートストラップスクリプトは Python をインストールしません。

[ビルドワークフロー](.github/workflows/build.yml) は、単体テスト、APK のビルド、パッケージ検証を実行します。検証では、モジュールのエントリーポイント、Xposed メタデータ、権限宣言がないこと、コンパイル専用スタブがパッケージに含まれないことを確認します。これは完全なセキュリティ監査ではなく、実機テストの代わりにはなりません。最初の GitHub Actions ビルドは成功済みですが、以降のコミットについては毎回そのワークフロー結果を確認してください。

## 他の言語への移植

現在のソースは簡体字中国語の翻訳を APK に直接コンパイルします。インストール後の言語切り替えには対応せず、単独でインポートできる言語パックでもありません。他の言語へ移植する場合は、まずリポジトリを Fork し、`lang/ja-JP` や `lang/de-DE` のように言語ごとのブランチを作成してください。

1. 英語の原文、リソース項目名、照合条件、正規表現は変更せず、対象言語の訳文だけを置き換えます。主な変更箇所は次のとおりです。

   | ファイル | 内容 |
   | --- | --- |
   | [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) | Android リソース項目名で照合するテキストと書式テンプレート |
   | [`TranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/TranslationDictionary.java) | 英語原文と訳文の完全一致マッピング |
   | [`SettingsArrayTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/SettingsArrayTranslation.java) | 設定画面の配列選択肢 |
   | [`DynamicLabelTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/DynamicLabelTranslation.java) | フライト、コールサイン、機種などの動的ラベル |
   | [`FlightDetailViewTextTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/FlightDetailViewTextTranslation.java) | フライト詳細内の対象を限定した動的テキスト |
   | [`MapAccessibilityTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/MapAccessibilityTranslation.java) | 地図のユーザー補助用説明テキスト |
   | [`HookTranslation.java`](app/src/main/java/io/github/fr24zh/localizer/HookTranslation.java) | 複数形、数量、特殊な書式のテキストとテンプレートの安全性検査 |

2. [`ResourceTranslationDictionary.java`](app/src/main/java/io/github/fr24zh/localizer/ResourceTranslationDictionary.java) で書式化に使用している `Locale.SIMPLIFIED_CHINESE` を対象言語のロケールへ変更します。テストコード内の同じロケールも更新してください。リポジトリ全体を機械的に置換するだけでは不十分です。対象言語の複数形、語順、日付形式には個別の実装が必要になる場合があります。
3. `%s`、`%d`、`%1$d`、`%%` などの書式プレースホルダーは、数、型、位置インデックスを完全に維持してください。便名、空港コード、機体登録記号、時刻などの動的データは訳文へ直接記述せず、引き続き元の引数から受け取る必要があります。
4. [`strings.xml`](app/src/main/res/values/strings.xml) と [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml) で、モジュールの表示名と説明を変更します。中国語版と同時にインストールする場合は、[`app/build.gradle.kts`](app/build.gradle.kts) の `applicationId` も別の値にしてください。対象アプリのパッケージ名 `com.flightradar24free` は変更しないでください。Java パッケージ自体も変更する場合に限り、`namespace`、ソース内の `package` 宣言、[`assets/xposed_init`](app/src/main/assets/xposed_init) のエントリークラス名も合わせて変更します。
5. 対応する `app/src/test` の期待値を更新し、上記の単体テスト、APK ビルド、`verify-apk.py` による検証を実行します。その後、実機で少なくとも地図、検索、フライト詳細、空港詳細、設定、複数形、書式付きテキストを確認してください。
6. Pull Request には、対象言語とロケールコード、検証した FR24/Android/フレームワークのバージョン、未翻訳の画面を明記してください。Flightradar24 の公式 APK、逆コンパイルした成果物、完全な端末ログ、鍵をコミットしないでください。

推奨される移植手順：`Fork → lang/<ロケールコード> ブランチ → 翻訳 → 自動テスト → 実機確認 → Pull Request`

## 既知の範囲とフィードバック

- 空港、都市、航空会社の固有名詞、便名、機体登録記号、単位、METAR/TAF の原文は変更しません。
- `FREE`、`LIVE` など画像内のテキストは変更しません。現段階では公式画像の置換や配布を行いません。
- 地図タイル、Web ページ、決済 SDK、Android/OEM のシステム UI は、ネイティブテキストの対象外です。
- サーバー接続エラーの 3 件のメッセージは翻訳済みですが、ネットワーク未接続の状態でアプリを起動する実機検証が残っています。以前表示されたオフラインメッセージだけではサーバー障害とは判断できません。
- 未翻訳テキストを報告する場合は、FR24、Android、フレームワークの各バージョン、画面への経路、原文を添えてください。スクリーンショットでは個人情報を伏せ、端末のシリアル番号、公式 APK、完全なログ、鍵はアップロードしないでください。
- モジュールはフレームワークを通じて限定的な診断ログを書き込みます。リソース診断に記録されるのはリソース名、型、一致状態だけで、リソース本文は記録しません。フレームワーク自体は強い権限を持つため、信頼できるモジュールだけをインストールしてください。

## 免責事項

本プロジェクトは Flightradar24 AB と関係がなく、同社の承認も受けていません。Flightradar24 の名称および関連商標は各権利者に帰属します。自分が管理する権限を持つ端末とアプリのコピーでのみテストし、適用される利用規約と法律を遵守してください。

本プロジェクト独自のコードは MIT License で提供されます。
