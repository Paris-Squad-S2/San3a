package com.paris_2.san3a.data.source.remote.user.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Timestamp(
    @SerialName("high")
    val high: Int? = null,
    @SerialName("low")
    val low: Int? = null,
    @SerialName("unsigned")
    val unsigned: Boolean? = null
)