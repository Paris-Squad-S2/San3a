package com.paris_2.san3a.data.mapper

import androidx.core.net.toUri
import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto
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
        messageContent = handleMessageContent(this.text, this.imageUrls),
        seen = this.seen
    )
}

fun handleMessageContent(
    text: String?,
    imageUrls: List<String>?,
): MessageContent {
    return if (text != null) {
        MessageContent.Text(text)
    } else if (imageUrls != null) {
        val imageUris = imageUrls.map { it.toUri().toString() }
        MessageContent.Image(imageUris)
    }else(
        MessageContent.Text("")
    )
}

fun Message.toImageMessageDto(urls: List<String>?=null): MessageDto {
    return MessageDto(
        id = id,
        text = getMessageContentText(messageContent),
        chatId = chatId,
        senderId = senderId,
        receiverId = receiverId,
        imageUrls = urls,
        dateTime = time,
        seen = seen
    )
}

fun getMessageContentText(messageContent: MessageContent): String{
    return when(messageContent){
        is MessageContent.Audio -> ""
        is MessageContent.Image -> ""
        is MessageContent.Text -> messageContent.content
    }
}