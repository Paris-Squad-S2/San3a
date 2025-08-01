package com.paris_2.san3a.presentation.screen.register.otpScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.navigation.Navigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    saveStateHandle: SavedStateHandle,
) : ViewModel(), OTPRegisterListenerInteraction, KoinComponent {

    private val _uiState = MutableStateFlow(OTPRegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val navigator: Navigator by inject()

    protected fun navigate(destination: Destination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    protected fun navigateUp() = viewModelScope.launch { navigator.navigateUp() }

    init {
        val phoneNumber = saveStateHandle.toRoute<Destinations.OTPRegisterScreen>().phoneNumber
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
        sendOtpToPhoneNumber()

    }

    private fun sendOtpToPhoneNumber() {
        viewModelScope.launch {
            try {
                val otp = generateOtp()
                sendOtpUseCase("", "Your verification code is $otp")
                _uiState.update { it.copy(verificationId = otp) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun updateSecondLeft() {
        var timerJob: Job? = null

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _uiState.update { it.copy(secondLeft = 59) }
            while (_uiState.value.secondLeft > 0) {
                delay(1000)
                _uiState.update {
                    it.copy(secondLeft = it.secondLeft - 1)
                }
            }
        }

    }

    override fun onOtpTextChange(otp: String) {
        _uiState.update {
            it.copy(otp = otp)
        }
    }

    override fun onClickVerify() {
        viewModelScope.launch {
            try {
                // if code is right should navigate to next screen
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
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