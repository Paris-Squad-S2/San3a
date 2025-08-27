package com.paris_2.san3a.domain.usecase.notification

import com.paris_2.san3a.domain.repository.NotificationRepository

class SendNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(token: String, title: String, description: String) {
        notificationRepository.sendNotification(token, title, description)
    }
}