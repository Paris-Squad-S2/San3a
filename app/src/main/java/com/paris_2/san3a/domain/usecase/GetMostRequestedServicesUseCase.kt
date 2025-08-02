package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.MostRequestedServices
import com.paris_2.san3a.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetMostRequestedServicesUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(userId: String): Flow<List<MostRequestedServices>> {
        return repository.getMostRequestedServices(userId)
    }
}