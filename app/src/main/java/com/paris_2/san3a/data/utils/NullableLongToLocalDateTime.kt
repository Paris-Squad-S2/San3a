package com.paris_2.san3a.data.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
fun Long?.toLocalDateTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this ?: System.currentTimeMillis())
        .toLocalDateTime(TimeZone.UTC)
}