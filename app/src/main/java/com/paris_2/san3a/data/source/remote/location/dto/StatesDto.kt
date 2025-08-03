package com.paris_2.san3a.data.source.remote.location.dto

import com.google.gson.annotations.SerializedName

data class StatesDto(
    @SerializedName("data")
    val data: Data? = null,
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("msg")
    val msg: String? = null
)

data class Data(
    @SerializedName("iso2")
    val iso2: String? = null,
    @SerializedName("iso3")
    val iso3: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("states")
    val states: List<State?>? = null
)

data class State(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("state_code")
    val stateCode: String? = null
)


