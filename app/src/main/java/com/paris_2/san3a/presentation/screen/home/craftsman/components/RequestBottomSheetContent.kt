package com.paris_2.san3a.presentation.screen.home.craftsman.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.ProgressBar
import com.paris_2.san3a.presentation.shared.components.RequestTitleContent
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestBottomSheetContent(
    title: String,
    imageUrl: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    optionalText: String? = null,
    step: Int,
    buttonTitle: String,
    horizontalContentPadding: Dp = 16.dp,
    buttonIsActive: Boolean = false,
    onClickBack: (() -> Unit)? = null,
    onButtonClick: () -> Unit,
    onExitClick: () -> Unit,
    requestButtonState: AppButtonState = AppButtonState.Enable,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colors.background.bottomSheet)
            .padding(top = 20.dp, bottom = 16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp),
                )
                Text(
                    text = title,
                    style = Theme.textStyle.label.medium.medium,
                    color = Theme.colors.additional.primary.blue
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 20.dp)
                    .padding(horizontal = 16.dp)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalContentPadding)
            ) {
                content()
            }
            Row(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
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
                        AnimatedVisibility(
                            visible = requestButtonState == AppButtonState.Loading
                        ) {
                            Row {
                                Spacer(Modifier.width(8.dp))
                                LoadingScreen(
                                    modifier = modifier.size(20.dp),
                                    background = Theme.colors.button.primary
                                )
                            }
                        }
                    }
                }

            }
            ProgressBar(
                step = step, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp)
                .size(40.dp)
                .clickable { onExitClick() },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = Theme.colors.shade.secondary
            )
        }

    }
}

@Preview
@Composable
private fun Preview() {
    RequestBottomSheetContent(
        title = "Plumping Request",
        imageUrl = "https://example.com/image.png",
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