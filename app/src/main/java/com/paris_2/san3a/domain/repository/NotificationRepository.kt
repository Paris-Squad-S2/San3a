package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.entity.NotificationToSend
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(userId: String): Flow<List<Notification>>
    suspend fun addNotification(userId: String, notification: NotificationToSend): String
    suspend fun markNotificationsAsRead(userId: String)
    fun getUnreadNotificationsCount(userId: String): Flow<Int>
}