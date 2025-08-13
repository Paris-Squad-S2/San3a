package com.paris_2.san3a.data.source.remote.location

import android.util.Log
import com.paris_2.san3a.data.service.location.LocationService
import com.paris_2.san3a.data.source.remote.location.dto.CitiesDto
import com.paris_2.san3a.data.source.remote.location.dto.StatesDto
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest

class LocationRemoteDataSourceImp(
    private val locationService: LocationService,
) :
    LocationRemoteDataSource {

    override suspend fun getGovernmentsInCountry(countryName: String): StatesDto {
        val c = Country()
        Log.d(
            "TAG",
            "getGovernments: in Remote Data Source ${locationService.getStates(c).data?.states} "
        )
        return locationService.getStates(c)
    }

    override suspend fun getCitiesInGovernment(citiesRequest: CitiesRequest): CitiesDto =
        locationService.getCities(body = citiesRequest)

}

data class Country(
    val country: String ="Egypt"
)