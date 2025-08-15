package com.paris_2.san3a.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashScreenViewModel: SplashScreenViewModel = koinViewModel(),
) {
    val state by splashScreenViewModel.screenState.collectAsStateWithLifecycle()
    when{
        state.isLoading ->{
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

        state.error!=null-> {
            LostConnectionScreen(
                onRetry = splashScreenViewModel::onRetryClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen)
                    .padding(horizontal = 60.dp)
            )
        }
        else -> splashScreenViewModel.navigateToDestination()

    }
}