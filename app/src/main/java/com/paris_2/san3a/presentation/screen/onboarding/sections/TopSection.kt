package com.paris_2.san3a.presentation.screen.onboarding.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.onboarding.Page

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(
        modifier = modifier,
    ) {
        page.item()
    }

}