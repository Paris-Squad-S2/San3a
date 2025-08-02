package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = { },
    topBar: @Composable () -> Unit = { },
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = bottomBar,
        topBar = topBar,
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            content()
        }
    }
}