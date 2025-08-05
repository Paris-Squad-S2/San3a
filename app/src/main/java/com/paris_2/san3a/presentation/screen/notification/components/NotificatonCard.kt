package com.paris_2.san3a.presentation.screen.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.datetime.LocalDate

@Composable
fun NotificationCard(
    title : String,
    caption : String,
    date : LocalDate,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Theme.colors.background.card, RoundedCornerShape(Theme.radius.extraExtraLarge))
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Icon(
           painter = painterResource(R.drawable.filled_notification_icon),
            contentDescription = stringResource(R.string.filled_notification_icon),
            tint = Theme.colors.shade.secondary,
            modifier = Modifier.background(Theme.colors.shade.quinary,RoundedCornerShape(Theme.radius.extraLarge))
                .padding(10.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = title,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary
            )
            Text(
                text = caption,
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.primary
            )
            Text(
                text = date.toString(),
                style = Theme.textStyle.body.small.regular,
                color = Theme.colors.shade.secondary
            )
        }

    }

}

@Preview
@Composable
private fun NotificationCardPreview() {
    San3aTheme {
        NotificationCard(
            title = "title",
            caption = "caption",
            date = LocalDate.parse("2023-01-01"),
        )
    }
}