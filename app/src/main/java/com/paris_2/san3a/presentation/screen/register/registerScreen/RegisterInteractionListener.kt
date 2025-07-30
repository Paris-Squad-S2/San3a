package com.paris_2.san3a.presentation.screen.register.registerScreen

import com.paris_2.san3a.presentation.shared.components.Country

interface RegisterInteractionListener {
    fun onCountrySelected(country: Country)

    fun onToggleCountryDropdown()

    fun onDismissCountryDropdown()

    fun onPhoneNumberChanged(phone: String)

    fun onClickContinue()

    fun onClickContinueAsGuest()
}