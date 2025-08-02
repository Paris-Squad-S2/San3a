package com.paris_2.san3a.data.source.remote.location.dto

import com.google.gson.annotations.SerializedName

class CitiesDto(
    @SerializedName("data")
    val data: List<String>? = null,
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("msg")
    val msg: String? = null,
)