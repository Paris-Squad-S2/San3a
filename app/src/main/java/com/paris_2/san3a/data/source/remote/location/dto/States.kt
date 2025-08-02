package com.paris_2.san3a.data.source.remote.location.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class States(
    @SerialName("name")
    val countryName: String? = null,
    @SerialName("states")
    val states: List<State?>? = null
)
