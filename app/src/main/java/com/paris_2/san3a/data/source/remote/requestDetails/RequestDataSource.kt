package com.paris_2.san3a.data.source.remote.requestDetails

import com.paris_2.san3a.data.source.remote.requestDetails.dto.OfferDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import kotlinx.coroutines.flow.Flow

interface RequestDataSource {
    suspend fun addOffer(offer: OfferDto)
    fun getAcceptedOffers(requestId: String): Flow<List<OfferDto>>
    suspend fun getRequestDetailsById(requestId: String):RequestServiceDto?
    suspend fun getCraftsmanOffers(craftsmanId: String): List<OfferDto>
    suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String)
    fun getOffers(requestId: String): Flow<List<OfferDto>>
    suspend fun acceptOffer(offerId: String)
    fun getCustomerRequests(userId: String): Flow<List<RequestServiceDto>>
}