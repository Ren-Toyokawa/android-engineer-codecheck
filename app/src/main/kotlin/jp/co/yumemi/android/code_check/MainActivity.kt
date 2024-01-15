/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.appcompat.app.AppCompatActivity
import java.util.Date

/**
 * 本アプリのMainActivity
 * このアクティビティが本アプリのエントリーポイントとなる
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    companion object {
        // 最後に検索した日時
        // 検索を実行した際に、この変数に現在時刻を代入する
        lateinit var lastSearchDate: Date
    }
}
