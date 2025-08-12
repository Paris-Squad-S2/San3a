package com.paris_2.san3a.presentation.screen.messages

import android.util.Log
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messages.GetChatsByUserIdUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MessagesViewModel(
    private val getChatsByUserIdUseCase: GetChatsByUserIdUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
) : MessagesInteractionListener,
    BaseViewModel<MessagesState>(MessagesState()) {

    init {
        getCurrentUserId()
    }

    private fun getCurrentUserId() {
        tryToExecute(
            execute = {
                updateState(screenState.value.copy(isLoading = true, error = null))
                getPhoneNumberUseCase()
            },
            onSuccess = { userId ->
                updateState(screenState.value.copy(currentUserId = userId))
                getChatsForCurrentUser()
            },
            onError = { exception ->
                updateState(screenState.value.copy(error = exception.message))
            },
        )
    }

    private fun getChatsForCurrentUser() {
        tryToObserve(
            observe = {
                getChatsByUserIdUseCase(userId = screenState.value.currentUserId)
            },
            onEach = { chats ->
                updateState(
                    screenState.value.copy(
                        chatsMap = chats.toChatUIMap(screenState.value.currentUserId),
                        isLoading = false,
                        error = null
                    )
                )
                getUserChatsInfo(screenState.value.chatsMap)
            },
            onError = { exception ->
                updateState(screenState.value.copy(error = exception.message, isLoading = false))
            },
        )
    }

    private fun getUserChatsInfo(chatsMap: Map<String, ChatUI>) {
        chatsMap.forEach { (chatId, chatUI) ->
            tryToExecute(
                execute = {
                    getUserUseCase(chatUI.theOtherUserId)
                },
                onSuccess = { user ->
                    updateState(
                        screenState.value.copy(
                            chatsMap = screenState.value.chatsMap.toMutableMap().apply {
                                this[chatId] = chatUI.copy(
                                    theOtherUserName = user.fullName,
                                    theOtherProfilePhoto = user.profilePhoto,
                                )
                            }
                        )
                    )
                },
                onError = { exception ->
                    updateState(
                        screenState.value.copy(
                            chatsMap = screenState.value.chatsMap.toMutableMap().apply {
                                this[chatId] = chatUI.copy(
                                    theOtherUserName = "Unknown User",
                                    theOtherProfilePhoto = FAKE_IMAGE_URL,
                                )
                            }
                        )
                    )
                    Log.e(
                        "MessagesViewModel",
                        "Error fetching user for chat $chatId: ${exception.message}",
                    )
                },
            )
        }
    }

    override fun onNotificationClick() {
        navigate(
            Destinations.Notification,
        )
    }

    override fun onRetryClick() {
        updateState(screenState.value.copy(error = null, isLoading = true))
        getCurrentUserId()
    }

    override fun onChatClick(chatId: String, otherUserId: String) {
        navigate(
            Destinations.MessageDetails(chatId, screenState.value.currentUserId, otherUserId),
        )
    }

    companion object {
        const val FAKE_IMAGE_URL =
            "https://firebasestorage.googleapis.com/v0/b/cell-monitor21.appspot.com/o/user2%2Fchat8%2F1000179245.jpg?alt=media&token=714e333b-7fc6-4be3-83a6-30d6b7f7fd4e"

    }
}