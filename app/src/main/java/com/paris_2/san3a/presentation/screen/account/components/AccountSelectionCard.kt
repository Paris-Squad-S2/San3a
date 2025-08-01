package com.paris_2.san3a.presentation.screen.account.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AccountSelectionCard(
    title: String,
    caption: String,
    userImage: Painter,
    modifier: Modifier = Modifier,
    isSelect: Boolean = false,
    onClick: () -> Unit = {},
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelect) Theme.colors.brand.tertiary else Theme.colors.background.card
    )
    val titleColor = if (isSelect) Theme.colors.brand.primary else Theme.colors.shade.primary
    val borderModifier = if (isSelect) {
        Modifier.border(
            width = 1.dp,
            color = Theme.colors.brand.secondary,
            shape = RoundedCornerShape(Theme.radius.extraLarge)
        )
    } else Modifier

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable {
                !isSelect
                onClick()
            }
            .then(borderModifier)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Theme.radius.extraLarge)
            )
    ) {
        Image(
            painter = userImage,
            contentDescription = stringResource(R.string.user_image),
            modifier = Modifier.padding(12.dp)
        )
        Text(
            text = title,
            color = titleColor,
            style = Theme.textStyle.body.medium.medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = caption,
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.body.small.medium,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AccountSelectionCardSelectedPreview() {
    San3aTheme {
        var isSelected by remember { mutableStateOf(false) }
        AccountSelectionCard(
            title = "title",
            caption = "caption",
            userImage = painterResource(id = R.drawable.customer),
            onClick = { isSelected = !isSelected}
        )
    }
}