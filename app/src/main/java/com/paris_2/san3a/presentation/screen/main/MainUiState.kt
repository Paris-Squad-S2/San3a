package com.paris_2.san3a.presentation.screen.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class MainUiState(
    val isDark: Flow<Boolean> = flowOf(false),
)
