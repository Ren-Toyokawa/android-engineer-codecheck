package jp.co.yumemi.android.code_check.feature.repository.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.core.designsystem.preview.MultiThemePreviews
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import jp.co.yumemi.android.code_check.core.model.dummySearchResults

@Composable
fun RepositoryInfoRoute(
    repositorySummary: GithubRepositorySummary,
) {
    RepositoryInfoScreen(
        repositorySummary = repositorySummary
    )
}

@Composable
fun RepositoryInfoScreen(
    modifier: Modifier = Modifier,
    repositorySummary: GithubRepositorySummary
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth(),
    ) {
        RepositoryInfoHeader(
            repositorySummary = repositorySummary
        )
    }
}

@Composable
fun RepositoryInfoHeader(
    repositorySummary: GithubRepositorySummary
) {
    val painter = rememberImagePainter(data = repositorySummary.ownerIconUrl)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: 読み込み中の時はPlaceholderを表示する
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Text(
                text = repositorySummary.name,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = stringResource(id = R.string.star_count_text, repositorySummary.stargazersCount))
            Text(text = stringResource(id = R.string.fork_count_text, repositorySummary.forksCount))
            Text(text = stringResource(id = R.string.watcher_count_text, repositorySummary.watchersCount))
            Text(text = stringResource(id = R.string.open_issue_count_text, repositorySummary.openIssuesCount))
            if (repositorySummary.language !== null) {
                Text(text = stringResource(id = R.string.written_language, repositorySummary.language))
            } else {
                Text(text = stringResource(id = R.string.language_not_specified))
            }
        }
    }
}

@MultiThemePreviews
@Composable
fun RepositoryInfoScreenPreview() {
    CodeCheckAppTheme {
        RepositoryInfoScreen(
            repositorySummary = dummySearchResults[0]
        )
    }
}