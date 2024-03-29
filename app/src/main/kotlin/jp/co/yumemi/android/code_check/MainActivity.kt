/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient
import javax.inject.Inject

/**
 * 本アプリのMainActivity
 * このアクティビティが本アプリのエントリーポイントとなる
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var client: HttpClient

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
