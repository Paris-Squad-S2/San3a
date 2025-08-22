package com.paris_2.san3a.presentation.utill

fun Long.formatToCurrency(): String {
    return String.format("%,d", this)
}