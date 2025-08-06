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
import androidx.compose.ui.layout.ContentScale
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
        CraftsmanOfferDetails(
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
            painter = painter,
            isVerified = offerDetails.isVerify,
            onChatClick = onChatClick,
            onAcceptOfferClick = onAcceptOfferClick,
            name = offerDetails.name,
            postedTime = offerDetails.postedTime,
            description = offerDetails.description,
            amount = offerDetails.amount,
            rate = offerDetails.rate,
            reviewsNumber = offerDetails.reviewsNumber,
            status = offerDetails.status,
            time = offerDetails.time

        )
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

