package com.paris_2.san3a.data.mapper


import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import com.paris_2.san3a.domain.entity.Notification
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun NotificationDto.toDomain(): Notification {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    return Notification(
        id = id,
        title = title,
        caption = caption,
        date = runCatching { LocalDate.parse(date) }.getOrDefault(currentDate),
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

