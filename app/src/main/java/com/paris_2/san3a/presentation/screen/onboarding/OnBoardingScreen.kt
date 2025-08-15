package com.paris_2.san3a.presentation.screen.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.onboarding.sections.BottomSection
import com.paris_2.san3a.presentation.screen.onboarding.sections.TextSection
import com.paris_2.san3a.presentation.screen.onboarding.sections.TopSection
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    pages: List<Page>,
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    when{
        state.error!=null ->{
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
        state.isCompleted ->{
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
        modifier
            .background(Theme.colors.background.screen)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            AppButton(
                type = AppButtonType.Secondary,
                onClick = interactionListener::onSkipClicked,
                text = stringResource(R.string.skip),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp, end = 16.dp, bottom = 32.dp),
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { index ->
                TopSection(
                    page = pages[index]
                )
            }
            TextSection(
                page = pages[pagerState.currentPage]
            )
            BottomSection(
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
        }
    }
}