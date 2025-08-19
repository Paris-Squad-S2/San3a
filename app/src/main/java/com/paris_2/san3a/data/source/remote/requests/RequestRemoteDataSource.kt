package com.paris_2.san3a.data.source.remote.requests

import com.paris_2.san3a.data.source.remote.requests.dto.OfferDto
import com.paris_2.san3a.data.source.remote.user.dto.RequestServiceDto
import kotlinx.coroutines.flow.Flow

interface RequestRemoteDataSource {
    suspend fun addOffer(offer: OfferDto)
    fun getAcceptedOffers(requestId: String): Flow<List<OfferDto>>
    suspend fun getRequestDetailsById(requestId: String):RequestServiceDto?
    suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String)
    fun getOffers(requestId: String): Flow<List<OfferDto>>
    fun getOffersCount(requestId: String): Flow<Int>
    suspend fun acceptOffer(offerId: String)
    fun getCustomerRequests(userId: String): Flow<List<RequestServiceDto>>
    fun getCraftsManRequests(userId: String): Flow<List<RequestServiceDto>>
    fun getCraftManOfferOnRequestUseCase(craftsManId: String, requestId: String): Flow<OfferDto?>
    suspend fun cancelRequest(requestId: String)
    suspend fun markRequestAsDone(requestId: String)
    fun getAcceptedOfferOnRequestUseCase(requestId: String): Flow<OfferDto?>
    fun getRecentRelatedJobs(relatedJobs: List<String>): Flow<List<RequestServiceDto>>
    suspend fun requestService(requestedServiceDto: RequestServiceDto): String
    fun getAvailableJobs(): Flow<List<RequestServiceDto>>
}