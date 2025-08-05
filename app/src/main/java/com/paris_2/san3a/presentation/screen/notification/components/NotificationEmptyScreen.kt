package com.paris_2.san3a.presentation.screen.notification.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun NotificationEmptyScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .padding(horizontal = 60.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.empty_notification_screen),
            contentDescription = stringResource(R.string.empty_notification_screen)
        )
        Text(
            text = stringResource(R.string.nothing_new_for_now),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.we_ll_keep_you_updated_when_there_s_something_new),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Preview
@Composable
private fun NotificationEmptyScreenPreview() {
    San3aTheme {
        NotificationEmptyScreen()
    }
}