package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.NoInternetConnectionException
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.SetLoginUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    private val setLoginUseCase: SetLoginUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getUserUseCase: GetUserUseCase,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<OTPRegisterScreenState>(OTPRegisterScreenState()),
    OTPRegisterListenerInteraction {

    private val otp = generateOtp()

    init {
        val phoneNumber = saveStateHandle.toRoute<Destinations.OTPRegisterScreen>().phoneNumber
        updateState(
            screenState.value.copy(
                otpRegisterUiState = screenState.value.otpRegisterUiState.copy(phoneNumber = phoneNumber),
            )
        )
        sendOtpToPhoneNumber()

    }

    private fun sendOtpToPhoneNumber() {
        updateState(
            screenState.value.copy(
                isLoading = false,
                otpRegisterUiState = screenState.value
                    .otpRegisterUiState
                    .copy(loadingVerifyButton = true)
            )
        )
        tryToExecute(
            execute = {
                sendOtpUseCase(
                    screenState.value.otpRegisterUiState.phoneNumber,
                    "Your verification code is $otp"
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
                    isNoInternet = false,
                    otpRegisterUiState = screenState.value.otpRegisterUiState.copy(
                        verificationId = otp,
                        loadingVerifyButton = false
                    ),
                    showBottomSheet = false
                )
            )
        }

    }

    private fun onSendOtpToPhoneNumberError(exception: Throwable) {
        if (exception is NoInternetConnectionException) {
            updateState(
                screenState.value.copy(isNoInternet = true)
            )
        } else {
            updateState(
                screenState.value.copy(
                    isLoading = false,
                    showBottomSheet = true,
                    otpRegisterUiState = screenState.value
                        .otpRegisterUiState
                        .copy(loadingVerifyButton = false)
                )
            )
        }
    }

    var timerJob: Job? = null
    private fun updateSecondLeft() {

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
            updateState(
                screenState.value.copy(
                    otpRegisterUiState = screenState.value.otpRegisterUiState.copy(
                        otp = otp
                    )
                )
            )
        }
    }

    override fun onClickVerify() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        otpRegisterUiState = screenState.value.otpRegisterUiState.copy(
                            loadingVerifyButton = true
                        )
                    )
                )

                if (screenState.value.otpRegisterUiState.otp == screenState.value.otpRegisterUiState.verificationId) {
                    savePhoneNumberUseCase(screenState.value.otpRegisterUiState.phoneNumber)
                    updateState(
                        screenState.value.copy(
                            otpRegisterUiState = screenState.value.otpRegisterUiState.copy(
                                loadingVerifyButton = false
                            )
                        )
                    )
                    setLoginUseCase(true)
                }
            },
            onSuccess = {
                navigateToNextScreen()
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        showBottomSheet = true
                    )
                )
            }
        )
    }

    private fun navigateToNextScreen() {
        tryToExecute(
            execute = {
                val phoneNumber = screenState.value.otpRegisterUiState.phoneNumber
                setUpAccountUseCase.getUserProgress(phoneNumber).also { progress ->
                    when (progress) {
                        AccountSetupStep.ACCOUNT_TYPE -> {
                            navigate(
                                Destinations.Account(accountSetupStep = progress), navOptions = NavOptions.Builder()
                                    .setPopUpTo(Destinations.RegisterScreen, inclusive = true)
                                    .build()
                            )
                        }
                        AccountSetupStep.COMPLETED -> {
                            val user = getUserUseCase(phoneNumber)
                            when (user.accountType) {
                                AccountType.CUSTOMER -> {
                                    navigate(
                                        Destinations.CustomerGraph,
                                        navOptions = NavOptions.Builder()
                                            .setPopUpTo(Destinations.Splash, inclusive = true)
                                            .build()
                                    )
                                }
                                AccountType.CRAFTSMAN -> {
                                    navigate(
                                        Destinations.CraftManGraph,
                                        navOptions = NavOptions.Builder()
                                            .setPopUpTo(Destinations.Splash, inclusive = true)
                                            .build()
                                    )
                                }
                            }
                        }
                        else -> {
                            navigate(
                                Destinations.Account(progress),
                                navOptions = NavOptions.Builder()
                                    .setPopUpTo(Destinations.RegisterScreen, inclusive = true)
                                    .build()
                            )
                        }
                    }
                }
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        showBottomSheet = true
                    )
                )
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
        updateState(
            screenState.value.copy(
                isLoading = true,
                errorMessage = null,
                isNoInternet = false
            )
        )
        sendOtpToPhoneNumber()

    }

    override fun onHideBottomSheet() {
        updateState(
            screenState.value.copy(
                showBottomSheet = false
            )
        )
    }

    fun generateOtp(): String = (10000..99999).random().toString()

}