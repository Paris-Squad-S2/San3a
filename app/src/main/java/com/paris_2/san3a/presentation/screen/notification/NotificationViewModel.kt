package com.paris_2.san3a.presentation.screen.notification


import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.notification.GetNotificationsUseCase
import com.paris_2.san3a.domain.usecase.notification.MarkNotificationsAsReadUseCase
import com.paris_2.san3a.presentation.screen.notification.components.NotificationUiState
import com.paris_2.san3a.presentation.screen.notification.components.toUiModelList
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val markNotificationsAsReadUseCase: MarkNotificationsAsReadUseCase
) : BaseViewModel<NotificationUiState>(NotificationUiState()) {

    init {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = { userId ->
                observeNotifications(userId)
                markNotificationsAsReadUseCase(userId)
            },
            onError = { e ->
                updateState(screenState.value.copy(error = e.message ?: "Unknown error"))
            }
        )
    }

    private fun observeNotifications(userId: String) {
        tryToObserve(
            observe = { getNotificationsUseCase(userId) },
            onEach = { notifications ->
                updateState(
                    screenState.value.copy(
                        notifications = notifications?.toUiModelList() ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                )
            },
            onStart = {
                updateState(screenState.value.copy(isLoading = true, error = null))
            },
            onError = { e ->
                updateState(screenState.value.copy(isLoading = false, error = e.message))
            }
        )
    }

    fun onBackClick() = navigateUp()
}
