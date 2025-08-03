package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDomain
import com.paris_2.san3a.data.mapper.toFirestoreDto
import com.paris_2.san3a.data.source.remote.notification.NotificationDataSource
import com.paris_2.san3a.domain.NotificationException
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository, BaseRepository() {

    override fun streamNotifications(): Flow<List<Notification>> {
        return notificationDataSource.getStreamNotifications()

            .map { dtoList ->
                dtoList.map { dto -> dto.toDomain() }
            }
    }
    override suspend fun addNotification(notification: Notification): String {
        return safeCall(NotificationException("Failed to add notification")) {
            notificationDataSource.addNotification(notification.toFirestoreDto())
        }
    }
}