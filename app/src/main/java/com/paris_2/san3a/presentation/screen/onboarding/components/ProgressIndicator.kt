package com.paris_2.san3a.presentation.screen.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    currentPage: Int,
    selectedColor: Color = Theme.colors.brand.primary,
    unSelectedColor: Color = Theme.colors.shade.quaternary
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageSize) {
            Box(
                modifier = Modifier
                    .width(
                        if (it == currentPage) 32.dp else 16.dp
                    )
                    .height(8.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(
                        color = if (it == currentPage) selectedColor else unSelectedColor
                    )
            )
        }
    }
}

@Preview()
@Composable
fun PageIndicatorPreview() {
    ProgressIndicator(
        pageSize = 3,
        currentPage = 1
    )
}