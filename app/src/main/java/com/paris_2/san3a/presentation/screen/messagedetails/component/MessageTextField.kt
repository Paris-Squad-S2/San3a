package com.paris_2.san3a.presentation.screen.messagedetails.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp

@Composable
fun MessageTextField(
    value: String,
    onValueChange: (text: String) -> Unit,
    imageIcon: @Composable (Modifier) -> Unit,
    voiceIcon: @Composable (Modifier) -> Unit,
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
                verticalAlignment = Alignment
                    .CenterVertically
            ) {
                Box (
                    modifier = Modifier
                        .weight(3f)
                ){
                    innerTextField()
                }
                imageIcon(Modifier.weight(1f).padding(horizontal = 10.dp))
                voiceIcon(Modifier.weight(1f).padding(horizontal = 10.dp))
            }
        }
    )
}

@PreviewScreenSizes
@Composable
private fun MessageTextFieldPreview() {
    MessageTextField(
        value = "Hello World",
        onValueChange = {
            it
        },
        imageIcon = {
            Box(modifier = it)
        },
        voiceIcon = {
            Box(modifier = it)
        },
        modifier = Modifier
    )
}