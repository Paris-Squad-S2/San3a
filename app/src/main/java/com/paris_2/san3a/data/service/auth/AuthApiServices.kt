package com.paris_2.san3a.data.service.auth

import com.paris_2.san3a.BuildConfig
import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiServices {
    @POST("api/send-message")
    suspend fun sendMessage(
        @Body body: WhatsAppMessage,
    ): OtpDto
}

data class WhatsAppMessage(
    val phoneNumber: String,
    val message: String,
    val sessionId: String = BuildConfig.SESSION_ID
)
