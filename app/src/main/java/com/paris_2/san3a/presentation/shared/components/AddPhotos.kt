package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AddPhotos(
    photos: List<String>,
    onClickAdd: () -> Unit,
    onClickDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(photos.indices.toList()) { index ->
            val photo = photos[index]
            PhotoHolder(
                photoUri = photo,
                onClickDelete = { onClickDelete(index) },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Theme.radius.large))
                    .background(Theme.colors.background.bottomSheetCard)
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_plus_outline),
                    contentDescription = "Add photo",
                    tint = Theme.colors.shade.secondary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { onClickAdd() }
                )
            }
        }
    }
}

@Composable
fun PhotoHolder(
    photoUri: String,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .size(64.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(photoUri),
            contentDescription = "photo",
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(Theme.radius.small))
                .background(Color.White.copy(alpha = 0.6f))
                .align(Alignment.TopEnd)
        ){
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Delete photo",
                tint = Theme.colors.shade.primary,
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable{ onClickDelete() }
            )
        }
    }
}

@Preview
@Composable
private fun Preview(){
    AddPhotos(
        photos = emptyList(),
        onClickAdd = {},
        onClickDelete = {},
    )
}