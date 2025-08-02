package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.SetLoginUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    private val setLoginUseCase: SetLoginUseCase,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<OTPRegisterScreenState>(OTPRegisterScreenState()), OTPRegisterListenerInteraction,
    KoinComponent {

    private val otp = generateOtp()

    init {
        val phoneNumber = saveStateHandle.toRoute<Destinations.OTPRegisterScreen>().phoneNumber
        updateState(
            screenState.value.copy(
                otpRegisterUiState = screenState.value.otpRegisterUiState.copy(phoneNumber = phoneNumber),
            )
        )
        sendOtpToPhoneNumber()
        if (screenState.value.otpRegisterUiState.otp.count() == 5) {
            verifyOTP()
        }
    }

    private fun sendOtpToPhoneNumber() {
        updateState(screenState.value.copy(
            otpRegisterUiState = screenState.value
                .otpRegisterUiState
                .copy(loadingVerifyButton = true)
        ))
        tryToExecute(
            execute = {
                sendOtpUseCase(
                    screenState.value.otpRegisterUiState.phoneNumber,
                    "Your verification code is ${generateOtp()}"
                )
            },
            onSuccess = ::onSendOtpToPhoneNumberSuccess,
            onError = ::onSendOtpToPhoneNumberError
        )

    }

    private fun onSendOtpToPhoneNumberSuccess(isSend: Boolean) {
        if (isSend) {
            updateState(
                screenState.value.copy(
                    errorMessage = null,
                    otpRegisterUiState = screenState.value.
                    otpRegisterUiState.copy(
                        verificationId = otp,
                        loadingVerifyButton = false
                    ),
                    showSnackBar = false
                )
            )
        }

    }

    private fun onSendOtpToPhoneNumberError(message: String) {
        updateState(
            screenState.value.copy(
                errorMessage = null,
                snackBarMessage = R.string.occurred_an_error_for_sending_otp,
                showSnackBar = true,
                otpRegisterUiState = screenState.value
                    .otpRegisterUiState
                    .copy(loadingVerifyButton = false)
            )
        )
    }

    private fun updateSecondLeft() {
        var timerJob: Job? = null

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            updateState(
                screenState.value.copy(
                    otpRegisterUiState = screenState.value
                        .otpRegisterUiState
                        .copy(secondLeft = 59)
                )
            )
            while (screenState.value.otpRegisterUiState.secondLeft > 0) {
                delay(1000)
                updateState(
                    screenState.value.copy(
                        otpRegisterUiState = screenState.value
                            .otpRegisterUiState
                            .copy(secondLeft = screenState.value.otpRegisterUiState.secondLeft - 1)
                    )
                )

            }
        }

    }

    override fun onOtpTextChange(otp: String) {
        if (otp.isDigitsOnly()) {
            updateState(screenState.value.copy(
                otpRegisterUiState = screenState.value.otpRegisterUiState.copy(
                    otp = otp
                )
            ))
        }
    }

    override fun onClickVerify() {
        verifyOTP()
    }

    private fun verifyOTP() {
        tryToExecute(
            execute = {
                updateState(screenState.value.copy(isLoading = true))
                delay(1000)
                if (screenState.value.otpRegisterUiState.otp == screenState.value.otpRegisterUiState.verificationId) {
                    savePhoneNumberUseCase(screenState.value.otpRegisterUiState.phoneNumber)
                    updateState(screenState.value.copy(isLoading = false))
                    setLoginUseCase(true)
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
        if (screenState.value.otpRegisterUiState.secondLeft == 0) {
            sendOtpToPhoneNumber()
        }
    }

    override fun onClickBackButton() {
        navigateUp()
    }

    override fun onClickRetry() {
        updateState(screenState.value.copy(isLoading = true, errorMessage = null))
    }

    fun generateOtp(): String = (10000..99999).random().toString()

}