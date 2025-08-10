package com.paris_2.san3a.presentation.screen.requests.component

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.BottomSheet
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RatingBottomSheet(
    isVisible: Boolean,
    rating: Float,
    onDismiss: () -> Unit,
    onRatingChange: (Float) -> Unit,
    onAddRating: () -> Unit,
) {
    BottomSheet(
        isVisible = isVisible,
        onDismissRequest = onDismiss,
        header = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.rate_craftsman),
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.small,
                    modifier = Modifier.weight(1F)
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.close),
                        tint = Theme.colors.shade.secondary
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            StarRatingBar(
                rating = rating,
                onRatingChange = onRatingChange,
            )


            Spacer(modifier = Modifier.height(16.dp))

            AppButton(
                type = AppButtonType.Primary,
                onClick = onAddRating,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                size = AppButtonSize.Large,
                state = if (rating > 0f) AppButtonState.Enable else AppButtonState.Disabled,
                text = stringResource(R.string.add_rating)
            )
        }
    }
}


@Composable
fun StarRatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    onRatingChange: (Float) -> Unit,
    maxRating: Int = 5,
    starSize: Dp = 32.dp,
    spacing: Dp = 6.dp,
    filledColor: Color = Theme.colors.additional.primary.yellow,
    unfilledColor: Color = Theme.colors.additional.primary.yellow,
    isRtl: Boolean = false,
) {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    Row(
        modifier = modifier
            .height(starSize)
            .pointerInput(maxRating, starSize, spacing) {
                detectHorizontalDragGestures { change, _ ->
                    val x = change.position.x
                    val starWidthPx = with(density) { starSize.toPx() }
                    val spacingPx = with(density) { spacing.toPx() }
                    val starPlusSpacing = starWidthPx + spacingPx
                    val effectiveX =
                        if (layoutDirection == LayoutDirection.Rtl) size.width - x else x
                    val newRating = (effectiveX / starPlusSpacing).coerceIn(0f, maxRating.toFloat())
                    onRatingChange(newRating)
                }
            }
            .pointerInput(maxRating, starSize, spacing) {
                detectTapGestures { offset ->
                    val starWidthPx = with(density) { starSize.toPx() }
                    val spacingPx = with(density) { spacing.toPx() }
                    val starPlusSpacing = starWidthPx + spacingPx
                    val x =
                        if (layoutDirection == LayoutDirection.Rtl) size.width - offset.x else offset.x
                    val newRating = (x / starPlusSpacing).coerceIn(0f, maxRating.toFloat())
                    onRatingChange(newRating)
                }
            },
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxRating) {
            val fillFraction = when {
                rating >= i -> 1f
                rating < i - 1 -> 0f
                else -> rating - (i - 1)
            }

            StarIcon(
                modifier = Modifier.size(starSize),
                fillRatio = if (isRtl) 1 - fillFraction else fillFraction,
                filledColor = filledColor,
                unfilledColor = unfilledColor,
                layoutDirection = if (isRtl) layoutDirection.toggle() else layoutDirection
            )
        }
    }
}

fun LayoutDirection.toggle(): LayoutDirection {
    return if (this == LayoutDirection.Ltr) LayoutDirection.Rtl else LayoutDirection.Ltr
}


@Composable
private fun StarIcon(
    modifier: Modifier = Modifier,
    fillRatio: Float,
    layoutDirection: LayoutDirection,
    filledColor: Color,
    unfilledColor: Color,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val iconModifier = Modifier.matchParentSize()

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_star_outline),
            contentDescription = null,
            tint = unfilledColor,
            modifier = iconModifier
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
            contentDescription = null,
            tint = filledColor,
            modifier = iconModifier
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    clipRect(
                        left = if (layoutDirection == LayoutDirection.Rtl) size.width - size.width * fillRatio else 0f,
                        right = if (layoutDirection == LayoutDirection.Rtl) size.width else size.width * fillRatio
                    ) {
                        this@drawWithContent.drawContent()
                    }
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStarRatingBar() {
    var currentRating by remember { mutableFloatStateOf(3.7f) }

    RatingBottomSheet(
        isVisible = true,
        rating = currentRating,
        onDismiss = {},
        onRatingChange = { currentRating = it },
        onAddRating = {}
    )
}


