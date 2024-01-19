package jp.co.yumemi.android.code_check.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Issue(
    val number: Int,
    val title: String
)
