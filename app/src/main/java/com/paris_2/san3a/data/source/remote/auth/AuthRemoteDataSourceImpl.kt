package com.paris_2.san3a.data.source.remote.auth

import com.paris_2.san3a.data.source.remote.auth.RetrofitClient.api
import com.paris_2.san3a.data.source.remote.auth.dto.OtpDto
import retrofit2.http.Body

class AuthRemoteDataSourceImpl: AuthRemoteDataSource {
    override suspend fun sendMessage(
        phoneNumber: String,
         message: String,
    ): OtpDto {
        return api.sendMessage(phoneNumber,message)
    }
}