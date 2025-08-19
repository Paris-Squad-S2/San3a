package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(phoneNumber: String): User =
        userRepository.getUser(phoneNumber)
}