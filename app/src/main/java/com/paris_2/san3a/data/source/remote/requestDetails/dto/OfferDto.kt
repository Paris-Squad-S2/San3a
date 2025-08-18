package com.paris_2.san3a.data.source.remote.requestDetails.dto

import com.google.firebase.firestore.DocumentId

data class OfferDto(
    @DocumentId val id: String = "",
    val requestId: String,
    val craftsmanId: String,
    val price: Double,
    val preferredDate: String,
    val preferredTime: String,
    val createdAt: String,
    val messageToCustomer: String,
    val isAccepted: Boolean
){
    companion object {
        fun fromJson(data: Map<String, Any>, id: String): OfferDto {
            return OfferDto(
                id = id,
                requestId = data["requestId"] as? String ?: "",
                craftsmanId = data["craftsmanId"] as? String ?: "",
                price = (data["price"] as? Number)?.toDouble() ?: 0.0,
                preferredDate = data["preferredDate"] as? String ?: "",
                preferredTime = data["preferredTime"] as? String ?: "",
                createdAt = data["createdAt"] as? String ?: "",
                messageToCustomer = data["messageToCustomer"] as? String ?: "",
                isAccepted = data["isAccepted"] as? Boolean ?: false
            )
        }
    }
    fun toJson(): Map<String, Any> {
        return mapOf(
            "requestId" to requestId,
            "craftsmanId" to craftsmanId,
            "price" to price,
            "preferredDate" to preferredDate,
            "preferredTime" to preferredTime,
            "createdAt" to createdAt,
            "messageToCustomer" to messageToCustomer,
            "isAccepted" to isAccepted
        )
    }
}