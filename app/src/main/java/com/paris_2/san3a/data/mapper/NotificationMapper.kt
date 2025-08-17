package com.paris_2.san3a.data.mapper


import com.paris_2.san3a.data.source.remote.notification.dto.NotificationDto
import com.paris_2.san3a.data.utils.getCurrentDateTime
import com.paris_2.san3a.domain.entity.Notification
import com.paris_2.san3a.domain.entity.NotificationToSend
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun NotificationDto.toDomain(language: String): Notification {
    return Notification(
        id = id,
        title = titleMap[language] ?: titleMap["en"] ?: "",
        caption = captionMap[language] ?: captionMap["en"] ?: "",
        date = runCatching { LocalDateTime.parse(date) }.getOrDefault(getCurrentDateTime()),
    )
}

fun NotificationToSend.toFirestoreDto(): NotificationDto {
    return NotificationDto(
        titleMap = title,
        captionMap = caption,
        date = getCurrentDateTime().toString(),
    )
}

