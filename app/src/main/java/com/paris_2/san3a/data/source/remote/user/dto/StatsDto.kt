package com.paris_2.san3a.data.source.remote.user.dto

data class StatsDto(
    val userId: String,
    val jobsDone: Int,
    val earnings: Double,
    val rating: Double
){
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): StatsDto {
            return StatsDto(
                userId = id,
                jobsDone = (data["jobsDone"] as? Long)?.toInt() ?: 0,
                earnings = (data["earnings"] as? Double) ?: 0.0,
                rating = (data["rating"] as? Double) ?: 0.0
            )
        }
    }
    fun toJson(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "jobsDone" to jobsDone,
            "earnings" to earnings,
            "rating" to rating
        )
    }
}