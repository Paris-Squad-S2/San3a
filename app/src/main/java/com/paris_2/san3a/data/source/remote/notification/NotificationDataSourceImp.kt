package com.paris_2.san3a.data.source.remote.notification

import com.google.firebase.firestore.Query
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.UpdateOperation
import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import kotlinx.coroutines.flow.Flow

class NotificationDataSourceImpl(
    private val fireStoreService: FireStoreService
) : NotificationDataSource {

    override fun getNotifications(userId: String): Flow<List<NotificationDto>> {
        return fireStoreService.streamCollection(
            path = "$USERS_COLLECTION/$userId/$NOTIFICATION_COLLECTION",
            fromJson = NotificationDto::fromMap,
            queryBuilder = { query ->
                query.orderBy("dateTime", Query.Direction.DESCENDING)
            }
        )
    }


    override suspend fun addNotification(userId: String, notification: NotificationDto): String {
        return fireStoreService.addToCollection(
            path = "$USERS_COLLECTION/${userId}/$NOTIFICATION_COLLECTION",
            data = notification.toMap(),
        )
    }

    override suspend fun markNotificationsAsRead(userId: String) {
        val unreadNotifications = fireStoreService.getCollection(
            path = "$USERS_COLLECTION/$userId/$NOTIFICATION_COLLECTION",
            fromJson = NotificationDto::fromMap,
            queryBuilder = { query ->
                query.whereEqualTo("isRead", false)
            }
        )

        if (unreadNotifications.isEmpty()) return

        val operations = unreadNotifications.map { notification ->
            UpdateOperation(
                path = "$USERS_COLLECTION/$userId/$NOTIFICATION_COLLECTION/${notification.id}",
                data = mapOf("isRead" to true)
            )
        }

        fireStoreService.batchWrite(operations)
    }

    override fun getUnreadNotificationsCount(userId: String): Flow<Int> {
        return fireStoreService.streamCountOfCollection(
            path = "$USERS_COLLECTION/$userId/$NOTIFICATION_COLLECTION",
            queryBuilder = { query ->
                query.whereEqualTo("isRead", false)
            }
        )
    }

    companion object {
        private const val NOTIFICATION_COLLECTION = "notifications"
        private const val USERS_COLLECTION = "users"
    }


}

