package com.paris_2.san3a.presentation.screen.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.onboarding.sections.BottomSection
import com.paris_2.san3a.presentation.screen.onboarding.sections.TextSection
import com.paris_2.san3a.presentation.screen.onboarding.sections.TopSection
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    pages: List<Page>,
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    when {
        state.error != null -> {
            LostConnectionScreen(
                onRetry = {
                    viewModel.setOnboardingCompleted()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen)
                    .padding(horizontal = 60.dp)
            )
        }

        state.isCompleted.not() -> {
            OnBoardingScreenContent(
                pages = pages,
                state = state,
                interactionListener = viewModel
            )
        }
    }
}

@Composable
fun OnBoardingScreenContent(
    modifier: Modifier = Modifier,
    pages: List<Page>,
    state: OnBoardingUIState,
    interactionListener: OnBoardingInteractionListener
) {
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        interactionListener.onPageChanged(pagerState.currentPage)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Theme.colors.background.screen)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
        ) { index ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 96.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                TopSection(
                    page = pages[index],
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                TextSection(
                    page = pages[index]
                )
            }
        }
        BottomSection(
            modifier = Modifier
                .background(Theme.colors.background.screen.copy(alpha = 0.6f))
                .align(Alignment.BottomCenter)
                .padding(vertical = 24.dp),
            onNextClick = {
                interactionListener.onNextClicked()
                coroutineScope.launch {
                    if (pagerState.currentPage < pages.lastIndex) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            pages = pages,
            state = state,
            buttonText = if (pagerState.currentPage == pages.lastIndex) {
                stringResource(R.string.get_started)
            } else {
                stringResource(R.string.next)
            },
        )
        AppButton(
            type = AppButtonType.Secondary,
            onClick = interactionListener::onSkipClicked,
            text = stringResource(R.string.skip),
            modifier = Modifier
                .padding(end = 16.dp, top = 24.dp)
                .align(Alignment.TopEnd)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(Theme.radius.full),
                    ambientColor = Theme.colors.shade.secondary,
                    spotColor = Theme.colors.shade.secondary,
                ),
        )
    }

}

@Preview(
    name = "Phone - Light",
    device = Devices.PIXEL_4,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Phone - Dark",
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Tablet",
    device = Devices.TABLET,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Foldable",
    device = Devices.FOLDABLE,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Foldable",
    device = Devices.PIXEL,
    showSystemUi = true,
    showBackground = true
)
@Composable
fun OnBoardingScreenPreview() {
    San3aTheme {
        OnBoardingScreenContent(
            pages = onboardingPages(),
            state = OnBoardingUIState(
                currentPage = 2,
                isCompleted = false,
                error = null
            ),
            interactionListener = object : OnBoardingInteractionListener {
                override fun onSkipClicked() {}
                override fun onNextClicked() {}
                override fun onPageChanged(index: Int) {}
            }
        )
    }
}
