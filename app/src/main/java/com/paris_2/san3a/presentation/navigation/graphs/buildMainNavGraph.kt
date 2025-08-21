package com.paris_2.san3a.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.account.AccountScreen
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingScreen
import com.paris_2.san3a.presentation.screen.onboarding.onboardingPages
import com.paris_2.san3a.presentation.screen.register.otpScreen.OTPRegisterScreen
import com.paris_2.san3a.presentation.screen.register.registerScreen.RegisterScreen
import com.paris_2.san3a.presentation.screen.splash.SplashScreen

fun NavGraphBuilder.buildMainNavGraph() {
    navigation<Destinations.MainGraph>(startDestination = Destinations.Splash) {
        composable<Destinations.Splash> { SplashScreen() }
        composable<Destinations.OnBoarding> { OnBoardingScreen(pages = onboardingPages()) }
        composable<Destinations.RegisterScreen> { RegisterScreen() }
        composable<Destinations.OTPRegisterScreen> { OTPRegisterScreen() }
        composable<Destinations.Account> { AccountScreen() }
    }
}