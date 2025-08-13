package com.paris_2.san3a.data.source.local.location

import com.paris_2.san3a.data.source.local.location.dto.CityDto
import com.paris_2.san3a.data.source.local.location.dto.GovernorateDto

interface LocationLocalDataSource {
    fun getGovernorates(): List<GovernorateDto>
    fun getCities(governorateId: Int): List<CityDto>
}