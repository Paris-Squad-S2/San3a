package com.paris_2.san3a.domain.entity

data class RequestService(
    val id: String,
    val serviceType: String,
    val customerComplain: String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val image : List<Int>,
    val relatedJob: String,
    val offers: List<Double>,
    val userId: String,
    val requestedCount: Int = 0
)