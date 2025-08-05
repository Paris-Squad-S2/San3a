package com.paris_2.san3a.presentation.screen.onboarding.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.onboarding.OnBoardingUIState
import com.paris_2.san3a.presentation.screen.onboarding.Page
import com.paris_2.san3a.presentation.screen.onboarding.components.ProgressIndicator
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType

@Composable
fun BottomSection(
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    pages : List<Page>,
    state: OnBoardingUIState,
    buttonText: String = stringResource(R.string.next)
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
        AppButton(
            type = AppButtonType.Primary,
            onClick = onNextClick,
            text = buttonText,
            size = AppButtonSize.Large
        )
    }
}