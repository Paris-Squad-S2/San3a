package com.paris_2.san3a.domain.entity

data class Location(
    val governmentId: Int,
    val cityId: Int,
    val addressInDetails: String,
){
    companion object {
        fun fromJson(data: Map<String, Any>) : Location {
            val governmentId = (data["governmentId"] as? Number)?.toInt() ?: 0
            val cityId = (data["cityId"] as? Number)?.toInt() ?: 0
            val addressInDetails = (data["addressInDetails"] as? String).orEmpty()
            return Location(governmentId, cityId, addressInDetails)
        }
    }
}