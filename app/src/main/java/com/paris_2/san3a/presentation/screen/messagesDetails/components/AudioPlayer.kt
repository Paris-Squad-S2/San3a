package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.background
import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AudioPlayer(
    onPlayClick: () -> Unit,
    recordWave: List<Float>,
    listenRatio: Float,
    isReceived: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(
                Theme.colors.stroke.primary,
                androidx.compose.foundation.shape.RoundedCornerShape(Theme.radius.full)
            )
            .padding(1.dp)
            .background(
                Theme.colors.background.card,
                androidx.compose.foundation.shape.RoundedCornerShape(Theme.radius.full)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (isReceived) {
            Icon(
                painter = painterResource(R.drawable.ic_play_bold),
                contentDescription = stringResource(R.string.play_icon),
                tint = Theme.colors.shade.secondary,
                modifier = Modifier
                    .size(20.dp)
                    .myClickable(onClick = onPlayClick)
            )
        }
        if (recordWave.isNotEmpty()) {
            AudioWave(recordWave, listenRatio, Modifier.weight(1f), isReceived)
        } else {
            Text(
                text = stringResource(R.string.no_audio_wave_available),
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
            )
        }
        if (!isReceived) {
            Icon(
                painter = painterResource(R.drawable.ic_play_bold),
                contentDescription = stringResource(R.string.play_icon),
                tint = Theme.colors.shade.secondary,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer(rotationZ = 180f)
                    .myClickable(onClick = onPlayClick)
            )
        }
    }
}