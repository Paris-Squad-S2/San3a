package com.paris_2.san3a.presentation.screen.register.registerScreen

import com.paris_2.san3a.presentation.screen.register.components.BottomSheetType

data class RegisterUiState(
    val phoneNumber: String = "",
    val isPhoneValid: Boolean = false,
    val bottomSheetType: BottomSheetType? = null
)

