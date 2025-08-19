package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.ServicesRepository

class GetAvailableJobsUseCase(private val servicesRepository: ServicesRepository) {
    operator fun invoke() = servicesRepository.getAvailableJobs()
}