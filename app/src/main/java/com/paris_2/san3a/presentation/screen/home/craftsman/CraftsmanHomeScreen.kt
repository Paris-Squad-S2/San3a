package com.paris_2.san3a.presentation.screen.home.craftsman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Stats
import com.paris_2.san3a.presentation.screen.home.craftsman.components.StatsContainer
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.RequestCardForCraftsMan
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import kotlinx.datetime.LocalDateTime
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CraftsmanHomeScreen(
    viewModel: CraftsmanHomeViewModel = koinViewModel()
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    CraftsmanHomeContent(
        action = viewModel,
        state = state
    )
}

@Composable
fun CraftsmanHomeContent(
    action: CraftsmanInteractionListener,
    modifier: Modifier = Modifier,
    state: CraftsmanHomeState,
) {
    Column(
        modifier = modifier
            .background(Theme.colors.background.screen)
            .statusBarsPadding()
    ) {
        AppBar(
            actionIcon = {
                Icon(
                    modifier = Modifier
                        .clickable(onClick = {
                            action.onNotificationClick()
                        }),
                    painter = painterResource(R.drawable.ic_notification_outline),
                    contentDescription = null,
                    tint = Theme.colors.shade.primary
                )
            },
            leadingIcon = {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.good_morning,
                            state.craftsmanHomeUiState.currentUserName
                        ),

                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_outline),
                            contentDescription = "",
                            tint = Theme.colors.shade.secondary,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            text = state.craftsmanHomeUiState.location,
                            style = Theme.textStyle.body.small.medium,
                            color = Theme.colors.shade.secondary
                        )
                    }
                }
            },
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.your_stats),
                    style = Theme.textStyle.title.medium,
                    color = Theme.colors.shade.primary,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            item {
                StatsContainer(
                    jobsDone = state.craftsmanHomeUiState.stats.jobsDone,
                    earnings = state.craftsmanHomeUiState.stats.earnings,
                    rating = state.craftsmanHomeUiState.stats.rating
                )
            }


            item {
                if(state.craftsmanHomeUiState.recentRelatedJobs.isNotEmpty()) {
                    Text(
                        text = stringResource(
                            R.string.recent_jobs,
                            state.craftsmanHomeUiState.relatedJob
                        ),
                        style = Theme.textStyle.title.medium,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.craftsmanHomeUiState.recentRelatedJobs) {
                            RequestCardForCraftsMan(
                                title = it.title,
                                type = it.serviceType,
                                offers = it.offers.size.toString(),
                                description = it.description,
                                location = it.location,
                                onClick = { action.onJobClick(it.id) }
                            )
                        }
                    }
                }
            }

            if (state.craftsmanHomeUiState.availableJobs.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.available_jobs),
                        style = Theme.textStyle.title.medium,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                items(state.craftsmanHomeUiState.availableJobs) {
                    RequestCardForCraftsMan(
                        title = it.title,
                        type = it.serviceType,
                        offers = it.offers.size.toString(),
                        description = it.description,
                        location = it.location,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { action.onJobClick(it.id) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    CraftsmanHomeContent(

        state = CraftsmanHomeState(
            isLoading = false,
            craftsmanHomeUiState = CraftsmanHomeUiState(
                currentUserName = "Muhammed",
                relatedJob = "Plumbing",
                location = "Cairo, Egypt",
                stats = Stats(
                    userId = "123",
                    jobsDone = 10,
                    earnings = 2500.0,
                    rating = 4.7
                ),
                recentRelatedJobs = listOf(
                    RequestService(
                        id = "1",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        location = "Cairo, Egypt",
                        offers = listOf(3.0, 2.0),
                        userId = "",
                        locationDetails = "",
                        image = listOf(),
                        serviceType = "",
                        selectedCraftsmanId = "",
                        time = LocalDateTime(2023, 10, 1, 12, 0, 0),
                        state = "",
                        requestStatus = RequestStatus.ONGOING
                    ),
                    RequestService(
                        id = "2",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        location = "Cairo, Egypt",
                        offers = listOf(3.0, 2.0),
                        userId = "",
                        locationDetails = "",
                        image = listOf(),
                        serviceType = "",
                        selectedCraftsmanId = "",
                        time = LocalDateTime(2023, 10, 1, 12, 0, 0),
                        state = "",
                        requestStatus = RequestStatus.ONGOING
                    ),
                    RequestService(
                        id = "3",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        location = "Cairo, Egypt",
                        offers = listOf(3.0, 2.0),
                        userId = "",
                        locationDetails = "",
                        image = listOf(),
                        serviceType = "",
                        selectedCraftsmanId = "",
                        time = LocalDateTime(2023, 10, 1, 12, 0, 0),
                        state = "",
                        requestStatus = RequestStatus.ONGOING
                    ),
                ),
                availableJobs = listOf(
                    RequestService(
                        id = "1",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        location = "Cairo, Egypt",
                        offers = listOf(3.0, 2.0),
                        userId = "",
                        locationDetails = "",
                        image = listOf(),
                        serviceType = "",
                        selectedCraftsmanId = "",
                        time = LocalDateTime(2023, 10, 1, 12, 0, 0),
                        state = "",
                        requestStatus = RequestStatus.ONGOING
                    ),
                    RequestService(
                        id = "2",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        location = "Cairo, Egypt",
                        offers = listOf(3.0, 2.0),
                        userId = "",
                        locationDetails = "",
                        image = listOf(),
                        serviceType = "",
                        selectedCraftsmanId = "",
                        time = LocalDateTime(2023, 10, 1, 12, 0, 0),
                        state = "",
                        requestStatus = RequestStatus.ONGOING
                    ),
                    RequestService(
                        id = "3",
                        title = "Shower is not working",
                        description = "Shower is not working",
                        location = "Cairo, Egypt",
                        offers = listOf(3.0, 2.0),
                        userId = "",
                        locationDetails = "",
                        image = listOf(),
                        serviceType = "",
                        selectedCraftsmanId = "",
                        time = LocalDateTime(2023, 10, 1, 12, 0, 0),
                        state = "",
                        requestStatus = RequestStatus.ONGOING
                    ),
                )
            )
        ),

        action = object : CraftsmanInteractionListener {
            override fun onNotificationClick() {}
            override fun onSearch(query: String) {}
            override fun onJobClick(serviceId: String) {}
        }

    )
}