package com.paris_2.san3a.presentation.shared.utils

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun BasePreview(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    San3aTheme {
        Surface(modifier = modifier, color = Theme.colors.background.screen) {
            content()
        }
    }
}