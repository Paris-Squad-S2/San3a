package com.paris_2.san3a.data.source.remote.location.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CitiesDto(
    @SerialName("data")
    val cities: List<String>? = null,
    @SerialName("error")
    val error: Boolean? = null,
    @SerialName("msg")
    val msg: String? = null
)