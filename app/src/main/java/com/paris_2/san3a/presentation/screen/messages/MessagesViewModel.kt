package com.paris_2.san3a.presentation.screen.messages

import android.util.Log
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.messaging.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.utill.fakeImage
import kotlinx.coroutines.delay

class MessagesViewModel(
    private val getChatsByUserIdUseCase: GetChatsByUserIdUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase
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
                getNotificationsCount(userId)
            },
            onError = { exception ->
                updateState(screenState.value.copy(error = exception.message))
            },
        )
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = {
                getUnReadNotificationsCountUseCase(userId)
            },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        notificationsCount = count ?: 0,
                    )
                )
            },
            onError = { exception ->
                Log.e("MessagesViewModel", "Error fetching notifications count: ${exception.message}")
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
                        chatsMap = chats?.toChatUIMap(screenState.value.currentUserId) ?: emptyMap(),
                    )
                )
                chats?.ifEmpty {
                    updateState(
                        screenState.value.copy(
                            isLoading = false,
                        )
                    )
                }
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
                                    theOtherProfilePhoto = user.profilePhoto ?: fakeImage,
                                )
                            },
                            error = null
                        )
                    )
                    delay(TIMEOUT)
                    updateState(
                        screenState.value.copy(
                            isLoading = false,
                        )
                    )
                },
                onError = { exception ->
                    updateState(
                        screenState.value.copy(
                            chatsMap = screenState.value.chatsMap.toMutableMap().apply {
                                this[chatId] = chatUI.copy(
                                    theOtherUserName = "Unknown User",
                                    theOtherProfilePhoto = fakeImage,
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

    private companion object {
        const val TIMEOUT = 200L
    }
}