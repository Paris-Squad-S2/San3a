package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class GetWorkMediaUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(phoneNumber: String): List<String> =
        userRepository.getWorkMedia(phoneNumber)
}