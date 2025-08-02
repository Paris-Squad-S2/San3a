package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toStates
import com.paris_2.san3a.data.source.remote.location.LocationRemoteDataSource
import com.paris_2.san3a.domain.entity.Cities
import com.paris_2.san3a.domain.entity.States
import com.paris_2.san3a.domain.repository.LocationRepository

class LocationRepositoryImp(
    private val locationRemoteDataSource: LocationRemoteDataSource,
) : LocationRepository, BaseRepository() {
    override suspend fun getGovernmentsInCountry(countryName: String): States {
        return locationRemoteDataSource.getGovernmentsInCountry(countryName).toStates()
    }

    override suspend fun getCitiesInGovernment(
        countryName: String,
        governmentName: String,
    ): Cities {
        TODO("Not yet implemented")
    }

}