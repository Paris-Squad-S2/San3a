package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestsRepository

class AcceptOfferUseCase(
    private val requestsRepository: RequestsRepository
) {
    suspend operator fun invoke(offerId: String) =
        requestsRepository.acceptOffer(offerId)
}