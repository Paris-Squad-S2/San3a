package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun AddPhotosContent(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = Theme.colors
    val radius = Theme.radius
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.bottomSheet)
            .clickable { onClick() }
    ){
        Canvas(modifier = Modifier
            .matchParentSize()
            .padding(1.dp)) {
            val stroke = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(25f,25f))
            )
            drawRoundRect(
                color = colors.shade.quaternary,
                size = size,
                cornerRadius = CornerRadius(radius.large.toPx(), radius.large.toPx()),
                style = stroke
            )
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = "Add Photos",
                tint = Theme.colors.shade.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(R.string.tap_to_add_photos),
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
            )
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun Preview(){
    San3aTheme {
        AddPhotosContent(
            icon = painterResource(id = R.drawable.ic_camera_outline),
            onClick = {}
        )
    }
}

