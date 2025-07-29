package com.paris_2.san3a.data.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalDateTime.toLong(): Long {
    return this.toInstant(TimeZone.UTC).toEpochMilliseconds()
}