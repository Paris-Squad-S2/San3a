package com.paris_2.san3a.presentation.screen.messagesDetails

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.screen.messagesDetails.components.DeleteChatBottomSheet
import com.paris_2.san3a.presentation.screen.messagesDetails.components.Message
import com.paris_2.san3a.presentation.screen.messagesDetails.components.MessageTextField
import com.paris_2.san3a.presentation.shared.components.AppBar
import com.paris_2.san3a.presentation.shared.components.AppScaffold
import com.paris_2.san3a.presentation.shared.components.LoadingScreen
import com.paris_2.san3a.presentation.shared.components.LostConnectionScreen
import com.paris_2.san3a.presentation.shared.components.PlaceHolderScreen
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MessageDetailsScreen(
    viewModel: MessagesDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    when {
        state.errorMessage != null -> {
            LostConnectionScreen(
                onRetry = viewModel::onRetryClick,
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
            MessageDetailsContent(
                state = state,
                messageInteractionListener = viewModel,
                viewModel = viewModel,
            )
        }
    }

}

@Composable
fun MessageDetailsContent(
    state: MessageDetailsUiState,
    messageInteractionListener: MessageInteractionListener,
    viewModel: MessagesDetailsViewModel
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        viewModel.saveImages(uris)
    }

    Column(
        modifier = Modifier
            .background(Theme.colors.background.card)
            .statusBarsPadding()
    ) {
        AppScaffold(
            topBar = {
                AppBar(
                    modifier = Modifier,
                    title = state.chatTitle,
                    actionIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = messageInteractionListener::onDropMenuClick)
                                .padding(end = 8.dp),
                            painter = painterResource(R.drawable.ic_menu_dots_outline),
                            contentDescription = null,
                            tint = Theme.colors.shade.primary
                        )
                    },
                    leadingIcon = {
                        AsyncImage(
                            model = state.profilePhoto,
                            contentScale = ContentScale.Crop,
                            contentDescription = stringResource(R.string.profile_image),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    },
                    onBackClick = messageInteractionListener::onBackClick
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background.screen)
                    .statusBarsPadding(),
            ) {
                if (state.groupedMessages.isEmpty()) {
                    PlaceHolderScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.colors.background.screen)
                            .padding(horizontal = 60.dp),
                        image = R.drawable.img_message,
                        title = R.string.no_messages_yet,
                        description = R.string.once_you_start_accepting_or_posting_requests_chats_with_craftsmen_will_appear_here,
                    )
                } else {
                    MessageList(
                        messagesSize = state.messagesSize,
                        groupedMessages = state.groupedMessages,
                        sendingMessage = state.sendingMessage,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .align(Alignment.Center)
                            .imePadding()
                            .navigationBarsPadding()
                    )
                }
                MessageTextField(
                    value = state.textMessage,
                    onValueChange = viewModel::onMessageChange,
                    sendButtonState = state.sendButtonState,
                    imageIcon = painterResource(R.drawable.ic_image),
                    voiceIcon = painterResource(R.drawable.ic_voice),
                    sendIcon = painterResource(R.drawable.ic_send),
                    onImageClick = { imagePickerLauncher.launch(MessagesDetailsViewModel.IMAGE_TYPE) },
                    onSendClick = viewModel::sendMessage,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .imePadding()
                )

                DropdownMenu(
                    shape = RoundedCornerShape(12.dp),
                    containerColor = Theme.colors.background.card,
                    modifier = Modifier
                        .width(120.dp)
                        .align(Alignment.BottomCenter),
                    offset = DpOffset(LocalConfiguration.current.screenWidthDp.dp / 1.5f, 50.dp),
                    expanded = state.showDropMenu,
                    onDismissRequest = messageInteractionListener::onDismissDropMenu
                ) {
                    DropdownMenuItem(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .align(Alignment.CenterHorizontally),
                        text = {
                            Text(
                                text = stringResource(R.string.delete_chat),
                                style = Theme.textStyle.body.medium.medium,
                                color = Theme.colors.additional.primary.error,
                                textAlign = TextAlign.Center
                            )
                        },
                        onClick = messageInteractionListener::onDropMenuItemClick
                    )
                }
                if (state.showDeleteChatBottomSheet) {
                    DeleteChatBottomSheet(
                        onDismissRequest = viewModel::onDismissDeleteBottomSheet,
                        onDeleteChat = viewModel::onDeleteButtonClick,
                        buttonState = state.bottomSheetButtonState,
                    )
                }
            }
        }
    }
}

@Composable
fun MessageList(
    messagesSize: Int,
    groupedMessages: Map<String, List<MessageUi>>,
    modifier: Modifier,
    sendingMessage: MessageUi?,
) {
    val listState = rememberLazyListState(messagesSize)

    LaunchedEffect(messagesSize) {
        if (messagesSize > 0) {
            listState.scrollToItem(messagesSize - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        groupedMessages.forEach { (date, messagesForDate) ->
            stickyHeader {
                Text(
                    text = date,
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.tertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            items(messagesForDate) { item ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Message(
                        images = item.images,
                        modifier = Modifier
                            .align(if (item.isReceived) Alignment.CenterStart else Alignment.CenterEnd)
                            .animateItem(),
                        text = item.text,
                        isReceived = item.isReceived,
                        isSeen = item.isSeen,
                        time = item.time,
                        profileImageUrl = if (item.isReceived) item.anotherUserImage else null,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        sendingMessage?.let { message ->
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Message(
                        images = message.images,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .animateItem(),
                        text = message.text,
                        isReceived = message.isReceived,
                        isSeen = null,
                        time = message.time,
                        profileImageUrl = null,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}
