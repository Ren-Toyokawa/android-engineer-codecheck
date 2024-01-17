/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.core.data.model.GithubRepository
import jp.co.yumemi.android.code_check.core.data.model.RepositorySearchResponse
import jp.co.yumemi.android.code_check.network.HttpClientSingleton.client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import java.net.UnknownHostException
import java.util.Date

/**
 * リポジトリ検索画面のViewModel
 */
class RepositorySearchViewModel : ViewModel() {
    companion object {
        private const val TAG = "RepositorySearchViewModel"
    }

    private val _errorState: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.Idle)
    val errorState = _errorState.asStateFlow()

    // StateFlowを使用して検索結果を保持
    private val _searchResults = MutableStateFlow<List<GithubRepository>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    /**
     * inputTextを元にリポジトリを検索する
     *
     * @param inputText 検索文字列
     * @return リポジトリ情報のリスト
     */
    fun executeSearchRepository(inputText: String) {
        viewModelScope.launch {
            try {
                val response: RepositorySearchResponse = searchRepository(inputText)
                _searchResults.value = response.items
            } catch (e: Exception) {
                handleError(e)
            }

            lastSearchDate = Date()
        }
    }

    /**
     * エラー状態をIdleにする
     */
    fun clearErrorState() {
        _errorState.value = ErrorState.Idle
    }

    /**
     * FIXME: 本来はRepositoryクラスを作成し、そこでAPIのリクエストを行うべき
     *        アーキテクチャ適用のissueで対応する
     * GithubのAPIにRequestしてリポジトリ情報を取得する
     */
    private suspend fun searchRepository(inputText: String): RepositorySearchResponse {
        return client.get("https://api.github.com/search/repositories") {
            header("Accept", "application/vnd.github.v3+json")
            parameter("q", inputText)
        }
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
