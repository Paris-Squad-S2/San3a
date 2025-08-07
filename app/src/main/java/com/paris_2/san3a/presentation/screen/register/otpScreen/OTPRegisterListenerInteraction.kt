package com.paris_2.san3a.presentation.screen.register.otpScreen

interface OTPRegisterListenerInteraction {
    fun onOtpTextChange(otp: String)
    fun onClickVerify()
    fun onClickResendCode()
    fun onClickBackButton()
    fun onClickRetry()
    fun onHideBottomSheet()
    fun onDismissSnackBar()
}