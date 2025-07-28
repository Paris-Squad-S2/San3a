
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paris_2.san3a.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingInteractionListener
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingUIState
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingViewModel
import com.paris_2.san3a.presentation.screen.onboarding.Page
import com.paris_2.san3a.presentation.screen.onboarding.sections.TopSection
import com.paris_2.san3a.presentation.screen.onboarding.sections.BottomSection
import com.paris_2.san3a.presentation.screen.onboarding.sections.TextSection
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    pages: List<Page>,
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    if (!state.isCompleted) {
        OnBoardingScreenContent(
            pages = pages,
            state = state,
            interactionListener = viewModel
        )
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
            TextButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 24.dp, end = 16.dp, bottom = 32.dp),
                onClick = interactionListener::onSkipClicked,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Theme.colors.button.secondary,
                ),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.skip),
                    color = Theme.colors.button.onSecondary,
                    style = Theme.textStyle.body.small.medium
                )
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { index ->
                TopSection(
                    page = pages[index]
                )
            }
            TextSection(page = pages[pagerState.currentPage])
            BottomSection(
                onNextClick = {
                        coroutineScope.launch {
                            interactionListener.onNextClicked()
                            if (pagerState.currentPage < pages.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                },
                pages = pages,
                state = state
            )
        }
    }
}