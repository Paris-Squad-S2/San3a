package com.paris_2.san3a.domain.entity

import okhttp3.Request

data class RequestService(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val relatedJob: String,
    val offers: List<Double>,
    val userId: String,
)