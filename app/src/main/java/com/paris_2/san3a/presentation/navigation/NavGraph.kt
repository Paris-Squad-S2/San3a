package com.paris_2.san3a.presentation.navigation

import com.paris_2.san3a.presentation.screen.splash.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.paris_2.san3a.presentation.screen.account.AccountScreen
import com.paris_2.san3a.presentation.screen.home.HomeScreen
import com.paris_2.san3a.presentation.screen.message.details.MessageDetails
import com.paris_2.san3a.presentation.screen.messages.MessagesScreen
import com.paris_2.san3a.presentation.screen.more.MoreScreen
import com.paris_2.san3a.presentation.screen.myRequest.MyRequestScreen
import com.paris_2.san3a.presentation.screen.notifications.NotificationsScreen
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingScreen
import com.paris_2.san3a.presentation.screen.onboarding.onboardingPages
import com.paris_2.san3a.presentation.screen.register.otpScreen.OTPRegisterScreen
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterScreen
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
    navigation<Destinations.MainGraph>(startDestination = Destinations.Splash) {
        composable<Destinations.Home> { HomeScreen() }
        composable<Destinations.Messages> { MessagesScreen() }
        composable<Destinations.MessageDetails> { MessageDetails() }
        composable<Destinations.MyRequest> { MyRequestScreen() }
        composable<Destinations.Notifications> { NotificationsScreen() }
        composable<Destinations.More> { MoreScreen() }
        composable<Destinations.Splash>{ SplashScreen() }
        composable<Destinations.OnBoarding>{ OnBoardingScreen(pages = onboardingPages()) }
        composable<Destinations.OTPRegisterScreen> { OTPRegisterScreen() }
        composable<Destinations.RegisterScreen> { RegisterScreen() }
        composable<Destinations.Account> { AccountScreen() }
    }
}
