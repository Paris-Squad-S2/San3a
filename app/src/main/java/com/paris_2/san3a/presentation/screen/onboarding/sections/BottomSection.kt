package com.paris_2.san3a.presentation.screen.onboarding.sections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingUIState
import com.paris_2.san3a.presentation.screen.onboarding.Page
import com.paris_2.san3a.presentation.screen.onboarding.components.ProgressIndicator
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun BottomSection(
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    pages : List<Page>,
    state: OnBoardingUIState,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        ProgressIndicator(
            pageSize = pages.size,
            currentPage = state.currentPage
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onNextClick,
                /*if (pagerState.currentPage == pages.lastIndex) {
                    onFinished()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )
                    }
                }*/

            colors = ButtonDefaults.textButtonColors(
                containerColor = Theme.colors.button.primary,
            ),
            shape = RoundedCornerShape(100.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.next),
                color = Theme.colors.button.onPrimary,
                style = Theme.textStyle.body.medium.medium,
            )
        }
    }

}