package com.paris_2.san3a.domain.usecase.user

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserServicesUseCase(private val userRepository: UserRepository) {
    operator fun invoke(phoneNumber: String, isCraftsman: Boolean): Flow<List<Service>> =
        userRepository.getServices(phoneNumber, isCraftsman)
}