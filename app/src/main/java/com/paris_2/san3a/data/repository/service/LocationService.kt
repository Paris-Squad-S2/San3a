package com.paris_2.san3a.data.repository.service

import com.paris_2.san3a.data.source.remote.dto.LocationDto

interface LocationService {
    suspend fun getCurrentLocation(): LocationDto?
}