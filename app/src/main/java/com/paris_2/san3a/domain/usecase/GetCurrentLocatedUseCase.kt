package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.LocationRepository

class GetCurrentLocatedUseCase(private val locationRepository: LocationRepository) {
    suspend fun getGovernments(countryName: String) =
        locationRepository.getGovernmentsInCountry(countryName)
    suspend fun getCities(countryName: String, stateName: String) =locationRepository.getCitiesInGovernment(
        countryName,
        stateName,
    )
}