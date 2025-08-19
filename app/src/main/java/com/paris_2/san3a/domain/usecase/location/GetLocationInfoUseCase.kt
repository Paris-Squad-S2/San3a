package com.paris_2.san3a.domain.usecase.location

import com.paris_2.san3a.domain.repository.LocationRepository

class GetLocationInfoUseCase(private val locationRepository: LocationRepository) {
    suspend fun getGovernments() = locationRepository.getGovernorates()

    suspend fun getCities(governorateId: Int) =
        locationRepository.getCitiesInGovernment(governorateId)

    suspend fun getGovernorateById(governorateId: Int) =
        locationRepository.getGovernorateById(governorateId)

    suspend fun getCityById(cityId: Int) = locationRepository.getCityById(cityId)
}