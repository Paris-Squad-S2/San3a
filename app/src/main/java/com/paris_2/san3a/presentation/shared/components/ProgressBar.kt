package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun ProgressBar(
    step: Int,
    modifier: Modifier = Modifier
){
    val targetWidth = when (step) {
        1 -> 82.dp
        2 -> 164.dp
        3 -> 246.dp
        4 -> 296.dp
        else -> 82.dp
    }

    val animatedWidth = animateDpAsState(
        targetValue = targetWidth,
        label = "progress_width"
    )

    Box(
        modifier = modifier
            .height(4.dp)
            .width(296.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(Theme.colors.background.bottomSheetCard),
    ){
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(animatedWidth.value)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Theme.colors.brand.primary)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ProgressBar(
        step = 2
    )
}