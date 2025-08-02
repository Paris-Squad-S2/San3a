package com.paris_2.san3a.data.source.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class OtpDto(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
) {
    data class Data(
        @SerializedName("messageId")
        val messageId: String?,
        @SerializedName("timestamp")
        val timestamp: Int?,
        @SerializedName("to")
        val to: String?
    )
}