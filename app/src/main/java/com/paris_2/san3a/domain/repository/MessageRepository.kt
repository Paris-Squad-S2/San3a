package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessagesByChatId(chatId: String): Flow<List<Message>>
    suspend fun saveMessage(message: Message)
}