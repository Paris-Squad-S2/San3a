package com.paris_2.san3a.domain.usecase.messaging

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.repository.MessagingRepository
import kotlinx.coroutines.flow.Flow

class GetChatsByUserIdUseCase(
    private val messagingRepository: MessagingRepository
) {
     operator fun invoke(userId: String): Flow<List<Chat>> {
        return messagingRepository.getChatsByUserId(userId)
    }
}
