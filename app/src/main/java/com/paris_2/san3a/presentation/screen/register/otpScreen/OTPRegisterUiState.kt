package com.paris_2.san3a.presentation.screen.register.otpScreen

import com.paris_2.san3a.presentation.shared.utils.UiText

data class OTPRegisterScreenState(
    val otpRegisterUiState: OTPRegisterUiState = OTPRegisterUiState(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val showBottomSheet: Boolean = false,
    val isNoInternet: Boolean = false,
    val showSnackBarError: Boolean = false
)

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val secondLeft: Int = 0,
    val verificationId: String = "",
    val loadingVerifyButton: Boolean = false,
)