package com.paris_2.san3a.data.source.remote.messages

import com.paris_2.san3a.data.source.remote.messages.dto.ChatDto
import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto
import kotlinx.coroutines.flow.Flow

interface MessagesRemoteDataSource {
    fun getUserChats(userId: String): Flow<List<ChatDto>>
    suspend fun addChat(participants: List<String>): String
    suspend fun deleteChat(chatId: String)

    fun getChatMessages(chatId: String): Flow<List<MessageDto>>
    suspend fun sendMessage(message: MessageDto): MessageDto
    suspend fun markMessagesAsSeen(chatId: String, userId: String)
}