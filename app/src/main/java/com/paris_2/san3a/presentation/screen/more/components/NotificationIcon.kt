package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices


@Composable
fun NotificationIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_notification_outline),
        contentDescription = stringResource(R.string.notification_icon),
        tint = Theme.colors.shade.primary
    )
}

@PreviewMultiDevices
@Preview
@Composable
private fun NotificationIconPreview() {
    BasePreview {
        NotificationIcon()
    }
}