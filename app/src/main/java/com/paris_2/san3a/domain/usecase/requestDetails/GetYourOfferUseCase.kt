package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestDetailsRepository

class GetYourOfferUseCase(
    private val requestDetailsRepository: RequestDetailsRepository

) {
    suspend operator fun invoke(craftsmanId: String) =
        requestDetailsRepository.getYourOffer(craftsmanId)
}