package com.paris_2.san3a.presentation.screen.account.components

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paris_2.san3a.R

@Composable
fun AddPhotosComponent(
    modifier: Modifier = Modifier,
    images: List<Uri>,
    onAddPhotoClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)

    if (images.isEmpty()) {
        Box(
            modifier = modifier
                .clip(shape)
                .background(Color.LightGray.copy(alpha = 0.1f))
                .border(1.dp, Color.Gray.copy(alpha = 0.3f), shape)
                .clickable { onAddPhotoClick() }
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_camera_outline),
                    contentDescription = "Add Photo",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Tap to add photos", color = Color.Gray)
            }
        }
    } else {
        LazyRow(
            modifier = modifier
                .clip(shape)
                .background(Color.LightGray.copy(alpha = 0.05f))
                .padding(8.dp)
        ) {
            items(images) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray.copy(alpha = 0.2f))
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .clickable { onAddPhotoClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add More", tint = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun AddSinglePhotoCircle(onTap: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clickable { onTap() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.ic_camera_outline),
                contentDescription = null,
                tint = Color.Gray)
            Text("Tap here", color = Color.Gray, style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview_AddPhotosComponent_Empty() {
    AddPhotosComponent(
        images = emptyList(),
        onAddPhotoClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_AddSinglePhotoCircle() {
    AddSinglePhotoCircle(onTap = {})
}
@Preview(showBackground = true)
@Composable
fun Preview_AddPhotosComponent_WithImages() {
    val fakeUris = listOf(
        Uri.parse("https://placekitten.com/200/200"),
        Uri.parse("https://placekitten.com/210/210")
    )
    AddPhotosComponent(
        images = fakeUris,
        onAddPhotoClick = {}
    )
}
