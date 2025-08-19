package com.paris_2.san3a.domain.usecase.services

import com.paris_2.san3a.domain.repository.ServicesRepository

class UpdateNumOfRequestsUseCase(private val servicesRepository: ServicesRepository) {
    suspend operator fun invoke(serviceId: String) =
        servicesRepository.updateNumOfRequestService(serviceId)
}