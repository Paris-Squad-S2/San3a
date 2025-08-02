package com.paris_2.san3a.data.source.remote.location

import com.paris_2.san3a.data.source.remote.location.dto.CitiesDto
import com.paris_2.san3a.data.source.remote.location.dto.StatesDto
import com.paris_2.san3a.data.source.remote.location.request.CitiesRequest

interface LocationRemoteDataSource {
    suspend fun getGovernmentsInCountry(countryName: String): StatesDto
    suspend fun getCitiesInGovernment(citiesRequest: CitiesRequest): CitiesDto
}