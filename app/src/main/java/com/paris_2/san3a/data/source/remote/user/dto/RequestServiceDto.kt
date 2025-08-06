package com.paris_2.san3a.data.source.remote.user.dto

import com.google.firebase.firestore.DocumentId

data class RequestServiceDto(
    @DocumentId val id: String = "",
    val title: String,
    val serviceType: String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val time: String,
    val state: String,
    val image: List<String>,
    val offers: List<Double>,
    val userId: String,
    val selectedCraftsmanId: String
) {
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): RequestServiceDto {
            return RequestServiceDto(
                id = id,
                title = data["title"] as? String ?: "",
                serviceType = data["serviceType"] as? String ?: "",
                description = data["description"] as? String ?: "",
                location = data["location"] as? String ?: "",
                locationDetails = data["locationDetails"] as? String ?: "",
                time = data["time"] as? String ?: "",
                state = data["state"] as? String ?: "",
                image = (data["image"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                offers = (data["offers"] as? List<*>)?.mapNotNull { (it as? Number)?.toDouble() }
                    ?: emptyList(),
                userId = data["userId"] as? String ?: "",
                selectedCraftsmanId = data["selectedCraftsmanId"] as? String ?: ""
            )
        }
    }

    fun toJson(): Map<String, Any> {
        val map = mutableMapOf(
            "title" to title,
            "serviceType" to serviceType,
            "description" to description,
            "location" to location,
            "locationDetails" to locationDetails,
            "image" to image,
            "offers" to offers,
            "userId" to userId,
            "selectedCraftsmanId" to selectedCraftsmanId,
            "time" to time,
            "state" to state
        )
        return map
    }
}