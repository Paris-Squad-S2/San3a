package com.paris_2.san3a.domain.entity

data class Offer(
    val id: String,
    val requestId: String,
    val craftsmanId: String,
    val price: Double,
    val preferredDate: String,
    val preferredTime: String,
    val messageToCustomer: String,
    val isAccepted: Boolean
)