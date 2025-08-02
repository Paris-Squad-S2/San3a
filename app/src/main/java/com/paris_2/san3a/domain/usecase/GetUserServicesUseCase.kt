package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.UserRepository

class GetUserServicesUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phoneNumber: String): List<Service> =
        userRepository.getServices(phone = phoneNumber)
}