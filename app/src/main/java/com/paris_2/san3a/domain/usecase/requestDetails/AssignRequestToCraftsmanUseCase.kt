package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestDetailsRepository

class AssignRequestToCraftsmanUseCase(
    private val requestDetailsRepository: RequestDetailsRepository
) {
    suspend operator fun invoke(requestId: String, craftsmanId: String) {
        requestDetailsRepository.assignRequestToCraftsman(requestId, craftsmanId)
    }
}