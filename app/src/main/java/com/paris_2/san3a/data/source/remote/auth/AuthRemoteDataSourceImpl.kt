package com.paris_2.san3a.data.source.remote.auth

import com.paris_2.san3a.data.service.auth.AuthApiServices
import com.paris_2.san3a.data.service.auth.WhatsAppMessage
import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto

class AuthRemoteDataSourceImpl(
    private val authApiServices: AuthApiServices
): AuthRemoteDataSource {
    override suspend fun sendOtpMessage(body: WhatsAppMessage): OtpDto {
       return authApiServices.sendMessage(body = body)
    } //TODO : in user
}