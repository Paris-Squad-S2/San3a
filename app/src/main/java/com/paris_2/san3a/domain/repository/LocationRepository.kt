package com.paris_2.san3a.domain.repository

interface LocationRepository {
    suspend fun getGovernmentsInCountry(countryName: String): List<String>
    suspend fun getCitiesInGovernment(governmentName: String): List<String>
}