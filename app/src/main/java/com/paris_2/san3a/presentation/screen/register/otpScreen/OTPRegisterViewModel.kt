package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.exceptions.InvalidNumberException
import com.paris_2.san3a.domain.exceptions.NoInternetConnectionException
import com.paris_2.san3a.domain.usecase.user.AddUserUseCase
import com.paris_2.san3a.domain.usecase.user.GenerateDeviceTokenUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.SavePhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.shared.utils.UiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    private val savePhoneNumberUseCase: SavePhoneNumberUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val generateDeviceTokenUseCase: GenerateDeviceTokenUseCase,
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
        when (exception) {
            is NoInternetConnectionException -> {
                updateState(
                    screenState.value.copy(isNoInternet = true)
                )
            }

            is InvalidNumberException -> {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        showBottomSheet = true,
                        bottomSheetDrawable = R.drawable.img_placeholder_lllustration1,
                        bottomSheetErrorMessage = UiText.StringResource(R.string.invalid_phone_number),
                        bottomSheetErrorMessageDesc = UiText.StringResource(R.string.please_enter_a_valid_phone_number),
                        otpRegisterUiState = screenState.value
                            .otpRegisterUiState
                            .copy(loadingVerifyButton = false)
                    )
                )
            }

            else -> {
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        showBottomSheet = true,
                        bottomSheetDrawable = R.drawable.img_placeholder_lllustration,
                        bottomSheetErrorMessage = UiText.StringResource(R.string.oops_something_broke),
                        bottomSheetErrorMessageDesc = UiText.StringResource(R.string.Our_team_is_working_on_a_fix__Please_try_again_later_),
                        otpRegisterUiState = screenState.value
                            .otpRegisterUiState
                            .copy(loadingVerifyButton = false)
                    )
                )
            }
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
                    generateDeviceTokenUseCase()
                    updateState(
                        screenState.value.copy(
                            otpRegisterUiState = screenState.value.otpRegisterUiState.copy(
                                loadingVerifyButton = false
                            )
                        )
                    )
                }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        showBottomSheet = false,
                        errorMessage = null,
                        showSnackBarError = false,
                        isLoading = false

                    )
                )
                if (screenState.value.otpRegisterUiState.otp == screenState.value.otpRegisterUiState.verificationId) {
                    navigateToNextScreen()
                } else {
                    updateState(
                        screenState.value.copy(
                            showBottomSheet = false,
                            errorMessage = UiText.StringResource(R.string.otp_code_is_wrong),
                            showSnackBarError = true,
                            isLoading = false

                        )
                    )
                    hideSnackBar()
                }
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        showBottomSheet = true,
                        errorMessage = null,
                        bottomSheetDrawable = R.drawable.img_placeholder_lllustration,
                        bottomSheetErrorMessage = UiText.StringResource(R.string.oops_something_broke),
                        bottomSheetErrorMessageDesc = UiText.StringResource(R.string.Our_team_is_working_on_a_fix__Please_try_again_later_),
                        showSnackBarError = false,
                        isLoading = false
                    )
                )
            }
        )
    }

    private fun navigateToNextScreen() {
        tryToExecute(
            execute = {
                var destination: Destination = Destinations.Account(AccountSetupStep.ACCOUNT_TYPE)
                try {
                    setUpAccountUseCase.getUserProgress(screenState.value.otpRegisterUiState.phoneNumber)
                        .also { progress ->
                            when (progress) {
                                AccountSetupStep.ACCOUNT_TYPE -> {
                                    addUserUseCase(screenState.value.otpRegisterUiState.phoneNumber)
                                    destination =
                                        Destinations.Account(accountSetupStep = AccountSetupStep.ACCOUNT_TYPE)
                                }

                                AccountSetupStep.COMPLETED -> {
                                    val user =
                                        getUserUseCase(screenState.value.otpRegisterUiState.phoneNumber)
                                    destination = when (user.accountType) {
                                        AccountType.CUSTOMER -> {
                                            Destinations.CustomerGraph
                                        }

                                        AccountType.CRAFTSMAN -> {
                                            Destinations.CraftManGraph
                                        }
                                    }
                                }

                                else -> {
                                    destination = Destinations.Account(progress)
                                }
                            }
                        }
                } catch (_: Exception) {
                    addUserUseCase(screenState.value.otpRegisterUiState.phoneNumber)
                    destination = Destinations.Account(AccountSetupStep.ACCOUNT_TYPE)
                }
                destination
            },
            onSuccess = { destination ->
                navigate(
                    destination, navOptions = NavOptions.Builder()
                        .setPopUpTo(Destinations.RegisterScreen, inclusive = true)
                        .build()
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        showBottomSheet = true,
                        bottomSheetDrawable = R.drawable.img_placeholder_lllustration,
                        bottomSheetErrorMessage = UiText.StringResource(R.string.oops_something_broke),
                        bottomSheetErrorMessageDesc = UiText.StringResource(R.string.Our_team_is_working_on_a_fix__Please_try_again_later_),
                    )
                )
            }
        )
    }

    override fun onClickResendCode() {
        if (screenState.value.otpRegisterUiState.secondLeft == 0) {
            sendOtpToPhoneNumber()
        }
        updateSecondLeft()
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

    override fun onDismissSnackBar() {
        updateState(
            screenState.value.copy(
                showSnackBarError = false
            )
        )
    }

    private fun generateOtp(): String = (10000..99999).random().toString()

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBarError) {
                delay(3000)
                updateState(screenState.value.copy(showSnackBarError = false))
            }
        }
    }

}