package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.HomeRepository

class RequestServiceUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(requestedService: RequestService) =
        repository.requestService(requestedService)
}