package jp.co.yumemi.android.code_check.core.data.fake

import jp.co.yumemi.android.code_check.core.data.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

class FakeUserDataRepository @Inject constructor(): UserDataRepository {
    private val _lastSearchDate: MutableStateFlow<Date?> = MutableStateFlow(null)
    override val latestSearchDate: Flow<Date?>
        get() = _lastSearchDate.asStateFlow()

    override suspend fun saveLastSearchDate(date: Date) {
        _lastSearchDate.value = date
    }
}