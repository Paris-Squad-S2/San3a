package com.paris_2.san3a.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation

import org.koin.compose.koinInject

@Composable
fun NavGraph(
    navigator: Navigator = koinInject(),
    startDestination: Destination? = null
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            NavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSan3aNavGraph(startDestination)
    }
}

fun NavGraphBuilder.buildSan3aNavGraph(startDestination: Destination? = null) {
    navigation<Destinations.Graph1>(startDestination = startDestination ?: Destinations.Screen) {
    //TODO -- Add the Screens Here
    }
}
