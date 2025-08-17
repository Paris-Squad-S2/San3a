package com.paris_2.san3a.data.repository

import androidx.core.net.toUri
import com.paris_2.san3a.data.mapper.toImageMessageDto
import com.paris_2.san3a.data.mapper.toMessageList
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.domain.MarkMessagesAsSeenException
import com.paris_2.san3a.domain.ReadMessagesException
import com.paris_2.san3a.domain.SendMessageException
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MessageRepositoryImpl(
    private val messagesRemoteDataSource: MessagesRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
) : MessageRepository, BaseRepository() {
    override fun getMessagesByChatId(chatId: String): Flow<List<Message>> {
        return messagesRemoteDataSource.getChatMessages(chatId).map { it.toMessageList() }.catch {
            throw ReadMessagesException(chatId)
        }
    }

    override suspend fun saveMessage(message: Message) {
        safeCall(SendMessageException(message.id)) {
            when (message.messageContent) {
                is MessageContent.Audio -> null
                is MessageContent.Image -> {
                    val paths = message.messageContent.uris.map { uri ->
                        "user${message.receiverId}/chat${message.chatId}/${
                            uri.toUri().path?.substringAfterLast(
                                "/"
                            ).orEmpty()
                        }.jpg"
                    }
                    storageRemoteDataSource.saveImages(
                        paths = paths,
                        uris = message.messageContent.uris.map { it.toUri() }
                    )
                    messagesRemoteDataSource.sendMessage(
                        message.toImageMessageDto(
                            storageRemoteDataSource.getImagesByPaths(paths,message.messageContent.uris.map { it.toUri() })
                        )
                    )
                }

                is MessageContent.Text -> {
                    messagesRemoteDataSource.sendMessage(message.toImageMessageDto())
                }
            }
        }
    }

    override suspend fun markMessagesAsSeen(chatId: String, userId: String) {
        safeCall(MarkMessagesAsSeenException(chatId, userId)) {
            messagesRemoteDataSource.markMessagesAsSeen(chatId, userId)
        }
    }
}