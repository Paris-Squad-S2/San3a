package com.paris_2.san3a.domain.entity

data class MostRequestedServices(
    val id: String,
    val serviceType: String,
    val description: String,
    val requestedCount: Int = 0
)
