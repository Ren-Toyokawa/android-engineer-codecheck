package jp.co.yumemi.android.code_check.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Githubのリポジトリ情報
 */
@Parcelize
data class GithubRepositorySummary(
    val name: String,
    val ownerIconUrl: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable