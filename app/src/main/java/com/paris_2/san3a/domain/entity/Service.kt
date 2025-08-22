package com.paris_2.san3a.domain.entity

data class Service(
    val id: String,
    val title: String,
    val description: String,
    val suggestions: List<String>,
    val imageUrl: String,
    val hint: String,
    val iconImageUrl: String,
    val colorCode: String,
)