package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.remote.requests.RequestRemoteDataSource
import com.paris_2.san3a.domain.exceptions.FailException
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.RequestsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RequestsRepositoryImpl(
    private val requestRemoteDataSource: RequestRemoteDataSource,
) : RequestsRepository, BaseRepository() {

    override suspend fun addOffer(offer: Offer) {
        requestRemoteDataSource.addOffer(offer.toDto())
    }

    override fun getAcceptedOffers(requestId: String): Flow<List<Offer>> {
        validateNetworkConnection()
        return requestRemoteDataSource.getAcceptedOffers(requestId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch accepted offers for request ID: $requestId") }
    }

    override fun getOffers(requestId: String): Flow<List<Offer>> {
        validateNetworkConnection()
        return requestRemoteDataSource.getOffers(requestId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch offers for request ID: $requestId") }
    }

    override fun getOffersCount(requestId: String): Flow<Int> {
        validateNetworkConnection()
        return requestRemoteDataSource.getOffersCount(requestId)
            .catch { throw FailException("Failed to fetch offers count for request ID: $requestId") }
    }

    override suspend fun getRequestDetailsById(requestId: String): RequestService {
        validateNetworkConnection()
        return safeCall(FailException("Failed to fetch request details for request ID: $requestId")) {
            requestRemoteDataSource.getRequestDetailsById(requestId)?.toEntity()
                ?: throw FailException("Request not found")
        }
    }

    override suspend fun getYourOffer(craftsmanId: String): List<Offer> {
        validateNetworkConnection()
        return safeCall(FailException("Failed to fetch your offers for craftsman ID: $craftsmanId")) {
            requestRemoteDataSource.getCraftsmanOffers(craftsmanId).map { it.toEntity() }
        }
    }

    override suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String) {
        validateNetworkConnection()
        return safeCall(FailException("Failed to assign request to craftsman")) {
            requestRemoteDataSource.assignRequestToCraftsman(requestId, craftsmanId)
        }
    }

    override suspend fun acceptOffer(offerId: String, craftsmanId: String, requestId: String) {
        validateNetworkConnection()
        safeCall(FailException("Failed to accept offer with ID: $offerId")) {
            coroutineScope {
                val accept = async { requestRemoteDataSource.acceptOffer(offerId) }
                val assign =
                    async {
                        requestRemoteDataSource.assignRequestToCraftsman(
                            requestId,
                            craftsmanId
                        )
                    }
                accept.await()
                assign.await()
            }
        }
    }

    override fun getCustomerRequests(userId: String): Flow<List<RequestService>> {
        validateNetworkConnection()
        return requestRemoteDataSource.getCustomerRequests(userId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch customer requests for user ID: $userId") }
    }

    override fun getCraftsManRequests(userId: String): Flow<List<RequestService>> {
        validateNetworkConnection()
        return requestRemoteDataSource.getCraftsManRequests(userId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to fetch craftsman requests for user ID: $userId") }
    }

    override fun getCraftManOfferOnRequestUseCase(
        craftsManId: String,
        requestId: String
    ): Flow<Offer?> {
        validateNetworkConnection()
        return requestRemoteDataSource.getCraftManOfferOnRequestUseCase(craftsManId, requestId)
            .map { it?.toEntity() }
            .catch { throw FailException("Failed to fetch craftsman offer for request ID: $requestId and craftsman ID: $craftsManId") }
    }

    override suspend fun cancelRequest(requestId: String) {
        validateNetworkConnection()
        safeCall(FailException("Failed to cancel request with ID: $requestId")) {
            requestRemoteDataSource.cancelRequest(requestId)
        }
    }

    override suspend fun markRequestAsDone(requestId: String) {
        validateNetworkConnection()
        safeCall(FailException("Failed to mark request as done for request ID: $requestId")) {
            requestRemoteDataSource.markRequestAsDone(requestId)
        }
    }

    override fun getAcceptedOfferOnRequestUseCase(requestId: String): Flow<Offer?> {
        validateNetworkConnection()
        return requestRemoteDataSource.getAcceptedOfferOnRequestUseCase(requestId)
            .map { it?.toEntity() }
            .catch { throw FailException("Failed to fetch accepted offer for request ID: $requestId") }
    }

    override fun getRecentRelatedJobs(relatedJobsIds: List<String>): Flow<List<RequestService>> {
        validateNetworkConnection()
        return requestRemoteDataSource.getRecentRelatedJobs(relatedJobsIds)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw FailException("Failed to get recent related jobs: $relatedJobsIds") }
    }
}