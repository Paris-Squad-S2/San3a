package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class RequestService(
    val id: String,
    val userId: String,
    val requestStatus: RequestStatus,
    val serviceId: String,
    val title: String,
    val serviceType: String,
    val description: String,
    val governorateId: Int,
    val cityId: Int,
    val locationDetails: String,
    val time: LocalDateTime,
    val state: String,
    val image : List<String>,
    val selectedCraftsmanId: String?
)

enum class RequestStatus {
    ONGOING,
    COMPLETED,
    CANCELLED
}