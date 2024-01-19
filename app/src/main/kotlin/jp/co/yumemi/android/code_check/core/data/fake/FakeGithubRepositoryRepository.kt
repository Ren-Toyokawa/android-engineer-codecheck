package jp.co.yumemi.android.code_check.core.data.fake

import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepository
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import javax.inject.Inject

class FakeGithubRepositoryRepository @Inject constructor(): GithubRepositoryRepository {
    override suspend fun searchRepository(query: String): List<GithubRepositorySummary> {
        return dummySearchResults
    }
}

val dummySearchResults = listOf(
    GithubRepositorySummary(
        name = "dummy1",
        fullName = "dummy1",
        ownerIconUrl = "https://dummy1.com",
        ownerName = "dummy1",
        description = "dummy1",
        language = "Kotlin",
        stargazersCount = 100,
        watchersCount = 100,
        forksCount = 100,
        openIssuesCount = 100,
    ),
    GithubRepositorySummary(
        name = "dummy2",
        fullName = "dummy2",
        ownerIconUrl = "https://dummy2.com",
        ownerName = "dummy2",
        description = null,
        language = "Java",
        stargazersCount = 200,
        watchersCount = 200,
        forksCount = 200,
        openIssuesCount = 200,
    ),
    GithubRepositorySummary(
        name = "dummy3",
        fullName = "dummy3",
        ownerIconUrl = "https://dummy3.com",
        ownerName = "dummy3",
        description = "dummy3",
        language = "Swift",
        stargazersCount = 300,
        watchersCount = 300,
        forksCount = 300,
        openIssuesCount = 300,
    ),
    GithubRepositorySummary(
        name = "dummy4",
        fullName = "dummy4",
        ownerIconUrl = "https://dummy4.com",
        ownerName = "dummy4",
        description = null,
        language = "Objective-C",
        stargazersCount = 400,
        watchersCount = 400,
        forksCount = 400,
        openIssuesCount = 400,
    ),
    GithubRepositorySummary(
        name = "dummy5",
        fullName = "dummy5",
        ownerIconUrl = "https://dummy5.com",
        ownerName = "dummy5",
        description = "dummy5",
        language = "C++",
        stargazersCount = 500,
        watchersCount = 500,
        forksCount = 500,
        openIssuesCount = 500,
    ),
    GithubRepositorySummary(
        name = "dummy6",
        fullName = "dummy6",
        ownerIconUrl = "https://dummy6.com",
        ownerName = "dummy6",
        description = null,
        language = "C#",
        stargazersCount = 600,
        watchersCount = 600,
        forksCount = 600,
        openIssuesCount = 600,
    ),
    GithubRepositorySummary(
        name = "dummy7",
        fullName = "dummy7",
        ownerIconUrl = "https://dummy7.com",
        ownerName = "dummy7",
        description = "dummy7",
        language = "JavaScript",
        stargazersCount = 700,
        watchersCount = 700,
        forksCount = 700,
        openIssuesCount = 700,
    ),
    GithubRepositorySummary(
        name = "dummy8",
        fullName = "dummy8",
        ownerIconUrl = "https://dummy8.com",
        ownerName = "dummy8",
        description = null,
        language = "Go",
        stargazersCount = 800,
        watchersCount = 800,
        forksCount = 800,
        openIssuesCount = 800,
    )
)