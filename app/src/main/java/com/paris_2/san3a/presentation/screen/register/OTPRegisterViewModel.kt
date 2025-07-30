package com.paris_2.san3a.presentation.screen.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.SendOtpUseCase
import com.paris_2.san3a.domain.usecase.VerifyOtpUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val secondLeft: Int = 59,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val verficationId: String = ""
)

class OTPRegisterViewModel(
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase
): ViewModel(), OTPRegisterListenerInteraction {

    private val _uiState = MutableStateFlow(OTPRegisterUiState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
           val foo =  sendOtpUseCase("")
            _uiState.update {
                it.copy(verficationId = foo)
            }

        }
    }
    fun updateSecondLeft(){
        viewModelScope.launch {
            while (_uiState.value.secondLeft > 0) {
                delay(1000)
                _uiState.update {
                    it.copy(secondLeft = _uiState.value.secondLeft - 1)
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
                verifyOtpUseCase(_uiState.value.verficationId, _uiState.value.otp)

            }catch (e: Exception){
                Log.e("OTPRegisterViewModel", "onClickVerify: ${e.message}")
                _uiState.update { it.copy(errorMessage = e.message)  }
            }

        }
    }

    override fun onClickResendCode() {
       viewModelScope.launch {
            _uiState.update {
                it.copy(secondLeft = 59, otp = "", isLoading = true)
            }
       }
    }

    override fun onClickBackButton() {

    }


}