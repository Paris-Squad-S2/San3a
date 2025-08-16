package com.paris_2.san3a.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.notification.components.NotificationCard
import com.paris_2.san3a.presentation.screen.notification.components.NotificationEmptyScreen
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = koinViewModel(),
) {
    val state by viewModel.screenState.collectAsState()
    AppScaffold(
        topBar = {
            AppBar(
                title = stringResource(R.string.notifications),
                onBackClick = viewModel::onBackClick,
            )
        },
        modifier = Modifier
            .background(Theme.colors.background.card)
            .statusBarsPadding()
            .fillMaxSize()
            .background(Theme.colors.background.screen),
        containerColor = Theme.colors.background.screen,
    ) {
        when {
            state.isLoading -> {
                LoadingScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                )
            }

            state.notifications.isEmpty() -> {
                NotificationEmptyScreen()
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.notifications) { notification ->
                        NotificationCard(
                            title = notification.title,
                            caption = notification.caption,
                            date = notification.date,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun NotificationScreenPreview() {
    San3aTheme {
        NotificationScreen()
    }
}