package com.paris_2.san3a.domain.entity

data class MostRequestedServices(
    val id: String,
    val title: String,
    val description: String,
    val userId: String,
    val requestedCount: Int = 0
)
