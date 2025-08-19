package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toDomain
import com.paris_2.san3a.data.mapper.toFirestoreDto
import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.local.UserPreferencesLocalDataStore
import com.paris_2.san3a.data.source.remote.notification.NotificationRemoteDataSource
import com.paris_2.san3a.domain.FailException
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.entity.NotificationToSend
import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val notificationRemoteDataSource: NotificationRemoteDataSource,
    private val locaDataSource: UserPreferencesLocalDataStore,
) : NotificationRepository, BaseRepository() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getNotifications(userId: String): Flow<List<Notification>> {
        validateNetworkConnection()
        return locaDataSource.getLatestSelectedAppLanguage().flatMapLatest { language ->
            notificationRemoteDataSource.getNotifications(userId)
                .map { dtoList -> dtoList.map { it.toDomain(language) } }
                .catch { throw FailException("Failed to get notifications: ${it.message}") }
        }
    }

    override suspend fun addNotification(userId: String, notification: NotificationToSend): String {
        validateNetworkConnection()
        return safeCall(FailException("Failed to add notification")) {
            notificationRemoteDataSource.addNotification(userId, notification.toFirestoreDto())
        }
    }

    override suspend fun markNotificationsAsRead(userId: String) {
        validateNetworkConnection()
        return safeCall(FailException("Failed to mark notifications as read")) {
            notificationRemoteDataSource.markNotificationsAsRead(userId)
        }
    }

    override fun getUnreadNotificationsCount(userId: String): Flow<Int> {
        validateNetworkConnection()
        return notificationRemoteDataSource.getUnreadNotificationsCount(userId).catch {
            throw FailException("Failed to get unread notifications count: ${it.message}")
        }
    }
}