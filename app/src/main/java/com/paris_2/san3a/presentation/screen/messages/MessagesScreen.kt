package com.paris_2.san3a.presentation.screen.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.paris_2.san3a.presentation.screen.messages.component.ChatCard
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
                        .background(Theme.colors.background.screen)
                        .padding(horizontal = 60.dp)
                )
            } else if (state.isLoading) {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize().background(Theme.colors.background.screen)
                )
            } else if (state.chats.isEmpty()) {
                PlaceHolderScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                        .padding(horizontal = 60.dp),
                    image = R.drawable.img_message,
                    title = R.string.no_messages_yet,
                    description = R.string.once_you_start_accepting_or_posting_requests_chats_with_craftsmen_will_appear_here,
                )
            } else {
                ChatList(
                    modifier = Modifier.fillMaxSize().background(Theme.colors.background.screen).padding(top = 16.dp, start = 16.dp, end = 16.dp),
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

     LazyColumn(
         modifier = modifier,
         verticalArrangement = Arrangement.spacedBy(12.dp),
     ) {
         items(chats) { item ->
             ChatCard(
                 onChatClick = {messagesInteractionListener.onChatClick(item.id)},
                 name = "kabanoka kazonga", // Todo
                 imageUrl = "https://plus.unsplash.com/premium_photo-1689568126014-06fea9d5d341?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                 lastMessage = "where are you", // Todo()
                 unreadMessagesCount = 99,
                 dateTime = "9:40",
                 isCurrentUserSendLastMessage = false // Todo()
             )
         }
     }
}