package jp.co.yumemi.android.code_check.feature.repository.search

import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
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

    @Before
    fun setUp() {
        testGithubRepositoryRepository = TestGithubRepositoryRepository()
        testUserDataRepository = TestUserDataRepository()
        viewModel = RepositorySearchViewModel(
            testGithubRepositoryRepository,
            testUserDataRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `executeSearchRepository_正常な検索結果が返却される`() = runTest {
        testGithubRepositoryRepository.testSearchResult = testSearchResults

        // 検索実行
        viewModel.executeSearchRepository("test")

        // 検索結果の検証
        assertEquals(
            testSearchResults,
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

private val testSearchResults = listOf(
    GithubRepositorySummary(
        name = "test",
        ownerIconUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        language = "Kotlin",
        stargazersCount = 1,
        watchersCount = 1,
        forksCount = 1,
        openIssuesCount = 1,
    ),
    GithubRepositorySummary(
        name = "test2",
        ownerIconUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        language = "Kotlin",
        stargazersCount = 1,
        watchersCount = 1,
        forksCount = 1,
        openIssuesCount = 1,
    ),
    GithubRepositorySummary(
        name = "test3",
        ownerIconUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        language = "Kotlin",
        stargazersCount = 1,
        watchersCount = 1,
        forksCount = 1,
        openIssuesCount = 1,
    ),
)