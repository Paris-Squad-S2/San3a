package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

enum class OfferStatus {
    PENDING_OFFER,
    OFFER_ACCEPTED,
    YOUR_ACCEPTED_OFFER
}

data class OfferDetailsUIState(
    val imageUrl: String? = null,
    val name: String = "Muhammed Ali",
    val rate: Float = 4.7f,
    val reviewsNumber: Int = 121,
    val description: String = "I can fix this today. I have 10+ years experience with kitchen plumbing.",
    val amount: String = "50,000 IQD",
    val time: String = "Tomorrow, 2:00 PM",
    val postedTime: String = "1 hour ago",
    val status: OfferStatus = OfferStatus.PENDING_OFFER,
    val isVerify: Boolean = true
)

/*todo use this when u want to load image from url firebase
val painter = if (offerDetails.imageUrl != null) {
            rememberAsyncImagePainter(
                model = offerDetails.imageUrl,
                placeholder = painterResource(R.drawable.img_avatar),
                error = painterResource(R.drawable.img_avatar),
            )
        } else {
            painterResource(id = R.drawable.img_avatar)
        }*/

@Composable
fun CraftsManOffer(
    modifier: Modifier = Modifier,
    painter: Painter,
    offerDetails: OfferDetailsUIState,
    addShadow: Boolean = false,
    onChatClick: () -> Unit,
    onAcceptOfferClick: () -> Unit
) {

    val targetColor = when (offerDetails.status) {
        OfferStatus.OFFER_ACCEPTED -> Theme.colors.additional.primary.success
        else -> Theme.colors.background.card
    }
    val animatedBgColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 500)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .let {
                if (addShadow) {
                    it
                        .graphicsLayer {
                            shadowElevation = 0.dp.toPx()
                            translationY = (-3.48).dp.toPx()
                        }
                        .shadow(
                            elevation = 69.52.dp,
                            shape = RoundedCornerShape(Theme.radius.extraLarge),
                            clip = false,
                            ambientColor = Color.Black.copy(alpha = 0.08f),
                            spotColor = Color.Black.copy(alpha = 0.08f)
                        )
                } else {
                    it
                }
            },
        shape = RoundedCornerShape(Theme.radius.extraLarge),
        colors = CardDefaults.cardColors(containerColor = animatedBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {

        if (offerDetails.status == OfferStatus.OFFER_ACCEPTED)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(R.string.accept_offer),
                color = Theme.colors.background.card,
                style = Theme.textStyle.body.medium.medium,
                textAlign = TextAlign.Center
            )
        Column(
            modifier = Modifier
                .padding(
                    start = 2.dp,
                    end = 2.dp,
                    bottom = 2.dp
                )
                .clip(
                    RoundedCornerShape(
                        bottomEnd = Theme.radius.extraLarge,
                        bottomStart = Theme.radius.extraLarge
                    )
                )
                .background(Theme.colors.background.card)
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
                        contentDescription = "",
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                    )

                    androidx.compose.animation.AnimatedVisibility(
                        modifier = Modifier
                            .align(Alignment.BottomCenter).offset(y=8.dp), visible = offerDetails.isVerify
                    ) {
                        Box(
                            modifier = Modifier.size(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_verified_check_bold),
                                contentDescription = stringResource(R.string.verified_check),
                                modifier = Modifier
                                    .size(16.dp),
                                tint = Theme.colors.additional.primary.success
                            )

                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = stringResource(R.string.verified_check),
                                modifier = Modifier
                                    .size(8.dp),
                                tint = Color.White
                            )
                        }

                    }

                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = offerDetails.name,
                            color = Theme.colors.shade.primary,
                            style = Theme.textStyle.body.medium.medium
                        )
                        Text(
                            text = offerDetails.postedTime,
                            style = Theme.textStyle.body.small.regular,
                            color = Theme.colors.shade.tertiary
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_bold),
                            contentDescription = "Star icon",
                            tint = Theme.colors.additional.primary.yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(
                                R.string.craftsman_reviews,
                                offerDetails.rate,
                                offerDetails.reviewsNumber
                            ),
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.primary
                        )
                    }
                }
            }


            Text(
                text = offerDetails.description,
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
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
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(16.dp)
                )
                Text(
                    text = offerDetails.amount,
                    color = Theme.colors.shade.secondary,
                    style = Theme.textStyle.body.small.medium,
                )
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(2.6.dp)
                        .clip(CircleShape)
                        .background(Theme.colors.shade.tertiary)

                )
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.ic_clock_circle_outline),
                    contentDescription = "",
                    tint = Theme.colors.shade.secondary
                )
                Text(
                    text = offerDetails.time,
                    color = Theme.colors.shade.secondary,
                    style = Theme.textStyle.body.small.medium
                )
            }

            if (offerDetails.status == OfferStatus.PENDING_OFFER)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    AppButton(
                        type = AppButtonType.Primary,
                        onClick = onChatClick,
                        text = stringResource(R.string.chat),
                        modifier = Modifier.weight(1f),
                        size = AppButtonSize.Large,
                        state = AppButtonState.Disabled
                    )
                    AppButton(
                        type = AppButtonType.Primary,
                        onClick = onAcceptOfferClick,
                        text = stringResource(R.string.accept_offer),
                        modifier = Modifier.weight(1f),
                        size = AppButtonSize.Large
                    )
                }
        }
    }
}

@PreviewMultiDevices
@Composable
fun CardsPreview() {
    BasePreview {
        Column(Modifier.padding(4.dp)) {
            CraftsManOffer(
                addShadow = true,
                offerDetails = OfferDetailsUIState(),
                painter = painterResource(id = R.drawable.img_avatar1),
                onChatClick = {},
                onAcceptOfferClick = {},
            )
            Spacer(Modifier.height(16.dp))
            CraftsManOffer(
                addShadow = true,
                offerDetails = OfferDetailsUIState(status = OfferStatus.OFFER_ACCEPTED),
                painter = painterResource(id = R.drawable.img_avatar2),
                onChatClick = {},
                onAcceptOfferClick = {},
            )
            Spacer(Modifier.height(16.dp))
            CraftsManOffer(
                addShadow = true,
                offerDetails = OfferDetailsUIState(status = OfferStatus.YOUR_ACCEPTED_OFFER),
                painter = painterResource(id = R.drawable.img_avatar3),
                onChatClick = {},
                onAcceptOfferClick = {},
            )
        }
    }
}

