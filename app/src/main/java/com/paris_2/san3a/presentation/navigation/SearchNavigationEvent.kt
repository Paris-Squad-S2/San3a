package com.paris_2.san3a.presentation.navigation

import androidx.navigation.NavOptions

sealed class SearchNavigationEvent {
    data class Navigate(val destination: SearchDestination, val navOptions: NavOptions? = null): SearchNavigationEvent()
    data object NavigateUp: SearchNavigationEvent()
}