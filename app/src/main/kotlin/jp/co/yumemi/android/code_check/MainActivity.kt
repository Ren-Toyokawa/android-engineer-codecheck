/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.network.HttpClientSingleton.client
import java.util.Date

/**
 * 本アプリのMainActivity
 * このアクティビティが本アプリのエントリーポイントとなる
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    // FIXME: Jetpack Datastoreなどを使用した方が良い気がする。
    companion object {
        // 最後に検索した日時
        // 検索を実行した際に、この変数に現在時刻を代入する
        var lastSearchDate: Date? = null
    }

    /**
     * 下記のタイミングで呼び出される
     * 1. アプリケーションがユーザーによって終了される場合。
     * 2. システムによってリソースの解放が必要な場合。
     * 3. アクティビティのライフサイクルの一部として、設定変更（例: 画面の回転）や他のアクティビティへの遷移時。
     */
    override fun onDestroy() {
        super.onDestroy()

        // アプリケーションが終了する際に、HttpClientをクローズする
        // 画面の回転時もこの関数自体は呼ばれるため、isFinishingでユーザーがアプリを終了したかどうかを判定する
        if (isFinishing) {
            client.close()
        }
    }
}
