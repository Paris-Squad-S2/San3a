package com.paris_2.san3a.data.source.remote.service

import com.google.firebase.firestore.Query
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import kotlinx.coroutines.flow.Flow

class ServiceRemoteDataSourceImpl(
    private val fireStoreService: FireStoreService,
): ServiceRemoteDataSource {
    
    override fun getAllServices(): Flow<List<ServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICES_COLLECTION,
            fromJson = ServiceDto::fromJson,
            limit = null
        )
    }

    override suspend fun requestService(requestedServiceDto: RequestServiceDto) {
        val docPath = "${SERVICE_REQUESTS_COLLECTION}/${requestedServiceDto.id}"
        val existingRequest = fireStoreService.getDoc(
            path = docPath,
            fromJson = RequestServiceDto.Companion::fromJson
        )
        if (existingRequest != null) {
            val updatedCount = existingRequest.requestedCount + 1
            fireStoreService.updateDoc(
                docPath,
                mapOf("requestedCount" to updatedCount)
            )
        } else {
            val newRequest = requestedServiceDto.copy(requestedCount = 1)
            fireStoreService.setDoc(docPath, newRequest)
        }
    }

    override fun searchServices(query: String): Flow<List<ServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICES_COLLECTION,
            fromJson = ServiceDto::fromJson,
            queryBuilder = { query ->
                query.startAt(query)
                    .endAt("$query\uf8ff")
            }
        )
    }

    override fun getMostRequestedServices(): Flow<List<RequestServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            limit = null,
            queryBuilder = { collection ->
                collection
                    .whereGreaterThan(NUMBER_OF_REQUESTS_FIELD, 0)
                    .orderBy(NUMBER_OF_REQUESTS_FIELD, Query.Direction.DESCENDING)
            }
        )
    }

    override fun getAvailableJobs(): Flow<List<RequestServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
        )
    }

    companion object {
        const val SERVICES_COLLECTION = "services"
        const val SERVICE_REQUESTS_COLLECTION = "service_requests"
        const val NUMBER_OF_REQUESTS_FIELD = "numberOfRequests"
    }
}