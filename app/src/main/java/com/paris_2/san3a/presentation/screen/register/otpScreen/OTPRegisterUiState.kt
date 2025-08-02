package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.annotation.StringRes

data class OTPRegisterScreenState(
    val otpRegisterUiState: OTPRegisterUiState = OTPRegisterUiState(),
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    @StringRes val snackBarMessage: Int? = null,
    val showSnackBar: Boolean = false,
)

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val secondLeft: Int = 0,
    val verificationId: String = "",
    val loadingVerifyButton: Boolean = false
)