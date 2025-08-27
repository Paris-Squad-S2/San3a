package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class GetDeviceTokenUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): String {
        return userRepository.getDeviceToken(userId)
    }
}