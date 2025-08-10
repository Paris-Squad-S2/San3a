package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestsRepository

class MarkRequestAsDoneUseCase(
    private val requestsRepository: RequestsRepository
) {
    suspend operator fun invoke(requestId: String) =
        requestsRepository.markRequestAsDone(requestId)
}