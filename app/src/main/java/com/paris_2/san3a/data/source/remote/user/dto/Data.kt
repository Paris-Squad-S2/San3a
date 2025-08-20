package com.paris_2.san3a.data.source.remote.user.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("from")
    val from: String? = null,
    @SerialName("messageId")
    val messageId: String? = null,
    @SerialName("sessionId")
    val sessionId: String? = null,
    @SerialName("timestamp")
    val timestamp: Timestamp? = null,
    @SerialName("to")
    val to: String? = null
)