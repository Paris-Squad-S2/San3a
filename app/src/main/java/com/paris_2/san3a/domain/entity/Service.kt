package com.paris_2.san3a.domain.entity

data class Service(
    val id: String,
    val title: Map<String, String>,
    val description: Map<String, String>,
    val imageUrl: String,
)