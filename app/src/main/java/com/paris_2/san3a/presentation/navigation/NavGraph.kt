package com.paris_2.san3a.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.paris_2.san3a.presentation.screen.register.otpScreen.OTPRegisterScreen
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterScreen
import com.paris_2.san3a.presentation.screen.home.HomeScreen
import com.paris_2.san3a.presentation.screen.messages.MessagesScreen
import com.paris_2.san3a.presentation.screen.more.MoreScreen
import com.paris_2.san3a.presentation.screen.myRequest.MyRequestScreen

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
        buildSan3aNavGraph()
    }
}

fun NavGraphBuilder.buildSan3aNavGraph() {
    navigation<Destinations.Graph1>(startDestination = Destinations.Home) {
        composable<Destinations.Home> { HomeScreen() }
        composable<Destinations.Messages> { MessagesScreen() }
        composable<Destinations.MyRequest> { MyRequestScreen() }
        composable<Destinations.More> { MoreScreen() }
        composable<Destinations.OTPRegisterScreen> { OTPRegisterScreen() }
        composable<Destinations.RegisterScreen> { RegisterScreen() }
    }
}
