package com.paris_2.san3a.presentation.navigation

import androidx.compose.runtime.Composable
import com.paris_2.san3a.presentation.screen.verification.VerificationScreen
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.paris_2.san3a.presentation.screen.account.AccountScreen
import com.paris_2.san3a.presentation.screen.home.craftsman.CraftsmanHomeScreen
import com.paris_2.san3a.presentation.screen.home.customer.CustomerHomeScreen
import com.paris_2.san3a.presentation.screen.messages.MessagesScreen
import com.paris_2.san3a.presentation.screen.messagesDetails.MessageDetails
import com.paris_2.san3a.presentation.screen.myRequest.craftsman.MyJobsScreen
import com.paris_2.san3a.presentation.screen.myRequest.customer.MyRequestScreen
import com.paris_2.san3a.presentation.screen.more.locationScreen.LocationScreen
import com.paris_2.san3a.presentation.screen.more.moreScreen.MoreScreen
import com.paris_2.san3a.presentation.screen.myService.MyServiceScreen
import com.paris_2.san3a.presentation.screen.notification.NotificationScreen
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingScreen
import com.paris_2.san3a.presentation.screen.onboarding.onboardingPages
import com.paris_2.san3a.presentation.screen.register.otpScreen.OTPRegisterScreen
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterScreen
import com.paris_2.san3a.presentation.screen.splash.SplashScreen
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
        composable<Destinations.Splash> { SplashScreen() }
        composable<Destinations.OnBoarding> { OnBoardingScreen(pages = onboardingPages()) }
        composable<Destinations.RegisterScreen> { RegisterScreen() }
        composable<Destinations.OTPRegisterScreen> { OTPRegisterScreen() }
        composable<Destinations.Account> { AccountScreen() }
    }

    navigation<Destinations.CustomerGraph>(startDestination = Destinations.Home) {
        composable<Destinations.Home> { CustomerHomeScreen() }
        composable<Destinations.Messages> { MessagesScreen() }
        composable<Destinations.MessageDetails> { MessageDetails() }
        composable<Destinations.MyRequest> { MyRequestScreen() }
        composable<Destinations.Notification> { NotificationScreen() }
        composable<Destinations.More> { MoreScreen() }
    }

    navigation<Destinations.CraftManGraph>(startDestination = Destinations.Home) {
        composable<Destinations.Home> { CraftsmanHomeScreen() }
        composable<Destinations.Messages> { MessagesScreen() }
        composable<Destinations.MessageDetails> { MessageDetails() }
        composable<Destinations.MyRequest> { MyJobsScreen() }
        composable<Destinations.Notification> { NotificationScreen() }
        composable<Destinations.More> { MoreScreen() }
        composable<Destinations.Verification> { VerificationScreen() }
        composable<Destinations.MyService> { MyServiceScreen() }
        composable<Destinations.Location> { LocationScreen() }

    }
}
