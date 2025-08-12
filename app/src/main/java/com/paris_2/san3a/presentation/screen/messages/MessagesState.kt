package com.paris_2.san3a.presentation.screen.messages

import android.util.Log
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.MessageContent
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

data class MessagesState(
    val chatsMap: Map<String, ChatUI> = emptyMap(),
    val currentUserId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class ChatUI(
    val chatId: String,
    val unreadMessagesCount: Int,
    val lastMessageTime: String,
    val lastMessageReceiverId: String,
    val lastMessage: String,
    val theOtherUserId: String,
    val theOtherUserName: String = "Unknown User",
    val theOtherProfilePhoto: String = "",
)

fun MessageContent.toMessageContentUI(): String {
    return when (this) {
        is MessageContent.Text -> this.content
        is MessageContent.Image -> "\uD83D\uDDBC️"
        is MessageContent.Audio -> "\uD83C\uDFB5"
    }
}

fun Chat.toChatUI(userId: String): ChatUI {
    Log.d("ChatUI", "toChatUI: ${this.lastMessage?.time}")
    return ChatUI(
        chatId = this.id,
        lastMessage = this.lastMessage?.messageContent?.toMessageContentUI() ?: "",
        lastMessageTime = formatLastMessageTime(this.lastMessage?.time),
        lastMessageReceiverId = this.lastMessage?.receiverId ?: "",
        unreadMessagesCount = this.unreadMessagesCount,
        theOtherUserId = this.usersParticipantIds.firstOrNull { it != userId } ?: userId,
    )
}

private fun formatLastMessageTime(time: LocalDateTime?): String {
    return time?.let {
        try {
            val outputFormat = java.time.format.DateTimeFormatter.ofPattern("hh:mm a")
            val javaLocalDateTime = java.time.LocalDateTime.of(
                it.year, it.month.number, it.day,
                it.hour, it.minute, it.second, it.nanosecond
            )
            javaLocalDateTime.format(outputFormat)
        } catch (e: Exception) {
            ""
        }
    }.orEmpty()
}

fun List<Chat>.toChatUIMap(userId: String): Map<String, ChatUI> {
    return this.associate { chat ->
        chat.id to chat.toChatUI(userId)
    }
}