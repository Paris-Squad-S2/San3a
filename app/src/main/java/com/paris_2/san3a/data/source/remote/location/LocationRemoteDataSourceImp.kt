package com.paris_2.san3a.data.source.remote.location

import com.paris_2.san3a.data.service.location.LocationService
import com.paris_2.san3a.data.source.remote.location.dto.CitiesDto
import com.paris_2.san3a.data.source.remote.location.dto.StatesDto
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest

class LocationRemoteDataSourceImp(private val locationService: LocationService) :
    LocationRemoteDataSource {

    override suspend fun getGovernmentsInCountry(countryName: String): StatesDto =
        locationService.getStates(mapOf("country" to countryName))


    override suspend fun getCitiesInGovernment(citiesRequest: CitiesRequest): CitiesDto =
        locationService.getCities(body = citiesRequest)

}