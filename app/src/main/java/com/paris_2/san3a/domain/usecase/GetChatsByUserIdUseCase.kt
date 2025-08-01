package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatsByUserIdUseCase(
    private val chatRepository: ChatRepository
) {
     operator fun invoke(userId: String): Flow<List<Chat>> {
        return chatRepository.getChatsByUserId(userId)
    }
}
