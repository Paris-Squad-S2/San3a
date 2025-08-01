package com.paris_2.san3a.presentation.screen.onboarding.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paris_2.san3a.presentation.screen.onboarding.Page
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme


@Composable
fun TextSection(
    page: Page,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = page.title,
            style = Theme.textStyle.display.xLarge,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 28.sp
        )
        Text(
            text = page.description,
            style = Theme.textStyle.body.large.regular,
            color = Theme.colors.shade.secondary,
            fontSize = 16.sp
        )
    }
}
