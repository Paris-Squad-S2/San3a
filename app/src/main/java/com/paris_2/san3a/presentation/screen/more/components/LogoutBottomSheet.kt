package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun LogoutBottomSheet(
    isVisible: Boolean,
    onLogout: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    BottomSheet(
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        header = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_language),
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        scaleX = if (layoutDirection == LayoutDirection.Rtl) -1f else 1f
                    }

            )

            Text(
                text = stringResource(R.string.logout_confirmation_title),
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.title.large,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.logout_confirmation_subtitle),
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.title.small,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 32.dp)
                    .fillMaxWidth()
            )

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.logout),
                onClick = onLogout,
                type = AppButtonType.Primary,
                size = AppButtonSize.Large,
                enablePrimaryBackgroundColor = Theme.colors.additional.primary.error
            )

            Spacer(modifier = Modifier.height(12.dp))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                type = AppButtonType.Secondary,
                size = AppButtonSize.Large,
                text = stringResource(R.string.cancel),
                onClick = onCancel,
                enablePrimaryBackgroundColor = Theme.colors.background.bottomSheetCard
            )
            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

