package com.paris_2.san3a.data.source.remote.notification

import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import kotlinx.coroutines.flow.Flow


class NotificationDataSourceImpl(
    private val fireStoreService: FireStoreService
) : NotificationDataSource {

    companion object {
        private const val NOTIFICATION_COLLECTION = "notifications"
    }

    override fun getStreamNotifications(): Flow<List<NotificationDto>> {
        return fireStoreService.streamCollection(
            path = NOTIFICATION_COLLECTION,
            fromJson = { data, id -> NotificationDto.fromMap(data, id) }
        )
    }


    override suspend fun addNotification(notification: NotificationDto): String {
        return fireStoreService.addToCollection(
            path = NOTIFICATION_COLLECTION,
            data = notification.toMap()
        )
    }

}

