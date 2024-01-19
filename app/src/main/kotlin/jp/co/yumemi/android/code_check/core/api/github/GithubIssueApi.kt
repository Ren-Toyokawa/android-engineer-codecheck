package jp.co.yumemi.android.code_check.core.api.github

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import jp.co.yumemi.android.code_check.core.data.model.Issue
import jp.co.yumemi.android.code_check.core.data.model.IssueResponse
import javax.inject.Inject

class GithubIssueApi @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun getIssueList(owner: String, repo: String): List<Issue> {
        val response: IssueResponse =
            httpClient.get("https://api.github.com/repos/$owner/$repo/issues") {
                header("Accept", "application/vnd.github.v3+json")
            }
        return response.items
    }
}