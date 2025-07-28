package com.paris_2.san3a.presentation.shared.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = isEnabled,
        thumbContent = {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        color = when {
                            !isEnabled -> Theme.colors.shade.tertiary
                            checked -> Color.White
                            else -> Theme.colors.shade.secondary
                        }
                    )
            )
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Theme.colors.brand.primary,
            checkedBorderColor = Theme.colors.brand.primary,
            uncheckedThumbColor = Theme.colors.shade.secondary,
            uncheckedTrackColor = Theme.colors.shade.quaternary,
            uncheckedBorderColor = Theme.colors.shade.secondary,
            disabledCheckedThumbColor = Theme.colors.shade.tertiary,
            disabledCheckedTrackColor = Theme.colors.shade.quaternary,
            disabledCheckedBorderColor = Theme.colors.shade.tertiary,
            disabledUncheckedThumbColor = Theme.colors.shade.tertiary,
            disabledUncheckedTrackColor = Theme.colors.shade.quaternary,
            disabledUncheckedBorderColor = Theme.colors.shade.tertiary
        ),
        modifier = modifier
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppSwitchPreview() {
    San3aTheme {
        var enabledChecked by remember { mutableStateOf(true) }
        var enabledUnchecked by remember { mutableStateOf(false) }
        var disabledChecked by remember { mutableStateOf(true) }
        var disabledUnchecked by remember { mutableStateOf(false) }
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AppSwitch(
                    checked = enabledChecked,
                    onCheckedChange = { enabledChecked = it },
                    isEnabled = true
                )
                AppSwitch(
                    checked = enabledUnchecked,
                    onCheckedChange = { enabledUnchecked = it },
                    isEnabled = true
                )
            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
            ){
                AppSwitch(
                    checked = disabledChecked,
                    onCheckedChange = { disabledChecked = it },
                    isEnabled = false
                )
                AppSwitch(
                    checked = disabledUnchecked,
                    onCheckedChange = { disabledUnchecked = it },
                    isEnabled = false
                )
            }
        }
    }
}