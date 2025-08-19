package com.paris_2.san3a.presentation.screen.account.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AccountAppButton(
    onClickButton: () -> Unit,
    textButton: String,
    state: AppButtonState,
    modifier: Modifier = Modifier
) {
    AppButton(
        onClick = onClickButton,
        type = AppButtonType.Primary,
        text = textButton,
        state = state,
        modifier = modifier.fillMaxWidth(),
        size = AppButtonSize.Large,
        loadingIcon = {
            LoadingScreen(
                modifier = Modifier.size(25.dp),
                background = Theme.colors.button.primary
            )
        }

    )
}