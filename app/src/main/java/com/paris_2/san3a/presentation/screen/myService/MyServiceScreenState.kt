package com.paris_2.san3a.presentation.screen.myService

import com.paris_2.san3a.presentation.screen.account.ServiceUiState
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.UiText

data class MyServiceScreenState(
    val myServiceUiState: List<ServiceUiState> = emptyList(),
    val isLoading: Boolean = true,
    val successMessageSnackBar: UiText? = null,
    val errorMessage: UiText? = null,
    val showSnackBarSuccess: Boolean = false,
    val showSnackBarError: Boolean = false,
    val isNoInternet: Boolean = false,
    val phoneNumber: String = "",
    val isCraftsman: Boolean = false,
    val serviceButtonState: AppButtonState = AppButtonState.Enable
)