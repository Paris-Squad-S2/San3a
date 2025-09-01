package com.paris_2.san3a.presentation.utill

import android.content.Context
import com.paris_2.san3a.R
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getTimeAgo(dateTime: LocalDateTime?, context: Context): String {
    val timeZone = TimeZone.Companion.currentSystemDefault()
    val now = getCurrentDateTime()
    if (dateTime == null) return ""
    val duration = now.toInstant(timeZone) - dateTime.toInstant(timeZone)
    val minutes = duration.inWholeMinutes
    val hours = duration.inWholeHours
    val days = duration.inWholeDays

    return when {
        days > 0L -> context.getString(R.string.d_ago, days)
        minutes == 0L -> context.getString(R.string.just_now)
        hours > 0L -> context.getString(R.string.h_ago, hours)
        else -> context.getString(R.string.min_ago, minutes)
    }
}