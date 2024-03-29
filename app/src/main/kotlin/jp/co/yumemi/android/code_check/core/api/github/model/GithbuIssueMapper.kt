package jp.co.yumemi.android.code_check.core.api.github.model

import jp.co.yumemi.android.code_check.core.model.GithubIssue

fun Issue.asExternalModel(): GithubIssue {
    return GithubIssue(
        number = number,
        title = title,
    )
}