package com.paris_2.san3a.data.mapper

import android.location.Location
import com.paris_2.san3a.data.source.remote.dto.LocationDto

fun Location.toLocationDto(): LocationDto {
    return LocationDto(
        latitude = this.latitude,
        longitude = this.longitude
    )
}