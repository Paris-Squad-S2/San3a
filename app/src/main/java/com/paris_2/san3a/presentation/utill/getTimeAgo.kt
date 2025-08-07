package com.paris_2.san3a.presentation.utill

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getTimeAgo(dateTime: LocalDateTime): String { // TODO: Translate this to Arabic
    val timeZone = TimeZone.Companion.currentSystemDefault()
    val now = getCurrentDateTime()
    val duration = now.toInstant(timeZone) - dateTime.toInstant(timeZone)
    val hours = duration.inWholeHours
    return if (hours == 0L) {
        val minutes = duration.inWholeMinutes
        "$minutes min ago"
    } else {
        "$hours h ago"
    }
}