package com.paris_2.san3a.presentation.screen.messagesDetails.components

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.utill.myClickable

@Composable
fun AudioPlayer(
    voiceUri: Uri,
    recordWave: List<Float>,
    isReceived: Boolean
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var listenRatio by remember { mutableFloatStateOf(0f) }

    DisposableEffect(voiceUri) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun playAudio(context: Context, uri: Uri) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, uri)
        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            listenRatio = 1f
        }
        mediaPlayer?.start()
        isPlaying = true
    }

    fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }

    LaunchedEffect(isPlaying, mediaPlayer) {
        while (isPlaying && mediaPlayer != null) {
            val player = mediaPlayer
            if (player != null && player.isPlaying && player.duration > 0) {
                listenRatio = player.currentPosition.toFloat() / player.duration
            }
            Log.d("AudioPlayer", "listenRatio: $listenRatio")
            kotlinx.coroutines.delay(100L)
        }
    }

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
                painter = painterResource(
                    if (isPlaying) R.drawable.ic_pause_bold else R.drawable.ic_play_bold
                ),
                contentDescription = stringResource(
                    if (isPlaying) R.string.pause_icon else R.string.play_icon
                ),
                tint = Theme.colors.shade.secondary,
                modifier = Modifier
                    .size(20.dp)
                    .myClickable{
                        if (isPlaying) {
                            stopAudio()
                        } else {
                            playAudio(context, voiceUri)
                        }
                    }
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
                painter = painterResource(
                    if (isPlaying) R.drawable.ic_pause_bold else R.drawable.ic_play_bold
                ),
                contentDescription = stringResource(
                    if (isPlaying) R.string.pause_icon else R.string.play_icon
                ),
                tint = Theme.colors.shade.secondary,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer(rotationZ = 180f)
                    .myClickable {
                        if (isPlaying) {
                            stopAudio()
                        } else {
                            playAudio(context, voiceUri)
                        }
                    }
            )
        }
    }
}