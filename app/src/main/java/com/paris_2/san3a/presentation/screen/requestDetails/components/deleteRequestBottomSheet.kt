package com.paris_2.san3a.presentation.screen.requestDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRequestBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onDeleteRequest: () -> Unit = {},
    buttonState: AppButtonState,
    isVisible: Boolean = true,
) {
    BottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        onDismissRequest = onDismissRequest
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
                contentDescription = stringResource(R.string.delete_request_icon)
            )
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.delete_this_request),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )
            Text(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(R.string.the_request_will_be_deleted_from_your_device_this_action_can_t_be_undone),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
                textAlign = TextAlign.Center
            )
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                type = AppButtonType.Primary,
                onClick = onDeleteRequest,
                enablePrimaryBackgroundColor = Theme.colors.additional.primary.error,
                text = stringResource(R.string.delete_request),
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
fun DeleteRequestBottomSheetPreview() {
    BasePreview {
        DeleteRequestBottomSheet(
            onDismissRequest = {},
            isVisible = true,
            buttonState = AppButtonState.Enable,
        )
    }
}
