package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class DeleteRequestByIdUseCase(private val requestDetailsRepository: RequestsRepository) {
    suspend operator fun invoke(requestId: String) = requestDetailsRepository.deleteRequestById(requestId)
}