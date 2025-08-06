package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {
    suspend fun addOffer(offer: Offer)
    fun getAcceptedOffers(requestId: String): Flow<List<Offer>>
    fun getOffers(requestId: String): Flow<List<Offer>>
    suspend fun getRequestDetailsById(requestId: String): RequestService
    suspend fun getYourOffer(craftsmanId: String): List<Offer>
    suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String)
    suspend fun acceptOffer(offerId: String)
    fun getCustomerRequests(userId: String): Flow<List<RequestService>>
}