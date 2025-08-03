package com.paris_2.san3a.presentation.screen.notification


import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.usecase.AddNotificationUseCase
import com.paris_2.san3a.domain.usecase.StreamNotificationsUseCase
import com.paris_2.san3a.presentation.screen.notification.components.NotificationUiState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.time.LocalDate

class NotificationViewModel(
    private val addNotificationUseCase: AddNotificationUseCase,
    private val streamNotificationsUseCase: StreamNotificationsUseCase,
) : BaseViewModel<NotificationUiState>(NotificationUiState()) {

    init {
        observeNotifications()
    }

    private fun observeNotifications() {
        streamNotificationsUseCase()
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
