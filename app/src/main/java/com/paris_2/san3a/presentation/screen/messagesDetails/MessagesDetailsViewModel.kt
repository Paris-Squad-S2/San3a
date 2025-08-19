package com.paris_2.san3a.presentation.screen.messagesDetails

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messaging.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.MarkMessagesAsSeenUseCase
import com.paris_2.san3a.domain.usecase.messaging.SendMessageUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MessagesDetailsViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val deleteChatByIdUseCase: DeleteChatByIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val markMessagesAsSeenUseCase: MarkMessagesAsSeenUseCase,
    savedStateHandle: SavedStateHandle,
) : MessageInteractionListener, BaseViewModel<MessageDetailsUiState>(
    MessageDetailsUiState()
) {

    val chatId = savedStateHandle.toRoute<Destinations.MessageDetails>().chatId
    val otherUserId = savedStateHandle.toRoute<Destinations.MessageDetails>().otherUserId
    val currentUserId = savedStateHandle.toRoute<Destinations.MessageDetails>().currentUserId

    init {
        getTheOtherUserData(otherUserId)
    }

    private fun markMessagesAsSeen() {
        tryToExecute(
            execute = {
                markMessagesAsSeenUseCase(chatId, currentUserId)
            },
            onError = {
                Log.d("MessagesDetailsViewModel", "markMessagesAsSeen error: ${it.message}", it)
            }
        )
    }

    private fun getTheOtherUserData(otherUserId: String) {
        tryToExecute(
            execute = {
                updateState(screenState.value.copy(isLoading = true))
                getUserUseCase(otherUserId)
            },
            onSuccess = { user ->
                updateState(
                    screenState.value.copy(
                        chatTitle = user.fullName,
                        profilePhoto = user.profilePhoto,
                    )
                )
                loadMessages(chatId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        chatTitle = FAKE_USER_NAME,
                        profilePhoto = FAKE_IMAGE_URL,
                    )
                )
                Log.d("MessagesDetailsViewModel", "getTheOtherUserData error: ${it.message}", it)
                loadMessages(chatId)
            }
        )
    }

    private fun loadMessages(chatId: String) {
        tryToObserve(
            observe = {
                updateState(screenState.value.copy(isLoading = true))
                getMessagesByChatIdUseCase(chatId)
            },
            onEach = { messages ->
                val messageUis =
                    messages?.map { it.toMessageUi(screenState.value.profilePhoto, currentUserId) } ?: emptyList()
                val groupedMessages = messageUis
                    .groupBy { it.date }
                    .toSortedMap(compareBy { it })

                updateState(
                    screenState.value.copy(
                        messagesSize = messageUis.size,
                        groupedMessages = groupedMessages,
                        chatTitle = screenState.value.chatTitle,
                        isLoading = false,
                        sendingMessage = null,
                    )
                )
                markMessagesAsSeen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message,
                        isLoading = false
                    )
                )
            }
        )
    }


    fun sendMessage() {
        tryToExecute(
            execute = {
                screenState.value.textMessage.trim().let { message ->
                    if (message.isBlank()) return@tryToExecute
                    Message(
                        senderId = currentUserId,
                        receiverId = otherUserId,
                        chatId = chatId,
                        messageContent = MessageContent.Text(
                            content = message
                        ),
                        seen = false
                    ).let { message ->
                        updateState(
                            screenState.value.copy(
                                sendButtonState = AppButtonState.Disabled,
                                textMessage = "",
                                sendingMessage = message.toMessageUi(
                                    imageUserUrl = "",
                                    currentUserId = currentUserId
                                ),
                                messagesSize = screenState.value.messagesSize + 1,
                            )
                        )
                        sendMessageUseCase(message)
                    }
                }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        sendButtonState = AppButtonState.Enable
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message,
                        sendButtonState = AppButtonState.Enable
                    )
                )
            }
        )
    }


    fun saveImages(uris: List<Uri>) {
        tryToExecute(
            execute = {
                if (uris.isNotEmpty()) {
                    sendMessageUseCase(
                        Message(
                            senderId = currentUserId,
                            receiverId = otherUserId,
                            chatId = chatId,
                            messageContent = MessageContent.Image(
                                uris = uris.map { it.toString() }
                            ),
                            seen = false
                        )
                    )
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message
                    )
                )
            },
        )
    }


    fun onMessageChange(message: String) {
        updateState(
            screenState.value.copy(
                textMessage = message
            )
        )
    }

    override fun onBackClick() {
        navigateUp()
    }

    override fun onDropMenuClick() {
        updateState(
            screenState.value.copy(
                showDropMenu = true
            )
        )
    }

    override fun onDismissDropMenu() {
        updateState(
            screenState.value.copy(
                showDropMenu = false
            )
        )
    }

    override fun onDropMenuItemClick() {
        updateState(
            screenState.value.copy(
                showDeleteChatBottomSheet = true
            )
        )
    }

    override fun onDeleteButtonClick() {
        tryToExecute(
            execute = {
                deleteChatByIdUseCase(chatId)
                updateState(
                    screenState.value.copy(
                        showDeleteChatBottomSheet = false
                    )
                )
                navigateUp()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message
                    )
                )
            },
        )
    }

    override fun onDismissDeleteBottomSheet() {
        updateState(
            screenState.value.copy(
                showDeleteChatBottomSheet = false
            )
        )
    }

    override fun onRetryClick() {
        loadMessages(chatId)
    }

    companion object {
        const val IMAGE_TYPE = "image/*"
        const val FAKE_IMAGE_URL =
            "https://firebasestorage.googleapis.com/v0/b/cell-monitor21.appspot.com/o/user2%2Fchat8%2F1000179245.jpg?alt=media&token=714e333b-7fc6-4be3-83a6-30d6b7f7fd4e"
        const val FAKE_USER_NAME = "Unknown User"
    }

}