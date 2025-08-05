package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDomain
import com.paris_2.san3a.data.mapper.toFirestoreDto
import com.paris_2.san3a.data.source.remote.notification.NotificationDataSource
import com.paris_2.san3a.domain.NotificationException
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository, BaseRepository() {
    override fun getStreamNotifications(userId: String): Flow<List<Notification>> {
        return notificationDataSource.getStreamNotifications(userId)
            .map { dtoList -> dtoList.map { it.toDomain() } }
            .catch { e ->
                throw NotificationException("Failed to get notifications: ${e.message}")
            }
    }
    override suspend fun addNotification(notification: Notification): String {
        return safeCall(NotificationException("Failed to add notification")) {
            notificationDataSource.addNotification(notification.toFirestoreDto())
        }
    }
}