package com.paris_2.san3a.presentation.screen.messagesDetails

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.messages.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.messages.MarkMessagesAsSeenUseCase
import com.paris_2.san3a.domain.usecase.messages.SendMessageUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
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
                        errorMessage = it.message
                    )
                )
            }
        )
    }

    private fun loadMessages(chatId: String) {
        tryToExecute(
            execute = {
                updateState(screenState.value.copy(isLoading = true))
                getMessagesByChatIdUseCase(chatId)
            },
            onSuccess = { flowMessages ->
                flowMessages.collect { messages ->
                    val groupedMessages = messages
                        .map { it.toMessageUi(screenState.value.profilePhoto, currentUserId) }
                        .groupBy { it.date }
                        .toSortedMap(compareBy { it })

                    updateState(
                        screenState.value.copy(
                            messages = messages.map { it.toMessageUi(screenState.value.profilePhoto, currentUserId) },
                            groupedMessages = groupedMessages,
                            chatTitle = screenState.value.chatTitle,
                            isLoading = false
                        )
                    )
                    markMessagesAsSeen()
                }
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
                sendMessageUseCase(
                    Message(
                        senderId = currentUserId,
                        receiverId = otherUserId,
                        chatId = chatId,
                        messageContent = MessageContent.Text(
                            content = screenState.value.textMessage
                        ),
                        seen = false
                    )
                )
                updateState(
                    screenState.value.copy(
                        textMessage = ""
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message
                    )
                )
            }
        )
    }


    fun saveImages(uris: List<Uri>) {
        tryToExecute(
            execute = {
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
        const val FAKE_USER_NAME = "CraftsMan"
    }

}