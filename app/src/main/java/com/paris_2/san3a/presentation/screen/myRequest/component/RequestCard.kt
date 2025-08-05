package com.paris_2.san3a.presentation.screen.myRequest.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.CraftsmanAvatar
import com.paris_2.san3a.presentation.shared.components.IconPosition
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun RequestCard(
    modifier: Modifier = Modifier,
    icon: Painter,
    type: String,
    date: String,
    time: String,
    craftsmanImageUri: Painter?,
    craftsmanName: String?,
    rating: Float?,
    actionButtonText: String,
    onActionClick: () -> Unit,
    showOffersLink: Boolean = false,
    buttonIconPosition: IconPosition? = null,
    buttonIcon: ImageVector? = null,
    isVerified: Boolean = false,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Theme.colors.background.card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Theme.colors.additional.secondary.blue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = Theme.colors.brand.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = stringResource(R.string.details_title),
                        style = Theme.textStyle.body.medium.semibold,
                        color = Theme.colors.shade.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = type,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .background(Theme.colors.shade.quaternary)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithText(
                    icon = ImageVector.vectorResource(R.drawable.ic_calendar_minimalistic_outline),
                    text = date
                )

                Spacer(
                    modifier = Modifier
                        .width(16.dp)
                )

                Divider(
                    modifier = Modifier
                        .height(16.dp)
                        .width(1.dp)
                        .background(Theme.colors.shade.quaternary)
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconWithText(
                    icon = ImageVector.vectorResource(R.drawable.ic_clock_circle_outline),
                    text = time,
                )
            }


            Spacer(modifier = Modifier.height(12.dp))
            Divider(
                modifier = Modifier
                    .background(Theme.colors.shade.quaternary)
                    .height(1.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CraftsmanSection(
                    imageUri = craftsmanImageUri,
                    name = craftsmanName,
                    rating = rating,
                    showOffersLink = showOffersLink,
                    isVerified = isVerified
                )
                AppButton(
                    text = actionButtonText,
                    onClick = onActionClick,
                    type = AppButtonType.Secondary,
                    size = AppButtonSize.Small,
                    iconPosition = buttonIconPosition,
                    icon = buttonIcon
                )
            }
        }
    }
}


@Composable
private fun IconWithText(icon: ImageVector, text: String) {
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isRtl) {
            Text(
                text = text,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Theme.colors.shade.secondary
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Theme.colors.shade.secondary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary
            )
        }
    }
}


@Composable
private fun CraftsmanSection(
    imageUri: Painter?,
    name: String?,
    rating: Float?,
    showOffersLink: Boolean,
    isVerified: Boolean = false,
) {
    if (imageUri != null && !name.isNullOrEmpty()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                )

            if (isVerified) {
                CraftsmanAvatar(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    isVerify = true,
                    modifier = Modifier.size(36.dp)
                )
            } else if (imageUri != null ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    name,
                    style = Theme.textStyle.body.medium.medium,
                    color = Theme.colors.shade.primary
                )
                rating?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (LocalLayoutDirection.current == LayoutDirection.Rtl) {
                            Text(
                                "$it",
                                style = Theme.textStyle.body.small.medium,
                                color = Theme.colors.shade.secondary
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_star_bold),
                                contentDescription = null,
                                tint = Theme.colors.additional.primary.yellow,
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_star_bold),
                                contentDescription = null,
                                tint = Theme.colors.additional.primary.yellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                "$it",
                                style = Theme.textStyle.body.small.medium,
                                color = Theme.colors.shade.secondary
                            )
                        }
                    }
                }
            }
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {

            val backgroundColor = Theme.colors.background.bottomSheetCard.copy(alpha = 0.1f)
            val borderColor = Theme.colors.shade.quaternary

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(backgroundColor, CircleShape)
                    .drawBehind {
                        val strokeWidth = 2.dp.toPx()
                        val radius = (size.minDimension - strokeWidth) / 2
                        drawCircle(
                            color = borderColor,
                            radius = radius,
                            center = center,
                            style = Stroke(
                                width = strokeWidth,
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(12f, 15f),
                                    phase = 0f
                                )
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.due_tone_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape),
                    tint = Theme.colors.shade.tertiary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = stringResource(R.string.craftsman_not_chosen),
                    style = Theme.textStyle.body.small.medium
                )
                Spacer(modifier = Modifier.width(8.dp))

                if (showOffersLink) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {}
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clipboard_outline),
                            contentDescription = null,
                            tint = Theme.colors.brand.primary,
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.offers),
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.brand.primary
                        )
                    }
                }
            }
        }

    }
}
