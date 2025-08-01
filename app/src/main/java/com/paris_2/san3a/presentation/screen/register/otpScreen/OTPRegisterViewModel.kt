package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paris_2.san3a.R
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
            updateState(screenState.value.copy(verificationId = otp))
        }

    }

    private fun onSendOtpToPhoneNumberError(message: String) {
        updateState(screenState.value.copy(errorMessage = R.string.occurred_an_error_for_sending_otp))
    }

    private fun updateSecondLeft() {
        var timerJob: Job? = null

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            updateState(screenState.value.copy(secondLeft = 59))
            while (screenState.value.secondLeft > 0) {
                delay(1000)
                updateState(screenState.value.copy(secondLeft = screenState.value.secondLeft - 1))

            }
        }

    }

    override fun onOtpTextChange(otp: String) {
        if (otp.isDigitsOnly()) {
            updateState(screenState.value.copy(otp = otp))
        }
    }

    override fun onClickVerify() {

        tryToExecute(
            execute = {
                if (screenState.value.otp == screenState.value.verificationId) {
                    savePhoneNumberUseCase(screenState.value.phoneNumber)
                    navigate(Destinations.Home)
                } 
            },
            onError = { errorMessage ->
                updateState(screenState.value.copy(errorMessage = R.string.otp_code_is_incorrect))
            }
        )
    }

    override fun onClickResendCode() {
        updateSecondLeft()
        if (screenState.value.secondLeft == 0) {
            sendOtpToPhoneNumber()
        }
    }

    override fun onClickBackButton() {
        navigateUp()
    }

    fun generateOtp(): String = (10000..99999).random().toString()

}