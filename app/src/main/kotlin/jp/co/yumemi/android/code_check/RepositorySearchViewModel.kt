/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import okio.IOException
import org.json.JSONObject
import java.util.Date

/**
 * リポジトリ検索画面のViewModel
 */
class RepositorySearchViewModel : ViewModel() {
    companion object {
        private const val TAG = "RepositorySearchViewModel"
    }

    private val client = HttpClient(Android) {
        install(JsonFeature){
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    private val _errorState: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.Idle)
    val errorState = _errorState.asStateFlow()

    // StateFlowを使用して検索結果を保持
    private val _searchResults = MutableStateFlow<List<RepositoryInfoItem>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    // region life cycle event
    override fun onCleared() {
        super.onCleared()
        // HACK: 現状は一つのViewModelしか存在しないため、ここでclientをcloseしている
        //       複数の画面で呼び出される場合はシングルトンにした方が良いため、必要に応じて修正する
        client.close()
    }
    // endregion


    /**
     * inputTextを元にリポジトリを検索する
     *
     * @param inputText 検索文字列
     * @return リポジトリ情報のリスト
     */
    fun searchRepository(inputText: String) {
        viewModelScope.launch {
            try {
                val response: RepositorySearchResponse =
                    client.get("https://api.github.com/search/repositories") {
                        header("Accept", "application/vnd.github.v3+json")
                        parameter("q", inputText)
                    }
                _searchResults.value = response.items
            } catch (e: SerializationException) {
                Log.e(TAG, "error: $e")
                _errorState.value = ErrorState.CantFetchRepositoryInfo
            }

            lastSearchDate = Date()
        }
    }

    fun clearErrorState() {
        _errorState.value = ErrorState.Idle
    }
}


@Parcelize
@Serializable
data class RepositorySearchResponse(
    val items: List<RepositoryInfoItem>
) : Parcelable


/**
 * Githubのリポジトリ情報
 */
@Parcelize
@Serializable
data class RepositoryInfoItem(
    @SerialName("full_name")
    val name: String,
    @SerialName("owner")
    val owner: Owner,
    @SerialName("language")
    val language: String?,
    @SerialName("stargazers_count")
    val stargazersCount: Long,
    @SerialName("watchers_count")
    val watchersCount: Long,
    @SerialName("forks_count")
    val forksCount: Long,
    @SerialName("open_issues_count")
    val openIssuesCount: Long,
) : Parcelable


@Parcelize
@Serializable
data class Owner(
    @SerialName("avatar_url")
    val avatarUrl: String,
) : Parcelable
/**
 * エラーの状態を表すsealed interface
 */
sealed interface ErrorState {
    object Idle : ErrorState

    object CantFetchRepositoryInfo : ErrorState
}
