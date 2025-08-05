package com.paris_2.san3a.domain.entity

data class RequestService(
    val id: String,
    val title: String,
    val serviceType: String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val time: String,
    val state: String,
    val image : List<String>,
    val offers: List<Double>,
    val userId: String,
    val selectedCraftsmanId: String
)