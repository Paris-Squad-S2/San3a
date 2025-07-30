package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.AuthRepository

class SendOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String):String{
         return repository.sendOtp(phoneNumber)
    }
}