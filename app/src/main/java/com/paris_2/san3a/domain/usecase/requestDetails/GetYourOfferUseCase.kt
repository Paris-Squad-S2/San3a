package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetYourOfferUseCase(
    private val requestsRepository: RequestsRepository

) {
    suspend operator fun invoke(craftsmanId: String) =
        requestsRepository.getYourOffer(craftsmanId)
}