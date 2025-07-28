package com.paris_2.san3a.data.source.remote.messages.dto

import com.paris_2.san3a.data.utils.getCurrentDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class MessageDto(
    val id: String,
    val chatId: String,
    val senderId: String,
    val receiverId: String,

    val text: String? = null,

    val imageUrls: List<String>? = null,

    val voiceUrl: String? = null,
    val voiceDuration: Int? = null, // seconds
    val voiceWaveform: List<Float>? = null,

    val dateTime: LocalDateTime = getCurrentDate(),

    val seen: Boolean = false
) {
    companion object {
        @OptIn(ExperimentalTime::class)
        fun fromJson(data: Map<String, Any>, id: String): MessageDto {
            return MessageDto(
                id = id,
                chatId = data["chatId"]?.toString() ?: "",
                senderId = data["senderId"]?.toString() ?: "",
                receiverId = data["receiverId"]?.toString() ?: "",

                text = data["text"]?.toString(),

                imageUrls = (data["imageUrls"] as? List<*>)?.filterIsInstance<String>(),

                voiceUrl = data["voiceUrl"]?.toString(),
                voiceDuration = data["voiceDuration"] as? Int,
                voiceWaveform = (data["voiceWaveform"] as? List<*>)?.filterIsInstance<Number>()
                    ?.map { it.toFloat() },

                dateTime = Instant.fromEpochMilliseconds(
                    data["timestamp"] as? Long ?: System.currentTimeMillis()
                )
                    .toLocalDateTime(TimeZone.UTC),
                seen = data["seen"] as? Boolean ?: false
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    fun toJson(): Map<String, Any> {
        val map = mutableMapOf<String, Any>(
            "chatId" to this.chatId,
            "senderId" to this.senderId,
            "receiverId" to this.receiverId,
            "timestamp" to this.dateTime.toInstant(TimeZone.UTC).toEpochMilliseconds(),
            "seen" to this.seen
        )
        this.text?.let { map["text"] = it }
        this.imageUrls?.let { map["imageUrls"] = it }
        this.voiceUrl?.let { map["voiceUrl"] = it }
        this.voiceDuration?.let { map["voiceDuration"] = it }
        this.voiceWaveform?.let { map["voiceWaveform"] = it }
        return map
    }
}