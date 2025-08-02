package com.paris_2.san3a.presentation.screen.messagesDetails

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.usecase.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.SendMessageUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.delay

class MessagesDetailsViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val deleteChatByIdUseCase: DeleteChatByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : MessageInteractionListener,BaseViewModel<MessageDetailsUiState>(
    MessageDetailsUiState()
) {

    val chatId by lazy { savedStateHandle.get<String>("chatId") ?: "" }

    init {
        loadMessages(chatId)
    }

    private fun loadMessages(chatId: String) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isLoading = true
                    )
                )
                getMessagesByChatIdUseCase("4SIGv3zmkMKwtO0BeQgN")
            },
            onSuccess = { flowMessages ->
                flowMessages.collect { messages ->
                updateState(
                    screenState.value.copy(
                        messages =   messages.map { it.toMessageUi() },
                        chatTitle = "CraftsMan",
                        isLoading = false
                    ),
                )
            }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it,
                        isLoading = false
                    )
                )
            },
        )
    }


    fun sendMessage() {
        tryToExecute(
            execute = {
                sendMessageUseCase(
                    Message(
                        senderId = "1",
                        receiverId = "2",
                        chatId = "8",
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
                        errorMessage = it
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
                        senderId = "1",
                        receiverId = "2",
                        chatId = "8",
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
                        errorMessage = it
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
                        errorMessage = it
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
    }

}