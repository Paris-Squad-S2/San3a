package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class AssignRequestToCraftsmanUseCase(
    private val requestDetailsRepository: RequestsRepository
) {
    suspend operator fun invoke(requestId: String, craftsmanId: String) {
        requestDetailsRepository.assignRequestToCraftsman(requestId, craftsmanId)
    }
}