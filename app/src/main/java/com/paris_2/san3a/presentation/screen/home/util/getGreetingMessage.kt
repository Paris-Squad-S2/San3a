package com.paris_2.san3a.presentation.screen.home.util

import com.paris_2.san3a.R
import kotlinx.datetime.LocalDateTime

fun LocalDateTime.getGreetingMessage() = if (time.hour in 5..11) {
    R.string.good_morning
} else {
    R.string.good_afternoon
}