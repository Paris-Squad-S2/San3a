package com.paris_2.san3a.presentation.screen.notification.components


import com.paris_2.san3a.domain.entity.Notification

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val error: String? = null
)

