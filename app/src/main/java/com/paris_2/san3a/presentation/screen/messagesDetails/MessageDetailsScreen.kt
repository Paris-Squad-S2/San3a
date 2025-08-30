package com.paris_2.san3a.presentation.screen.messagesDetails

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import com.paris_2.san3a.presentation.utill.myClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.paris_2.san3a.presentation.screen.messagesDetails.components.MessageList
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

    MessageDetailsContent(
        state = state,
        messageInteractionListener = viewModel,
        viewModel = viewModel,
    )

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
                    title = state.chatTitle ?: stringResource(R.string.loading),
                    actionIcon = {
                        Icon(
                            modifier = Modifier
                                .myClickable(onClick = messageInteractionListener::onDropMenuClick)
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
                                sendingTextMessage = state.sendingTextMessage,
                                sendingImageMessage = state.sendingImageMessage,
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
                            offset = DpOffset(
                                LocalConfiguration.current.screenWidthDp.dp / 1.5f,
                                50.dp
                            ),
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
    }
}

