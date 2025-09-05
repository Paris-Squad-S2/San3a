package com.paris_2.san3a.presentation.shared.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * A composition local for managing language state across the entire app.
 * This provides immediate language updates without waiting for datastore persistence.
 */
val LocalAppLanguage = staticCompositionLocalOf<MutableState<String>> { 
    mutableStateOf("en") 
}

/**
 * Hook to get current language state
 */
@Composable
fun rememberAppLanguage(): MutableState<String> {
    return LocalAppLanguage.current
}

/**
 * Hook to get current language value
 */
@Composable
fun currentAppLanguage(): String {
    return LocalAppLanguage.current.value
}