package com.paris_2.san3a.domain.entity

data class AppLocation(
    val latitude: Double,
    val longitude: Double,
    val cityName: String = "",
    val countryName: String = "",
)
