package com.paris_2.san3a.presentation.screen.home.craftsman.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.ProgressBar
import com.paris_2.san3a.presentation.shared.components.RequestTitleContent
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestBottomSheetContent(
    title: String,
    icon: Int,
    color: Color,
    subTitle: String,
    optionalText: String? = null,
    step: Int,
    buttonTitle: String,
    modifier: Modifier = Modifier,
    buttonIsActive: Boolean = false,
    onClickBack: (() -> Unit)? = null,
    onButtonClick: () -> Unit,
    onExitClick: () -> Unit,
    containImg: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background.bottomSheet)
            .padding(horizontal = 16.dp)
            .padding(top = 20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp),
                    tint = color
                )
                Text(
                    text = title,
                    style = Theme.textStyle.label.medium.medium,
                    color = color
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 20.dp)
            ) {
                Text(
                    text = subTitle,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                )
                if (optionalText != null) {
                    Text(
                        text = optionalText,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.secondary,
                    )
                }
            }
            content()
            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
            ) {
                if (onClickBack != null) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(48.dp)
                            .clip(RoundedCornerShape(Theme.radius.full))
                            .background(
                                Theme.colors.background.bottomSheetCard
                            )
                            .clickable { onClickBack() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_alt_arrow_left_outline),
                            contentDescription = null,
                            tint = Theme.colors.button.onSecondary,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(Theme.radius.full))
                        .background(
                            if (buttonIsActive) Theme.colors.button.primary else Theme.colors.button.disabled
                        )
                        .clickable(enabled = buttonIsActive, onClick = onButtonClick)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = buttonTitle,
                            style = Theme.textStyle.body.medium.medium,
                            color = Theme.colors.button.onPrimary,
                        )
                    }
                }

            }
            ProgressBar(
                step = step, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .clickable { onExitClick() },
            tint = Theme.colors.shade.secondary
        )

    }
}

@Preview
@Composable
private fun Preview() {
    RequestBottomSheetContent(
        title = "Plumping Request",
        icon = R.drawable.ic_waterdrops_bold,
        color = Theme.colors.shade.primary,
        subTitle = "What do you need help with?",
        optionalText = "(Optional)",
        buttonTitle = "Next",
        onButtonClick = {},
        onExitClick = {},
        step = 1,
    ) {
        RequestTitleContent(
            value = "",
            onValueChange = { },
            suggestions = emptyList(),
            selectedSuggestion = "",
            onChipClick = { }
        )
    }
}