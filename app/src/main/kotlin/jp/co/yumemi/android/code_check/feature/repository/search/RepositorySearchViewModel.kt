/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.feature.repository.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.features.ClientRequestException
import jp.co.yumemi.android.code_check.core.data.GithubRepositoryRepository
import jp.co.yumemi.android.code_check.core.data.UserDataRepository
import jp.co.yumemi.android.code_check.core.model.GithubRepositorySummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.net.UnknownHostException
import java.util.Date
import javax.inject.Inject

/**
 * リポジトリ検索画面のViewModel
 */
@HiltViewModel
class RepositorySearchViewModel
@Inject
constructor(
    private val githubRepositoryRepository: GithubRepositoryRepository,
    private val userDataRepository: UserDataRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "RepositorySearchViewModel"
        const val QUERY_KEY = "query"
    }

    val query: StateFlow<String> = savedStateHandle.getStateFlow(QUERY_KEY, "")

    private val _errorState: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.Idle)
    val errorState = _errorState.asStateFlow()

    // StateFlowを使用して検索結果を保持
    private val _searchResults = MutableStateFlow<List<GithubRepositorySummary>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    /**
     * 検索のクエリが変更されたときに呼ばれる
     */
    fun onQueryChanged(query: String) {
        savedStateHandle[QUERY_KEY] = query
    }


    /**
     * inputTextを元にリポジトリを検索する
     *
     * @param inputText 検索文字列
     * @return リポジトリ情報のリスト
     */
    fun executeSearchRepository(inputText: String) {
        viewModelScope.launch {
            try {
                val searchResults = githubRepositoryRepository.searchRepository(inputText)
                _searchResults.value = searchResults
            } catch (e: Exception) {
                handleError(e)
            }

            // 検索した日時を保存する
            userDataRepository.saveLastSearchDate(Date())
        }
    }

    /**
     * エラー状態をIdleにする
     */
    fun clearErrorState() {
        _errorState.value = ErrorState.Idle
    }

    /**
     * エラーをハンドリングする
     */
    private fun handleError(e: Exception) {
        Log.e(TAG, "error: $e")
        when (e) {
            is SerializationException -> {
                _errorState.value = ErrorState.CantFetchRepositoryInfo
            }

            is UnknownHostException -> {
                _errorState.value = ErrorState.NetworkError
            }

            is ClientRequestException -> {
                _errorState.value = ErrorState.NetworkError
            }

            else -> {
                _errorState.value = ErrorState.CantFetchRepositoryInfo
            }
        }
    }
}

/**
 * エラーの状態を表すsealed interface
 */
sealed interface ErrorState {
    /**
     * 何もしていない状態
     */
    object Idle : ErrorState

    /**
     * リポジトリ情報の取得に失敗した場合
     */
    object CantFetchRepositoryInfo : ErrorState

    /**
     * ネットワークエラーが発生した場合
     */
    object NetworkError : ErrorState
}
