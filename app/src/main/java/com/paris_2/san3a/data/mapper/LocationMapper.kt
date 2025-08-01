package com.paris_2.san3a.data.mapper

import android.location.Location
import com.paris_2.san3a.data.source.remote.dto.LocationDto
import com.paris_2.san3a.domain.entity.AppLocation

fun Location.toLocationDto(): LocationDto {
    return LocationDto(
        latitude = this.latitude,
        longitude = this.longitude
    )
}

fun LocationDto.toLocation(): AppLocation{
    return AppLocation(
        latitude = this.latitude,
        longitude = this.longitude,
    )
}