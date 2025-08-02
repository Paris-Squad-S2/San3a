package com.paris_2.san3a.presentation.screen.register.registerScreen

data class RegisterUiState(
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

