package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Service
import kotlinx.coroutines.flow.Flow

interface ServicesRepository {
    fun getAllServices(): Flow<List<Service>>
    suspend fun getServiceById(serviceId: String): Service?
    fun getMostRequestedServices(): Flow<List<Service>>
    suspend fun updateNumOfRequestService(serviceId: String)
}