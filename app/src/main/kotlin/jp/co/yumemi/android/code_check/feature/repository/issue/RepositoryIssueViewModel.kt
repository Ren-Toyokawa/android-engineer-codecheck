package jp.co.yumemi.android.code_check.feature.repository.issue

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.core.data.GithubIssueRepository
import javax.inject.Inject

@HiltViewModel
class RepositoryIssueViewModel @Inject constructor(
//    private val githubIssueRepository: GithubIssueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val repositoryName: String? = savedStateHandle.get<String>("repositoryName")
    private val ownerName: String? = savedStateHandle.get<String>("ownerName")

    init {
        Log.d("RepositoryIssueViewModel", "repositoryName: $repositoryName")
        Log.d("RepositoryIssueViewModel", "ownerName: $ownerName")
    }

}