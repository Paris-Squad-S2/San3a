package com.paris_2.san3a.presentation.screen.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.paris_2.san3a.R
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
    AppScaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.card)
            .statusBarsPadding(),
        topBar = {
            AppBar(
                title = stringResource(R.string.messages),
                modifier = Modifier.padding(horizontal = 8.dp),
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
        when {
            state.error != null -> {
                LostConnectionScreen(
                    onRetry = messagesInteractionListener::onRetryClick,
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
            state.chatsMap.isEmpty() -> {
                PlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                        .padding(horizontal = 60.dp),
                    image = R.drawable.img_message,
                    title = R.string.no_messages_yet,
                    description = R.string.once_you_start_accepting_or_posting_requests_chats_with_craftsmen_will_appear_here,
                )
            }
            else -> {
                ChatList(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Theme.colors.background.screen)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    chatsMap = state.chatsMap,
                    messagesInteractionListener = messagesInteractionListener
                )
            }
        }
    }
}

@Composable
fun ChatList(
    modifier: Modifier,
    chatsMap: Map<String, ChatUI>,
    messagesInteractionListener: MessagesInteractionListener
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(chatsMap.size) { item ->
            chatsMap.keys.elementAt(item).let { chatId ->
                chatsMap[chatId]?.let { chatUI ->
                    ChatCard(
                        onChatClick = { messagesInteractionListener.onChatClick(chatUI.chatId, chatUI.theOtherUserId) },
                        name = chatUI.theOtherUserName ?: "Unknown User",
                        imageUrl = chatUI.theOtherProfilePhoto ?: "https://example.com/default_image.png",
                        lastMessage = chatUI.lastMessage,
                        unreadMessagesCount = chatUI.unreadMessagesCount,
                        dateTime = chatUI.lastMessageTime,
                        isCurrentUserSendLastMessage = chatUI.lastMessageReceiverId == chatUI.theOtherUserId,
                    )
                }
            }
        }
    }
}