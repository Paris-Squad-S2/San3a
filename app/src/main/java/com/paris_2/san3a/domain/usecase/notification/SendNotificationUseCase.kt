package com.paris_2.san3a.domain.usecase.notification

import android.util.Log
import com.paris_2.san3a.domain.repository.NotificationRepository

class SendNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(token: String, title: String, description: String) {
        Log.d("NotificationTest", "SendNotificationUseCase")
        notificationRepository.sendNotification(token, title, description)
    }
}