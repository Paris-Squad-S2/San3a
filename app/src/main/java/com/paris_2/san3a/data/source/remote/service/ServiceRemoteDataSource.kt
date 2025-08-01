package com.paris_2.san3a.data.source.remote.service

import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import kotlinx.coroutines.flow.Flow

interface ServiceRemoteDataSource {
    suspend fun getAllServices(): Flow<List<ServiceDto>>
    suspend fun requestService()
}