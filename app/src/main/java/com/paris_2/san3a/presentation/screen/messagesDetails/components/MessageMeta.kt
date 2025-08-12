package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MessageMeta(
    time: String?,
    isSeen: Boolean?,
    isReceived: Boolean,
    modifier: Modifier = Modifier
) {
    if (time == null && (isSeen == null || !isSeen)) return

    Row(
        modifier = modifier
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        time?.let {
            Text(
                text = it,
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.tertiary,
            )
        }
        if (!isReceived && isSeen != null) {
            Icon(
                painter = painterResource(if (isSeen) R.drawable.ic_check_read else R.drawable.ic_check_unread),
                contentDescription = stringResource(R.string.seen_icon),
                tint = Theme.colors.additional.primary.blue,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}