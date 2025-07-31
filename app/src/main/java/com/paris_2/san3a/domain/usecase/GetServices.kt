package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.HomeRepository

class GetServices(
    private val repository: HomeRepository
) {
    suspend fun invoke(): List<Service> {
        return repository.getServices()
    }
}