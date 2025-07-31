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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AddPhotosComponent(
    modifier: Modifier = Modifier,
    images: List<Uri>,
    onAddPhotoClick: () -> Unit
) {

    if (images.isEmpty()) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.bottomSheet)
                .dashedBorder(
                    color = Theme.colors.shade.quaternary,
                    strokeWidth = 1.dp,
                    dashLength = 6.dp,
                    gapLength = 6.dp,
                    shape = RoundedCornerShape(Theme.radius.large)
                )
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
                    tint = Theme.colors.shade.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    stringResource(R.string.tap_to_add_photos),
                    color = Theme.colors.shade.secondary,
                    style = Theme.textStyle.body.small.medium
                    )
            }
        }
    } else {
        LazyRow(
            modifier = modifier
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.bottomSheet)
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
                        .border(1.dp, Theme.colors.background.bottomSheet)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Theme.colors.background.bottomSheet)
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
            .background(Theme.colors.background.bottomSheet)
            .dashedBorder(
                color = Theme.colors.shade.quaternary,
                strokeWidth = 1.dp,
                dashLength = 6.dp,
                gapLength = 6.dp,
                shape = CircleShape
            )
            .clickable { onTap() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.ic_camera_outline),
                contentDescription = null,
                tint = Theme.colors.shade.secondary
            )
            Text(
                stringResource(R.string.tap_here),
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.small.medium
            )
        }
    }
}

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 2.dp,
    shape: androidx.compose.ui.graphics.Shape = RectangleShape,
    cap: StrokeCap = StrokeCap.Butt
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx())
            ),
            cap = cap
        )
        val outline = shape.createOutline(
            size = size,
            layoutDirection = layoutDirection,
            density = this
        )
        drawOutline(
            outline = outline,
            color = color,
            style = stroke
        )
    }
)

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
