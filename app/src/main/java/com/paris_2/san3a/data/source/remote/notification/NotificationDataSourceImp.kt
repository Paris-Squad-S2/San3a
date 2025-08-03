package com.paris_2.san3a.data.source.remote.notification


import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.source.remote.notification.dto.toDomain
import com.paris_2.san3a.data.source.remote.notification.dto.toFirestoreDto
import com.paris_2.san3a.domain.entity.Notification
import kotlinx.coroutines.flow.Flow

class NotificationDataSourceImpl(
    private val fireStoreService: FireStoreService
) : NotificationDataSource {

    companion object {
        private const val NOTIFICATION_COLLECTION = "notifications"
    }

    override fun streamNotifications(): Flow<List<Notification>> {
        return fireStoreService.streamCollection(
            path = NOTIFICATION_COLLECTION,
            fromJson = { data, id ->
                data.toDomain(id)
            }
        )
    }

    override suspend fun addNotification(notification: Notification): String {
        return fireStoreService.addToCollection(
            path = NOTIFICATION_COLLECTION,
            data = notification.toFirestoreDto()
        )
    }
}
