package com.paris_2.san3a.presentation.screen.requests.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.screen.requests.customer.MyRequestCustomerUi
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.CraftsmanAvatar
import com.paris_2.san3a.presentation.shared.components.IconPosition
import com.paris_2.san3a.presentation.shared.components.ServiceTypeCard
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun RequestCard(
    modifier: Modifier = Modifier,
    requestUi: MyRequestCustomerUi = MyRequestCustomerUi(),
    onActionClick: () -> Unit,
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

            ServiceTypeCard(title = requestUi.requestTitle, serviceType = requestUi.serviceType)

            if (requestUi.status == RequestStatus.ONGOING && requestUi.offer.isAccepted) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    thickness = 1.dp,
                    color = Theme.colors.shade.quaternary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconWithText(
                        icon = ImageVector.vectorResource(R.drawable.ic_calendar_minimalistic_outline),
                        text = requestUi.date,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(16.dp)
                            .clip(RoundedCornerShape(Theme.radius.full)),
                        thickness = 1.dp,
                        color = Theme.colors.shade.quaternary,

                        )

                    IconWithText(
                        icon = ImageVector.vectorResource(R.drawable.ic_clock_circle_outline),
                        text = requestUi.time,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 1.dp,
                color = Theme.colors.shade.quaternary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CraftsmanSection(
                    imageUri = requestUi.offer.craftsMan.profileUrl,
                    name = requestUi.offer.craftsMan.name,
                    rating = requestUi.offer.craftsMan.rating,
                    isAcceptedOffer = requestUi.offer.isAccepted,
                    isVerified = requestUi.offer.craftsMan.isVerify,
                    numberOfOffers = requestUi.offersCount
                )

                val buttonConfig = getButtonConfig(requestUi)

                if (buttonConfig.text != null) {
                    AppButton(
                        text = buttonConfig.text,
                        onClick = onActionClick,
                        type = AppButtonType.Secondary,
                        size = AppButtonSize.Small,
                        icon = buttonConfig.icon,
                        iconPosition = IconPosition.Start,
                    )
                }
            }
        }
    }
}

@Composable
private fun IconWithText(modifier: Modifier, icon: ImageVector, text: String) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Theme.colors.shade.secondary
        )

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            style = Theme.textStyle.body.small.medium,
            color = Theme.colors.shade.secondary
        )
    }
}


@Composable
private fun CraftsmanSection(
    imageUri: String?,
    name: String?,
    rating: Float?,
    isAcceptedOffer: Boolean,
    isVerified: Boolean = false,
    numberOfOffers: Int = 0
) {


    if (imageUri != null && !name.isNullOrEmpty()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isVerified) {
                CraftsmanAvatar(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    isVerify = true,
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
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
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
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
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))

                if (!isAcceptedOffer) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {}
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
                            text = stringResource(
                                R.string.number_of_offers,
                                numberOfOffers
                            ),
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.brand.primary
                        )
                    }
                }
            }
        }

    }
}

@PreviewMultiDevices
@Composable
fun RequestCardNoCraftsmanWithOffersPreview() {
    BasePreview {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .padding(4.dp)
                .scrollable(state = scrollState, orientation = Orientation.Vertical)
        ) {
            RequestCard(
                requestUi = MyRequestCustomerUi(
                    requestTitle = "request title",
                    serviceType = "Plumbing",
                    offersCount = 3,
                    date = "2025-08-04",
                    time = "10:30 AM",
                ),
                onActionClick = {},

                )
            Spacer(Modifier.height(16.dp))
            RequestCard(

                requestUi = MyRequestCustomerUi(
                    requestTitle = "request title",
                    serviceType = "Electrical",
                    offersCount = 3,
                    date = "2025-08-05",
                    time = "2:00 PM",
                ),
                onActionClick = {},
            )
            Spacer(Modifier.height(16.dp))
            RequestCard(
                requestUi = MyRequestCustomerUi(
                    requestTitle = "Electrical Work",
                    serviceType = "Electrical",
                    offersCount = 3,
                    date = "2025-08-06",
                    time = "9:00 AM",
                    status = RequestStatus.COMPLETED,
                    isRated = true
                ),
                onActionClick = {},
            )
        }
    }
}


