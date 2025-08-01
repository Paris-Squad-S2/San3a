package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<OTPRegisterUiState>(OTPRegisterUiState()), OTPRegisterListenerInteraction,
    KoinComponent {

    private val otp = generateOtp()

    init {
        val phoneNumber = saveStateHandle.toRoute<Destinations.OTPRegisterScreen>().phoneNumber
        updateState(screenState.value.copy(phoneNumber = phoneNumber))
        sendOtpToPhoneNumber()

    }

    private fun sendOtpToPhoneNumber() {
        tryToExecute(
            execute = {
                sendOtpUseCase(
                    screenState.value.phoneNumber,
                    "Your verification code is ${generateOtp()}"
                )
            },
            onSuccess = ::onSendOtpToPhoneNumberSuccess,
            onError = ::onSendOtpToPhoneNumberError
        )

    }

    private fun onSendOtpToPhoneNumberSuccess(isSend: Boolean) {
        if (isSend) {
            screenState.value.copy(verificationId = otp)
        }

    }

    private fun onSendOtpToPhoneNumberError(message: String) {
        screenState.value.copy(errorMessage = message)
    }

    private fun updateSecondLeft() {
        var timerJob: Job? = null

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            screenState.value.copy(secondLeft = 59)
            while (screenState.value.secondLeft > 0) {
                delay(1000)
                screenState.value.copy(secondLeft = screenState.value.secondLeft - 1)

            }
        }

    }

    override fun onOtpTextChange(otp: String) {
        screenState.value.copy(otp = otp)

    }

    override fun onClickVerify() {

        viewModelScope.launch {
            try {
                // if code is right should navigate to next screen
                if (screenState.value.otp == screenState.value.verificationId) {
                    savePhoneNumberUseCase(screenState.value.phoneNumber)
                    navigate(Destinations.Home)
                }
            } catch (e: Exception) {
                screenState.value.copy(errorMessage = e.message)
            }

        }
    }

    override fun onClickResendCode() {
        updateSecondLeft()
    }

    override fun onClickBackButton() {
        navigateUp()
    }

    fun generateOtp(): String = (10000..99999).random().toString()

}