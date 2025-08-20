package com.paris_2.san3a.domain.usecase.notification

import com.paris_2.san3a.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetUnReadNotificationsCountUseCase(private val repository: NotificationRepository) {
    operator fun invoke(userId: String): Flow<Int> =
        repository.getUnreadNotificationsCount(userId = userId)
}