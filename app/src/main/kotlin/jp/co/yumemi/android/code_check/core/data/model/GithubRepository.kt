package jp.co.yumemi.android.code_check.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Githubのリポジトリ情報
 */
@Serializable
data class GithubRepository(
    @SerialName("id")
    val id: Int,
    @SerialName("full_name")
    val name: String,
    @SerialName("owner")
    val owner: Owner,
    @SerialName("description")
    val description: String?,
    @SerialName("language")
    val language: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Long,
    @SerialName("watchers_count")
    val watchersCount: Long,
    @SerialName("forks_count")
    val forksCount: Long,
    @SerialName("open_issues_count")
    val openIssuesCount: Long,
)

@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String,
)
