package com.paris_2.san3a.presentation.screen.account

import com.paris_2.san3a.R

data class AccountScreenUiState(
    val accountUiState: AccountUiState,
    val isLoading: Boolean,
    val errorMassage: String?,
)

data class AccountUiState(
    val progressIndicator: Float,
    val userType: UserType = UserType.CUSTOMER,
    val serviceUiState: List<ServiceUiState>,
    //TODO add location
)

data class ServiceUiState(
    val id: Int = 0,
    val serviceTitle: String = "",
    val isSelected: Boolean = false,
)

enum class UserType(val displayActor: Int) {
    CUSTOMER(R.string.customer),
    CRAFTSMAN(R.string.craftman)
}
