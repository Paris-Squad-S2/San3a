package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestDetailsRepository

class GetOffersUseCase(
    private val requestDetailsRepository: RequestDetailsRepository

) {
    operator fun invoke(requestId: String) = requestDetailsRepository.getOffers(requestId)
}