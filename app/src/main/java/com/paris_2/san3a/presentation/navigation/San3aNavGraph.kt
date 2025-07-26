package com.paris_2.san3a.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

import org.koin.compose.koinInject

@Composable
fun San3aNavGraph(
    navigator: San3aNavigator = koinInject(),
    startDestination: San3aDestination? = null
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is San3aNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            San3aNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSan3aNavGraph(startDestination)
    }
}

fun NavGraphBuilder.buildSan3aNavGraph(startDestination: San3aDestination? = null) {
    navigation<San3aDestinations.San3aGraph1>(startDestination = startDestination ?: San3aDestinations.San3aScreen) {
    //TODO -- Add the Screens Here
    }
}
