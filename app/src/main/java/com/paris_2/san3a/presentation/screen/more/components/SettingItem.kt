package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppSwitch
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun SettingItem(
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    contentDescriptionIcon: String? = null,
    hasSwitchIcon: Boolean = false,
    isCheckSwitch: Boolean = false,
    isLoading: Boolean = false,
    iconLabelColor: Color = Theme.colors.shade.primary,
    labelColor: Color = Theme.colors.shade.primary,
    onCheckedChange: (Boolean) -> Unit = {},
    onClickItem: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Theme.colors.background.card)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescriptionIcon,
            tint = iconLabelColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            style = Theme.textStyle.body.medium.medium,
            color = labelColor,
            modifier = Modifier.weight(1f)
        )

        AnimatedContent(targetState = hasSwitchIcon) { showSwitchIcon ->
            if (showSwitchIcon) {
                AppSwitch(
                    checked = isCheckSwitch,
                    onCheckedChange = { isChecked -> onCheckedChange(isChecked) },
                    isEnabled = true
                )
            } else {
                AnimatedContent(targetState = isLoading) { loading ->
                    if (loading) {
                        LoadingScreen(modifier = Modifier.size(20.dp))
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_alt_arrow_right_outline),
                            contentDescription = "navigate icon",
                            tint = Theme.colors.shade.primary,
                            modifier = Modifier
                                .size(20.dp)
                                .myClickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { onClickItem() }
                                )
                        )
                    }
                }
            }
        }

    }
}

@PreviewMultiDevices
@Preview
@Composable
private fun SettingItemPreview() {
    BasePreview {
        SettingItem(
            icon = R.drawable.ic_location_outline,
            label = stringResource(R.string.home),
            hasSwitchIcon = true,
            isCheckSwitch = true,
            onCheckedChange = {},
            onClickItem = {},
        )
    }
}