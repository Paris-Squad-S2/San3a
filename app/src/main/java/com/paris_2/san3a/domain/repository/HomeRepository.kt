package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Service

interface HomeRepository {
    suspend fun getServices(): List<Service>
    suspend fun getMostRequestedServices()
    suspend fun requestService()
    suspend fun getStats()
    suspend fun getAvailableJobs()
    suspend fun getRecentRelatedJobs()
}