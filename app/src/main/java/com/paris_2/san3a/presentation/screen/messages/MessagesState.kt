package com.paris_2.san3a.presentation.screen.messages

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.MessageContent

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
    val lastMessageSenderId: String,
    val lastMessage: String,
    val theOtherUserId: String,
    val theOtherUserName: String? = null,
    val theOtherProfilePhoto: String? = null,
)

fun MessageContent.toMessageContentUI(): String {
    return when (this) {
        is MessageContent.Text -> this.content
        is MessageContent.Image -> "[Image]"
        is MessageContent.Audio -> "[Audio]"
    }
}

fun Chat.toChatUI(userId: String): ChatUI {
    return ChatUI(
        chatId = this.id,
        lastMessage = this.lastMessage?.messageContent?.toMessageContentUI() ?: "",
        lastMessageTime = this.lastMessage?.time?.toString() ?: "",
        lastMessageSenderId = this.lastMessage?.senderId ?: "",
        unreadMessagesCount = this.unreadMessagesCount,
        theOtherUserId = this.usersParticipantIds.firstOrNull { it != userId } ?: "",
    )
}

fun List<Chat>.toChatUIMap(userId: String): Map<String, ChatUI> {
    return this.associate { chat ->
        chat.id to chat.toChatUI(userId)
    }
}