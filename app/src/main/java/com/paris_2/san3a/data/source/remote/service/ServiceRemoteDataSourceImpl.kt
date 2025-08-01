package com.paris_2.san3a.data.source.remote.service

import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import kotlinx.coroutines.flow.Flow

class ServiceRemoteDataSourceImpl(
    private val fireStoreService: FireStoreService,
): ServiceRemoteDataSource {
    
    override suspend fun getAllServices(): Flow<List<ServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICES_COLLECTION,
            fromJson = ServiceDto::fromJson,
            limit = null
        )
    }

    override suspend fun requestService() {
        TODO("Not yet implemented")
    }

    companion object {
        const val SERVICES_COLLECTION = "services"
    }
}