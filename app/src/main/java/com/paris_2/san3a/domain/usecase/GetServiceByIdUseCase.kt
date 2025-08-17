package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository

class GetServiceByIdUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(serviceId: String): Service? {
        return repository.getServiceById(serviceId)
    }
}