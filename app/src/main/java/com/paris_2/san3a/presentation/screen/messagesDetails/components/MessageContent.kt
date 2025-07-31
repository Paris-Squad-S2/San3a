package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MessageContent(
    text: String?,
    time: String?,
    isReceived: Boolean,
    isSeen: Boolean?,
    images: List<Painter>,
    onPlayClick: (() -> Unit)?,
    recordWave: List<Float>,
    listenRatio: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        MessageBubble(
            text = text,
            time = time,
            isReceived = isReceived,
            isSeen = isSeen,
            images = images
        )
        if (onPlayClick != null) {
            AudioPlayer(
                onPlayClick = onPlayClick,
                recordWave = recordWave,
                listenRatio = listenRatio
            )
        }
    }
}