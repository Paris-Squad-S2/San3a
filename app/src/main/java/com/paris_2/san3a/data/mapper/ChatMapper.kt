package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.messages.dto.ChatDto
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent


fun List<ChatDto>.toChatList(): List<Chat>  = this.map { it.toChat() }

fun ChatDto.toChat(): Chat {
    return Chat(
        id = this.id,
        usersParticipantIds = this.participants,
        lastMessage = this.lastMessage?.toMessage()?: Message(
            senderId = "",
            receiverId = "",
            chatId = "",
            messageContent = MessageContent.Text(""),
            seen = false
        ),
        unreadMessagesCount = this.unreadMessageCount,
    )
}