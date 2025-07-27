package com.paris_2.san3a.presentation.shared.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme


enum class AppButtonState {
    Enable,
    Disabled,
    Loading
}

enum class AppButtonSize {
    Small,
    Large
}

sealed class AppButtonType {
    object Primary : AppButtonType()
    object Secondary : AppButtonType()
    object Tertiary : AppButtonType()
}

@Composable
fun AppButton(
    type: AppButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: AppButtonSize = AppButtonSize.Small,
    state: AppButtonState = AppButtonState.Enable,
    @StringRes text: Int? = null,
    loadingIcon: @Composable (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isLoading = (state == AppButtonState.Loading)
    val isLarge = (size == AppButtonSize.Large)
    val enabled = (state == AppButtonState.Enable)

    val backgroundColor = getBackgroundColor(
        type = type,
        isDisabled = (state == AppButtonState.Disabled),
    )
    val buttonContentColor = getContentColor(state, type)

    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = Theme.colors.background.screen,
        contentColor = buttonContentColor,
        onClick = { if (enabled) onClick() },
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Row(
            modifier = Modifier
                .background(backgroundColor)
                .padding(getAppContentPadding(type, isLoading, isLarge)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            text?.let {
                Text(
                    text = stringResource(it),
                    style = if (size == AppButtonSize.Large) Theme.textStyle.body.medium.medium else Theme.textStyle.body.small.regular,
                    color = buttonContentColor
                )
            }

            AnimatedVisibility(visible = isLoading, enter = fadeIn()) {
                loadingIcon?.let {
                    it()
                }
            }
        }
    }
}


@Composable
private fun getBackgroundColor(
    type: AppButtonType,
    isDisabled: Boolean,
): Color {
    return if (isDisabled) {
        when (type) {
            AppButtonType.Tertiary -> Color.Transparent
            AppButtonType.Primary, AppButtonType.Secondary -> Theme.colors.button.disabled
        }
    } else {
        when (type) {
            AppButtonType.Primary -> Theme.colors.button.primary
            AppButtonType.Secondary -> Theme.colors.button.secondary
            AppButtonType.Tertiary -> Color.Transparent
        }
    }
}


@Composable
private fun getContentColor(
    state: AppButtonState,
    type: AppButtonType,
): Color {
    return when {
        state == AppButtonState.Disabled -> Theme.colors.button.onDisabled
        else -> when (type) {
            AppButtonType.Primary -> Theme.colors.button.onPrimary
            AppButtonType.Secondary -> Theme.colors.button.onSecondary
            AppButtonType.Tertiary -> Theme.colors.button.onTertiary
        }
    }
}

@Composable
private fun getAppContentPadding(
    type: AppButtonType,
    isLoading: Boolean,
    isLarge: Boolean
): PaddingValues {
    if (isLoading) {
        return when (type) {
            AppButtonType.Primary, AppButtonType.Secondary -> PaddingValues(
                horizontal = if (isLarge) 24.dp else 16.dp,
                vertical = if (isLarge) 12.dp else 8.dp
            )

            AppButtonType.Tertiary -> PaddingValues(
                horizontal = if (isLarge) 24.dp else 16.dp,
                vertical = if (isLarge) 24.dp else 4.dp
            )


        }
    } else {
        return when (type) {
            AppButtonType.Primary, AppButtonType.Secondary -> PaddingValues(
                horizontal = if (isLarge) 24.dp else 16.dp,
                vertical = if (isLarge) 15.dp else 12.5.dp
            )

            AppButtonType.Tertiary -> PaddingValues(
                horizontal = if (isLarge) 24.dp else 16.dp,
                vertical = if (isLarge) 7.dp else 8.5.dp
            )

        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppPrimaryLargeButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Primary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Large
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppSecondaryLargeButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Secondary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Large
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppTertiaryLargeButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Tertiary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Large
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppDisablePrimaryLargeButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Primary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Large,
            state = AppButtonState.Disabled
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppDisableSecondaryLargeButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Secondary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Large,
            state = AppButtonState.Disabled
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppDisableTertiaryLargeButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Tertiary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Large,
            state = AppButtonState.Disabled
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppPrimarySmallButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Primary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Small,
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppSecondarySmallButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Secondary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Small,
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun AppTertiarySmallButtonPreview() {
    San3aTheme {
        AppButton(
            type = AppButtonType.Tertiary,
            onClick = {},
            text = R.string.button,
            size = AppButtonSize.Small,
        )
    }
}