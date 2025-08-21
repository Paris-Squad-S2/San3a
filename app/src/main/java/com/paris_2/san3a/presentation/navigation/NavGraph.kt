package com.paris_2.san3a.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.paris_2.san3a.presentation.navigation.graphs.buildCraftManNavGraph
import com.paris_2.san3a.presentation.navigation.graphs.buildCustomerNavGraph
import com.paris_2.san3a.presentation.navigation.graphs.buildMainNavGraph
import org.koin.compose.koinInject

@Composable
fun San3aNavGraph(
    navigator: Navigator = koinInject(),
    navController: NavHostController = rememberNavController(),
) {

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
        buildMainNavGraph()
        buildCustomerNavGraph()
        buildCraftManNavGraph()
    }
}