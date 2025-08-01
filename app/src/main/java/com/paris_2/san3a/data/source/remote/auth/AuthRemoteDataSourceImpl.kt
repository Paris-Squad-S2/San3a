package com.paris_2.san3a.data.source.remote.auth

import com.paris_2.san3a.data.service.auth.AuthApiServices
import com.paris_2.san3a.data.service.auth.WhatsAppMessage
import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto

class AuthRemoteDataSourceImpl(
    private val api: AuthApiServices
): AuthRemoteDataSource {
    override suspend fun sendMessage(body: WhatsAppMessage): OtpDto {
       return api.sendMessage(body = body)
    }
}