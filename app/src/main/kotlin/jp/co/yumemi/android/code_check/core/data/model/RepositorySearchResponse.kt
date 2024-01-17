package jp.co.yumemi.android.code_check.core.data.model

import kotlinx.serialization.Serializable

/**
 * リポジトリ検索APIのレスポンス
 */
@Serializable
data class RepositorySearchResponse(
    val items: List<GithubRepository>
)

