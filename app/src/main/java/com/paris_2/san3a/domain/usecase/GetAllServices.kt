package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetAllServices(
    private val repository: HomeRepository
) {
    suspend fun invoke(): Flow<List<Service>> {
        return repository.getAllServices()
    }
}