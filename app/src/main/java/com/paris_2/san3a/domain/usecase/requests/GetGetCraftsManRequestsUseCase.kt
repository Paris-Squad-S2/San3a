package com.paris_2.san3a.domain.usecase.requests

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.RequestsRepository
import kotlinx.coroutines.flow.Flow

class GetGetCraftsManRequestsUseCase(
    private val requestsRepository: RequestsRepository
) {
    operator fun invoke(userId: String): Flow<List<RequestService>> {
        return requestsRepository.getCraftsManRequests(userId)
    }
}