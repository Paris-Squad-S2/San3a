package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetRecentRelatedJobsUseCase(
    private val requestsRepository: RequestsRepository
) {
    operator fun invoke(relatedJobs: List<String>) =
        requestsRepository.getRecentRelatedJobs(relatedJobs)
}