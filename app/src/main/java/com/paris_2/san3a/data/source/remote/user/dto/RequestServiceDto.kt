package com.paris_2.san3a.data.source.remote.user.dto

import com.google.firebase.firestore.DocumentId

data class RequestServiceDto(
    @DocumentId val id: String = "",
    val title: String,
    val description: String,
    val governorateId: Int,
    val cityId: Int,
    val locationDetails: String,
    val time: String?,
    val image: List<String>,
    val userId: String,
    val serviceId: String,
    val selectedCraftsmanId: String?,
    val requestStatus: String?,
    val createdAt: Long = System.currentTimeMillis(),
) {
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): RequestServiceDto {
            return RequestServiceDto(
                id = id,
                title = data["title"] as? String ?: "",
                description = data["description"] as? String ?: "",
                governorateId = (data["governorateId"] as? Number)?.toInt() ?: 0,
                cityId = (data["cityId"] as? Number)?.toInt() ?: 0,
                locationDetails = data["locationDetails"] as? String ?: "",
                time = data["time"] as? String,
                image = (data["image"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                userId = data["userId"] as? String ?: "",
                serviceId = data["serviceId"] as? String ?: "",
                selectedCraftsmanId = data["selectedCraftsmanId"] as? String,
                requestStatus = data["requestStatus"] as? String,
                createdAt = (data["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis()
            )
        }
    }

    fun toJson(): Map<String, Any> {
        val map = mutableMapOf(
            "title" to title,
            "description" to description,
            "governorateId" to governorateId,
            "cityId" to cityId,
            "locationDetails" to locationDetails,
            "image" to image,
            "userId" to userId,
            "serviceId" to serviceId,
            "createdAt" to createdAt
        )
        if (selectedCraftsmanId != null) {
            map["selectedCraftsmanId"] = selectedCraftsmanId
        }
        if (requestStatus != null) {
            map["requestStatus"] = requestStatus
        }
        if (time != null) {
            map["time"] = time
        }
        return map
    }
}