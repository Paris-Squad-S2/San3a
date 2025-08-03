package com.paris_2.san3a.data.source.remote.user.dto

import com.google.firebase.firestore.DocumentId

data class RequestServiceDto(
    @DocumentId val id: String,
    val title: String,
    val description: String,
    val location: String,
    val relatedJob: String,
    val offers: List<Double>,
    val userId: String,
    val requestedCount: Int = 0,
    val images: List<String> = emptyList()
) {
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): RequestServiceDto {
            return RequestServiceDto(
                id = id,
                title = data["title"] as? String ?: "",
                description = data["description"] as? String ?: "",
                location = data["location"] as? String ?: "",
                relatedJob = data["relatedJob"] as? String ?: "",
                offers = (data["offers"] as? List<*>)
                    ?.mapNotNull { (it as? Number)?.toDouble() } ?: emptyList(),
                userId = data["userId"] as? String ?: "",
                requestedCount = (data["requestedCount"] as? Long)?.toInt() ?: 0
            )
        }
        fun RequestServiceDto.toJson(): Map<String, Any> {
            val map = mutableMapOf<String, Any>(
                "id" to id,
                "title" to title,
                "description" to description,
                "location" to location,
                "relatedJob" to relatedJob,
                "offers" to offers,
                "userId" to userId,
                "requestedCount" to requestedCount,
                "images" to images
            )
            return map
        }

    }
}