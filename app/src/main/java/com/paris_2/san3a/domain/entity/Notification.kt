package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: String,
    val title: String,
    val caption: String,
    val dateTime: LocalDateTime,
)

data class NotificationToSend (
    val title: Map<String, String>,
    val caption: Map<String, String>,
)
