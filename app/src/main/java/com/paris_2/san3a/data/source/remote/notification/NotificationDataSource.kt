package com.paris_2.san3a.data.source.remote.notification

import com.paris_2.san3a.domain.entity.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationDataSource {
    fun streamNotifications(): Flow<List<Notification>>
    suspend fun addNotification(notification: Notification): String
}