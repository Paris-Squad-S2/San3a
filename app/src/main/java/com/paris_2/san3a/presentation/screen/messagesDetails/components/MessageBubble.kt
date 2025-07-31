package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MessageBubble(
    text: String?,
    time: String?,
    isReceived: Boolean,
    isSeen: Boolean?,
    images: List<Painter>
) {
    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = Theme.radius.extraLarge,
                    topEnd = Theme.radius.extraLarge,
                    bottomEnd = if (!isReceived) Theme.radius.extraSmall else Theme.radius.extraLarge,
                    bottomStart = if (!isReceived) Theme.radius.extraLarge else Theme.radius.extraSmall,
                )
            )
            .background(if (isReceived) Theme.colors.background.card else Theme.colors.brand.tertiary),
    ) {
        text?.let {
            Text(
                text = it,
                style = Theme.textStyle.body.medium.regular,
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 12.dp,
                    bottom = 4.dp
                ),
                color = Theme.colors.shade.secondary,
                textAlign = TextAlign.Start
            )
        }
        if (images.isNotEmpty()) {
            ImagesGrid(images)
        }
        MessageMeta(
            time = time,
            isSeen = isSeen,
            isReceived = isReceived,
            modifier = Modifier.align(Alignment.End)
        )
    }
}