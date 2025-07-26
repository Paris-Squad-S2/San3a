package com.paris_2.san3a.presentation.navigation

import androidx.navigation.NavOptions

sealed class San3aNavigationEvent {
    data class Navigate(val destination: San3aDestination, val navOptions: NavOptions? = null): San3aNavigationEvent()
    data object NavigateUp: San3aNavigationEvent()
}