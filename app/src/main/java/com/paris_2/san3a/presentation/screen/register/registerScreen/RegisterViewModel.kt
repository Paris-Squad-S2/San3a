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
        navigate(Destinations.OTPRegisterScreen(screenState.value.phoneNumber))
    }

    override fun onClickContinueAsGuest() {
        navigate(Destinations.Home)
    }

}