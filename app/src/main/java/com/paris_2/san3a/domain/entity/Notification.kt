package com.paris_2.san3a.domain.entity

import java.time.LocalDate

data class Notification(
    val id: String,
    val title: String,
    val caption: String,
    val date: LocalDate
)
