package jp.co.yumemi.android.code_check.core.data

import jp.co.yumemi.android.code_check.core.datastore.CodeCheckAppPreferencesDatasource
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class OfflineUserDataRepository
    @Inject
    constructor(
        private val codeCheckAppPreferencesDatasource: CodeCheckAppPreferencesDatasource,
    ) : UserDataRepository {
        override val latestSearchDate: Flow<Date?>
            get() = codeCheckAppPreferencesDatasource.lastSearchDate

        override suspend fun saveLastSearchDate(date: Date) {
            codeCheckAppPreferencesDatasource.saveLastSearchDate(date)
        }
    }
