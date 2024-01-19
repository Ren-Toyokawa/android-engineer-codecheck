# 株式会社ゆめみ Android エンジニアコードチェック課題

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app_light.gif" width="320">

### 環境
- IDE：Android Studio Jellyfish | 2023.3.1 Canary 2
- Kotlin：1.6.21
- Java：17
- Gradle：8.0
- minSdk：23
- targetSdk：34

※ ライブラリの利用はオープンソースのものに限ります。
※ 環境は適宜更新してください。

### 動作
1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示
4. Issue 数をタップしたら、該当リポジトリの Issue 一覧を表示(`repo/{owner}/{repo}/issues`)

## ディレクトリ構造
### src/main
#### src/main/java/com/yumemi/android/code_check
##### core.api
GithubのAPIクライアントを提供するパッケージです。
対象となるデータの種別ごとにApiクラスを切っています

##### core.data
Repositoryクラスをを提供するパッケージです。
core.api, core.datastoreにに依存しています

##### core.datastore
Jetpack Datastoreのデータソースを提供するパッケージです。

##### core.designsystem
Compose のテーマを格納しています。
内容はNow in Androidのものと同じです

##### core.model
feature, repositoryで使用するデータクラスを提供するパッケージです。

##### core.network
Github APIクライアントで利用するHttpClientを提供するパッケージです。
HttpClientはKtorを使用しています

##### core.testing
Android Testで使用する TestRunner を提供するパッケージです。

##### core.ui
共通で使用するUIコンポーネントやアノテーションを提供します

##### feature.repository
リポジトリの検索機能を提供するパッケージです。
レポジトリ検索、レポジトリ情報、レポジトリのIssue一覧を提供します

### src/test
Unit Testを格納しています
基本的にはViewModelのテストと、そこで使用するクラスを配置しています

#### testdouble
ユニットテストで使用するテストダブルを格納しています  
Mockを使用しても良かったのですが、Android公式でもFakeを使用することを推奨しているため、Fakeを使用しています

#### util
テストで使用するユーティリティクラスを格納しています

### src/androidTest
Instrumented Testとそこで使用するクラスを格納しています

#### di
Insutrumented Testで使用するDIを格納しています
この配下にある ModuleクラスがandroidTest時はDIされます

#### feature
Instrumented Testを格納しています
