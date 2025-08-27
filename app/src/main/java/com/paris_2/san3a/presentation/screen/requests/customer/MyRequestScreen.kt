package com.paris_2.san3a.presentation.screen.requests.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.screen.requests.component.RatingBottomSheet
import com.paris_2.san3a.presentation.screen.requests.component.RequestCard
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppTabBar
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.NotificationIcon
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyRequestScreen(
    viewModel: MyRequestCustomerViewModel = koinViewModel()
) {
    val state by viewModel.screenState.collectAsState()

    MyRequestScreenContent(state, viewModel)
}

@Composable
private fun MyRequestScreenContent(
    state: MyRequestCustomerScreenState,
    myRequestCustomerInteractionListener: MyRequestCustomerInteractionListener,
    modifier: Modifier = Modifier,
) {
    RatingBottomSheet(
        isVisible = state.myRequestCustomerUiState.isRatingVisible,
        rating = state.myRequestCustomerUiState.rating,
        onDismiss = myRequestCustomerInteractionListener::onRatingDismiss,
        onRatingChange = { rating ->
            myRequestCustomerInteractionListener.onRatingChange(rating.toFloat())
        },
        onAddRating = {
            myRequestCustomerInteractionListener.onRatingCraftsMan()
        },
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
    ) {
        AppBar(
            title = stringResource(R.string.my_requests),
            actionIcon = {
                NotificationIcon(
                    modifier = Modifier
                        .padding(end = 8.dp),
                    count = state.myRequestCustomerUiState.notificationsCount,
                    onNotificationClick = myRequestCustomerInteractionListener::onNotificationClick
                )
            },
        )
        when {

            state.errorMessage != null -> {
                PlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    action = {
                        myRequestCustomerInteractionListener.onRetryClick()
                    },
                    actionText = R.string.try_again,
                    image = R.drawable.img_lost_connection,
                    title = R.string.oops_no_internet,
                    description = R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background
                )
            }

            state.isLoading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }

            else -> {
                val tabs = listOf(
                    stringResource(R.string.ongoing),
                    stringResource(R.string.completed),
                    stringResource(R.string.canceled)
                )
                val selectedIndex = remember { mutableIntStateOf(0) }
                AppTabBar(
                    tabItems = tabs,
                    selectedIndex = selectedIndex.intValue,
                    onTabSelected = { index ->
                        selectedIndex.intValue = index
                    },
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                ) {
                    when (selectedIndex.intValue) {
                        0 -> {
                            val ongoingRequests = state.myRequestCustomerUiState.ongoing
                            if (ongoingRequests.isEmpty()) {
                                Box(Modifier.fillMaxSize()) {
                                    PlaceHolderScreen(
                                        Modifier.align(Alignment.Center),
                                        image = R.drawable.img_placeholder_lllustration1,
                                        title = R.string.no_service_requests_yet,
                                        description = R.string.start_by_choosing_a_service_and_submitting_your_first_request
                                    )
                                }
                            } else {
                                RequestList(
                                    requests = ongoingRequests.values.toList(),
                                    interactionListener = myRequestCustomerInteractionListener
                                )
                            }
                        }

                        1 -> {
                            val completedRequests = state.myRequestCustomerUiState.completed
                            if (completedRequests.isEmpty()) {
                                Box(Modifier.fillMaxSize()) {
                                    PlaceHolderScreen(
                                        Modifier.align(Alignment.Center),
                                        image = R.drawable.img_placeholder_lllustration1,
                                        title = R.string.no_service_requests_yet,
                                        description = R.string.start_by_choosing_a_service_and_submitting_your_first_request
                                    )
                                }
                            } else {
                                RequestList(
                                    requests = completedRequests.values.toList(),
                                    interactionListener = myRequestCustomerInteractionListener
                                )
                            }
                        }

                        2 -> {
                            val canceledRequests = state.myRequestCustomerUiState.canceled
                            if (canceledRequests.isEmpty()) {
                                Box(Modifier.fillMaxSize()) {
                                    PlaceHolderScreen(
                                        Modifier.align(Alignment.Center),
                                        image = R.drawable.img_placeholder_lllustration1,
                                        title = R.string.no_service_requests_yet,
                                        description = R.string.start_by_choosing_a_service_and_submitting_your_first_request
                                    )
                                }
                            } else {
                                RequestList(
                                    requests = canceledRequests.values.toList(),
                                    interactionListener = myRequestCustomerInteractionListener
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun RequestList(
    requests: List<MyRequestCustomerUi>,
    interactionListener: MyRequestCustomerInteractionListener
) {
    LazyColumn {
        items(requests) { request ->
            RequestCard(
                requestUi = request,
                onClick = { interactionListener.onRequestClick(request.id) },
                onActionClick = {
                    if (request.status == RequestStatus.ONGOING && request.offer.craftsMan.phoneNumber.isBlank()) {
                        interactionListener.onRequestClick(request.id)
                    } else if (request.status == RequestStatus.COMPLETED) {
                        interactionListener.onRatingClick(craftsmanId = request.offer.craftsMan.phoneNumber)
                    } else if (request.status == RequestStatus.ONGOING) {
                        interactionListener.onClickChat(request.offer.craftsMan.phoneNumber)
                    } else {
                        interactionListener.onRequestClick(request.id)
                    }
                },
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun MyRequestScreenPreview() {
    BasePreview {
        MyRequestScreenContent(
            state = MyRequestCustomerScreenState(
                myRequestCustomerUiState = MyRequestCustomerUiState(
                    ongoing = mapOf(
                        "1" to MyRequestCustomerUi(),
                        "2" to MyRequestCustomerUi(),
                        "3" to MyRequestCustomerUi(),
                    ),
                ),
            ),
            myRequestCustomerInteractionListener = object : MyRequestCustomerInteractionListener {
                override fun onRequestClick(requestId: String) {}

                override fun onNotificationClick() {}

                override fun onRetryClick() {}
                override fun onClickChat(phoneNumber: String) {}
                override fun onRatingClick(craftsmanId: String) {}
                override fun onRatingDismiss() {}

                override fun onRatingChange(rating: Float) {}

                override fun onRatingCraftsMan() {}
            },
        )
    }
}