package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.requestDetails.RequestDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.RequestsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RequestsRepositoryImpl(
    private val requestDataSource: RequestDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
) : RequestsRepository, BaseRepository() {

    override suspend fun addOffer(offer: Offer) {
        requestDataSource.addOffer(offer.toDto())
    }

    override fun getAcceptedOffers(requestId: String): Flow<List<Offer>> {
        return requestDataSource.getAcceptedOffers(requestId)
                .map { list -> list.map { it.toEntity() }}
                .catch { throw FailException("Failed to fetch accepted offers for request ID: $requestId") }

    }

    override fun getOffers(requestId: String): Flow<List<Offer>> {
        return requestDataSource.getOffers(requestId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch offers for request ID: $requestId") }
    }

    override fun getOffersCount(requestId: String): Flow<Int> {
        return requestDataSource.getOffersCount(requestId)
            .catch { throw FailException("Failed to fetch offers count for request ID: $requestId") }
    }

    override suspend fun getRequestDetailsById(requestId: String): RequestService {
        return safeCall(FailException("Failed to fetch request details for request ID: $requestId")){
            requestDataSource.getRequestDetailsById(requestId)?.toEntity() ?: throw FailException("Request not found")
        }
    }

    override suspend fun getYourOffer(craftsmanId: String): List<Offer> {
        return safeCall(FailException("Failed to fetch your offers for craftsman ID: $craftsmanId")) {
            requestDataSource.getCraftsmanOffers(craftsmanId).map { it.toEntity() }
        }
    }

    override suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String) {
        return safeCall(FailException("Failed to assign request to craftsman")) {
            requestDataSource.assignRequestToCraftsman(requestId, craftsmanId)
        }
    }

    override suspend fun acceptOffer(offerId: String, craftsmanId: String, requestId: String) {
        safeCall(FailException("Failed to accept offer with ID: $offerId")) {
            coroutineScope {
                val accept = async { requestDataSource.acceptOffer(offerId) }
                val assign = async { requestDataSource.assignRequestToCraftsman(requestId, craftsmanId) }
                accept.await()
                assign.await()
            }
        }
    }

    override fun getCustomerRequests(userId: String): Flow<List<RequestService>> {
        return requestDataSource.getCustomerRequests(userId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch customer requests for user ID: $userId") }
    }

    override fun getCraftsManRequests(userId: String): Flow<List<RequestService>> {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return requestDataSource.getCraftsManRequests(userId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch craftsman requests for user ID: $userId") }
    }

    override fun getCraftManOfferOnRequestUseCase(
        craftsManId: String,
        requestId: String
    ): Flow<Offer?> {
        return requestDataSource.getCraftManOfferOnRequestUseCase(craftsManId, requestId)
            .map { it?.toEntity() }
            .catch { throw FailException("Failed to fetch craftsman offer for request ID: $requestId and craftsman ID: $craftsManId") }
    }

    override suspend fun cancelRequest(requestId: String) {
        safeCall(FailException("Failed to cancel request with ID: $requestId")) {
            requestDataSource.cancelRequest(requestId)
        }
    }

    override suspend fun markRequestAsDone(requestId: String) {
        safeCall(FailException("Failed to mark request as done for request ID: $requestId")) {
            requestDataSource.markRequestAsDone(requestId)
        }
    }

    override fun getAcceptedOfferOnRequestUseCase(requestId: String): Flow<Offer?> {
        return requestDataSource.getAcceptedOfferOnRequestUseCase(requestId)
            .map { it?.toEntity() }
            .catch { throw FailException("Failed to fetch accepted offer for request ID: $requestId") }
    }


}