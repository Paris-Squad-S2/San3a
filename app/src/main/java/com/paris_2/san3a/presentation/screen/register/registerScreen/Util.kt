package com.paris_2.san3a.presentation.screen.register.registerScreen

fun formatPhoneNumber(input: String): String {
    val digits = input.filter { it.isDigit() }.take(11)
    return buildString {
        for (i in digits.indices) {
            append(digits[i])
            when (i) {
                2, 6 -> append(" - ")
            }
        }
    }
}