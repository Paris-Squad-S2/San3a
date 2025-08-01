package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    imageIcon: Painter,
    voiceIcon: Painter,
    sendIcon: Painter,
    onImageClick: () -> Unit,
    onVoiceClick: () -> Unit = {},
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val paddingText = animateDpAsState(
        targetValue = if (value.isNotEmpty()) 12.dp else 0.dp
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .animateContentSize()
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
                if (value.isEmpty()) {

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
                            .padding(end = 30.dp)
                            .clickable(onClick = onImageClick)
                    )

                    Icon(
                        painter = voiceIcon,
                        tint = Theme.colors.shade.secondary,
                        contentDescription = stringResource(R.string.voice_icon),
                        modifier = Modifier
                            .padding(end = 19.dp)
                            .clickable(onClick = onVoiceClick)
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
                            .clickable(onClick = onSendClick)
                            .align(Alignment.Bottom)
                    )

                }
            }
        },
        textStyle = Theme.textStyle.body.medium.regular.copy(color = Theme.colors.shade.primary),
    )
}

@PreviewScreenSizes
@Composable
private fun MessageTextFieldPreview() {
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
                .fillMaxWidth()
                .height(80.dp)
        )
    }
}