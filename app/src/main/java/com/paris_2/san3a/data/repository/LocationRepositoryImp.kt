package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toCities
import com.paris_2.san3a.data.mapper.toStates
import com.paris_2.san3a.data.source.remote.location.LocationRemoteDataSource
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest
import com.paris_2.san3a.domain.entity.Cities
import com.paris_2.san3a.domain.entity.States
import com.paris_2.san3a.domain.repository.LocationRepository

class LocationRepositoryImp(
    private val locationRemoteDataSource: LocationRemoteDataSource,
) : LocationRepository, BaseRepository() {
    override suspend fun getGovernmentsInCountry(countryName: String): States =
        locationRemoteDataSource.getGovernmentsInCountry(countryName).toStates()


    override suspend fun getCitiesInGovernment(
        countryName: String,
        stateName: String,
    ): Cities = locationRemoteDataSource.getCitiesInGovernment(
        CitiesRequest(
            countryName,
            stateName
        )
    ).toCities()
}