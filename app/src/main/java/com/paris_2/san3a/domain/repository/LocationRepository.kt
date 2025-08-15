package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate

interface LocationRepository {
    suspend fun getGovernorates(): List<Governorate>
    suspend fun getCitiesInGovernment(governorateId: Int): List<City>
    suspend fun getGovernorateById(governorateId: Int): Governorate?
    suspend fun getCityById(cityId: Int): City?
}