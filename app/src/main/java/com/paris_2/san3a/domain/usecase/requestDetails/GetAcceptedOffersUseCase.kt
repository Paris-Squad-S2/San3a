package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetAcceptedOffersUseCase(
    private val requestDetailsRepository: RequestsRepository

) {
    operator fun invoke(requestId: String) =
        requestDetailsRepository.getAcceptedOffers(requestId)
}