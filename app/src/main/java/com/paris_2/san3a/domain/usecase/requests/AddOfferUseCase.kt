package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.repository.RequestsRepository

class AddOfferUseCase(
    private val requestDetailsRepository: RequestsRepository
) {
    suspend operator fun invoke(offer: Offer) {
        requestDetailsRepository.addOffer(offer)
    }
}