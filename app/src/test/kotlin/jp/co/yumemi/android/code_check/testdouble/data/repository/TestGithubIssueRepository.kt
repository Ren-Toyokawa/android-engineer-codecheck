package jp.co.yumemi.android.code_check.testdouble.data.repository

import jp.co.yumemi.android.code_check.core.data.GithubIssueRepository
import jp.co.yumemi.android.code_check.core.model.GithubIssue

class TestGithubIssueRepository : GithubIssueRepository {
    var testSearchResult: List<GithubIssue> = emptyList()
    var shouldThrowException: Exception? = null

    override suspend fun fetchIssues(owner: String, repo: String): List<GithubIssue> {
        shouldThrowException?.let { throw it }
        return testSearchResult
    }
}