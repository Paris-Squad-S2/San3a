package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun NotificationIcon(
    modifier: Modifier = Modifier,
    notificationsCount: Int,
    onNotificationClick: () -> Unit
) {
    Box(modifier) {
        Icon(
            modifier = Modifier.Companion
                .clickable(onClick = onNotificationClick),
            painter = painterResource(R.drawable.ic_notification_outline),
            contentDescription = null,
            tint = Theme.colors.shade.primary
        )
        if (notificationsCount > 0) {
            Box(
                modifier = Modifier.Companion
                    .align(Alignment.Companion.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(16.dp)
                    .background(Theme.colors.additional.primary.red, shape = CircleShape)
            ) {
                Text(
                    text = notificationsCount.toString(),
                    color = Theme.colors.additional.secondary.red,
                    fontSize = 10.sp,
                    modifier = Modifier.Companion.align(Alignment.Companion.Center)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun NotificationIconPreview() {
    NotificationIcon(
        notificationsCount = 5,
        onNotificationClick = {}
    )
}