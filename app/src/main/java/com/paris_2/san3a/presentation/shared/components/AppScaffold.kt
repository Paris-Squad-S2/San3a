package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = { },
    topBar: @Composable () -> Unit = { },
    containerColor: Color = Theme.colors.background.card,
    content: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = bottomBar,
        topBar = topBar,
        containerColor = containerColor,
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}