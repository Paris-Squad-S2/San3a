package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.CraftsManOffer
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.OfferDetailsUIState
import com.paris_2.san3a.presentation.shared.components.OfferStatus
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CraftsManRequestDetailsScreen(
    viewModel: CraftsmanRequestDetailsViewModel = koinViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    CraftsManRequestDetailsScreenContent(state, viewModel)
}

@Composable
private fun CraftsManRequestDetailsScreenContent(
    state: CraftsmanRequestDetailsScreenState,
    interactionListener: CraftsmanRequestDetailsInteractionListener,
    modifier: Modifier = Modifier
) {

    AppScaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
        topBar = {
            AppBar(
                onBackClick = interactionListener::onClickBack,
                title = stringResource(R.string.request_details),
                actionIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = interactionListener::onClickFavorite),
                        painter = painterResource(R.drawable.ic_heart_outline),
                        contentDescription = null,
                        tint = Theme.colors.shade.primary
                    )
                }
            )
        },
        containerColor = Theme.colors.background.screen,
    ) {
        when {
            state.error != null -> {
                LostConnectionScreen(
                    onRetry = interactionListener::onRetryClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                        .padding(horizontal = 60.dp)
                )
            }

            state.isLoading -> {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                )
            }

            else -> {
                CraftsmanRequestDetailsContent(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = state.uiState,
                    interactionListener = interactionListener
                )
            }
        }
    }

}

@Composable
fun CraftsmanRequestDetailsContent(
    modifier: Modifier,
    state: CraftsmanRequestDetailsUiState,
    interactionListener: CraftsmanRequestDetailsInteractionListener
) {
    LazyColumn(
        modifier = modifier
            .navigationBarsPadding()
    ) {
        item {
            RequestInfoSection(
                request = state.request,
            )
        }
        item {
            AddYourOfferSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                interactionListener = interactionListener
            )
        }
        item {
            OffersFromCraftsmenSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = state,
                interactionListener = interactionListener
            )
        }
    }
}

@Composable
fun OffersFromCraftsmenSection(
    modifier: Modifier,
    state: CraftsmanRequestDetailsUiState,
    interactionListener: CraftsmanRequestDetailsInteractionListener
) {
    Column {
        AnimatedVisibility(
//            visible = state.yourOffers.contains(state.acceptedOffer)
            visible = true
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.chat_with_the_poster),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                ChatWithPosterCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = state,
                    interactionListener = interactionListener
                )
            }
        }
        AnimatedVisibility(
//            visible = state.acceptedOffer != null
            visible = true
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.selected_offer),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                CraftsManOffer(
                    addShadow = true,
                    offerDetails = OfferDetailsUIState(
                        status =
                            if (state.yourOffers.contains(state.acceptedOffer))
                                OfferStatus.YOUR_ACCEPTED_OFFER
                            else
                                OfferStatus.OFFER_ACCEPTED
                    ),
                    painter = painterResource(id = R.drawable.img_avatar2),
                    onChatClick = {},
                    onAcceptOfferClick = {},
                )
            }
        }
    }
}

@Composable
fun ChatWithPosterCard(
    modifier: Modifier = Modifier,
    state: CraftsmanRequestDetailsUiState,
    interactionListener: CraftsmanRequestDetailsInteractionListener
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.background.card)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(40.dp)
            ) {
                AsyncImage(
                    model = state.customer.profilePhoto,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = state.customer.name,
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.body.medium.medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = stringResource(R.string.request_poster),
                    style = Theme.textStyle.body.small.regular,
                    color = Theme.colors.shade.tertiary
                )
            }
        }

        AppButton(
            type = AppButtonType.Primary,
            onClick = { interactionListener.onChatWithPosterClick(state.customer.id) },
            text = stringResource(R.string.send_message),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            size = AppButtonSize.Large
        )
    }
}

@Composable
fun AddYourOfferSection(
    modifier: Modifier,
    interactionListener: CraftsmanRequestDetailsInteractionListener
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.tripleXLarge))
            .background(Theme.colors.background.card)
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(R.string.add_your_offer),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(R.string.be_competitive_but_fair_consider_materials_and_time),
            style = Theme.textStyle.body.small.regular,
            color = Theme.colors.shade.secondary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

    }
}

@Composable
fun RequestInfoSection(
    modifier: Modifier = Modifier,
    request: RequestServiceUIState,
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(Theme.radius.full))
                    .background(Theme.colors.additional.primary.blue.copy(alpha = 0.08f))
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_waterdrops_bold),
                    contentDescription = "",
                    tint = Theme.colors.additional.primary.blue,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = request.serviceType,
                    style = Theme.textStyle.body.medium.semibold,
                    color = Theme.colors.shade.primary
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = request.title,
                    style = Theme.textStyle.body.small.medium,
                    color = Theme.colors.shade.secondary
                )
            }
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            text = request.description,
            style = Theme.textStyle.body.medium.regular,
            color = Theme.colors.shade.secondary
        )
        RequestInfoIconTextRow(
            painter = painterResource(id = R.drawable.ic_clock_circle_outline),
            text = request.time,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        )
        RequestInfoIconTextRow(
            painter = painterResource(id = R.drawable.ic_location_outline),
            text = "${request.location}, ${request.locationDetails}",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
            color = Theme.colors.brand.primary
        )

        if (request.images.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(request.images.size) { index ->
                    val image = request.images[index]
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(Theme.radius.extraLarge))
                            .background(Theme.colors.background.card),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

@Composable
fun RequestInfoIconTextRow(
    painter: Painter,
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Theme.colors.shade.secondary
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = Theme.textStyle.body.small.regular,
            color = color
        )
    }
}