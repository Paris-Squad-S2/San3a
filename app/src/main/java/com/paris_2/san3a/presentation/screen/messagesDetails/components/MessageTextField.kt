package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import com.paris_2.san3a.presentation.utill.myClickable

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    imageIcon: Painter,
    voiceIcon: Painter,
    sendIcon: Painter,
    sendButtonState: AppButtonState,
    onImageClick: () -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    voiceDuration: Float,
    voiceWave: List<Float>,
    onVoiceClick: () -> Unit,
) {

    val paddingText = animateDpAsState(
        targetValue = if (value.isNotEmpty()) 12.dp else 0.dp
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .heightIn(min = 72.dp, 300.dp)
            .background(
                Theme.colors.background.card,
                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .border(
                color = Theme.colors.stroke.primary,
                width = 1.dp,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = paddingText.value)
            ) {
                if (voiceDuration > 0f) {
                    VoiceRecordItem(
                        duration = voiceDuration,
                        wave = voiceWave,
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 16.dp)
                    )
                    Icon(
                        painter = sendIcon,
                        tint = Theme.colors.brand.primary,
                        contentDescription = stringResource(R.string.send_icon),
                        modifier = Modifier
                            .padding(start = 19.dp, end = 19.dp)
                            .myClickable(
                                onClick = onSendClick,
                                enabled = sendButtonState == AppButtonState.Enable
                            )
                            .align(Alignment.CenterVertically)
                    )
                } else if (value.isEmpty()) {

                    Text(
                        text = stringResource(R.string.write_a_message),
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.shade.tertiary,
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 16.dp)
                    )

                    Icon(
                        painter = imageIcon,
                        tint = Theme.colors.shade.secondary,
                        contentDescription = stringResource(R.string.add_images),
                        modifier = Modifier
                            .padding(end = 19.dp)
                            .myClickable(onClick = onImageClick)
                    )

                    Icon(
                        painter = voiceIcon,
                        tint = Theme.colors.shade.secondary,
                        contentDescription = stringResource(R.string.voice_icon),
                        modifier = Modifier
                            .padding(end = 19.dp)
                            .myClickable(onClick = onVoiceClick)
                    )

                } else {
                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 16.dp)
                    ) {
                        innerTextField()
                    }

                    Icon(
                        painter = sendIcon,
                        tint = Theme.colors.brand.primary,
                        contentDescription = stringResource(R.string.send_icon),
                        modifier = Modifier
                            .padding(start = 19.dp, end = 19.dp, bottom = 15.dp)
                            .myClickable(
                                onClick = onSendClick,
                                enabled = sendButtonState == AppButtonState.Enable
                            )
                            .align(Alignment.Bottom)
                    )

                }
            }
        },
        cursorBrush = SolidColor(Theme.colors.shade.primary),
        textStyle = Theme.textStyle.body.medium.regular.copy(color = Theme.colors.shade.primary),
    )
}

@Composable
fun VoiceRecordItem(duration: Float, wave: List<Float>, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val animatedColor = if ((duration % 1f) > 0.5f) {
            Theme.colors.additional.primary.red
        } else {
            Theme.colors.shade.secondary
        }

        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    color = animatedColor,
                    shape = RoundedCornerShape(50)
                )
        )
        val totalSeconds = duration.toInt()
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        val timeText = if (hours > 0)
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        else
            String.format("%02d:%02d", minutes, seconds)
        Text(
            text = timeText,
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(start = 8.dp)
        )
        AudioWave(
            recordWave = wave,
            listenRatio = 1f,
            isReceived = true,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
    }
}

@PreviewMultiDevices
@Composable
private fun MessageTextFieldPreview() {
    San3aTheme {
        MessageTextField(
            value = "defreferkhkerhfhher jvjhrevjerejvbhjbl ufjljfjhebfjebjbekqbjkeqbkhbkbeewkrb ekr bkjerqbkhbk vjre jhverjhv evhjer vj ber jer vhj er vje verjv jer vj er vjer v erjv er jv er vre vj erj vjer v erv er v erv re ver jhv rejhv e vjer jv erj vjer v jer ver jv jer vjer jv ej ver jv erj er vjer jv erj vj ver jv er ve rver",
            onValueChange = {},
            imageIcon = painterResource(R.drawable.ic_image),
            voiceIcon = painterResource(R.drawable.ic_voice),
            sendIcon = painterResource(R.drawable.ic_send),
            onImageClick = {},
            onSendClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            sendButtonState = AppButtonState.Enable,
            voiceDuration = 0f,
            voiceWave = emptyList(),
            onVoiceClick = {}
        )
    }
}

@PreviewMultiDevices
@Composable
private fun MessageTextFieldPreview1() {
    San3aTheme {
        MessageTextField(
            value = "",
            onValueChange = {},
            imageIcon = painterResource(R.drawable.ic_image),
            voiceIcon = painterResource(R.drawable.ic_voice),
            sendIcon = painterResource(R.drawable.ic_send),
            onImageClick = {},
            onSendClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            sendButtonState = AppButtonState.Enable,
            voiceDuration = 0f,
            voiceWave = emptyList(),
            onVoiceClick = {}
        )
    }
}

@PreviewMultiDevices
@Composable
private fun MessageTextFieldPreview2() {
    San3aTheme {
        MessageTextField(
            value = "",
            onValueChange = {},
            imageIcon = painterResource(R.drawable.ic_image),
            voiceIcon = painterResource(R.drawable.ic_voice),
            sendIcon = painterResource(R.drawable.ic_send),
            onImageClick = {},
            onSendClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            voiceDuration = 60.5f,
            voiceWave = listOf(0.3f, 0.5f, 0.2f, 0.7f, 0.4f, 0.6f, 0.3f, 0.5f, 0.2f, 0.7f),
            sendButtonState = AppButtonState.Enable,
            onVoiceClick = {}
        )
    }
}