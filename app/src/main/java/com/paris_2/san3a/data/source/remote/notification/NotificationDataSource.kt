package com.paris_2.san3a.data.source.remote.notification

import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    fun getStreamNotifications(userId : String): Flow<List<NotificationDto>>
    suspend fun addNotification(notification: NotificationDto): String
}
