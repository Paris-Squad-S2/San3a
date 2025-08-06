package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.requestDetails.RequestDataSource
import com.paris_2.san3a.domain.AcceptOfferException
import com.paris_2.san3a.domain.AssignRequestToCraftsmanException
import com.paris_2.san3a.domain.GetCraftsmanOffersException
import com.paris_2.san3a.domain.GetOffersException
import com.paris_2.san3a.domain.GetRequestDetailsException
import com.paris_2.san3a.domain.GetRequestsException
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.RequestDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RequestDetailsRepositoryImpl(
    private val requestDataSource: RequestDataSource
) : RequestDetailsRepository, BaseRepository() {

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

    override suspend fun acceptOffer(offerId: String) {
        safeCall(AcceptOfferException()) {
            requestDataSource.acceptOffer(offerId)
        }
    }

}