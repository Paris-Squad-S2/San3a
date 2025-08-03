package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.AuthRepository

class GetPhoneNumberUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String {
        return authRepository.getPhoneNumber()
    }
}