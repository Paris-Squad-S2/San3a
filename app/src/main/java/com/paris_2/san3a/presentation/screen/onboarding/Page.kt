package com.paris_2.san3a.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.onboarding.components.Cards
import com.paris_2.san3a.presentation.screen.onboarding.components.Offers

data class Page(
    val item: @Composable () -> Unit,
    val title: String,
    val description: String,
)

@Composable
fun onboardingPages(): List<Page> {
    return listOf(
        Page(
            item = {
                Image(
                    painter = painterResource(id = R.drawable.img_onboarding1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = ""
                )
            },
            title = stringResource(id = R.string.onboarding1title),
            description = stringResource(id = R.string.onboarding1description)
        ),
        Page(
            item = { Cards() },
            title = stringResource(id = R.string.onboarding2title),
            description = stringResource(id = R.string.onboarding2description)
        ),
        Page(
            item = {
                Offers(modifier = Modifier.padding(horizontal = 16.dp))
            },
            title = stringResource(id = R.string.onboarding3title),
            description = stringResource(id = R.string.onboarding3description)
        )
    )
}

