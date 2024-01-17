package jp.co.yumemi.android.code_check.core.data

import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary

interface GithubRepositoryRepository {
    suspend fun searchRepository(query: String): List<GithubRepositorySummary>
}
