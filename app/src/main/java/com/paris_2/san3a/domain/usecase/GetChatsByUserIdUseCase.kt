package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.fakerepo.ChatRepository

class GetChatsByUserIdUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: Int): List<Chat> {
        return chatRepository.getChatsByUserId(userId)
    }
}
