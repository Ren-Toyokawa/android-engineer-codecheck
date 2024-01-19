package jp.co.yumemi.android.code_check.feature.repository.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.data.fake.dummySearchResults
import jp.co.yumemi.android.code_check.core.designsystem.preview.MultiThemePreviews
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary

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
    repositorySummary: GithubRepositorySummary
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.testTag("RepositoryInfoScreen")
    ) {
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