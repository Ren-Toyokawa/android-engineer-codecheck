package jp.co.yumemi.android.code_check.core.api.github.model

import kotlinx.serialization.Serializable

/**
 * リポジトリ検索APIのレスポンス
 */
@Serializable
data class RepositorySearchResponse(
    val items: List<GithubRepository>,
)
