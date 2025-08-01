package com.paris_2.san3a.presentation.screen.messagedetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    imageIcon: Painter,
    voiceIcon: Painter,
    sendIcon: Painter,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxSize()
                    .background(Theme.colors.background.card, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                ,
                verticalAlignment = Alignment
                    .CenterVertically,
            ) {
                if (value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Write a message...",
                            style = Theme.textStyle.body.medium.regular,
                            color = Theme.colors.shade.tertiary
                        )
                    }

                    Icon(
                        painter = imageIcon,
                        tint =  Theme.colors.shade.secondary,
                        contentDescription = "Add Images",
                        modifier = Modifier
                    )

                    Icon(
                        painter = voiceIcon,
                        tint =  Theme.colors.shade.secondary,
                        contentDescription = "voice icon",
                        modifier = Modifier
                            .weight(1f)
                    )

                }else{
                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 16.dp)
                    ) {
                        innerTextField()
                    }
                    Icon(
                        painter = sendIcon,
                        contentDescription = "send icon",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        },
        cursorBrush = SolidColor( Theme.colors.shade.tertiary),
        textStyle = Theme.textStyle.body.medium.regular,
    )
}

@PreviewScreenSizes
@Composable
private fun MessageTextFieldPreview() {
}