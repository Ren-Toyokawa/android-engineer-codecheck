package jp.co.yumemi.android.code_check.feature.repository.search

import androidx.lifecycle.SavedStateHandle
import jp.co.yumemi.android.code_check.core.data.fake.dummySearchResults
import jp.co.yumemi.android.code_check.testdouble.data.repository.TestGithubRepositoryRepository
import jp.co.yumemi.android.code_check.testdouble.data.repository.TestUserDataRepository
import jp.co.yumemi.android.code_check.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RepositorySearchViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var testGithubRepositoryRepository: TestGithubRepositoryRepository
    private lateinit var testUserDataRepository: TestUserDataRepository
    private lateinit var viewModel: RepositorySearchViewModel
    private val savedStateHandle = SavedStateHandle()

    @Before
    fun setUp() {
        testGithubRepositoryRepository = TestGithubRepositoryRepository()
        testUserDataRepository = TestUserDataRepository()
        viewModel = RepositorySearchViewModel(
            testGithubRepositoryRepository,
            testUserDataRepository,
            savedStateHandle
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `executeSearchRepository_正常な検索結果が返却される`() = runTest {
        testGithubRepositoryRepository.testSearchResult = dummySearchResults

        // 検索実行
        viewModel.executeSearchRepository("test")

        // 検索結果の検証
        assertEquals(
            dummySearchResults,
            viewModel.searchResults.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `検索実行時にExceptionが発生した場合 errorStateがCantFetchRepositoryInfoとなること`() = runTest {
        testGithubRepositoryRepository.shouldThrowException = Exception("test")

        // 検索実行
        viewModel.executeSearchRepository("test")

        // エラー状態の検証
        assertEquals(
            ErrorState.CantFetchRepositoryInfo,
            viewModel.errorState.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `検索実行時にSerializationExceptionが発生した場合 errorStateがCantFetchRepositoryInfoとなること`() = runTest {
        testGithubRepositoryRepository.shouldThrowException = SerializationException("test")

        // 検索実行
        viewModel.executeSearchRepository("test")

        // エラー状態の検証
        assertEquals(
            ErrorState.CantFetchRepositoryInfo,
            viewModel.errorState.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `検索実行時にUnknownHostExceptionが発生した場合 errorStateがNetworkErrorとなること`() = runTest {
        testGithubRepositoryRepository.shouldThrowException = java.net.UnknownHostException("test")

        // 検索実行
        viewModel.executeSearchRepository("test")

        // エラー状態の検証
        assertEquals(
            ErrorState.NetworkError,
            viewModel.errorState.value
        )
    }
}

