package com.paris_2.san3a.data.mapper

import androidx.core.net.toUri
import com.paris_2.san3a.data.source.remote.messages.dto.ChatDto
import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent


fun List<ChatDto>.toChatList(): List<Chat> = this.map { it.toChat() }

fun ChatDto.toChat(): Chat {
    return Chat(
        id = this.id,
        usersParticipantIds = this.participants,
        lastMessage = this.lastMessage?.toMessage(),
        unreadMessagesCount = this.unreadMessageCount,
    )
}

fun List<MessageDto>.toMessageList(): List<Message> = this.map { it.toMessage() }

fun MessageDto.toMessage(): Message {
    return Message(
        id = this.id,
        time = this.dateTime,
        senderId = this.senderId,
        receiverId = this.receiverId,
        chatId = this.chatId,
        messageContent = handleMessageContent(
            this.text,
            this.imageUrls,
            this.voiceUrl,
            this.voiceDuration,
            this.voiceWaveform
        ),
        seen = this.seen
    )
}

fun handleMessageContent(
    text: String?,
    imageUrls: List<String>?,
    voiceUrl: String? = null,
    voiceDuration: Int? = null,
    voiceWaveform: List<Float>? = null,
): MessageContent {
    return if (text != null) {
        MessageContent.Text(text)
    } else if (imageUrls != null) {
        val imageUris = imageUrls.map { it.toUri().toString() }
        MessageContent.Image(imageUris)
    } else if (voiceUrl != null) {
        MessageContent.Audio(
            url = voiceUrl.toUri(),
            duration = voiceDuration ?: 0,
            waves = voiceWaveform ?: emptyList()
        )
    } else {
        MessageContent.Text("")
    }
}

fun Message.toMessageDto(
    text: String? = null,
    imagesUris: List<String>? = null,
    voiceUrl: String? = null,
    voiceDuration: Int? = null,
    voiceWaveform: List<Float>? = null,
): MessageDto {
    return MessageDto(
        id = id,
        text = text,
        chatId = chatId,
        senderId = senderId,
        receiverId = receiverId,
        imageUrls = imagesUris,
        voiceUrl = voiceUrl,
        voiceDuration = voiceDuration,
        voiceWaveform = voiceWaveform,
        dateTime = time,
        seen = seen
    )
}