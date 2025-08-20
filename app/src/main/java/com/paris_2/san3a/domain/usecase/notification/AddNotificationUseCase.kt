package com.paris_2.san3a.domain.usecase.notification

import com.paris_2.san3a.domain.entity.NotificationToSend
import com.paris_2.san3a.domain.repository.NotificationRepository

class AddNotificationUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(userId: String, notification: NotificationToSend): String =
        repository.addNotification(userId, notification)
}