package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class AcceptOfferUseCase(private val requestsRepository: RequestsRepository) {
    suspend operator fun invoke(offerId: String, craftsmanId: String, requestId: String) =
        requestsRepository.acceptOffer(offerId = offerId, craftsmanId = craftsmanId, requestId = requestId)
}