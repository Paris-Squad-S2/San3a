package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class CancelRequestUseCase(
    private val requestsRepository: RequestsRepository
) {
    suspend operator fun invoke(requestId: String) =
        requestsRepository.cancelRequest(requestId)
}