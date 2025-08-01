package com.paris_2.san3a.data.repository

import com.paris_2.san3a.domain.repository.LocationRepository

class LocationRepositoryImp(): LocationRepository {
    override suspend fun getGovernmentsInCountry(countryName: String): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getCitiesInGovernment(governmentName: String): List<String> {
        TODO("Not yet implemented")
    }
}