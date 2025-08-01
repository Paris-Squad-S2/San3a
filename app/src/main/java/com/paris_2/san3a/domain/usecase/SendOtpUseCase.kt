package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.AuthRepository

class SendOtpUseCase (private val authRepository: AuthRepository) {
    suspend operator fun invoke(phoneNumber: String, message: String): Boolean{
        return authRepository.sendMessage(phoneNumber,message)
    }
}