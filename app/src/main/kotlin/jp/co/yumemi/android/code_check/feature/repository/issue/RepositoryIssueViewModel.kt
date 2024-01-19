package jp.co.yumemi.android.code_check.feature.repository.issue

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.core.data.GithubIssueRepository
import jp.co.yumemi.android.code_check.core.model.GithubIssue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryIssueViewModel @Inject constructor(
    private val githubIssueRepository: GithubIssueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val repositoryName: String = requireNotNull(savedStateHandle["repositoryName"])
    private val ownerName: String = requireNotNull(savedStateHandle["ownerName"])

    private val _issues = MutableStateFlow<List<GithubIssue>>(emptyList())
    val issues = _issues.asStateFlow()

    init {
        viewModelScope.launch {
            val issues = githubIssueRepository.fetchIssues(ownerName, repositoryName)
            _issues.value = issues
        }
    }

}