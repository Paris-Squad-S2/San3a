package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.RequestsRepository

class RequestServiceUseCase(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(requestedService: RequestService) =
        requestsRepository.requestService(requestedService)
}