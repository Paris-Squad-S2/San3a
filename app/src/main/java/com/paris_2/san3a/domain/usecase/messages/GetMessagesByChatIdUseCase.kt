package com.paris_2.san3a.domain.usecase.messages

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.repository.MessagingRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesByChatIdUseCase(
    private val messagingRepository: MessagingRepository
) {
     operator fun invoke(chatId: String): Flow<List<Message>> {
        return messagingRepository.getMessagesByChatId(chatId)
    }

}