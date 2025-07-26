package com.paris_2.san3a.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface San3aNavigator {
    val startGraph: San3aGraph
    val navigationEvent: Flow<San3aNavigationEvent>
    suspend fun navigate(destination: San3aDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}