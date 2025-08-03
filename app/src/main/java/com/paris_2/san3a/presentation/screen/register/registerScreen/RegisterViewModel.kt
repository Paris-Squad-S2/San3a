package com.paris_2.san3a.presentation.screen.register.registerScreen

import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.register.components.BottomSheetType
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterUiState>(RegisterUiState()),
    RegisterInteractionListener {

    override fun onPhoneNumberChanged(phone: String) {
        val cleanedPhone = phone.removePrefix("+20")
        val isValid = cleanedPhone.length == 10 && cleanedPhone.all { it.isDigit() }

        updateState(
            screenState.value.copy(
                phoneNumber = phone,
                isPhoneValid = isValid
            )
        )
    }

    override fun onClickContinue() {
        val phone = screenState.value.phoneNumber
        val isValid = screenState.value.isPhoneValid

        if (isValid) {
            navigate(Destinations.OTPRegisterScreen(phone))
        } else {
            updateState(screenState.value.copy(phoneNumber = ""))
        }
    }

    override fun changeBottomSheetType(bottomSheetType: BottomSheetType?) {
        updateState(
            screenState.value.copy(
                bottomSheetType = bottomSheetType
            )
        )
    }
}
