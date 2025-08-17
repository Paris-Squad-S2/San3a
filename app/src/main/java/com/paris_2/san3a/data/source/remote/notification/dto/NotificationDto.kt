package com.paris_2.san3a.data.source.remote.notification.dto

import com.paris_2.san3a.data.utils.toLocalDateTime
import com.paris_2.san3a.data.utils.toLong
import kotlinx.datetime.LocalDateTime

data class NotificationDto(
    val id: String = "",
    val titleMap: Map<String, String>,
    val captionMap: Map<String, String>,
    val dateTime: LocalDateTime,
    val isRead: Boolean = false
) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun fromMap(data: Map<String, Any>, id: String): NotificationDto {
            return NotificationDto(
                id = id,
                titleMap = data["titleMap"] as? Map<String, String> ?: emptyMap(),
                captionMap = data["captionMap"] as? Map<String, String> ?: emptyMap(),
                dateTime = (data["dateTime"] as? Long).toLocalDateTime(),
                isRead = data["isRead"] as? Boolean ?: false
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "titleMap" to titleMap,
            "captionMap" to captionMap,
            "dateTime" to dateTime.toLong(),
            "isRead" to isRead
        )
    }
}



