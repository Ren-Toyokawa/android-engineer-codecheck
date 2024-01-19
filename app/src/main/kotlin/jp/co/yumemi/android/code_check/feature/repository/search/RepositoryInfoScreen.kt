package jp.co.yumemi.android.code_check.feature.repository.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryInfoScreen(
    modifier: Modifier = Modifier,
    repositorySummary: GithubRepositorySummary
) {
    val scrollState = rememberScrollState()

    Scaffold {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(it)
                .padding(8.dp),
        ) {
            RepositoryInfoHeader(
                repositorySummary = repositorySummary
            )
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