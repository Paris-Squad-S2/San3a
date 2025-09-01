package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import kotlinx.datetime.LocalDateTime

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
    val amount: String = "50,000",
    val dateTime: LocalDateTime? = null,
    val postedTime: LocalDateTime? = null,
    val status: OfferStatus = OfferStatus.PENDING_OFFER,
    val isVerify: Boolean = true
)

@Composable
fun CraftsManOffer(
    modifier: Modifier = Modifier,
    painter: Painter,
    offerDetails: OfferDetailsUIState,
    addShadow: Boolean = false,
    onSecondaryButtonClick: () -> Unit,
    onPrimaryButtonClick: () -> Unit,
    showActionButtons: Boolean = true,
    forCraftsMan: Boolean
) {

    val targetColor = when (offerDetails.status) {
        OfferStatus.OFFER_ACCEPTED -> Theme.colors.additional.primary.success
        OfferStatus.YOUR_ACCEPTED_OFFER -> Theme.colors.additional.primary.success
        else -> Theme.colors.background.card
    }
    val animatedBgColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 500)
    )

    Card(
        modifier = modifier
            .run {
                if (!addShadow) {
                    this
                } else {
                    shadow(
                        elevation = 5.dp,
                        clip = false,
                        shape = RoundedCornerShape(Theme.radius.extraLarge),
                        ambientColor = Color.Black.copy(alpha = 0.6f),
                        spotColor = Color.Black.copy(alpha = 0.6f)
                    )
                }
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(Theme.radius.extraLarge),
        colors = CardDefaults.cardColors(containerColor = animatedBgColor),
    ) {

        val paddingAcceptedRequest = if (offerDetails.status == OfferStatus.OFFER_ACCEPTED || offerDetails.status == OfferStatus.YOUR_ACCEPTED_OFFER)  2.dp  else 0.dp

        if (offerDetails.status == OfferStatus.OFFER_ACCEPTED || offerDetails.status == OfferStatus.YOUR_ACCEPTED_OFFER) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = stringResource(R.string.accept_offer),
                color = Theme.colors.background.card,
                style = Theme.textStyle.body.medium.medium,
                textAlign = TextAlign.Center
            )
        }
        CraftsmanOfferDetails(
            modifier = Modifier
                .padding(
                    start = paddingAcceptedRequest,
                    end = paddingAcceptedRequest,
                    bottom = paddingAcceptedRequest
                )
                .clip(
                    RoundedCornerShape(
                        bottomEnd = Theme.radius.extraLarge,
                        bottomStart = Theme.radius.extraLarge
                    )
                )
                .background(Theme.colors.background.card)
                .fillMaxWidth()
                .padding(if (offerDetails.status == OfferStatus.OFFER_ACCEPTED || offerDetails.status == OfferStatus.YOUR_ACCEPTED_OFFER) 14.dp else 16.dp),
            painter = painter,
            isVerified = offerDetails.isVerify,
            name = offerDetails.name,
            postedTime = offerDetails.postedTime,
            description = offerDetails.description,
            amount = offerDetails.amount,
            rate = offerDetails.rate,
            reviewsNumber = offerDetails.reviewsNumber,
            status = offerDetails.status,
            dateTime = offerDetails.dateTime,
            stickyFooter = {
                if (showActionButtons) {
                    when {
                        it == OfferStatus.PENDING_OFFER && !forCraftsMan -> {
                            ActionButtonsRow(
                                primaryText = stringResource(R.string.accept_offer),
                                secondaryText = stringResource(R.string.chat),
                                onPrimaryClick = onPrimaryButtonClick,
                                onSecondaryClick = onSecondaryButtonClick
                            )
                        }
                        it == OfferStatus.YOUR_ACCEPTED_OFFER && forCraftsMan -> {
                            ActionButtonsRow(
                                primaryText = stringResource(R.string.mark_as_done),
                                secondaryText = stringResource(R.string.cancel_request),
                                onPrimaryClick = onPrimaryButtonClick,
                                onSecondaryClick = onSecondaryButtonClick
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ActionButtonsRow(
    primaryText: String,
    secondaryText: String,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AppButton(
            type = AppButtonType.Secondary,
            onClick = onSecondaryClick,
            text = secondaryText,
            modifier = Modifier.weight(1f),
            size = AppButtonSize.Small,
            state = AppButtonState.Enable,
            enableSecondaryBackgroundColor = Theme.colors.shade.quaternary
        )
        AppButton(
            type = AppButtonType.Primary,
            onClick = onPrimaryClick,
            text = primaryText,
            modifier = Modifier.weight(1f),
            size = AppButtonSize.Small
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
                onSecondaryButtonClick = {},
                onPrimaryButtonClick = {},
                forCraftsMan = false
            )
            Spacer(Modifier.height(16.dp))
            CraftsManOffer(
                addShadow = true,
                offerDetails = OfferDetailsUIState(status = OfferStatus.OFFER_ACCEPTED),
                painter = painterResource(id = R.drawable.img_avatar2),
                onSecondaryButtonClick = {},
                onPrimaryButtonClick = {},
                forCraftsMan = false
            )
            Spacer(Modifier.height(16.dp))
            CraftsManOffer(
                addShadow = true,
                offerDetails = OfferDetailsUIState(status = OfferStatus.YOUR_ACCEPTED_OFFER),
                painter = painterResource(id = R.drawable.img_avatar3),
                onSecondaryButtonClick = {},
                onPrimaryButtonClick = {},
                forCraftsMan = true
            )
        }
    }
}