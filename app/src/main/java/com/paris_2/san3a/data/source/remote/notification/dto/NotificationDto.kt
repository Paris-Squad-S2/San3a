package com.paris_2.san3a.data.source.remote.notification.dto

import com.paris_2.san3a.domain.entity.Notification
import java.time.LocalDate

fun Map<String, Any>.toDomain(id: String): Notification {
    return Notification(
        id = id,
        title = this["title"] as? String ?: "",
        caption = this["caption"] as? String ?: "",
        date = runCatching { LocalDate.parse(this["date"] as? String ?: "") }
            .getOrDefault(LocalDate.now())
    )
}

fun Notification.toFirestoreDto(): Map<String, Any> {
    return mapOf(
        "title" to title,
        "caption" to caption,
        "date" to date.toString()
    )
}
