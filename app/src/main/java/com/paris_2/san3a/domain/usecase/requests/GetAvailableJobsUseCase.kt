package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetAvailableJobsUseCase(private val requestsRepository: RequestsRepository) {
    operator fun invoke(userId: String) = requestsRepository.getAvailableJobs(userId)
}