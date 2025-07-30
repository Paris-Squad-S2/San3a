package com.paris_2.san3a.domain.repository

import androidx.core.net.toUri
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.StorageRemoteDataSource
import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MessageRepository(
    private val messagesRemoteDataSource: MessagesRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
) {
    fun getMessagesByChatId(chatId: Int): List<Message> {
        return listOf(
            Message(
                id = 1,
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                senderId = 1,
                receiverId = 2,
                chatId = chatId,
                messageContent = MessageContent.Text("Hello world!"),
                false
            )
        )
    }

    suspend fun saveMessage(message: Message) {
        when (message.messageContent) {
            is MessageContent.Audio -> null
            is MessageContent.Image -> {
                val paths = message.messageContent.uris.map { uri ->
                    "user${message.receiverId}/chat${message.chatId}/${uri.toUri().path?.substringAfterLast("/").orEmpty()}.jpg"
                }
                messagesRemoteDataSource.sendMessage(
                    message.toImageMessageDto(
                        storageRemoteDataSource.getImagesByPath(paths)
                    )
                )
                storageRemoteDataSource.saveImagesMessage(
                    receiverId = message.receiverId,
                    chatId = message.chatId,
                    uris = message.messageContent.uris.map { it.toUri() }
                )
            }

            is MessageContent.Text -> null
        }
    }
}

fun Message.toImageMessageDto(urls: List<String>): MessageDto {
    return MessageDto(
        id = id.toString(),
        chatId = chatId.toString(),
        senderId = senderId.toString(),
        receiverId = receiverId.toString(),
        imageUrls = urls,
        dateTime = time,
        seen = seen
    )
}