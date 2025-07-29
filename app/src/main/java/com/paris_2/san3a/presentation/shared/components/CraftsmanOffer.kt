package com.paris_2.san3a.presentation.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import com.paris_2.san3a.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.shadow

@Composable
fun CraftsManOffer(
    modifier: Modifier = Modifier,
    painter: Painter,
    name: String,
    rate: Float,
    reviewsNumber: Int,
    description: String,
    amount: String,
    time: String,
    addShadow: Boolean = false,
    onChatClick: () -> Unit,
    postedTime: String ,
    onAcceptOfferClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .let {
                if (addShadow) {
                    it
                        .graphicsLayer {
                            shadowElevation = 0.dp.toPx()
                            translationY = (-3.48).dp.toPx()
                        }
                        .shadow(
                            elevation = 69.52.dp,
                            shape = RoundedCornerShape(16.dp),
                            clip = false,
                            ambientColor = Color.Black.copy(alpha = 0.08f),
                            spotColor = Color.Black.copy(alpha = 0.08f)
                        )
                } else {
                    it
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.background.card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "User profile picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_verified_check_bold),
                        contentDescription = "Verified icon",
                        tint = Theme.colors.additional.primary.success,
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.body.medium.medium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_bold),
                            contentDescription = "Star icon",
                            tint = Theme.colors.additional.primary.yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$rate ($reviewsNumber reviews)",
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.primary
                        )
                    }
                }
                Text(
                    text = postedTime,
                    style = Theme.textStyle.body.small.regular,
                    color = Theme.colors.shade.tertiary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wallet_outline),
                    contentDescription = "Amount icon",
                    tint = Theme.colors.shade.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = amount,
                    color = Theme.colors.shade.secondary,
                    style = Theme.textStyle.body.small.medium,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(2.6.dp)
                        .clip(CircleShape)
                        .background(Theme.colors.shade.tertiary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock_circle_outline),
                    contentDescription = "Time icon",
                    tint = Theme.colors.shade.secondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = time,
                    color = Theme.colors.shade.secondary,
                    style = Theme.textStyle.body.small.medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AppButton(
                    type = AppButtonType.Primary,
                    onClick = onChatClick,
                    text = R.string.chat,
                    modifier = Modifier.weight(1f),
                    size = AppButtonSize.Large,
                    state = AppButtonState.Disabled
                )
                AppButton(
                    type = AppButtonType.Primary,
                    onClick = onAcceptOfferClick,
                    text = R.string.accept_offer,
                    modifier = Modifier.weight(1f),
                    size = AppButtonSize.Large
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardsPreview() {
    CraftsManOffer(
        painter = painterResource(id = R.drawable.img_avatar1),
        name = "Muhammed Ali",
        rate = 4.7f,
        reviewsNumber = 121,
        description = "I can fix this today. I have 10+ years experience with kitchen plumbing.",
        amount = "50,000 IQD",
        time = "Tomorrow, 2:00 PM",
        addShadow = true,
        postedTime = "1 hour ago",
        onChatClick = {},
        onAcceptOfferClick = {}
    )
}

