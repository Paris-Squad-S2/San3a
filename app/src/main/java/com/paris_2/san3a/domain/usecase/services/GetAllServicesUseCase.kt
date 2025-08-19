package com.paris_2.san3a.domain.usecase.services

import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.repository.ServicesRepository
import kotlinx.coroutines.flow.Flow

class GetAllServicesUseCase(private val servicesRepository: ServicesRepository) {
    operator fun invoke(): Flow<List<Service>> = servicesRepository.getAllServices()
}