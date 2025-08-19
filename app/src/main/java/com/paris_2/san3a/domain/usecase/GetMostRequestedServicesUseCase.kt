package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.ServicesRepository
import kotlinx.coroutines.flow.Flow

class GetMostRequestedServicesUseCase(
    private val repository: ServicesRepository
) {
    operator fun invoke(): Flow<List<Service>> {
        return repository.getMostRequestedServices()
    }
}