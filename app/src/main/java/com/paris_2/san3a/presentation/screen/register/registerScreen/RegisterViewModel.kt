package com.paris_2.san3a.presentation.screen.register.registerScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.paris_2.san3a.presentation.navigation.Destination
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.navigation.Navigator
import com.paris_2.san3a.presentation.shared.components.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegisterViewModel : ViewModel(), RegisterInteractionListener, KoinComponent {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val navigator: Navigator by inject()

    protected fun navigate(destination: Destination, navOptions: NavOptions? = null) =
        viewModelScope.launch {
            navigator.navigate(destination = destination, navOptions = navOptions)
        }

    override fun onCountrySelected(country: Country) {
        _uiState.value = _uiState.value.copy(
            selectedCountry = country,
            phoneNumber = country.code
        )
    }

    override fun onToggleCountryDropdown() {
        _uiState.value =
            _uiState.value.copy(isCountryDropdownExpanded = !_uiState.value.isCountryDropdownExpanded)
    }

    override fun onDismissCountryDropdown() {
        _uiState.value = _uiState.value.copy(isCountryDropdownExpanded = false)
    }

    override fun onPhoneNumberChanged(phone: String) {
        _uiState.value =_uiState.value.copy(phoneNumber = phone)
    }

    override fun onClickContinue() {
        Log.e("RegisterScreenToOtpScreen","Called")
        navigate(Destinations.OTPRegisterScreen)
    }

    override fun onClickContinueAsGuest() {
    }

}