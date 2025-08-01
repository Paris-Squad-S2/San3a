package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesByChatIdUseCase(
    private val messageRepository: MessageRepository
) {
     operator fun invoke(chatId: String): Flow<List<Message>> {
        return messageRepository.getMessagesByChatId(chatId)
    }

}