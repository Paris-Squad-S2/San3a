package com.paris_2.san3a.presentation.screen.requestDetails.customer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.requestDetails.components.RequestInfoSection
import com.paris_2.san3a.presentation.screen.requestDetails.craftsman.toOfferDetailsUIState
import com.paris_2.san3a.presentation.screen.requests.component.RequestDetails
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.CraftsManOffer
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CustomerRequestDetailsScreen(
    viewModel: CustomerRequestDetailsRequestDetailsViewModel = koinViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    CustomerRequestDetailsScreenContent(state, viewModel)
}

@Composable
private fun CustomerRequestDetailsScreenContent(
    state: CustomerRequestDetailsScreenState,
    interactionListener: CustomerRequestDetailsInteractionListener,
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
                            .clickable(onClick = interactionListener::onClickActonDots),
                        painter = painterResource(R.drawable.ic_menu_dots_outline),
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
                CustomerRequestDetailsContent(
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
fun CustomerRequestDetailsContent(
    modifier: Modifier,
    state: CustomerRequestDetailsUiState,
    interactionListener: CustomerRequestDetailsInteractionListener
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
            RequestDetails(
                currentStep = if (state.offers.any { it.value.isAccepted }) {
                    2
                } else if (state.offers.isNotEmpty()) {
                    1
                } else {
                    0
                },
            )
        }

        if (state.offers.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    text = stringResource(R.string.offers_from_craftsmen),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary,
                )
            }

            items(state.offers.values.toList()) { offer ->
                CraftsManOffer(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .animateItem(),
                    addShadow = true,
                    showActionButtons = state.offers.any { it.value.isAccepted }.not(),
                    offerDetails = offer.toOfferDetailsUIState(offer.isAccepted),
                    painter = rememberAsyncImagePainter(offer.craftsmanImageUrl),
                    onSecondaryButtonClick = {
                        interactionListener.onChartWithCraftsmanClick(offer.craftsmanId)
                    },
                    onPrimaryButtonClick = {
                        interactionListener.onAcceptOfferClick(offer.id)
                    },
                    forCraftsMan = false,
                )
            }

        }

        item {
            AnimatedVisibility(
                visible = state.offers.isEmpty()
            ) {
                PlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp),
                    image = R.drawable.img_empty_offers,
                    title = R.string.waiting_for_offers,
                    description = R.string.craftsmen_in_your_area_will_send_offers_soon_you_ll_be_notified_when_they_arrive,
                )
            }
        }
    }
}