package com.paris_2.san3a.data.source.remote.messages.dto

import com.paris_2.san3a.data.utils.getCurrentDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class ChatDto(
    val id: String,
    val participants: List<String>,
    val createdAt: LocalDateTime = getCurrentDate(),
    val updatedAt: LocalDateTime = getCurrentDate(),
    val lastMessage: MessageDto? = null,
    val unreadMessageCount: Int = 0
) {
    companion object {

        @OptIn(ExperimentalTime::class)
        @Suppress("UNCHECKED_CAST")
        fun fromJson(data: Map<String, Any>, id: String): ChatDto {
            return ChatDto(
                id = id,
                participants = (data["participants"] as? List<*>)?.filterIsInstance<String>()
                    ?: emptyList(),
                createdAt = (data["createdAt"] as? Long)?.let {
                    Instant.fromEpochMilliseconds(it)
                        .toLocalDateTime(TimeZone.UTC)
                } ?: LocalDateTime(1970, 1, 1, 0, 0),
                updatedAt = (data["updatedAt"] as? Long)?.let {
                    Instant.fromEpochMilliseconds(it)
                        .toLocalDateTime(TimeZone.UTC)
                } ?: LocalDateTime(1970, 1, 1, 0, 0),
                lastMessage = (data["lastMessage"] as? Map<String, Any>)?.let { messageData ->
                    MessageDto.fromJson(messageData, messageData["id"]?.toString() ?: "")
                }
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    fun toJson(): Map<String, Any> {
        val map = mutableMapOf<String, Any>(
            "participants" to this.participants,
            "createdAt" to this.createdAt.toInstant(TimeZone.UTC).toEpochMilliseconds(),
            "updatedAt" to this.updatedAt.toInstant(TimeZone.UTC).toEpochMilliseconds()
        )
        this.lastMessage?.let {
            map["lastMessage"] = it.toJson()
        }
        return map
    }
}

