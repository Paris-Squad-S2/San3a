package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.annotation.StringRes

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val secondLeft: Int = 0,
    @StringRes val errorMessage: Int? = null,
    val verificationId: String = "",
)