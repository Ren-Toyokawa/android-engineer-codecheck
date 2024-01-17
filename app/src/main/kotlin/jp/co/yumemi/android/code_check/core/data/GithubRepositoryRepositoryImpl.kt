package jp.co.yumemi.android.code_check.core.data

import io.ktor.client.request.get
import jp.co.yumemi.android.code_check.core.api.github.GithubSearchApi
import jp.co.yumemi.android.code_check.core.data.model.RepositorySearchResponse
import jp.co.yumemi.android.code_check.core.data.model.toExternalModel
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import javax.inject.Inject

/**
 * Githubのレポジトリにまつわるデータを取得する
 */
class GithubRepositoryRepositoryImpl
    @Inject
    constructor(
        private val githubSearchApi: GithubSearchApi,
    ) : GithubRepositoryRepository {
        /**
         * Githubのレポジトリを検索する
         *
         * @param query 検索文字列
         * @return リポジトリ概要のリスト
         */
        override suspend fun searchRepository(query: String): List<GithubRepositorySummary> {
            val response: RepositorySearchResponse = githubSearchApi.searchRepository(query)
            return response.items.map { it.toExternalModel() }
        }
    }
