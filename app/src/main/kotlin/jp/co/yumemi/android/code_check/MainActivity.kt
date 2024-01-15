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
        lateinit var lastSearchDate: Date
    }
}
