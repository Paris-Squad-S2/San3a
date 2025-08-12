package com.paris_2.san3a.data.source.remote.requestDetails

import com.google.firebase.firestore.FieldPath
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class RequestDataSourceImpl(
    private val fireStoreService: FireStoreService,
): RequestDataSource {

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
            path = "$REQUEST_DETAILS_COLLECTION/$requestId",
            fromJson = RequestServiceDto::fromJson,
        )
    }

    override suspend fun getCraftsmanOffers(craftsmanId: String): List<OfferDto> {
        return fireStoreService.getCollection(
            path = OFFERS_COLLECTION,
            fromJson = OfferDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("craftsmanId", craftsmanId)
            }
        )
    }

    override suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String) {
        fireStoreService.updateDoc(
            path = "$REQUEST_DETAILS_COLLECTION/$requestId",
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

    override fun getCustomerRequests(userId: String): Flow<List<RequestServiceDto>>{
        return fireStoreService.streamCollection(
            path = REQUEST_DETAILS_COLLECTION,
            fromJson = RequestServiceDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("userId", userId)
            }
        )
    }

    override suspend fun cancelRequest(requestId: String) {
        fireStoreService.updateDoc(
            path = "$REQUEST_DETAILS_COLLECTION/$requestId",
            data = mapOf(
                "requestStatus" to "CANCELLED"
            )
        )
    }

    override suspend fun markRequestAsDone(requestId: String) {
        fireStoreService.updateDoc(
            path = "$REQUEST_DETAILS_COLLECTION/$requestId",
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
            }
        ).flatMapLatest { offers ->
            val requestIds = offers.map { it.requestId }
            if (requestIds.isEmpty()) {
                flow { emit(emptyList()) }
            } else {
                fireStoreService.streamCollection(
                    path = REQUEST_DETAILS_COLLECTION,
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


    companion object{
        const val REQUEST_DETAILS_COLLECTION = "service_requests"
        const val OFFERS_COLLECTION = "offers"
    }
}