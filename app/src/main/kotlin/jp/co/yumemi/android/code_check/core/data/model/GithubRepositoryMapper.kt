package jp.co.yumemi.android.code_check.core.data.model

import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary

fun GithubRepository.toExternalModel(): GithubRepositorySummary {
    return GithubRepositorySummary(
        name = name,
        fullName = fullName,
        ownerIconUrl = owner.avatarUrl,
        ownerName = owner.name,
        language = language,
        description = description,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount,
    )
}
