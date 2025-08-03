package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.AuthRepository

class SavePhoneNumberUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String){
        authRepository.savePhoneNumber(phoneNumber)
    }
}