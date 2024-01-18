package jp.co.yumemi.android.code_check.feature.repository.search

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.co.yumemi.android.code_check.core.designsystem.theme.CodeCheckAppTheme
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import jp.co.yumemi.android.code_check.core.model.dummySearchResults

@Composable
fun RepositorySearchRoute(
    viewModel: RepositorySearchViewModel = hiltViewModel(),
) {
    RepositorySearchScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositorySearchScreen(
    query: String = "",
    onQueryChanged: (String) -> Unit = {},
) {
    Scaffold(
        topBar = {
            SearchTextField(
                modifier = Modifier
                    .padding(8.dp),
                searchQuery = query,
                onSearchQueryChanged = onQueryChanged,

            )
        }
    ){
        RepositorySearchList(
            modifier = Modifier
                .padding(it)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onTapImeAction: () -> Unit = {},
){
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = modifier
            .height(56.0.dp)
            .fillMaxWidth(1.0f)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small,
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = MaterialTheme.shapes.small,
            )
            .focusRequester(focusRequester),
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onTapImeAction()
            }
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
        ),
    )
}

@Composable
fun RepositorySearchList(
    modifier: Modifier = Modifier,
    repositorySearchResults: List<GithubRepositorySummary> = dummySearchResults,
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
            )
        }
    }
}

@Composable
fun RepositorySearchResultItem(
    modifier: Modifier = Modifier,
    repositorySearchResult: GithubRepositorySummary,
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

    ) {
        Text(
            repositorySearchResult.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}



@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RepositorySearchScreenPreview() {
    CodeCheckAppTheme {
        RepositorySearchScreen()
    }
}