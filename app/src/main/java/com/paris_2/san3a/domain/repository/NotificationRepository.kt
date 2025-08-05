package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getStreamNotifications(userId: String): Flow<List<Notification>>
    suspend fun addNotification(notification: Notification): String
}