package com.paris_2.san3a.data.source.local.location.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    @SerialName("name_en")
    val englishName: String,
    @SerialName("name_ar")
    val arabicName: String,
    @SerialName("governorate_id")
    val governorateId: Int
)