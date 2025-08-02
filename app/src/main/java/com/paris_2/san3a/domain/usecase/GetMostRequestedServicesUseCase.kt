package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetMostRequestedServicesUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<List<Service>> {
        return repository.getMostRequestedServices()
    }
}