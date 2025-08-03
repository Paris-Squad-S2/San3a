package com.paris_2.san3a.data.source.remote.notification.dto

data class NotificationDto(
    val id: String = "",
    val title: String = "",
    val caption: String = "",
    val date: String = ""
) {
    companion object {
        fun fromMap(data: Map<String, Any>, id: String): NotificationDto {
            return NotificationDto(
                id = id,
                title = data["title"] as? String ?: "",
                caption = data["caption"] as? String ?: "",
                date = data["date"] as? String ?: ""
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "caption" to caption,
            "date" to date
        )
    }
}



