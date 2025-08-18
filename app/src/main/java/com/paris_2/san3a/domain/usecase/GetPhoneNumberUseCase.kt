package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.UserRepository

class GetPhoneNumberUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String {
        return userRepository.getPhoneNumber()
    }
}