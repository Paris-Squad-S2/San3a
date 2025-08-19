package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.ServicesRepository

class RequestServiceUseCase(private val servicesRepository: ServicesRepository) {
    suspend operator fun invoke(requestedService: RequestService) =
        servicesRepository.requestService(requestedService)
}