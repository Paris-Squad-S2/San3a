package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatsByUserId(userId: String): Flow<List<Chat>>
    suspend fun createChat(participants: List<String>): String
    suspend fun deleteChatById(chatId: String)
}