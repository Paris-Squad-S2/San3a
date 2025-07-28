package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.repository.LocationRepository

class GetCurrentLocationUseCase(private val locationRepository: LocationRepository) {
    suspend fun getCurrentLocation() = locationRepository.getCurrentLocation()
}