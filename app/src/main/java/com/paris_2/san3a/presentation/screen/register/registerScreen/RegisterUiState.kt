package com.paris_2.san3a.presentation.screen.register.registerScreen

import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.Country


object CountryProvider {
    val defaultCountries = listOf(
        Country("EG", "+20", R.drawable.ic_eg_flag),
        Country("Iraq", "+964", R.drawable.ic_iraq_flag),
        Country("UK", "+44", R.drawable.ic_uk_flag)
    )
}

data class RegisterUiState(
    val selectedCountry: Country = CountryProvider.defaultCountries[0],
    val countries: List<Country> = CountryProvider.defaultCountries,
    val isCountryDropdownExpanded: Boolean = false,
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

