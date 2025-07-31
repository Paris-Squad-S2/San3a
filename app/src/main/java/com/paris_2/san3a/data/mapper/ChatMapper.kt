package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.messages.dto.ChatDto
import com.paris_2.san3a.data.source.remote.storage.EmptyMessageException
import com.paris_2.san3a.domain.entity.Chat


fun List<ChatDto>.toChatList(): List<Chat>  = this.map { it.toChat() }

fun ChatDto.toChat(): Chat {
    return Chat(
        id = this.id,
        usersParticipantIds = this.participants,
        lastMessage = this.lastMessage?.toMessage()?:throw EmptyMessageException(this.id),
        unreadMessagesCount = this.unreadMessageCount,
    )
}