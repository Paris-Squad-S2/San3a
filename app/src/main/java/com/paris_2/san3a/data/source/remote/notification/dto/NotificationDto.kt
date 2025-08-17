package com.paris_2.san3a.data.source.remote.notification.dto

data class NotificationDto(
    val id: String = "",
    val titleMap: Map<String, String>,
    val captionMap: Map<String, String>,
    val date: String = "",
    val isRead: Boolean = false
) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun fromMap(data: Map<String, Any>, id: String): NotificationDto {
            return NotificationDto(
                id = id,
                titleMap = data["titleMap"] as? Map<String, String> ?: emptyMap(),
                captionMap = data["captionMap"] as? Map<String, String> ?: emptyMap(),
                date = data["date"] as? String ?: "",
                isRead = data["isRead"] as? Boolean ?: false
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "titleMap" to titleMap,
            "captionMap" to captionMap,
            "date" to date,
            "isRead" to isRead
        )
    }
}



