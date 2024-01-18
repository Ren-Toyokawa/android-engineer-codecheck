package jp.co.yumemi.android.code_check.feature.repository.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.co.yumemi.android.code_check.core.designsystem.preview.MultiThemePreviews
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import jp.co.yumemi.android.code_check.core.model.dummySearchResults
import jp.co.yumemi.android.code_check.core.ui.component.dialog.CantFetchRepositoryInfoDialog
import jp.co.yumemi.android.code_check.core.ui.component.dialog.NetworkErrorDialog
import jp.co.yumemi.android.code_check.core.ui.component.textfield.SearchTextField

@Composable
fun RepositorySearchRoute(
    viewModel: RepositorySearchViewModel = hiltViewModel(),
    navigateRepositoryInfo: (GithubRepositorySummary) -> Unit,
) {
    val query: String by viewModel.query.collectAsState()
    val searchRepositoryResult: List<GithubRepositorySummary> by viewModel.searchResults.collectAsState()
    val errorState: ErrorState by viewModel.errorState.collectAsState()

    RepositorySearchScreen(
        query = query,
        onQueryChanged = viewModel::onQueryChanged,
        onTapImeAction = viewModel::executeSearchRepository,
        errorState = errorState,
        onTapErrorDialogConfirm = viewModel::clearErrorState,
        searchRepositoryResult = searchRepositoryResult,
        onTapSearchRepositoryResult = navigateRepositoryInfo,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositorySearchScreen(
    query: String = "",
    onQueryChanged: (String) -> Unit = {},
    onTapImeAction: (String) -> Unit = {},
    errorState: ErrorState,
    onTapErrorDialogConfirm: () -> Unit,
    searchRepositoryResult: List<GithubRepositorySummary>,
    onTapSearchRepositoryResult: (GithubRepositorySummary) -> Unit = {},
) {
    Scaffold(
        topBar = {
            SearchTextField(
                modifier = Modifier
                    .padding(8.dp),
                searchQuery = query,
                onSearchQueryChanged = onQueryChanged,
                onTapImeAction = onTapImeAction,
            )
        }
    ){
        RepositorySearchList(
            modifier = Modifier
                .padding(it),
            repositorySearchResults = searchRepositoryResult,
            onTapSearchRepositoryResult = onTapSearchRepositoryResult,
        )
    }

    // エラーダイアログ
    when (errorState) {
        ErrorState.CantFetchRepositoryInfo -> CantFetchRepositoryInfoDialog(
            onClickConfirm = onTapErrorDialogConfirm,
        )
        ErrorState.NetworkError -> NetworkErrorDialog(
            onClickConfirm = onTapErrorDialogConfirm,
        )
        ErrorState.Idle -> { /* 何もしない */ }
    }

}

@Composable
fun RepositorySearchList(
    modifier: Modifier = Modifier,
    repositorySearchResults: List<GithubRepositorySummary> = dummySearchResults,
    onTapSearchRepositoryResult: (GithubRepositorySummary) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            repositorySearchResults.count()
        ) { index ->
            RepositorySearchResultItem(
                repositorySearchResult = repositorySearchResults[index],
                onTap = onTapSearchRepositoryResult,
            )
        }
    }
}

@Composable
fun RepositorySearchResultItem(
    modifier: Modifier = Modifier,
    repositorySearchResult: GithubRepositorySummary,
    onTap: (GithubRepositorySummary) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small,
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.small,
            )
            .padding(8.dp)
            .clickable {
                 onTap(repositorySearchResult)            }
    ) {
        Text(
            repositorySearchResult.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}



@MultiThemePreviews
@Composable
fun RepositorySearchScreenPreview() {
    CodeCheckAppTheme {
        RepositorySearchScreen(
            query = "android-coding-check",
            onQueryChanged = {},
            onTapImeAction = {},
            errorState = ErrorState.Idle,
            onTapErrorDialogConfirm = {},
            searchRepositoryResult = dummySearchResults,
        )
    }
}