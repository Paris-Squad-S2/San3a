package com.paris_2.san3a.data.source.remote.user.service

import com.paris_2.san3a.data.source.remote.user.dto.OtpDto
import com.paris_2.san3a.data.source.remote.user.dto.OtpMessageDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiServices {
    @POST("api/send-message")
    suspend fun sendOtpMessage(
        @Body body: OtpMessageDto,
    ): OtpDto
}