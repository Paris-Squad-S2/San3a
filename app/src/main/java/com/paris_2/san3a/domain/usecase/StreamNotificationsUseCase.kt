package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class StreamNotificationsUseCase(
    private val repository: NotificationRepository
) {
    operator fun invoke(userId: String): Flow<List<Notification>> {
        return repository.getStreamNotifications(userId = userId)
    }
}
