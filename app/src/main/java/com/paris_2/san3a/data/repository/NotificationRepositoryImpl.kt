package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDomain
import com.paris_2.san3a.data.mapper.toFirestoreDto
import com.paris_2.san3a.data.source.remote.notification.NotificationDataSource
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository, BaseRepository() {

    override fun getNotifications(userId: String): Flow<List<Notification>> {
        return notificationDataSource.getNotifications(userId)
            .map { dtoList -> dtoList.map { it.toDomain() } }
            .catch { e ->
                throw FailException("Failed to get notifications: ${e.message}")
            }
    }

    override suspend fun addNotification(notification: Notification): String {
        return safeCall(FailException("Failed to add notification")) {
            notificationDataSource.addNotification(notification.toFirestoreDto())
        }
    }

    override suspend fun markNotificationsAsRead(userId: String) {
        return safeCall(FailException("Failed to mark notifications as read")) {
            notificationDataSource.markNotificationsAsRead(userId)
        }
    }

    override fun getUnreadNotificationsCount(userId: String): Flow<Int> {
        return notificationDataSource.getUnreadNotificationsCount(userId)
    }
}