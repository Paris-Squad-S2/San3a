package com.paris_2.san3a.presentation.screen.splash

import com.paris_2.san3a.presentation.navigation.Destination

data class SplashScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val destination: Destination? = null
)