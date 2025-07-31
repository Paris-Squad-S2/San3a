package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AudioWave(
    recordWave: List<Float>,
    listenRatio: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        recordWave.forEachIndexed { index, wave ->
            val ratioIndex = (listenRatio * recordWave.size).toInt()
            val waveColor = if (index < ratioIndex) {
                Theme.colors.brand.primary
            } else {
                Theme.colors.shade.quaternary
            }
            Surface(
                modifier = Modifier
                    .width(4.dp)
                    .height((36 * wave).dp)
                    .clip(RoundedCornerShape(Theme.radius.full)),
                color = waveColor
            ) {}
        }
    }
}