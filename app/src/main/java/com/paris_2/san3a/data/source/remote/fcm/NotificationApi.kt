package com.paris_2.san3a.data.source.remote.fcm

import com.paris_2.san3a.domain.entity.Notification

interface NotificationApi {
    suspend fun sendNotification(token: String, title: String, description: String)
}
