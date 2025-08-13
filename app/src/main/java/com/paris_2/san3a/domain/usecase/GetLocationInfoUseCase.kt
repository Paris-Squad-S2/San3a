package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.LocationRepository

class GetLocationInfoUseCase(private val locationRepository: LocationRepository) {
    suspend fun getGovernments() = locationRepository.getGovernments()

    suspend fun getCities(governorateId: Int) =
        locationRepository.getCitiesInGovernment(governorateId = governorateId)
}