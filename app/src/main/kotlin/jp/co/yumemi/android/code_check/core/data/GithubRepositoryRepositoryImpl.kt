package jp.co.yumemi.android.code_check.core.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import jp.co.yumemi.android.code_check.core.data.model.RepositorySearchResponse
import jp.co.yumemi.android.code_check.core.data.model.toExternalModel
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import jp.co.yumemi.android.code_check.core.api.github.GithubSearchApi
import javax.inject.Inject

/**
 * Githubのレポジトリにまつわるデータを取得する
 */
class GithubRepositoryRepositoryImpl @Inject constructor(
    private val githubSearchApi: GithubSearchApi,
): GithubRepositoryRepository {
    override suspend fun searchRepository(query: String): List<GithubRepositorySummary> {
        val response: RepositorySearchResponse = githubSearchApi.searchRepository(query)
        return response.items.map { it.toExternalModel() }
    }
}