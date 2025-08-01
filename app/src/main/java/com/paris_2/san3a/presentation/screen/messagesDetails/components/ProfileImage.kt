package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R

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