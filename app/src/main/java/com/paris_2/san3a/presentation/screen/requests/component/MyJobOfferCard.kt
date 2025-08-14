package com.paris_2.san3a.presentation.screen.requests.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.screen.requests.craftsman.JobUiState
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.CraftsmanOfferDetails
import com.paris_2.san3a.presentation.shared.components.OfferStatus
import com.paris_2.san3a.presentation.shared.components.ServiceTypeCard
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun MyJobOfferCard(
    modifier: Modifier = Modifier,
    jobUiState: JobUiState = JobUiState(),
    onViewDetailsRequest: () -> Unit,
    onSendMessage: () -> Unit,
    onMarkAsDone: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Theme.colors.background.card
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Column {
                ServiceTypeCard(
                    title = jobUiState.title,
                    serviceType = jobUiState.serviceType,
                    serviceImageUrl = jobUiState.serviceImage,
                )

                Row(Modifier.padding(top = 16.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_location_pin),
                        contentDescription = "",
                        tint = Theme.colors.shade.secondary
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = jobUiState.address,
                        style = Theme.textStyle.body.small.medium,
                        color = Theme.colors.shade.secondary
                    )
                }
                AppButton(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    type = AppButtonType.Secondary,
                    onClick = onViewDetailsRequest,
                    enableSecondaryBackgroundColor = Theme.colors.shade.quaternary,
                    text = stringResource(R.string.view_request_details),
                    size = AppButtonSize.Small,
                    state = AppButtonState.Enable
                )
            }


            jobUiState.offer?.let {

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Theme.colors.shade.quaternary,
                    thickness = 1.dp
                )

                CraftsmanOfferDetails(
                    modifier = Modifier
                        .background(Theme.colors.background.card)
                        .fillMaxWidth(),
                    painter = rememberAsyncImagePainter(jobUiState.offer.craftsMan.profileUrl),
                    isVerified = jobUiState.offer.craftsMan.isVerify,
                    name = jobUiState.offer.craftsMan.name,
                    postedTime = jobUiState.offer.preferredTime,
                    description = jobUiState.offer.messageToCustomer,
                    amount = jobUiState.offer.price.toString(),
                    rate = jobUiState.offer.craftsMan.rating,
                    reviewsNumber = jobUiState.offer.craftsMan.review,
                    status = OfferStatus.PENDING_OFFER,
                    time = jobUiState.offer.preferredTime,
                    stickyFooter = {
                        if (jobUiState.status == RequestStatus.ONGOING) {
                            when (jobUiState.offer.isAccepted) {
                                true -> {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 24.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        AppButton(
                                            type = AppButtonType.Secondary,
                                            onClick = onSendMessage,
                                            text = stringResource(R.string.send_message),
                                            modifier = Modifier.weight(1f),
                                            size = AppButtonSize.Small,
                                            state = AppButtonState.Enable,
                                            enableSecondaryBackgroundColor = Theme.colors.shade.quaternary
                                        )
                                        AppButton(
                                            type = AppButtonType.Primary,
                                            onClick = onMarkAsDone,
                                            text = stringResource(R.string.mark_as_done),
                                            modifier = Modifier.weight(1f),
                                            size = AppButtonSize.Small
                                        )
                                    }
                                }

                                false -> {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 16.dp),
                                        color = Theme.colors.shade.quaternary,
                                        thickness = 1.dp
                                    )
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = stringResource(R.string.waiting_for_the_approval),
                                        textAlign = TextAlign.Center,
                                        style = Theme.textStyle.body.medium.regular,
                                        color = Theme.colors.shade.secondary
                                    )
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}

@PreviewMultiDevices
@Composable
fun MyJobOfferCardPreview() {
    BasePreview {
        MyJobOfferCard(
            jobUiState = JobUiState(),
            onViewDetailsRequest = {},
            onSendMessage = {},
            onMarkAsDone = {}
        )
    }
}