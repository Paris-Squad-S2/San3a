package com.paris_2.san3a.data.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getCurrentDate(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
}