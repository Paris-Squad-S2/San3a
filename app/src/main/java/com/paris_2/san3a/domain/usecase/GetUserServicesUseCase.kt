package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserServicesUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(phoneNumber: String, isCraftsman: Boolean): Flow<List<Service>> =
        userRepository.getServices(phone = phoneNumber, isCraftsman = isCraftsman)
}