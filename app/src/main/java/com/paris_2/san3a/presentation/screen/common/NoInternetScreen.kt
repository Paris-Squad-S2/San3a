package com.paris_2.san3a.presentation.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun NoInternetScreen(
    onClickRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = Theme.colors.background.screen),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.width(240.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.image_no_internet),
                contentDescription = stringResource(R.string.no_internet_connection),
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = stringResource(R.string.oops_no_internet),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = stringResource(R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AppButton(
                modifier = Modifier
                    .width(240.dp),
                type = AppButtonType.Primary,
                size = AppButtonSize.Small,
                state = AppButtonState.Enable,
                onClick = {onClickRetry()},
                text = stringResource(R.string.try_again),
            )
        }

    }
}