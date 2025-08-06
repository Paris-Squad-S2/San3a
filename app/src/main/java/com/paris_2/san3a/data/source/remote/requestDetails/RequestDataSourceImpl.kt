package com.paris_2.san3a.data.source.remote.requestDetails

import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import kotlinx.coroutines.flow.Flow
import kotlin.collections.mapOf

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
                "craftsmanId" to craftsmanId
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

    override suspend fun acceptOffer(offerId: String) {
        return fireStoreService.updateDoc(
            path = "$OFFERS_COLLECTION/$offerId",
            data = mapOf(
                "isAccepted" to true,
            )
        )
    }

    companion object{
        const val REQUEST_DETAILS_COLLECTION = "service_requests"
        const val OFFERS_COLLECTION = "offers"
    }
}