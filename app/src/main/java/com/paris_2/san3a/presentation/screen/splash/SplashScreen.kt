package com.paris_2.san3a.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptions
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.navigation.Navigator
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigator: Navigator = koinInject(),
    isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase = koinInject(),
    getPhoneNumberUseCase: GetPhoneNumberUseCase = koinInject(),
    getUserUseCase: GetUserUseCase = koinInject()
) {
    LaunchedEffect(Unit) {
        delay(2000)
        val phoneNumber = getPhoneNumberUseCase()
        if (phoneNumber.isNotBlank()) {
            getUserUseCase(phoneNumber).also {
                when(it.accountType){
                    AccountType.CUSTOMER -> {
                        navigator.navigate(
                            destination = Destinations.CustomerGraph,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(Destinations.OnBoarding, inclusive = true)
                                .build()
                        )
                    }
                    AccountType.CRAFTSMAN -> {
                        navigator.navigate(
                            destination = Destinations.CraftManGraph,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(Destinations.OnBoarding, inclusive = true)
                                .build()
                        )
                    }
                }
            }
        } else if (isOnboardingCompletedUseCase()) {
            navigator.navigate(
                destination = Destinations.RegisterScreen,
                navOptions = NavOptions.Builder()
                    .setPopUpTo(Destinations.OnBoarding, inclusive = true)
                    .build()
            )
        } else {
            navigator.navigate(
                Destinations.OnBoarding, NavOptions.Builder()
                    .setPopUpTo(Destinations.Splash, true)
                    .build()
            )
        }

    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.colors.background.screen),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Splash Background",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp)
        )
    }
}