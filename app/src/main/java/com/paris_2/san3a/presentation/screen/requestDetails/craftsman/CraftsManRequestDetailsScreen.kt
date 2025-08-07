package com.paris_2.san3a.presentation.screen.requestDetails.craftsman

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
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
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
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
    Column(
        modifier = modifier
    ) {
        RequestInfoSection(
            request = state.request,
        )
        AddYourOfferSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            interactionListener = interactionListener
        )
    }
}

@Composable
fun AddYourOfferSection(
    modifier: Modifier,
    interactionListener: CraftsmanRequestDetailsInteractionListener
) {

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