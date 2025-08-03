package com.paris_2.san3a.presentation.screen.notification


import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.StreamNotificationsUseCase
import com.paris_2.san3a.presentation.screen.notification.components.NotificationUiState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class NotificationViewModel(
    private val streamNotificationsUseCase: StreamNotificationsUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
) : BaseViewModel<NotificationUiState>(NotificationUiState()) {

    init {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = { userId -> observeNotifications(userId) },
            onError = { e ->
                updateState(screenState.value.copy(error = e.message ?: "Unknown error"))
            }
        )
    }

    private fun observeNotifications(userId: String) {
        streamNotificationsUseCase(userId)
            .onStart {
                updateState(
                    screenState.value.copy(isLoading = true, error = null)
                )
            }
            .catch { e ->
                updateState(
                    screenState.value.copy(isLoading = false, error = e.message)
                )
            }
            .onEach { notifications ->
                updateState(
                    screenState.value.copy(
                        notifications = notifications,
                        isLoading = false,
                        error = null
                    )
                )
            }
            .launchIn(viewModelScope)
    }

    fun onBackClick() = navigateUp()
}
