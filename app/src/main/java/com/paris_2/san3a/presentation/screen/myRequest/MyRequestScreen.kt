package com.paris_2.san3a.presentation.screen.myRequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MyRequestScreen(modifier: Modifier = Modifier) {
    MyRequestScreenContent()
}

@Composable
private fun MyRequestScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Request Screen",
            style = Theme.textStyle.title.large,
            color = Theme.colors.brand.primary
        )
    }
}