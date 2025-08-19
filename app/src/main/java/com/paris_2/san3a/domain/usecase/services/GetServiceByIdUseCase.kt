package com.paris_2.san3a.domain.usecase.services

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.ServicesRepository

class GetServiceByIdUseCase(private val servicesRepository: ServicesRepository) {
    suspend operator fun invoke(serviceId: String): Service? =
        servicesRepository.getServiceById(serviceId)
}