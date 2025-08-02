package com.paris_2.san3a.data.source.remote.location.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class State(
    @SerialName("name")
    val name: String? = null,
    @SerialName("state_code")
    val stateCode: String? = null,
)
