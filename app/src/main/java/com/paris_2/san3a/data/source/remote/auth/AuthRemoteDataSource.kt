package com.paris_2.san3a.data.source.remote.auth

import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteDataSource {
    @POST("api/send-message")
    suspend fun sendMessage(
        @Body phoneNumber: String,
        @Body message: String
        ): OtpDto
}