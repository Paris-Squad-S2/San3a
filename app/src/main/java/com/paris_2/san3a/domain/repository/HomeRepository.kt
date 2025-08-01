package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Service
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllServices(): Flow<List<Service>>
    suspend fun getMostRequestedServices()
    suspend fun requestService()
    suspend fun getStats()
    suspend fun getAvailableJobs()
    suspend fun getRecentRelatedJobs()
}