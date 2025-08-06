package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestDetailsRepository

class AcceptOfferUseCase(
    private val requestDetailsRepository: RequestDetailsRepository
) {
    suspend operator fun invoke(offerId: String) =
        requestDetailsRepository.acceptOffer(offerId)
}