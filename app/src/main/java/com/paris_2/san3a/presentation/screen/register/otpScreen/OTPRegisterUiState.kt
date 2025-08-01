package com.paris_2.san3a.presentation.screen.register.otpScreen

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val secondLeft: Int = 0,
    val errorMessage: String? = null,
    val verificationId: String = "",
)