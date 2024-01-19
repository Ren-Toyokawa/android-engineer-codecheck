package jp.co.yumemi.android.code_check.feature.repository.issue

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.core.model.GithubIssue

@Composable
fun RepositoryIssueRoute(
    modifier: Modifier = Modifier,
    viewModel: RepositoryIssueViewModel = hiltViewModel()
) {
    val issues by viewModel.issues.collectAsState()

    RepositoryIssueScreen(
        issues = issues
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryIssueScreen(
    modifier: Modifier = Modifier,
    issues: List<GithubIssue> = emptyList()
) {
    Scaffold(
        modifier = Modifier.testTag("RepositoryIssueScreen"),
        topBar = {
            Text(
                text = "Issues",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(issues.size) { index ->
                IssueItem(
                    modifier = Modifier.fillMaxWidth(),
                    issue = issues[index]
                )
            }
        }
    }
}

@Composable
fun IssueItem(
    modifier: Modifier = Modifier,
    issue: GithubIssue
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
    ) {
        Text(text = "#${issue.number}")
        Text(
            text = issue.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview
@Composable
fun RepositoryIssueScreenPreview() {
    CodeCheckAppTheme {
        RepositoryIssueScreen()
    }
}