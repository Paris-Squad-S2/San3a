package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDate

data class Notification(
    val id: String,
    val title: String,
    val caption: String,
    val date: LocalDate,
    val userId: String,
)
