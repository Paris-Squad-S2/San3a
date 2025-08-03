package com.paris_2.san3a.presentation.screen.messages

import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.messages.GetChatsByUserIdUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class MessagesViewModel(
    private val getChatsByUserIdUseCase: GetChatsByUserIdUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
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
        tryToExecute(
            execute = {
                getChatsByUserIdUseCase(userId = screenState.value.currentUserId)
            },
            onSuccess = { chatsFlow ->
                chatsFlow.collect { chats ->
                    updateState(
                        screenState.value.copy(
                            chats = chats,
                            isLoading = false,
                            error = null
                        )
                    )
                }
            },
            onError = { exception ->
                updateState(screenState.value.copy(error = exception.message))
            },
        )
    }

    override fun onNotificationClick() {
        navigate(
            Destinations.Notifications,
        )
    }

    override fun onRetryClick() {
        updateState(screenState.value.copy(error = null, isLoading = true))
        getCurrentUserId()
    }

    override fun onChatClick(chatId: String) {
        navigate(
            Destinations.MessageDetails(chatId),
        )
    }

}