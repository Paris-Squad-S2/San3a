package com.paris_2.san3a.data.source.remote.notification

import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    fun getNotifications(userId : String): Flow<List<NotificationDto>>
    suspend fun addNotification(userId: String, notification: NotificationDto): String
    suspend fun markNotificationsAsRead(userId: String)
    fun getUnreadNotificationsCount(userId: String): Flow<Int>
}
