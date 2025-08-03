package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Cities
import com.paris_2.san3a.domain.entity.States

interface LocationRepository {
    suspend fun getGovernmentsInCountry(countryName: String): States
    suspend fun getCitiesInGovernment(countryName: String, stateName: String): Cities
}