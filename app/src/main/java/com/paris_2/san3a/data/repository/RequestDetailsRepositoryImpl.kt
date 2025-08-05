package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDto
import com.paris_2.san3a.data.mapper.toEntity
import com.paris_2.san3a.data.source.remote.requestDetails.RequestDetailsDataSource
import com.paris_2.san3a.domain.AcceptOfferException
import com.paris_2.san3a.domain.AssignRequestToCraftsmanException
import com.paris_2.san3a.domain.GetCraftsmanOffersException
import com.paris_2.san3a.domain.GetOffersException
import com.paris_2.san3a.domain.GetRequestDetailsException
import com.paris_2.san3a.domain.entity.Offer
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.repository.RequestDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RequestDetailsRepositoryImpl(
    private val requestDetailsDataSource: RequestDetailsDataSource
) : RequestDetailsRepository {

    override suspend fun addOffer(offer: Offer) {
        requestDetailsDataSource.addOffer(offer.toDto())
    }

    override fun getAcceptedOffers(requestId: String): Flow<List<Offer>> {
        return requestDetailsDataSource.getAcceptedOffers(requestId)
                .map { list -> list.map { it.toEntity() }}
                .catch { GetOffersException() }

    }

    override fun getOffers(requestId: String): Flow<List<Offer>> {
        return requestDetailsDataSource.getOffers(requestId)
            .map { list -> list.map { it.toEntity() } }
            .catch { GetOffersException() }
    }

    override suspend fun getRequestDetailsById(requestId: String): RequestService {
        return try {
            requestDetailsDataSource.getRequestDetailsById(requestId)?.toEntity() ?:
                throw GetRequestDetailsException()
        }catch (e: Exception) {
            throw GetRequestDetailsException()
        }
    }

    override suspend fun getYourOffer(craftsmanId: String): List<Offer> {
        return try {
            requestDetailsDataSource.getCraftsmanOffers(craftsmanId).map { it.toEntity() }
        } catch (e: Exception) {
            throw GetCraftsmanOffersException()
        }
    }

    override suspend fun assignRequestToCraftsman(requestId: String, craftsmanId: String) {
        try {
            requestDetailsDataSource.assignRequestToCraftsman(requestId, craftsmanId)
        }catch (e: Exception) {
            throw AssignRequestToCraftsmanException()
        }
    }

    override suspend fun acceptOffer(offerId: String) {
        try {
            requestDetailsDataSource.acceptOffer(offerId)
        }catch (e: Exception) {
            throw AcceptOfferException()
        }
    }

}