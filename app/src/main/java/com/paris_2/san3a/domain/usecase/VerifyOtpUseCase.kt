package com.paris_2.san3a.domain.usecase

import com.google.firebase.auth.AuthResult
import com.paris_2.san3a.domain.repository.AuthRepository

class VerifyOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(verificationId: String, code: String): AuthResult {
         return repository.verifyOtp(verificationId, code)
    }
}