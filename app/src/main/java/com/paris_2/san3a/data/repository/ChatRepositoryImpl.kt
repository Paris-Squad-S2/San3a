package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toChatList
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.domain.CreateChatException
import com.paris_2.san3a.domain.DeleteChatException
import com.paris_2.san3a.domain.ReadChatException
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ChatRepositoryImpl(
    private val messagesRemoteDataSource: MessagesRemoteDataSource
) : ChatRepository, BaseRepository() {

    override fun getChatsByUserId(userId: String): Flow<List<Chat>> {
        return messagesRemoteDataSource.getUserChats(userId).map { it.toChatList() }.catch {
            throw ReadChatException(userId)
        }
    }

    override suspend fun createChat(participants: List<String>): String {
        return safeCall(CreateChatException(participants)) {
            messagesRemoteDataSource.addChat(participants)
        }
    }

    override suspend fun deleteChatById(chatId: String) {
        safeCall(DeleteChatException(chatId)) {
            messagesRemoteDataSource.deleteChat(chatId)
        }
    }
}