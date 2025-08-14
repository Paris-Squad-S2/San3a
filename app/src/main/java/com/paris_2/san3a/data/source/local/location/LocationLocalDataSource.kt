package com.paris_2.san3a.data.source.local.location

import com.paris_2.san3a.data.source.local.location.dto.CityDto
import com.paris_2.san3a.data.source.local.location.dto.GovernorateDto

interface LocationLocalDataSource {
    suspend fun getGovernorates(): List<GovernorateDto>
    suspend fun getCities(governorateId: Int): List<CityDto>
    suspend fun getGovernorateById(governorateId: Int): GovernorateDto?
    suspend fun getCityById(cityId: Int): CityDto?
}