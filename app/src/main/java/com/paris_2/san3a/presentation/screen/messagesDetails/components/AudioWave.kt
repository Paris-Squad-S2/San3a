package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview

@Composable
fun AudioWave(
    recordWave: List<Float>,
    listenRatio: Float,
    modifier: Modifier = Modifier,
    isReceived: Boolean
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val displayWave = if (!isReceived) recordWave.reversed() else recordWave
        val ratioIndex = if (isReceived) (listenRatio * recordWave.size).toInt() else recordWave.size - (listenRatio * recordWave.size).toInt()
        displayWave.forEachIndexed { index, wave ->
            val isActive = if (isReceived) index < ratioIndex else index >= ratioIndex
            val targetColor = if (isActive) {
                Theme.colors.brand.primary
            } else {
                Theme.colors.shade.quaternary
            }
            val animatedColor = animateColorAsState(targetColor, label = "waveColor")
            val targetHeight = (36 * wave).dp
            val animatedHeight = animateDpAsState(targetHeight, label = "waveHeight")

            Surface(
                modifier = Modifier
                    .width(4.dp)
                    .height(animatedHeight.value)
                    .clip(RoundedCornerShape(Theme.radius.full)),
                color = animatedColor.value
            ) {}
        }
    }
}

@Composable
@PreviewLightDark
fun AudioWavePreview() {
    BasePreview {
        AudioWave(
            recordWave = listOf(
                0.1f,
                0.5f,
                0.3f,
                0.7f,
                0.2f,
                0.9f,
                0.4f,
                0.6f,
                0.8f,
                0.2f,
                0.5f,
                0.3f
            ),
            listenRatio = 0.5f,
            isReceived = true
        )
    }
}

@Composable
@PreviewLightDark
fun AudioWavePreview2() {
    BasePreview {
        AudioWave(
            recordWave = listOf(
                0.1f,
                0.5f,
                0.3f,
                0.7f,
                0.2f,
                0.9f,
                0.4f,
                0.6f,
                0.8f,
                0.2f,
                0.5f,
                0.3f
            ),
            listenRatio = 0.5f,
            isReceived = false
        )
    }
}