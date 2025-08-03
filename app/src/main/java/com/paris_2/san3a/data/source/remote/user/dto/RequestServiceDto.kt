package com.paris_2.san3a.data.source.remote.user.dto

import com.google.firebase.firestore.DocumentId

data class RequestServiceDto(
    @DocumentId val id: String,
    val serviceType: String,
    val customerComplain : String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val image : List<Int>,
    val relatedJob: String,
    val offers: List<Double>,
    val userId: String,
    val requestedCount: Int = 0
) {
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): RequestServiceDto {
            return RequestServiceDto(
                id = id,
                serviceType = data["title"] as? String ?: "",
                customerComplain = data["customerComplain"] as? String ?: "",
                description = data["description"] as? String ?: "",
                location = data["location"] as? String ?: "",
                locationDetails = data["locationDetails"] as? String ?: "",
                image = (data["image"] as? List<*>)
                    ?.mapNotNull { (it as? Number)?.toInt() } ?: emptyList(),
                relatedJob = data["relatedJob"] as? String ?: "",
                offers = (data["offers"] as? List<*>)
                    ?.mapNotNull { (it as? Number)?.toDouble() } ?: emptyList(),
                userId = data["userId"] as? String ?: "",
                requestedCount = (data["requestedCount"] as? Long)?.toInt() ?: 0
            )
        }
    }
}