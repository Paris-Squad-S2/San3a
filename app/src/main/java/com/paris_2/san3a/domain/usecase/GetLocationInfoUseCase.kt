package com.paris_2.san3a.domain.usecase

import android.util.Log
import com.paris_2.san3a.domain.entity.States
import com.paris_2.san3a.domain.repository.LocationRepository

class GetLocationInfoUseCase(private val locationRepository: LocationRepository) {
    suspend fun getGovernments(countryName: String): States {
        Log.d(
            "TAG",
            "getGovernments: in Remote Data Source ${locationRepository.getGovernmentsInCountry(countryName).names} "
        )
       return locationRepository.getGovernmentsInCountry(countryName)
    }

    suspend fun getCities(countryName: String, stateName: String) =
        locationRepository.getCitiesInGovernment(
            countryName,
            stateName,
        )
}