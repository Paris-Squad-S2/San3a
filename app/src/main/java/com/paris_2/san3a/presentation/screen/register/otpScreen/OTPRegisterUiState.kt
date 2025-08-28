package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.annotation.DrawableRes
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.utils.UiText

data class OTPRegisterScreenState(
    val otpRegisterUiState: OTPRegisterUiState = OTPRegisterUiState(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val showBottomSheet: Boolean = false,
    @DrawableRes val bottomSheetDrawable: Int = R.drawable.img_placeholder_lllustration,
    val bottomSheetErrorMessage: UiText = UiText.StringResource(R.string.oops_something_broke),
    val bottomSheetErrorMessageDesc: UiText = UiText.StringResource(R.string.Our_team_is_working_on_a_fix__Please_try_again_later_),
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