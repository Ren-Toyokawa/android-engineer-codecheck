package jp.co.yumemi.android.code_check.core.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import jp.co.yumemi.android.code_check.core.data.model.RepositorySearchResponse
import jp.co.yumemi.android.code_check.core.data.model.toExternalModel
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import javax.inject.Inject

/**
 * Githubのレポジトリにまつわるデータを取得する
 */
class GithubRepositoryRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
): GithubRepositoryRepository {
    override suspend fun searchRepository(query: String): List<GithubRepositorySummary> {
        val response: RepositorySearchResponse =
            httpClient.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", query)
        }

        return response.items.map { it.toExternalModel() }
    }
}