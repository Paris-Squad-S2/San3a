package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import com.paris_2.san3a.R

data class AccountScreenUiState(
    val accountUiState: AccountUiState = AccountUiState(),
    val isLoading: Boolean = false,
    val errorMassage: String? = "",
)

data class AccountUiState(
    val pageNumber: String = "",
    val userType: UserType? = null,
    val serviceUiState: List<ServiceUiState> = emptyList(),
    val locationUiState: LocationUiState = LocationUiState(),
    val customerName: String = "",
    val customerProfilePhotoUri: Uri? = null,
)

data class ServiceUiState(
    val id: String = "",
    val serviceTitle: String = "",
    val serviceDescription: String = "",
    val isSelected: Boolean = false,
)

data class LocationUiState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val countryName: String = "",
    val cityName: String = "",
)

enum class UserType(val displayActor: Int) {
    CUSTOMER(R.string.customer),
    CRAFTSMAN(R.string.craftman)
}
