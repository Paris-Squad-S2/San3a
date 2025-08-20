package com.paris_2.san3a.data.source.remote.user.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpDto(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("success")
    val success: Boolean? = null
)