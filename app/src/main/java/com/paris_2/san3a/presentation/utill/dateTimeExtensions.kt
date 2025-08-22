package com.paris_2.san3a.presentation.utill

import android.content.Context
import com.paris_2.san3a.R
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalDateTime.minusNHours(n: Int): LocalDateTime {
    val tz = TimeZone.Companion.currentSystemDefault()
    val duration = Duration.Companion.parse("PT${n}H")
    return this.toInstant(tz)
        .minus(duration)
        .toLocalDateTime(tz)
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.plusNHours(n: Int): LocalDateTime {
    val tz = TimeZone.Companion.currentSystemDefault()
    val duration = Duration.Companion.parse("PT${n}H")
    return this.toInstant(tz)
        .plus(duration)
        .toLocalDateTime(tz)
}

fun LocalDate.plusNDays(n: Int): LocalDate {
    return this.plus(n, DateTimeUnit.DAY)
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.plusNDays(n: Int): LocalDateTime {
    val tz = TimeZone.currentSystemDefault()
    return this.toInstant(tz)
        .plus(n, DateTimeUnit.DAY, tz)
        .toLocalDateTime(tz)
}

fun LocalDateTime.format(context: Context): String {
    val now = getCurrentDateTime()
    val isToday = this.date == now.date
    val isTomorrow = this.date == now.date.plusNDays(1)
    val hour = (this.hour % 12).let { if (it == 0) 12 else it }
    val minute = this.minute.toString().padStart(2, '0')
    val amPm = context.getString(if (this.hour < 12) R.string.am else R.string.pm)
    return when {
        isToday -> context.getString(R.string.today_time, hour.toString(), minute, amPm)
        isTomorrow -> context.getString(R.string.tomorrow_time, hour.toString(), minute, amPm)
        else -> context.getString(R.string.date_time, this.date.toString(), hour.toString(), minute, amPm)
    }
}