package jp.co.yumemi.android.code_check.core.data

import jp.co.yumemi.android.code_check.core.model.GithubIssue

interface GithubIssueRepository {
    suspend fun fetchIssues(owner: String, repo: String): List<GithubIssue>
}