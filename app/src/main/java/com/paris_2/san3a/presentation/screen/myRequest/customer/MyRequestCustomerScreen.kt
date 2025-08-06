package com.paris_2.san3a.presentation.screen.myRequest.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.myRequest.component.RequestCard
import com.paris_2.san3a.presentation.shared.components.AppTabBar
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyRequestCustomerScreen(
    viewModel: MyRequestCustomerViewModel = koinViewModel()
) {
    val state by viewModel.screenState.collectAsState()

    when (state) {
        is MyRequestCustomerScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.card)
            ) {
                LoadingScreen(modifier = Modifier.align(Alignment.Center))
            }
        }

        is MyRequestCustomerScreenState.Error -> {
            val error = (state as MyRequestCustomerScreenState.Error).message
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.card)
            ) {
                PlaceHolderScreen(
                    action = {},
                    actionText = R.string.try_again,
                    image = R.drawable.img_lost_connection,
                    title = R.string.oops_no_internet,
                    description = R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background
                )
            }
        }

        is MyRequestCustomerScreenState.Success -> {
            MyRequestScreenContent(state = state)
        }
    }
}

@Composable
private fun MyRequestScreenContent(
    modifier: Modifier = Modifier,
    state: MyRequestCustomerScreenState = MyRequestCustomerScreenState.Loading
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Requests",
                style = Theme.textStyle.title.medium,
                color = Theme.colors.shade.primary,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_notification_outline),
                contentDescription = "Request Icon",
                tint = Theme.colors.shade.primary,
            )
        }

        when (state) {
            is MyRequestCustomerScreenState.Loading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }

            is MyRequestCustomerScreenState.Error -> {
                PlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    action = {},
                    actionText = R.string.try_again,
                    image = R.drawable.img_lost_connection,
                    title = R.string.oops_no_internet,
                    description = R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background
                )
            }

            is MyRequestCustomerScreenState.Success -> {
                val tabs = listOf(
                    stringResource(R.string.ongoing),
                    stringResource(R.string.completed),
                    stringResource(R.string.canceled)
                )
                val selectedIndex = remember { mutableStateOf(0) }
                AppTabBar(
                    tabItems = tabs,
                    selectedIndex = selectedIndex.value,
                    onTabSelected = { index ->
                        selectedIndex.value = index
                    },
                )

                when (selectedIndex.value) {
                    0 -> {
                        val ongoingRequests = state.ongoing
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
                            RequestList(requests = ongoingRequests)
                        }
                    }

                    1 -> {
                        val completedRequests = state.completed
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
                            RequestList(requests = completedRequests)
                        }
                    }

                    2 -> {
                        val canceledRequests = state.canceled
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
                            RequestList(requests = canceledRequests)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun RequestList(requests: List<MyRequestCustomerUi>) {
    LazyColumn {
        items(requests) { request ->
            RequestCard(
                requestUi = request,
                onActionClick = {},
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun MyRequestScreenPreview() {
    BasePreview {
        MyRequestScreenContent(
            state = MyRequestCustomerScreenState.Success(
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
        /*state = MyRequestCustomerScreenState.Error(
                message = "Something went wrong"
            )*/
        )
    }
}