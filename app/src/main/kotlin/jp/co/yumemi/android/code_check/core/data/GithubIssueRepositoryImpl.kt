package jp.co.yumemi.android.code_check.core.data

import jp.co.yumemi.android.code_check.core.api.github.GithubIssueApi
import jp.co.yumemi.android.code_check.core.api.github.model.asExternalModel
import jp.co.yumemi.android.code_check.core.model.GithubIssue
import javax.inject.Inject

class GithubIssueRepositoryImpl @Inject constructor(
    private val githubIssueApi: GithubIssueApi,
) : GithubIssueRepository {
    override suspend fun fetchIssues(owner: String, repo: String): List<GithubIssue> {
        val issues = githubIssueApi.getIssueList(owner, repo)
        return issues.map { it.asExternalModel() }
    }
}