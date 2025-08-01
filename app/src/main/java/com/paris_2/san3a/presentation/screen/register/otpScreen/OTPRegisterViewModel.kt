package com.paris_2.san3a.presentation.screen.register.otpScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val secondLeft: Int = 0,
    val errorMessage: String? = null,
    val verificationId: String = "",
)

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase
): ViewModel(), OTPRegisterListenerInteraction {

    private val _uiState = MutableStateFlow(OTPRegisterUiState())
    val uiState = _uiState.asStateFlow()

    init {
        sendOtpToPhoneNumber()
    }

    private fun sendOtpToPhoneNumber() {
        viewModelScope.launch {
            try {
                val otp = generateOtp()
                 sendOtpUseCase("","Your verification code is $otp")
                _uiState.update { it.copy(verificationId = otp) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun updateSecondLeft(){
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
            }catch (e: Exception){
                Log.e("OTPRegisterViewModel", "onClickVerify: ${e.message}")
                _uiState.update { it.copy(errorMessage = e.message)  }
            }

        }
    }

    override fun onClickResendCode() {
        updateSecondLeft()
    }

    override fun onClickBackButton() {

    }
    fun generateOtp(): String = (10000..99999).random().toString()

}