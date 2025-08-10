package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: String,
    val title: String,
    val caption: String,
    val date: LocalDateTime,
    val userId: String,
)
