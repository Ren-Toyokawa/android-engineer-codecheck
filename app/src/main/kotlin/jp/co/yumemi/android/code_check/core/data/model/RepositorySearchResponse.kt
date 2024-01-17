package jp.co.yumemi.android.code_check.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * リポジトリ検索APIのレスポンス
 */
@Parcelize
@Serializable
data class RepositorySearchResponse(
    val items: List<GithubRepository>
) : Parcelable


