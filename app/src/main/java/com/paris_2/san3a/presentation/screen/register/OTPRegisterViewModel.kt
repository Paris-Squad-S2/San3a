package com.paris_2.san3a.presentation.screen.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OTPRegisterUiState(
    val otp: String = "",
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class OTPRegisterViewModel: ViewModel(), OTPRegisterListenerInteraction {

    private val _uiState = MutableStateFlow(OTPRegisterUiState())
    val uiState = _uiState.asStateFlow()


    override fun onOtpTextChange(otp: String) {
        _uiState.update {
            it.copy(otp = otp)
        }
    }

    override fun onClickVerify() {
        TODO("Not yet implemented")
    }

    override fun onClickResendCode() {
        TODO("Not yet implemented")
    }


}