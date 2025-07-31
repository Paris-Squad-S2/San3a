package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@Composable
fun DeleteChatBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onDeleteChat: () -> Unit = {},
    buttonState: AppButtonState,
    isVisible: Boolean = true,
) {
    BottomSheet(
        modifier = modifier,
        isVisible = isVisible,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp),
                painter = painterResource(R.drawable.img_trash),
                contentDescription = "delete chat icon"
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.delete_this_chat),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )
            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(R.string.the_chat_will_be_deleted_from_your_device_this_action_can_t_be_undone),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
                textAlign = TextAlign.Center
            )
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                type = AppButtonType.Primary,
                onClick = onDeleteChat,
                enablePrimaryBackgroundColor = Theme.colors.additional.primary.error,
                text = stringResource(R.string.delete_chat),
                size = AppButtonSize.Large,
                state = buttonState,
            )
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                type = AppButtonType.Secondary,
                onClick = onDismissRequest,
                text = stringResource(R.string.cancel),
                size = AppButtonSize.Large,
                state = AppButtonState.Enable,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun DeleteChatBottomSheetPreview() {
    BasePreview {
        DeleteChatBottomSheet(
            onDismissRequest = {},
            isVisible = true,
            buttonState = AppButtonState.Enable,
        )
    }
}
