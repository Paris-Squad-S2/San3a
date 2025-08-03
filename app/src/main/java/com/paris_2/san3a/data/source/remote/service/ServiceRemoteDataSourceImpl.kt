package com.paris_2.san3a.data.source.remote.service

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.service.dto.ServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto.Companion.toJson
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

    override suspend fun requestService(requestedServiceDto: RequestServiceDto): String {
        return fireStoreService.addToCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            data = requestedServiceDto.toJson()
        )
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

    override fun getMostRequestedServices(): Flow<List<ServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICES_COLLECTION,
            fromJson = ServiceDto::fromJson,
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

    override suspend fun updateNumOfRequestService(serviceId: String) {
        Log.e("FirestorePath", "$SERVICES_COLLECTION/$serviceId")
        return fireStoreService.updateDoc(
            path = "$SERVICES_COLLECTION/$serviceId",
            data = mapOf(
                NUMBER_OF_REQUESTS_FIELD to FieldValue.increment(1)
            )
        )
    }

    companion object {
        const val SERVICES_COLLECTION = "services"
        const val SERVICE_REQUESTS_COLLECTION = "service_requests"
        const val NUMBER_OF_REQUESTS_FIELD = "numberOfRequests"

    }
}