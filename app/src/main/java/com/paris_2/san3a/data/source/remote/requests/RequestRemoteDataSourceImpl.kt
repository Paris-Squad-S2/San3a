package com.paris_2.san3a.data.source.remote.requests

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.requests.dto.OfferDto
import com.paris_2.san3a.data.source.remote.requests.dto.RequestServiceDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class RequestRemoteDataSourceImpl(
    private val fireStoreService: FireStoreService,
) : RequestRemoteDataSource {

    override suspend fun addOffer(offer: OfferDto) {
        fireStoreService.addToCollection(
            path = OFFERS_COLLECTION,
            data = offer.toJson()
        )
    }

    override fun getAcceptedOffers(requestId: String): Flow<List<OfferDto>> {
        return fireStoreService.streamCollection(
            path = OFFERS_COLLECTION,
            fromJson = OfferDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("requestId", requestId)
                    .whereEqualTo("isAccepted", true)
            }
        )
    }

    override suspend fun getRequestDetailsById(requestId: String): RequestServiceDto? {
        return fireStoreService.getDoc(
            path = "$SERVICE_REQUESTS_COLLECTION/$requestId",
            fromJson = RequestServiceDto::fromJson,
        )
    }

    override suspend fun deleteRequestById(requestId: String) {
        return fireStoreService.deleteDoc(
            path = "$SERVICE_REQUESTS_COLLECTION/$requestId",
        )
    }

    override suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String) {
        fireStoreService.updateDoc(
            path = "$SERVICE_REQUESTS_COLLECTION/$requestId",
            data = mapOf(
                "selectedCraftsmanId" to craftsmanId
            )
        )
    }

    override fun getOffers(requestId: String): Flow<List<OfferDto>> {
        return fireStoreService.streamCollection(
            path = OFFERS_COLLECTION,
            fromJson = OfferDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("requestId", requestId)
            }
        )
    }

    override fun getOffersCount(requestId: String): Flow<Int> {
        return fireStoreService.streamCountOfCollection(
            path = OFFERS_COLLECTION,
            queryBuilder = { query ->
                query.whereEqualTo("requestId", requestId)
            }
        )
    }

    override suspend fun acceptOffer(offerId: String) {
        return fireStoreService.updateDoc(
            path = "$OFFERS_COLLECTION/$offerId",
            data = mapOf(
                "isAccepted" to true,
            )
        )
    }

    override fun getCustomerRequests(userId: String): Flow<List<RequestServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("userId", userId)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
            }
        )
    }

    override suspend fun cancelRequest(requestId: String) {
        fireStoreService.updateDoc(
            path = "$SERVICE_REQUESTS_COLLECTION/$requestId",
            data = mapOf(
                "requestStatus" to "CANCELLED"
            )
        )
    }

    override suspend fun markRequestAsDone(requestId: String) {
        fireStoreService.updateDoc(
            path = "$SERVICE_REQUESTS_COLLECTION/$requestId",
            data = mapOf(
                "requestStatus" to "COMPLETED"
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCraftsManRequests(userId: String): Flow<List<RequestServiceDto>> =
        fireStoreService.streamCollection(
            path = OFFERS_COLLECTION,
            fromJson = OfferDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("craftsmanId", userId)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
            }
        ).flatMapLatest { offers ->
            val requestIds = offers.map { it.requestId }
            if (requestIds.isEmpty()) {
                flow { emit(emptyList()) }
            } else {
                fireStoreService.streamCollection(
                    path = SERVICE_REQUESTS_COLLECTION,
                    fromJson = RequestServiceDto::fromJson,
                    queryBuilder = { query ->
                        query.whereIn(FieldPath.documentId(), requestIds)
                    }
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCraftManOfferOnRequestUseCase(
        craftsManId: String,
        requestId: String
    ): Flow<OfferDto?> {
        return fireStoreService.streamCollection(
            path = OFFERS_COLLECTION,
            fromJson = OfferDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("craftsmanId", craftsManId)
                    .whereEqualTo("requestId", requestId)
            }
        ).flatMapLatest { offers ->
            flow { emit(offers.firstOrNull()) }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAcceptedOfferOnRequestUseCase(
        requestId: String
    ): Flow<OfferDto?> {
        return fireStoreService.streamCollection(
            path = OFFERS_COLLECTION,
            fromJson = OfferDto::fromJson,
            queryBuilder = { query ->
                query
                    .whereEqualTo("requestId", requestId)
                    .whereEqualTo("isAccepted", true)
            }
        ).flatMapLatest { offers ->
            flow { emit(offers.firstOrNull()) }
        }
    }

    override fun getRecentRelatedJobs(relatedJobs: List<String>, userId: String): Flow<List<RequestServiceDto>> {
        if (relatedJobs.isEmpty()) {
            return flow { emit(emptyList()) }
        }
        return fireStoreService.streamCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            queryBuilder = { query ->
                query
                    .whereNotEqualTo("userId", userId)
                    .whereEqualTo("requestStatus", "ONGOING")
                    .whereIn("serviceId", relatedJobs)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
            }
        )
    }


    override fun getAvailableJobs(userId: String): Flow<List<RequestServiceDto>> {
        return fireStoreService.streamCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            queryBuilder = { query ->
                query
                    .whereNotEqualTo("userId", userId)
                    .whereEqualTo("requestStatus", "ONGOING")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
            }
        )
    }

    override suspend fun requestService(requestedServiceDto: RequestServiceDto): String {
        return fireStoreService.addToCollection(
            path = SERVICE_REQUESTS_COLLECTION,
            data = requestedServiceDto.toJson()
        )
    }


    private companion object {
        const val SERVICE_REQUESTS_COLLECTION = "service_requests"
        const val OFFERS_COLLECTION = "offers"
    }
}