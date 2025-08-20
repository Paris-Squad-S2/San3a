package com.paris_2.san3a.data.repository

import androidx.core.net.toUri
import com.paris_2.san3a.data.mapper.toChatList
import com.paris_2.san3a.data.mapper.toMessageDto
import com.paris_2.san3a.data.mapper.toMessageList
import com.paris_2.san3a.data.repository.shared.BaseRepository
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.data.source.remote.storage.StorageRemoteDataSource
import com.paris_2.san3a.data.source.remote.storage.dto.ImageDto
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.exceptions.FailException
import com.paris_2.san3a.domain.repository.MessagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MessagingRepositoryImpl(
    private val messagesRemoteDataSource: MessagesRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
) : MessagingRepository, BaseRepository() {

    override fun getMessagesByChatId(chatId: String): Flow<List<Message>> {
        validateNetworkConnection()
        return messagesRemoteDataSource.getChatMessages(chatId).map { it.toMessageList() }.catch {
            throw FailException("Messages with related chat id $chatId is cant be read")
        }
    }

    override suspend fun sendMessage(message: Message) {
        safeNetworkCall(FailException("Message $message is not send")) {
            when (message.messageContent) {
                is MessageContent.Audio -> null
                is MessageContent.Image -> {
                    val imageUrls = processImageMessage(
                        message.messageContent,
                        chatId = message.chatId,
                        receiverId = message.receiverId
                    )
                    messagesRemoteDataSource.sendMessage(message.toMessageDto(imageUrls))
                }

                is MessageContent.Text -> {
                    messagesRemoteDataSource.sendMessage(message.toMessageDto())
                }
            }
        }
    }

    private suspend fun processImageMessage(
        messageContent: MessageContent.Image,
        chatId: String,
        receiverId: String
    ): List<String> {
        val images = messageContent.uris.mapIndexed { index, stringUri ->
            createImageDto(stringUri, receiverId, chatId, index)
        }
        storageRemoteDataSource.saveImages(images)
        return storageRemoteDataSource.getImagesByPaths(images)
    }

    private fun createImageDto(
        stringUri: String,
        receiverId: String,
        chatId: String,
        index: Int
    ): ImageDto {
        val uri = stringUri.toUri()
        return ImageDto(
            path = "user${receiverId}/chat${chatId}/${uri.lastPathSegment ?: "image_$index"}.jpg",
            uri = uri
        )
    }

    override suspend fun markMessagesAsSeen(chatId: String, userId: String) {
        safeNetworkCall(FailException("Messages with chat id $chatId and user id $userId is cant be marked as seen")) {
            messagesRemoteDataSource.markMessagesAsSeen(chatId, userId)
        }
    }

    override fun getChatsByUserId(userId: String): Flow<List<Chat>> {
        validateNetworkConnection()
        return messagesRemoteDataSource.getUserChats(userId).map { it.toChatList() }.catch {
            throw FailException(it.message ?: "An error occurred while fetching chats")
        }
    }

    override suspend fun createChat(participants: List<String>): String {
        return safeNetworkCall(FailException("Failed to create chat with participants: $participants")) {
            messagesRemoteDataSource.addChat(participants)
        }
    }

    override suspend fun deleteChatById(chatId: String) {
        safeNetworkCall(FailException("Failed to delete chat with ID: $chatId")) {
            messagesRemoteDataSource.deleteChat(chatId)
        }
    }
}