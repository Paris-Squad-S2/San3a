package com.paris_2.san3a.presentation.screen.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MessagesScreen(viewModel: MessagesViewModel = koinViewModel()) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    MessagesScreenContent(
        messagesInteractionListener = viewModel,
        state = state
    )
}

@Composable
private fun MessagesScreenContent(
    messagesInteractionListener: MessagesInteractionListener,
    modifier: Modifier = Modifier,
    state: MessagesState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppScaffold(
            topBar = {
                AppBar(
                    title = stringResource(R.string.messages),
                    actionIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = messagesInteractionListener::onNotificationClick),
                            painter = painterResource(R.drawable.ic_notification_outline),
                            contentDescription = null,
                            tint = Theme.colors.shade.primary
                        )
                    }
                )
            },
        ) {
            if (state.error != null) {
                LostConnectionScreen(
                    onRetry = messagesInteractionListener::onRetryClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 60.dp)
                )
            } else if (state.isLoading) {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize()
                )
            } else if (state.chats.isEmpty()) {
                PlaceHolderScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 60.dp),
                    image = R.drawable.img_message,
                    title = R.string.no_messages_yet,
                    description = R.string.once_you_start_accepting_or_posting_requests_chats_with_craftsmen_will_appear_here,
                )
            } else {
                ChatList(
                    modifier = Modifier.fillMaxSize(),
                    chats = state.chats,
                    messagesInteractionListener = messagesInteractionListener
                )
            }
        }
    }
}

@Composable
fun ChatList(
    modifier: Modifier,
    chats: List<Chat>,
    messagesInteractionListener: MessagesInteractionListener
) {

     LazyColumn(modifier = modifier) {
         items(chats.size) { chat ->
                val item = chats[chat]
             //TODO: Implement ChatItem composable
         }
     }
}