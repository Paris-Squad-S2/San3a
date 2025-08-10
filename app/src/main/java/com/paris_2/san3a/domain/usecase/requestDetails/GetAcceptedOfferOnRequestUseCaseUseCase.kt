package com.paris_2.san3a.domain.usecase.requestDetails

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetAcceptedOfferOnRequestUseCaseUseCase(
    private val requestsRepository: RequestsRepository
) {
    operator fun invoke(requestId: String) =
        requestsRepository.getAcceptedOfferOnRequestUseCase(requestId)
}