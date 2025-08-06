package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AdCard(
    title: String,
    caption: String,
    buttonTitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    val layoutDirection = LocalLayoutDirection.current
    val shouldMirror = layoutDirection == LayoutDirection.Rtl
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(Theme.radius.tripleXLarge),
        border = BorderStroke(1.dp, Theme.colors.brand.secondary),
        colors = CardDefaults.cardColors(
            containerColor = Theme.colors.brand.tertiary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Theme.colors.background.card, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_sledgehammer_bold),
                    contentDescription = null,
                    tint = Theme.colors.brand.primary,
                    modifier = Modifier.then(
                        if (shouldMirror) Modifier.scale(scaleX = -1f, scaleY = 1f)
                        else Modifier
                    )
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = caption,
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
            }

        }

        AppButton(
            onClick = onClick,
            type = AppButtonType.Primary,
            text = buttonTitle,
            size = AppButtonSize.Large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ,
            icon = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_outline),
            iconPosition = IconPosition.End
        )
    }

}

@Preview(
    name = "AdCard LTR",
    showBackground = true,
    locale = "en"
)
@Preview(
    name = "AdCard RTL (Arabic)",
    showBackground = true,
    locale = "ar"
)
@Composable
fun AdCardPreview() {
    San3aTheme {
        AdCard(
            title = stringResource(R.string.craftsman_title),
            caption = stringResource(R.string.craftsman_desc),
            buttonTitle = stringResource(R.string.craftsman_button),
            onClick = {}
        )
    }
}

