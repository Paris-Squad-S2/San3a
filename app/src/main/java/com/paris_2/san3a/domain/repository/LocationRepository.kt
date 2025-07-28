package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
}