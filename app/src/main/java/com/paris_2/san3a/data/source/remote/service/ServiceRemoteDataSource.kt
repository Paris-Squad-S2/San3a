package com.paris_2.san3a.data.source.remote.service

import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.domain.entity.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRemoteDataSource {
    fun getAllServices(): Flow<List<ServiceDto>>
    suspend fun requestService(requestedServiceDto: RequestServiceDto): String
    fun searchServices(query: String): Flow<List<ServiceDto>>
    fun getMostRequestedServices(): Flow<List<ServiceDto>>
    fun getAvailableJobs(): Flow<List<RequestServiceDto>>
    suspend fun updateNumOfRequestService(serviceId: String)
}