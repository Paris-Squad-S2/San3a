package com.paris_2.san3a.presentation.screen.requests.craftsman

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.paris_2.san3a.presentation.screen.requests.component.MyJobOfferCard
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.AppTabBar
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.components.SnackBar
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.shared.utils.BasePreview
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyJobsScreen(
    viewModel: MyJobsCraftsmanViewModel = koinViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    MyRequestScreenContent(uiState = state, myJobCraftsmanInteractionListener = viewModel)
}

@Composable
private fun MyRequestScreenContent(
    modifier: Modifier = Modifier,
    myJobCraftsmanInteractionListener: MyJobCraftsmanInteractionListener,
    uiState: MyJobsCraftsmanScreenState = MyJobsCraftsmanScreenState()
) {

    AppScaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
        topBar = {
            AppBar(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Theme.colors.background.card)
                    .padding(start = 16.dp),
                title = stringResource(R.string.my_jobs),
                actionIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable(onClick = myJobCraftsmanInteractionListener::onNotificationClick),
                        painter = painterResource(R.drawable.ic_notification_outline),
                        contentDescription = "Request Icon",
                        tint = Theme.colors.shade.primary,
                    )
                }
            )
        },
        containerColor = Theme.colors.background.screen
    ) {
        when {
            uiState.isLoading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }

            uiState.isNoInternet -> {
                PlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    action = {
                        myJobCraftsmanInteractionListener.onRetryClick()
                    },
                    actionText = R.string.try_again,
                    image = R.drawable.img_lost_connection,
                    title = R.string.oops_no_internet,
                    description = R.string.please_check_your_connection_and_try_again_we_ll_keep_trying_in_the_background
                )
            }

            else -> {
                Column {
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
                    when (selectedIndex.intValue) {
                        0 -> {
                            val ongoingRequests = uiState.myOffersCraftsmanUiState.ongoing
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
                                JobsList(
                                    jobs = ongoingRequests.values.toList(),
                                    myJobCraftsmanInteractionListener = myJobCraftsmanInteractionListener
                                )
                            }
                        }

                        1 -> {
                            val completedRequests = uiState.myOffersCraftsmanUiState.completed
                            if (completedRequests.isEmpty()) {
                                Box(Modifier.fillMaxSize()) {
                                    PlaceHolderScreen(
                                        modifier = Modifier.fillMaxWidth().height(192.dp).align(Alignment.Center).padding(horizontal = 32.dp),
                                        image = R.drawable.img_placeholder_lllustration1,
                                        title = R.string.no_service_requests_yet,
                                        description = R.string.start_by_choosing_a_service_and_submitting_your_first_request
                                    )
                                }
                            } else {
                                JobsList(
                                    jobs = completedRequests.values.toList(),
                                    myJobCraftsmanInteractionListener = myJobCraftsmanInteractionListener
                                )
                            }
                        }

                        2 -> {
                            val canceledRequests = uiState.myOffersCraftsmanUiState.canceled
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
                                JobsList(
                                    jobs = canceledRequests.values.toList(),
                                    myJobCraftsmanInteractionListener = myJobCraftsmanInteractionListener
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            AnimatedVisibility(uiState.showSnackBarError) {
                uiState.errorMessage?.let {
                    SnackBar(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .align(Alignment.TopCenter),
                        onClick = myJobCraftsmanInteractionListener::onDismissSnackBar
                    )
                }
            }

        }
    }
}

@Composable
private fun JobsList(
    jobs: List<JobUiState>,
    myJobCraftsmanInteractionListener: MyJobCraftsmanInteractionListener,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(jobs) { job ->
            MyJobOfferCard(
                jobUiState = job,
                onViewDetailsRequest = { myJobCraftsmanInteractionListener.onViewRequestDetails(job.id) },
                onSendMessage = { myJobCraftsmanInteractionListener.onSendMessageClick(job.customerPhone) },
                onMarkAsDone = { myJobCraftsmanInteractionListener.onMarkAsDone(requestId = job.id, requestTitle = job.title, customerId = job.customerPhone) }
            )
        }
    }
}

@PreviewMultiDevices
@Composable
fun MyRequestScreenPreview() {
    BasePreview {
        MyRequestScreenContent(
            myJobCraftsmanInteractionListener = object : MyJobCraftsmanInteractionListener {
                override fun onRetryClick() {}
                override fun onDismissSnackBar() {}

                override fun onMarkAsDone(
                    requestId: String,
                    requestTitle: String,
                    customerId: String
                ) {}

                override fun onSendMessageClick(phoneNumber: String) {}

                override fun onViewRequestDetails(requestId: String) {}

                override fun onNotificationClick() {}
            },
        )
    }
}