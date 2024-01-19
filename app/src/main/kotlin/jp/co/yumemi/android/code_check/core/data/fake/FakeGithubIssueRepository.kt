package jp.co.yumemi.android.code_check.core.data.fake

import jp.co.yumemi.android.code_check.core.data.GithubIssueRepository
import jp.co.yumemi.android.code_check.core.model.GithubIssue
import javax.inject.Inject

class FakeGithubIssueRepository @Inject constructor(): GithubIssueRepository {
    override suspend fun fetchIssues(owner: String, repo: String): List<GithubIssue> {
        return dummyIssues
    }
}

val dummyIssues = listOf(
    GithubIssue(
        number = 1,
        title = "test",
    ),
    GithubIssue(
        number = 2,
        title = "test2",
    ),
    GithubIssue(
        number = 3,
        title = "test3",
    ),
)