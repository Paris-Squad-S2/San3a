package com.paris_2.san3a.presentation.utill

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun minusNHours(dateTime: LocalDateTime, n: Int): LocalDateTime {
    val tz = TimeZone.Companion.currentSystemDefault()
    val duration = Duration.Companion.parse("PT${n}H")
    return dateTime.toInstant(tz)
        .minus(duration)
        .toLocalDateTime(tz)
}