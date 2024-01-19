package jp.co.yumemi.android.code_check.feature.repository.search

import androidx.lifecycle.SavedStateHandle
import jp.co.yumemi.android.code_check.core.data.fake.dummyIssues
import jp.co.yumemi.android.code_check.feature.repository.issue.RepositoryIssueViewModel
import jp.co.yumemi.android.code_check.testdouble.data.repository.TestGithubIssueRepository
import jp.co.yumemi.android.code_check.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryIssueViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var testGithubIssueRepository: TestGithubIssueRepository
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
    private lateinit var viewModel: RepositoryIssueViewModel

    @Before
    fun setUp() {
        testGithubIssueRepository = TestGithubIssueRepository()
        savedStateHandle["repositoryName"] = "test"
        savedStateHandle["ownerName"] = "test"
    }

    @Test
    fun `fetchIssues should return issues`() = runTest {
        testGithubIssueRepository.testSearchResult = dummyIssues
        viewModel = RepositoryIssueViewModel(
            testGithubIssueRepository,
            savedStateHandle
        )

        assertEquals(dummyIssues, viewModel.issues.value)
    }
}