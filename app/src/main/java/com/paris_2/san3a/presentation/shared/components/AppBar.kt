package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    actionIcon: @Composable () -> Unit = {},
    title: String,
    location : String? = null,
    onBackClick: (() -> Unit)? = null,
    showBackGround: Boolean = true,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = if (showBackGround) Theme.colors.background.card else Color.Unspecified)
            .padding(start = 8.dp, end = 16.dp)
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_outline),
                    contentDescription = null,
                    tint = Theme.colors.button.onSecondary
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = if (onBackClick == null) 16.dp else 0.dp)
        ){
            Text(
                text = title,
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary,
            )
            if(location != null) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location_outline),
                        contentDescription = "",
                        tint = Theme.colors.shade.secondary,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = location,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_alt_arrow_down_outline),
                        contentDescription = "",
                        tint = Theme.colors.shade.secondary,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 4.dp)
                    )
                }
            }


        }

        actionIcon()
    }

}

@PreviewLightDark
@Composable
private fun AppBarPrev() {
    BasePreview {
        AppBar(
            title = "Title",
            onBackClick = {},
            actionIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_notification_outline),
                    contentDescription = null,
                    tint = Theme.colors.shade.primary
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun AppBarPrev2() {
    BasePreview {
        AppBar(
            title = "Title",
            onBackClick = null,
            actionIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_notification_outline),
                    contentDescription = null,
                    tint = Theme.colors.shade.primary
                )
            },
            location = "Location",
        )
    }
}