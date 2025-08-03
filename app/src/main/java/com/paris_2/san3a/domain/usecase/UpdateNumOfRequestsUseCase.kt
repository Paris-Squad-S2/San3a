package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.HomeRepository

class UpdateNumOfRequestsUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(serviceId: String) {
        homeRepository.updateNumOfRequestService(serviceId)
    }
}