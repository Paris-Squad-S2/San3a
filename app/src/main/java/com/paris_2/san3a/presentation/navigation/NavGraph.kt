package com.paris_2.san3a.presentation.navigation

import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingScreen
import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.compose.composable
import com.paris_2.san3a.presentation.screen.HomeScreen
import com.paris_2.san3a.presentation.screen.onboarding.onboardingPages

import org.koin.compose.koinInject

@Composable
fun San3aNavGraph(
    navigator: Navigator = koinInject(),
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
        buildSan3aNavGraph()
    }
}

fun NavGraphBuilder.buildSan3aNavGraph() {
    navigation<Destinations.MainGraph>(startDestination = Destinations.Splash) {
        composable<Destinations.Splash>{ SplashScreen() }
        composable<Destinations.OnBoarding>{ OnBoardingScreen(pages = onboardingPages()) }
        composable<Destinations.Home>{ HomeScreen() }
    }
}
