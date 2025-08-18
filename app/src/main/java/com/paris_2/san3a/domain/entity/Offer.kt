package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Offer(
    val id: String,
    val requestId: String,
    val craftsmanId: String,
    val price: Double,
    val preferredDate: LocalDate,
    val preferredTime: LocalTime,
    val createdAt: LocalDateTime,
    val messageToCustomer: String,
    val isAccepted: Boolean
)