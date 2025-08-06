package com.paris_2.san3a.presentation.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun CraftsmanOfferDetails(
    modifier: Modifier = Modifier,
    painter: Painter?,
    isVerified : Boolean = false,
    name : String?,
    postedTime : String,
    description : String,
    amount : String,
    rate : Float?,
    reviewsNumber : Int?,
    status : OfferStatus = OfferStatus.PENDING_OFFER,
    time : String,
    onChatClick: () -> Unit,
    onAcceptOfferClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(40.dp)
            ) {
                if (painter != null) {
                    Image(
                        painter = painter,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                    )
                }

                this@Row.AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 8.dp),
                    visible = isVerified
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
                    name?.let {
                        Text(
                            text = it,
                            color = Theme.colors.shade.primary,
                            style = Theme.textStyle.body.medium.medium
                        )
                    }
                    Text(
                        text = postedTime,
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
                    rate?.let {
                        reviewsNumber?.let { it1 ->
                            Text(
                                text = stringResource(
                                    R.string.craftsman_reviews,
                                    it,
                                    it1
                                ),
                                style = Theme.textStyle.body.small.medium,
                                color = Theme.colors.shade.primary
                            )
                        }
                    }
                }
            }
        }


        Text(
            text = description,
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
                text = amount,
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
                text = time,
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.small.medium
            )
        }

        if (status == OfferStatus.PENDING_OFFER)
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