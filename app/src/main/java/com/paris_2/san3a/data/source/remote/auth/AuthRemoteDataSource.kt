package com.paris_2.san3a.data.source.remote.auth

import com.google.firebase.auth.AuthResult

interface AuthRemoteDataSource {
    suspend fun sendOtp(phone: String): String
    suspend fun verifyOtp(verificationId: String, code: String): AuthResult
}