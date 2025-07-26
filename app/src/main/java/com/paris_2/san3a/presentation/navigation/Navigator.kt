package com.paris_2.san3a.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface SearchNavigator {
    val startGraph: SearchGraph
    val searchNavigationEvent: Flow<SearchNavigationEvent>
    suspend fun navigate(destination: SearchDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}