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
                userId = data["userId"] as? String ?: ""
            )
        }
    }
}