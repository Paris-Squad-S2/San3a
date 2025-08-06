package com.paris_2.san3a.presentation.screen.more.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices


@Composable
fun SettingHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        thickness = 1.dp,
        color = Theme.colors.shade.quaternary
    )
}

@PreviewMultiDevices
@Preview
@Composable
private fun SettingHorizontalDividerPreview() {
    BasePreview {
        SettingHorizontalDivider()
    }
}