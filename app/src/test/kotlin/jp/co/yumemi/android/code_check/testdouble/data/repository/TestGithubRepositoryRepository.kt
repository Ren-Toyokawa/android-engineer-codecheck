package jp.co.yumemi.android.code_check.testdouble.data.repository

import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepository
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary

class TestGithubRepositoryRepository : GithubRepositoryRepository {
    var testSearchResult: List<GithubRepositorySummary> = emptyList()
    var shouldThrowException: Exception? = null

    override suspend fun searchRepository(query: String): List<GithubRepositorySummary> {
        shouldThrowException?.let { throw it }
        return testSearchResult
    }
}