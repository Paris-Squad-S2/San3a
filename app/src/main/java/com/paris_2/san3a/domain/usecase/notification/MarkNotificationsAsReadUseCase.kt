package com.paris_2.san3a.domain.usecase.notification

import com.paris_2.san3a.domain.repository.NotificationRepository

class MarkNotificationsAsReadUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(userId: String) = repository.markNotificationsAsRead(userId)
}