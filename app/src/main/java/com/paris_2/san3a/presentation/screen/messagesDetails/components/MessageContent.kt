package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun MessageContent(
    text: String?,
    time: String?,
    isReceived: Boolean,
    isSeen: Boolean?,
    images: List<String>,
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
                listenRatio = listenRatio,
                isReceived = isReceived
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun MessageContentPreview() {
    BasePreview {
        MessageContent(
            text = "Hello, this is a sample message to demonstrate the message component in the app. It can be quite long to test text wrapping and alignment properly. Let's see how it looks! This is a test message.",
            time = "12:34 PM",
            isReceived = true,
            isSeen = true,
            images = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
            onPlayClick = null,
            recordWave = emptyList(),
            listenRatio = 0f,
            modifier = Modifier
        )
    }
}
