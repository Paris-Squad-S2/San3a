package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.repository.UserRepository

class SavePhoneNumberUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(phoneNumber: String) = userRepository.savePhoneNumber(phoneNumber)
}