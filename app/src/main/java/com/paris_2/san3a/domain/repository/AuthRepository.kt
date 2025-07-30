package com.paris_2.san3a.domain.repository

import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun sendOtp(phoneNumber: String):String
    suspend fun verifyOtp(verificationId: String, code: String): AuthResult
}