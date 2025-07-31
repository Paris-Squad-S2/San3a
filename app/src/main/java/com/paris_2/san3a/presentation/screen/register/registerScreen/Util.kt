package com.paris_2.san3a.presentation.screen.register.registerScreen


fun formatPhoneNumber(phoneNumber: String): String {
    return when {
        phoneNumber.length <= 3 -> phoneNumber
        phoneNumber.length <= 7 -> "${phoneNumber.substring(0, 3)} - ${phoneNumber.substring(3)}"
        else -> "${phoneNumber.substring(0, 3)} - ${
            phoneNumber.substring(
                3,
                7
            )
        } - ${phoneNumber.substring(7)}"
    }
}