package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetOffersCountUseCase(
    private val requestDetailsRepository: RequestsRepository
) {
    operator fun invoke(requestId: String) = requestDetailsRepository.getOffersCount(requestId)
}