package com.paris_2.san3a.presentation.screen.onboarding.components

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.paris_2.san3a.R
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun OnBoardingCard(
    title: String,
    icon: Painter,
    backgroundColor: Color,
    iconBackgroundColor: Color,
    modifier: Modifier = Modifier,
    textColor: Color,
    iconColor: Color,
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.extraExtraLarge))
            .background(color = backgroundColor)
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = iconBackgroundColor,
                    shape = RoundedCornerShape(
                        Theme.radius.extraLarge
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "$title icon",
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = title,
            color = textColor,
            style = Theme.textStyle.body.medium.medium
        )
    }
}

@Preview(
    name = "Phone - Dark",
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Tablet",
    device = Devices.TABLET,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Foldable",
    device = Devices.FOLDABLE,
    showSystemUi = true,
    showBackground = true
)
@Preview(
    name = "Foldable",
    device = Devices.PIXEL,
    showSystemUi = true,
    showBackground = true
)
@Preview(showBackground = true)
@Composable
fun CardPreview() {
    OnBoardingCard(
        title = "Cleaning",
        icon = painterResource(id = R.drawable.ic_washing_machine_bold),
        backgroundColor = Theme.colors.additional.primary.purple,
        textColor = Theme.colors.background.card,
        iconColor = Theme.colors.additional.primary.purple,
        iconBackgroundColor = Color.White,
    )
}