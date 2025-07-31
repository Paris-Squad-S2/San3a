package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
    images: List<String> = emptyList(),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        if (!isReceived) {
            imagePainter?.let {
                Image(
                    painter = imagePainter,
                    contentDescription = stringResource(R.string.profile_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f) //
                .clip(
                    RoundedCornerShape(
                        topStart = Theme.radius.extraLarge,
                        topEnd = Theme.radius.extraLarge,
                        bottomEnd = if (isReceived) Theme.radius.extraSmall else Theme.radius.extraLarge,
                        bottomStart = if (isReceived) Theme.radius.extraLarge else Theme.radius.extraSmall,
                    )
                )
                .background(Theme.colors.background.card),
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
                    textAlign = TextAlign.End
                )
            }
            time?.let {
                Text(
                    text = it,
                    style = Theme.textStyle.body.medium.regular,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp
                    ),
                    color = Theme.colors.shade.tertiary,
                    textAlign = TextAlign.Start
                )
            }
        }
        if (isReceived) {
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
    }
}

@Composable
@PreviewMultiDevices
fun MessagePreview() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                imagePainter = painterResource(R.drawable.person_chat),
//                text = "Hello, this is a sample message to demonstrate the message component in the app. It can be quite long to test text wrapping and alignment properly. Let's see how it looks! This is a test message.",
                text = "Hello jhgjhcvhjevcjvevcjherreerrerfrfrereferrefrefrfre",
                time = "10:30 AM",
                isReceived = true,
            )
        }
    }

}