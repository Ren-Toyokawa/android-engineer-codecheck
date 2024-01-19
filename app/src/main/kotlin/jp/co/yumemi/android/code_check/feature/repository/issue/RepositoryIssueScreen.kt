package jp.co.yumemi.android.code_check.feature.repository.issue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RepositoryIssueRoute(
    modifier: Modifier = Modifier,
    viewModel: RepositoryIssueViewModel = hiltViewModel()
) {
    RepositoryIssueScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryIssueScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.testTag("RepositoryIssueScreen")
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(
                text = "RepositoryIssueScreen"
            )
        }
    }
}

@Preview
@Composable
fun RepositoryIssueScreenPreview() {
    RepositoryIssueScreen()
}