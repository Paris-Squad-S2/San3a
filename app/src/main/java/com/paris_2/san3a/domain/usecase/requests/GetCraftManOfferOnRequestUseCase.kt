package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.repository.RequestsRepository

class GetCraftManOfferOnRequestUseCase(
    private val requestDetailsRepository: RequestsRepository
) {
    operator fun invoke(craftsManId: String, requestId: String) =
        requestDetailsRepository.getCraftManOfferOnRequestUseCase(
            craftsManId = craftsManId,
            requestId = requestId
        )
}