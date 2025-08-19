package com.paris_2.san3a.domain.usecase.notification

import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    operator fun invoke(userId: String): Flow<List<Notification>> =
        repository.getNotifications(userId = userId)
}