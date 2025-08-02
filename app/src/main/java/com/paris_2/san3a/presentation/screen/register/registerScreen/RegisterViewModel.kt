package com.paris_2.san3a.presentation.screen.register.registerScreen

import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterUiState>(RegisterUiState()),
    RegisterInteractionListener {

    override fun onPhoneNumberChanged(phone: String) {
        updateState(
            screenState.value.copy(phoneNumber = phone)
        )
    }

    override fun onClickContinue() {
        val phone = screenState.value.phoneNumber
        val isValid =
            !phone.isNullOrBlank() &&
            phone.removePrefix("+20").length == 10
                    && phone.removePrefix(
                "+20"
            ).all { it.isDigit() }

        if (isValid) {
            navigate(Destinations.OTPRegisterScreen(phone))
        } else {
            updateState(screenState.value.copy(phoneNumber = ""))

        }
    }

    override fun onClickContinueAsGuest() {
        navigate(Destinations.Home)
    }

}