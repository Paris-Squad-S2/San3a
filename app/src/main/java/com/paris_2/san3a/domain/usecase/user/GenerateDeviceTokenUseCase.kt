package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class GenerateDeviceTokenUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(){
        userRepository.generateDeviceToken()
    }
}