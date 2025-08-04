package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.HomeRepository

class GetAvailableJobsUseCase(
    private val homeRepository: HomeRepository
) {
    operator fun invoke() = homeRepository.getAvailableJobs()
}