package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository

class AddNotificationUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notification: Notification): String {
        return repository.addNotification(notification)
    }
}
