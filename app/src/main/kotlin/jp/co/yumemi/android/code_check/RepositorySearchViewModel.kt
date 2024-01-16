/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.util.Date

/**
 * リポジトリ検索画面のViewModel
 */
class RepositorySearchViewModel: ViewModel() {
    companion object {
        private const val TAG = "RepositorySearchViewModel"
    }

    private val _errorState: MutableStateFlow<ErrorState> = MutableStateFlow(ErrorState.Idle)
    val errorState = _errorState.asStateFlow()

    /**
     * inputTextを元にリポジトリを検索する
     *
     * @param inputText 検索文字列
     * @return リポジトリ情報のリスト
     */
    fun searchRepository(inputText: String): List<RepositoryInfoItem> =
        runBlocking {
            val client = HttpClient(Android)

            return@runBlocking GlobalScope.async {
                val response: HttpResponse =
                    client?.get("https://api.github.com/search/repositories") {
                        header("Accept", "application/vnd.github.v3+json")
                        parameter("q", inputText)
                    }

                val jsonBody = JSONObject(response.receive<String>())

                val jsonItems = jsonBody.optJSONArray("items")

                if (jsonItems == null) {
                    _errorState.value = ErrorState.CantFetchRepositoryInfo
                    Log.d(TAG, "jsonItems is null, inputText: $inputText")
                    return@async listOf<RepositoryInfoItem>()
                }

                val repositoryInfoItemList = mutableListOf<RepositoryInfoItem>()

                // アイテムの個数分ループし、JsonをパースしてRepositoryInfoのリストを作成する
                for (i in 0 until jsonItems.length()) {
                    try {
                        val jsonItem = jsonItems.getJSONObject(i)
                        val name = jsonItem.getString("full_name")
                        val ownerIconUrl = jsonItem.getJSONObject("owner").optString("avatar_url")
                        val language = jsonItem.optString("language") ?: null
                        val stargazersCount = jsonItem.getLong("stargazers_count")
                        val watchersCount = jsonItem.getLong("watchers_count")
                        val forksCount = jsonItem.getLong("forks_count")
                        val openIssuesCount = jsonItem.getLong("open_issues_count")

                        val repositoryInfoItem =
                            RepositoryInfoItem(
                                name = name,
                                ownerIconUrl = ownerIconUrl ?: "",
                                language = language,
                                stargazersCount = stargazersCount,
                                watchersCount = watchersCount,
                                forksCount = forksCount,
                                openIssuesCount = openIssuesCount,
                            )

                        repositoryInfoItemList.add(repositoryInfoItem)
                    } catch (e: Exception) {
                        Log.e(TAG, "error: $e")
                        _errorState.value = ErrorState.CantFetchRepositoryInfo
                    }
                }

                lastSearchDate = Date()

                return@async repositoryInfoItemList.toList()
            }.await()
        }

    fun clearErrorState() {
        _errorState.value = ErrorState.Idle
    }
}

/**
 * Githubのリポジトリ情報
 */
@Parcelize
data class RepositoryInfoItem(
    val name: String,
    val ownerIconUrl: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable

/**
 * エラーの状態を表すsealed interface
 */
sealed interface ErrorState {
    object Idle : ErrorState

    object CantFetchRepositoryInfo : ErrorState
}
