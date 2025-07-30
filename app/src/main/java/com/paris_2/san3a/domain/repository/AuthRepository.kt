package com.paris_2.san3a.domain.repository

interface AuthRepository {
    suspend fun sendOtp(phoneNumber: String):String
    suspend fun verifyOtp(verificationId: String, code: String)
}