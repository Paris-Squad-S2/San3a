package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.repository.RequestDetailsRepository

class AddOfferUseCase(
    private val requestDetailsRepository: RequestDetailsRepository
) {
    suspend operator fun invoke(offer: Offer) {
        requestDetailsRepository.addOffer(offer)
    }
}