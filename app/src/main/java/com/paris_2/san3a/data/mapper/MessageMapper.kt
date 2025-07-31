package com.paris_2.san3a.data.mapper

import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto
import com.paris_2.san3a.data.source.remote.storage.EmptyMessageException
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent


fun List<MessageDto>.toMessageList():List<Message> = this.map { it.toMessage() }

fun MessageDto.toMessage(): Message {
    return Message(
        id = this.id,
        time = this.dateTime,
        senderId = this.senderId,
        receiverId = this.receiverId,
        chatId = this.chatId,
        messageContent = handleMessageContent(this.id, this.text, this.imageUrls),
        seen = this.seen
    )
}

fun handleMessageContent(
    id: String?,
    text: String?,
    imageUrls: List<String>?,
): MessageContent {
    return if (text != null) {
        MessageContent.Text(text)
    } else if (imageUrls != null) {
        MessageContent.Image(imageUrls)
    } else {
        throw EmptyMessageException(id.orEmpty())
    }
}

fun Message.toImageMessageDto(urls: List<String>): MessageDto {
    return MessageDto(
        id = id,
        chatId = chatId,
        senderId = senderId,
        receiverId = receiverId,
        imageUrls = urls,
        dateTime = time,
        seen = seen
    )
}