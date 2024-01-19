package jp.co.yumemi.android.code_check.core.api.github

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import jp.co.yumemi.android.code_check.core.api.github.model.RepositorySearchResponse
import javax.inject.Inject

class GithubSearchApi
    @Inject
    constructor(
        private val httpClient: HttpClient,
    ) {
        /**
         * Githubのレポジトリを検索する
         * エンドポイント: search/repositories
         *
         * @param query 検索文字列, request parameter: q
         */
        suspend fun searchRepository(query: String): RepositorySearchResponse {
            val response: RepositorySearchResponse =
                httpClient.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", query)
                }

            return response
        }
    }
