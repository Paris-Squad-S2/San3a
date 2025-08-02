package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

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