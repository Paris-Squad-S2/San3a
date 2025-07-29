package com.paris_2.san3a.presentation.screen.register

interface OTPRegisterListenerInteraction {
    fun onOtpTextChange(otp: String)
    fun onClickVerify()
    fun onClickResendCode()
}