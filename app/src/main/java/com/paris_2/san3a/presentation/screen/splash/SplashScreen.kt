package com.paris_2.san3a.presentation.screen.splash

import android.util.Log
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
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.IsOnboardingCompletedUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.navigation.Destination
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
    getUserUseCase: GetUserUseCase = koinInject(),
    setUpAccountUseCase: SetUpAccountUseCase = koinInject(),
) {
    LaunchedEffect(Unit) {
        var destination: Destination = Destinations.OnBoarding

        val phoneNumber = getPhoneNumberUseCase()
        if (phoneNumber.isNotBlank()) {
            setUpAccountUseCase.getUserProgress(phoneNumber).also { progress ->
                when(progress){
                    AccountSetupStep.ACCOUNT_TYPE -> {
                        destination = if (isOnboardingCompletedUseCase()) {
                            Destinations.RegisterScreen
                        } else {
                            Destinations.OnBoarding
                        }
                    }
                    AccountSetupStep.COMPLETED -> {
                        getUserUseCase(phoneNumber).also { user ->
                            when(user.accountType){
                                AccountType.CUSTOMER -> {
                                    LocalAccountType.value = AccountType.CUSTOMER
                                    destination = Destinations.CustomerGraph
                                }
                                AccountType.CRAFTSMAN -> {
                                    LocalAccountType.value = AccountType.CRAFTSMAN
                                    destination = Destinations.CraftManGraph
                                }
                            }
                        }
                    }
                    else -> {
                        Log.d("SplashScreen", "User progress: $progress")
                        destination = Destinations.Account(progress)
                    }
                }
            }
        } else if (isOnboardingCompletedUseCase()) {
            destination = Destinations.RegisterScreen
        } else {
            destination = Destinations.OnBoarding
        }

        delay(2000)

        navigator.navigate(
            destination = destination,
            navOptions = NavOptions.Builder()
                .setPopUpTo(Destinations.Splash, inclusive = true)
                .build()
        )
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