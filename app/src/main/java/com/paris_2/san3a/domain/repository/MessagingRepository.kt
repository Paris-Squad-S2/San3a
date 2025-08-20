package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.Message
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {
    fun getMessagesByChatId(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(message: Message)
    suspend fun markMessagesAsSeen(chatId: String, userId: String)
    fun getChatsByUserId(userId: String): Flow<List<Chat>>
    suspend fun createChat(participants: List<String>): String
    suspend fun deleteChatById(chatId: String)
}