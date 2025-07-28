package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.fakerepo.ChatRepository

class GetChatByIdUseCase (
   private val chatRepository: ChatRepository
){
    suspend operator fun invoke(chatId: Int): Chat {
        return chatRepository.getChatById(chatId)
    }
}
