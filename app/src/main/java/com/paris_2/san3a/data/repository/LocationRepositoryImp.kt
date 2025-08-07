package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toCities
import com.paris_2.san3a.data.mapper.toStates
import com.paris_2.san3a.data.source.remote.location.LocationRemoteDataSource
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest
import com.paris_2.san3a.data.utils.NetworkConnectionChecker
import com.paris_2.san3a.domain.NoCitiesFoundException
import com.paris_2.san3a.domain.NoGovernmentsFoundException
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.Cities
import com.paris_2.san3a.domain.entity.States
import com.paris_2.san3a.domain.repository.LocationRepository

class LocationRepositoryImp(
    private val locationRemoteDataSource: LocationRemoteDataSource,
    private val networkConnectionChecker: NetworkConnectionChecker,
) : LocationRepository, BaseRepository() {
    override suspend fun getGovernmentsInCountry(countryName: String): States {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return safeCall(
            exception = NoGovernmentsFoundException(),
            call = { locationRemoteDataSource.getGovernmentsInCountry(countryName).toStates() })
    }

    override suspend fun getCitiesInGovernment(
        countryName: String,
        stateName: String,
    ): Cities {
        if (networkConnectionChecker.isConnected.value.not()) {
            throw NoInternetConnectionException()
        }

        return safeCall(
            exception = NoCitiesFoundException(),
            call = {
                locationRemoteDataSource.getCitiesInGovernment(
                    CitiesRequest(
                        "Egypt", stateName
                    )
                ).toCities()
            })
    }
}