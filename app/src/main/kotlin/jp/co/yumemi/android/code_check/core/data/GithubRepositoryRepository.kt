package jp.co.yumemi.android.code_check.core.data

import jp.co.yumemi.android.code_check.core.data.model.GithubRepository

interface GithubRepositoryRepository {
    suspend fun getGithubRepositoryList(): List<GithubRepository>
}