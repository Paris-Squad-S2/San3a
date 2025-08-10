package com.paris_2.san3a.data.mapper


import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import com.paris_2.san3a.data.utils.getCurrentDateTime
import com.paris_2.san3a.domain.entity.Notification
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        caption = caption,
        date = runCatching { LocalDateTime.parse(date) }.getOrDefault(getCurrentDateTime()),
        userId = userId
    )
}

fun Notification.toFirestoreDto(): NotificationDto {
    return NotificationDto(
        title = title,
        caption = caption,
        date = date.toString(),
        userId = userId,
        id = id
    )
}

