package com.paris_2.san3a.data.source.local.location.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GovernorateDto(
    val id: Int,
    @SerialName("name_en")
    val englishName: String,
    @SerialName("name_ar")
    val arabicName: String
)