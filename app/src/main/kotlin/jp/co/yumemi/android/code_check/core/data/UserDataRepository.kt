package jp.co.yumemi.android.code_check.core.data

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface UserDataRepository {
    val latestSearchDate: Flow<Date?>

    suspend fun saveLastSearchDate(date: Date)
}