package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.requestDetails.components.AddOfferForm
import com.paris_2.san3a.presentation.screen.requestDetails.components.RequestInfoSection
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppButton
import com.paris_2.san3a.presentation.shared.components.AppButtonSize
import com.paris_2.san3a.presentation.shared.components.AppButtonType
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.CraftsManOffer
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
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
                state = state,
                interactionListener = interactionListener
            )
        }

        item {
            AnimatedVisibility(
                visible = state.yourOffers.contains(state.acceptedOffer)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
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
        }


        item {

            AnimatedVisibility(
                visible = state.acceptedOffer != null
            ) {
                if (state.acceptedOffer != null) {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.selected_offer),
                            style = Theme.textStyle.title.small,
                            color = Theme.colors.shade.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        CraftsManOffer(
                            addShadow = true,
                            showActionButtons = state.yourOffers.contains(state.acceptedOffer),
                            offerDetails = state.acceptedOffer.toOfferDetailsUIState(
                                offerAccepted = state.yourOffers.contains(state.acceptedOffer)
                            ),
                            painter = rememberAsyncImagePainter(model = state.acceptedOffer.craftsmanImageUrl),
                            onChatClick = {
                                interactionListener.onChatWithPosterClick(state.customer.id)
                            },
                            onAcceptOfferClick = {
                                interactionListener.onAcceptOfferClick(state.acceptedOffer.id)
                            },
                        )
                    }
                }
            }
        }

        if (state.yourOffers.isNotEmpty()){
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.your_offers),
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(
                            R.string.offers_count,
                            state.yourOffers.size
                        ),
                        style = Theme.textStyle.body.small.regular,
                        color = Theme.colors.shade.tertiary,
                    )
                }
            }

            items(state.yourOffers) { offer ->
                CraftsManOffer(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .animateItem(),
                    addShadow = true,
                    showActionButtons = false,
                    offerDetails = offer.toOfferDetailsUIState(),
                    painter = rememberAsyncImagePainter(model = offer.craftsmanImageUrl),
                    onChatClick = {
                        interactionListener.onChatWithPosterClick(offer.craftsmanId)
                    },
                    onAcceptOfferClick = {
                        interactionListener.onAcceptOfferClick(offer.id)
                    },
                )
            }
        }

        if (state.offersFromCraftsman.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.offers_from_craftsmen),
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(
                            R.string.offers_count,
                            state.offersFromCraftsman.size
                        ),
                        style = Theme.textStyle.body.small.regular,
                        color = Theme.colors.shade.tertiary,
                    )
                }
            }

            items(state.offersFromCraftsman) { offer ->
                CraftsManOffer(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .animateItem(),
                    addShadow = true,
                    showActionButtons = false,
                    offerDetails = offer.toOfferDetailsUIState(),
                    painter = rememberAsyncImagePainter(model = offer.craftsmanImageUrl),
                    onChatClick = {
                        interactionListener.onChatWithPosterClick(offer.craftsmanId)
                    },
                    onAcceptOfferClick = {
                        interactionListener.onAcceptOfferClick(offer.id)
                    },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddYourOfferSection(
    modifier: Modifier,
    interactionListener: CraftsmanRequestDetailsInteractionListener,
    state: CraftsmanRequestDetailsUiState
) {
    AddOfferForm(
        modifier = modifier,
        price = state.offerToAdd.price,
        onPriceChange = interactionListener::onPriceChanged,
        selectedDate = state.offerToAdd.preferredDate,
        onDateChange = interactionListener::onDateChanged,
        selectedTime = state.offerToAdd.preferredTime,
        onTimeChange = interactionListener::onTimeChanged,
        message = state.offerToAdd.messageToCustomer,
        onMessageChange = interactionListener::onMessageChanged,
        onSendClick = interactionListener::onSendOfferClick,
        showDatePicker = state.showDatePicker,
        onShowDatePickerChange = interactionListener::onShowDatePickerChange,
        datePickerState = rememberDatePickerState(),
        timePickerState = rememberTimePickerState(),
        showTimePicker = state.showTimePicker,
        onShowTimePickerChange = interactionListener::onShowTimePickerChange
    )
}

