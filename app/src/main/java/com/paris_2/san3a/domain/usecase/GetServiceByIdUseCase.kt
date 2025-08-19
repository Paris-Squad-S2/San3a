package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.ServicesRepository

class GetServiceByIdUseCase(
    private val repository: ServicesRepository
) {
    suspend operator fun invoke(serviceId: String): Service? {
        return repository.getServiceById(serviceId)
    }
}