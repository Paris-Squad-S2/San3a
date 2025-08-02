package com.paris_2.san3a.data.repository

import android.util.Log
import com.paris_2.san3a.data.mapper.toCities
import com.paris_2.san3a.data.mapper.toStates
import com.paris_2.san3a.data.source.remote.location.LocationRemoteDataSource
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest
import com.paris_2.san3a.domain.NoCitiesFoundException
import com.paris_2.san3a.domain.NoGovernmentsFoundException
import com.paris_2.san3a.domain.entity.Cities
import com.paris_2.san3a.domain.entity.States
import com.paris_2.san3a.domain.repository.LocationRepository

class LocationRepositoryImp(
    private val locationRemoteDataSource: LocationRemoteDataSource,
) : LocationRepository, BaseRepository() {
    override suspend fun getGovernmentsInCountry(countryName: String): States {
        Log.d(
            "TAG",
            "getGovernments: in RpoImp ${locationRemoteDataSource.getGovernmentsInCountry(countryName).data?.states?.size} "
        )
        return safeCall(
            exception = NoGovernmentsFoundException(),
            call = { locationRemoteDataSource.getGovernmentsInCountry(countryName).toStates() })
    }

    override suspend fun getCitiesInGovernment(
        countryName: String,
        stateName: String,
    ): Cities = safeCall(
        exception = NoCitiesFoundException(), call = {
            locationRemoteDataSource.getCitiesInGovernment(
                CitiesRequest(
                    countryName, stateName
                )
            ).toCities()
        })
}