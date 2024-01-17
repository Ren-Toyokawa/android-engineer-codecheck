package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class CodeCheckAppPreferencesDatasource @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    companion object {
        private val LATEST_SEARCH_DATE = longPreferencesKey("latest_search_date")
    }

    suspend fun saveLastSearchDate(date: Date) {
        dataStore.edit { settings ->
            settings[LATEST_SEARCH_DATE] = date.time
        }
    }

    val lastSearchDate = dataStore.data.map { settings ->
        val latestSearchDateTime = settings[LATEST_SEARCH_DATE]
        if (latestSearchDateTime != null) {
            Date(latestSearchDateTime)
        } else {
            null
        }
    }
}