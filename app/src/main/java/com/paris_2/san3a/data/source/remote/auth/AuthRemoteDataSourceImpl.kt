package com.paris_2.san3a.data.source.remote.auth

import com.paris_2.san3a.data.source.remote.auth.RetrofitClient.api
import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto

class AuthRemoteDataSourceImpl: AuthRemoteDataSource {
    override suspend fun sendMessage(body: WhatsAppMessage): OtpDto {
       return api.sendMessage(body = body)
    }
}