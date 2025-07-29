package com.paris_2.san3a.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val errorMessage: String? = null
)

class OTPRegisterViewModel: ViewModel(), OTPRegisterListenerInteraction {

    private val _uiState = MutableStateFlow(OTPRegisterUiState())
    val uiState = _uiState.asStateFlow()


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
        TODO("Not yet implemented")
    }

    override fun onClickResendCode() {
       viewModelScope.launch {
            _uiState.update {
                it.copy(secondLeft = 59, otp = "", isLoading = true)
            }
       }
    }

    override fun onClickBackButton() {
        TODO("Not yet implemented")
    }


}