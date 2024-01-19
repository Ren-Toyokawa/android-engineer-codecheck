package jp.co.yumemi.android.code_check.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Issue(
    val id: Int,
    @SerialName("node_id")
    val nodeId: String,
    val url: String,
    @SerialName("repository_url")
    val repositoryUrl: String,
    @SerialName("labels_url")
    val labelsUrl: String,
    @SerialName("comments_url")
    val commentsUrl: String,
    @SerialName("events_url")
    val eventsUrl: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val number: Int,
    val state: String,
    @SerialName("state_reason")
    val stateReason: String?,
    val title: String,
    val body: String?,
)
