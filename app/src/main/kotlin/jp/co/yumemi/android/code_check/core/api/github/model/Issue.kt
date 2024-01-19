package jp.co.yumemi.android.code_check.core.api.github.model

import kotlinx.serialization.Serializable

@Serializable
data class Issue(
    val number: Int,
    val title: String
)
