package com.paris_2.san3a.data.source.remote.service

import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import kotlinx.coroutines.flow.Flow

interface ServiceRemoteDataSource {
    fun getAllServices(): Flow<List<ServiceDto>>
    suspend fun getServiceById(serviceId: String): ServiceDto?
    fun searchServices(query: String): Flow<List<ServiceDto>>
    fun getMostRequestedServices(): Flow<List<ServiceDto>>
    suspend fun updateNumOfRequestService(serviceId: String)
}