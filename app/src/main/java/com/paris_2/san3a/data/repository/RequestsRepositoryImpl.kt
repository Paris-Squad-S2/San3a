package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.requestDetails.RequestDataSource
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.AcceptOfferException
import com.paris_2.san3a.domain.AssignRequestToCraftsmanException
import com.paris_2.san3a.domain.CancelRequestException
import com.paris_2.san3a.domain.GetAcceptedOfferOnRequestException
import com.paris_2.san3a.domain.GetCraftsmanOfferOnRequestException
import com.paris_2.san3a.domain.GetCraftsmanOffersException
import com.paris_2.san3a.domain.GetCraftsmanRequestsException
import com.paris_2.san3a.domain.GetCustomerRequestsException
import com.paris_2.san3a.domain.GetOffersCountException
import com.paris_2.san3a.domain.GetOffersException
import com.paris_2.san3a.domain.GetRequestDetailsException
import com.paris_2.san3a.domain.MarkRequestAsDoneException
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
                .catch { throw GetOffersException() }

    }

    override fun getOffers(requestId: String): Flow<List<Offer>> {
        return requestDataSource.getOffers(requestId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw GetOffersException() }
    }

    override fun getOffersCount(requestId: String): Flow<Int> {
        return requestDataSource.getOffersCount(requestId)
            .catch { throw GetOffersCountException() }
    }

    override suspend fun getRequestDetailsById(requestId: String): RequestService {
        return safeCall(GetRequestDetailsException()){
            requestDataSource.getRequestDetailsById(requestId)?.toEntity() ?:
                throw GetRequestDetailsException()
        }
    }

    override suspend fun getYourOffer(craftsmanId: String): List<Offer> {
        return safeCall(GetCraftsmanOffersException()) {
            requestDataSource.getCraftsmanOffers(craftsmanId).map { it.toEntity() }
        }
    }

    override suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String) {
        return safeCall(AssignRequestToCraftsmanException()) {
            requestDataSource.assignRequestToCraftsman(requestId, craftsmanId)
        }
    }

    override suspend fun acceptOffer(offerId: String, craftsmanId: String, requestId: String) {
        safeCall(AcceptOfferException()) {
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
            .catch { throw GetCustomerRequestsException() }
    }

    override fun getCraftsManRequests(userId: String): Flow<List<RequestService>> {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }
        return requestDataSource.getCraftsManRequests(userId)
            .map { list -> list.map { it.toEntity() } }
            .catch { throw GetCraftsmanRequestsException() }
    }

    override fun getCraftManOfferOnRequestUseCase(
        craftsManId: String,
        requestId: String
    ): Flow<Offer?> {
        return requestDataSource.getCraftManOfferOnRequestUseCase(craftsManId, requestId)
            .map { it?.toEntity() }
            .catch { throw GetCraftsmanOfferOnRequestException() }
    }

    override suspend fun cancelRequest(requestId: String) {
        safeCall(CancelRequestException()) {
            requestDataSource.cancelRequest(requestId)
        }
    }

    override suspend fun markRequestAsDone(requestId: String) {
        safeCall(MarkRequestAsDoneException()) {
            requestDataSource.markRequestAsDone(requestId)
        }
    }

    override fun getAcceptedOfferOnRequestUseCase(requestId: String): Flow<Offer?> {
        return requestDataSource.getAcceptedOfferOnRequestUseCase(requestId)
            .map { it?.toEntity() }
            .catch { throw GetAcceptedOfferOnRequestException() }
    }


}