package com.paris_2.san3a.data.mapper


import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import com.paris_2.san3a.domain.entity.Notification
import java.time.LocalDate

fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        caption = caption,
        date = runCatching { LocalDate.parse(date) }.getOrDefault(LocalDate.now())

    )
}

fun Notification.toFirestoreDto(): NotificationDto {
    return NotificationDto(
        title = title,
        caption = caption,
        date = date.toString()
    )
}

