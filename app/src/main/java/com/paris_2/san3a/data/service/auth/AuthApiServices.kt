package com.paris_2.san3a.data.service.auth

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
    val sessionId: String =  "662d3967-d32e-4c95-9e60-6ff8e037cc2b"
)
