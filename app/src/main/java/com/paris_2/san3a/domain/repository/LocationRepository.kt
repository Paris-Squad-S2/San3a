package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate

interface LocationRepository {
    suspend fun getGovernments(): List<Governorate>
    suspend fun getCitiesInGovernment(governorateId: Int): List<City>
}