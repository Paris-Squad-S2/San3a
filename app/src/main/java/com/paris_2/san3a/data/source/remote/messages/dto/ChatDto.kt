package com.paris_2.san3a.data.source.remote.messages.dto

import com.paris_2.san3a.data.utils.getCurrentDateTime
import com.paris_2.san3a.data.utils.toLocalDateTime
import com.paris_2.san3a.data.utils.toLong
import com.paris_2.san3a.data.utils.toMessageDto
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

data class ChatDto(
    val id: String,
    val participants: List<String>,
    val createdAt: LocalDateTime = getCurrentDateTime(),
    val updatedAt: LocalDateTime = getCurrentDateTime(),
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
                createdAt = (data["createdAt"] as? Long).toLocalDateTime(),
                updatedAt = (data["updatedAt"] as? Long).toLocalDateTime(),
                lastMessage = (data["lastMessage"] as? Map<String, Any>)?.toMessageDto(),
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    fun toJson(): Map<String, Any> {
        val map = mutableMapOf<String, Any>(
            "participants" to this.participants,
            "createdAt" to this.createdAt.toLong(),
            "updatedAt" to this.updatedAt.toLong()
        )
        this.lastMessage?.let {
            map["lastMessage"] = it.toJson()
        }
        return map
    }
}
