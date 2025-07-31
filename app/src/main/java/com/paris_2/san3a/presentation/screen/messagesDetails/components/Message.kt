package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun Message(
    modifier: Modifier = Modifier,
    imagePainter: Painter? = null,
    text: String? = null,
    time: String? = null,
    isReceived: Boolean = true,
    isSeen: Boolean? = false,
    images: List<Painter> = emptyList(),
    onPlayClick: (() -> Unit)? = null,
    recordWave: List<Float> = emptyList(),
    listenRatio: Float = 0f,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        if (!isReceived) {
            ProfileImage(imagePainter)
        }
        MessageContent(
            text = text,
            time = time,
            isReceived = isReceived,
            isSeen = isSeen,
            images = images,
            onPlayClick = onPlayClick,
            recordWave = recordWave,
            listenRatio = listenRatio,
            modifier = Modifier
                .weight(1f)
        )
        if (isReceived) {
            ProfileImage(imagePainter)
        }
    }
}

@Composable
fun ProfileImage(imagePainter: Painter?) {
    imagePainter?.let {
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.profile_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}

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
                    bottomEnd = if (isReceived) Theme.radius.extraSmall else Theme.radius.extraLarge,
                    bottomStart = if (isReceived) Theme.radius.extraLarge else Theme.radius.extraSmall,
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

@Composable
fun ImagesGrid(images: List<Painter>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 124.dp),
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(images.size) { index ->
            Image(
                painter = images[index],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(124.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
            )
        }
    }
}

@Composable
fun MessageMeta(
    time: String?,
    isSeen: Boolean?,
    isReceived: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        time?.let {
            Text(
                text = it,
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.tertiary,
            )
        }
        if (isSeen ?: false && !isReceived) {
            Icon(
                painter = painterResource(R.drawable.ic_verified_check_outline),
                contentDescription = stringResource(R.string.seen_icon),
                tint = Theme.colors.additional.primary.blue,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun AudioPlayer(
    onPlayClick: () -> Unit,
    recordWave: List<Float>,
    listenRatio: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(
                Theme.colors.stroke.primary,
                RoundedCornerShape(Theme.radius.full)
            )
            .padding(1.dp)
            .background(
                Theme.colors.background.card,
                RoundedCornerShape(Theme.radius.full)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_play_bold),
            contentDescription = stringResource(R.string.play_icon),
            tint = Theme.colors.shade.secondary,
            modifier = Modifier
                .size(20.dp)
                .clickable(onClick = onPlayClick)
        )
        if (recordWave.isNotEmpty()) {
            AudioWave(recordWave, listenRatio, Modifier.weight(1f))
        } else {
            Text(
                text = stringResource(R.string.no_audio_wave_available),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
            )
        }
    }
}

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

@Composable
@PreviewMultiDevices
fun MessagePreview() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                imagePainter = painterResource(R.drawable.person_chat),
                text = "Hello, this is a sample message to demonstrate the message component in the app. It can be quite long to test text wrapping and alignment properly. Let's see how it looks! This is a test message.",
                time = "10:30 AM",
                isReceived = false,
                images = listOf(
                    painterResource(R.drawable.person_chat),
                    painterResource(R.drawable.person_chat),
                    painterResource(R.drawable.person_chat),
                ),
                isSeen = true,
                onPlayClick = { /* Handle play click */ },
                recordWave = listOf(0.3f, 0.2f, 0.3f, 0.7f, 0.5f, 0.1f, 0.7f, 0.4f, 0.9f, 1.0f,0.3f, 0.2f, 0.3f, 0.7f, 0.5f, 0.1f, 0.7f, 0.4f, 0.9f, 1.0f),
                listenRatio = 0.5f,
            )
        }
    }

}