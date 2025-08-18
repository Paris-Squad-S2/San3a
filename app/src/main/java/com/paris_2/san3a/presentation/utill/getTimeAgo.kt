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
    val hours = duration.inWholeHours
    return if (hours == 0L) {
        val minutes = duration.inWholeMinutes
        context.getString(R.string.min_ago, minutes)
    } else {
        context.getString(R.string.h_ago, hours)
    }
}