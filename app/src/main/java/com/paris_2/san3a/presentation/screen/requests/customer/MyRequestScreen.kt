package com.paris_2.san3a.presentation.screen.requests.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.presentation.screen.requests.component.RequestCard
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppTabBar
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
    ) {
        AppBar(
            title = stringResource(R.string.my_requests),
            actionIcon = {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable(onClick = myRequestCustomerInteractionListener::onNotificationClick),
                    painter = painterResource(R.drawable.ic_notification_outline),
                    contentDescription = null,
                    tint = Theme.colors.shade.primary
                )
            },
        )
        when {
            state.isLoading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }

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
                                RequestList(requests = ongoingRequests, interactionListener = myRequestCustomerInteractionListener)
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
                                RequestList(requests = completedRequests, interactionListener = myRequestCustomerInteractionListener)
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
                                RequestList(requests = canceledRequests, interactionListener = myRequestCustomerInteractionListener)
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun RequestList(requests: List<MyRequestCustomerUi>, interactionListener: MyRequestCustomerInteractionListener) {
    LazyColumn {
        items(requests) { request ->
            RequestCard(
                requestUi = request,
                onActionClick = {
                    interactionListener.onRequestClick(request.id)
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
                    ongoing = listOf(
                        MyRequestCustomerUi(
                            isCraftsmanVerified = true,
                            status = RequestStatus.ONGOING,
                            craftsmanURL = "",
                            isAcceptedOffer = true
                        ),
                        MyRequestCustomerUi(),
                        MyRequestCustomerUi(),
                    ),
                    completed = emptyList(),
                    canceled = emptyList()
                )
            ),
            myRequestCustomerInteractionListener = object : MyRequestCustomerInteractionListener {
                override fun onRequestClick(requestId: String) {
                    // Handle request click
                }

                override fun onNotificationClick() {
                    TODO("Not yet implemented")
                }

                override fun onRetryClick() {
                    TODO("Not yet implemented")
                }
            },
        )
    }
}