package com.paris_2.san3a.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.onboarding.components.CardsSection
import com.paris_2.san3a.presentation.screen.onboarding.components.OffersSection


data class Page(
    val item: @Composable () -> Unit,
    val title: String,
    val description: String
)


@Composable
fun onboardingPages(): List<Page> {
    return listOf(
        Page(
            item = {
                Image(
                    painter = painterResource(id = R.drawable.img_onboarding1),
                    contentDescription = "Onboarding 1"
                )
            },
            title = stringResource(id = R.string.onboarding1title),
            description = stringResource(id = R.string.onboarding1description)
        ),
        Page(
            item = { CardsSection() },
            title = stringResource(id = R.string.onboarding2title),
            description = stringResource(id = R.string.onboarding2description)
        ),
        Page(
            item = {OffersSection(
                images = listOf(
                    painterResource(id = R.drawable.img_craftsman_offer3),
                    painterResource(id = R.drawable.img_craftsman_offer1),
                    painterResource(id = R.drawable.img_craftsman_offer2)
                )
            )},
            title = stringResource(id = R.string.onboarding3title),
            description = stringResource(id = R.string.onboarding3description)
        )
    )
}

