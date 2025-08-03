package com.paris_2.san3a.data.repository


import com.paris_2.san3a.data.source.remote.notification.NotificationDataSource
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {

    override fun streamNotifications(): Flow<List<Notification>> {
        return notificationDataSource.streamNotifications()
    }

    override suspend fun addNotification(notification: Notification): String {
        return notificationDataSource.addNotification(notification)
    }
}
