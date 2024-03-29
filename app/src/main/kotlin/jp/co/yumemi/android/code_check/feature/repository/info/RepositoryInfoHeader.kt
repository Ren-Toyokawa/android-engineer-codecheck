package jp.co.yumemi.android.code_check.feature.repository.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.core.data.fake.dummySearchResults
import jp.co.yumemi.android.code_check.core.designsystem.preview.MultiThemePreviews
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import jp.co.yumemi.android.code_check.core.ui.component.toKString

@Composable
fun RepositoryInfoHeader(
    repositorySummary: GithubRepositorySummary,
    onTapIssue: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RepositoryNameAndIcon(
            repositorySummary = repositorySummary
        )

        RepositoryBasicData(
            repositorySummary = repositorySummary,
            onTapIssue = onTapIssue,
        )

        RepositoryWrittenLanguage(
            language = repositorySummary.language
        )
    }
}

@Composable
fun RepositoryNameAndIcon(
    repositorySummary: GithubRepositorySummary
) {
    val painter = rememberImagePainter(data = repositorySummary.ownerIconUrl)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(MaterialTheme.shapes.small)
        )

        Text(
            text = repositorySummary.fullName,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun RepositoryBasicData(
    repositorySummary: GithubRepositorySummary,
    onTapIssue: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 右詰めにするためにSpacerを使用する
        Spacer(modifier = Modifier.weight(1f))
        IconWithCount(
            iconPainterResource = painterResource(id = R.drawable.star),
            count = repositorySummary.stargazersCount
        )

        IconWithCount(
            iconPainterResource = painterResource(id = R.drawable.repo_forked),
            count = repositorySummary.forksCount
        )

        IconWithCount(
            iconPainterResource = painterResource(id = R.drawable.eye),
            count = repositorySummary.watchersCount
        )

        IconWithCount(
            modifier = Modifier
                .clickable {
                    onTapIssue()
                }
                .testTag("IssueButton"),
            iconPainterResource = painterResource(id = R.drawable.issue_opened),
            count = repositorySummary.openIssuesCount
        )
    }
}

@Composable
fun IconWithCount(
    modifier: Modifier = Modifier,
    iconPainterResource: Painter,
    count: Long
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = iconPainterResource,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            text = count.toKString(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun RepositoryWrittenLanguage(
    language: String?
) {
    if (language !== null) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(
                id = R.string.written_language,
                language
            ),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
    } else {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.language_not_specified),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
    }
}

@MultiThemePreviews
@Composable
fun RepositoryInfoHeaderPreview() {
    CodeCheckAppTheme {
        RepositoryInfoHeader(
            repositorySummary = dummySearchResults[0],
            onTapIssue = {}
        )
    }
}