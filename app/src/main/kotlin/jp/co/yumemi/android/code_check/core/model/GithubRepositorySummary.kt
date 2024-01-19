package jp.co.yumemi.android.code_check.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Githubのリポジトリ概要
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

val dummySearchResults = listOf(
    GithubRepositorySummary(
        name = "test",
        ownerIconUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        language = "Kotlin",
        stargazersCount = 999,
        watchersCount = 20000,
        forksCount = 2900,
        openIssuesCount = 20900,
    ),
    GithubRepositorySummary(
        name = "test2",
        ownerIconUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        language = "Kotlin",
        stargazersCount = 1,
        watchersCount = 1,
        forksCount = 1,
        openIssuesCount = 1,
    ),
    GithubRepositorySummary(
        name = "test3",
        ownerIconUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        language = "Kotlin",
        stargazersCount = 1,
        watchersCount = 1,
        forksCount = 1,
        openIssuesCount = 1,
    ),
)