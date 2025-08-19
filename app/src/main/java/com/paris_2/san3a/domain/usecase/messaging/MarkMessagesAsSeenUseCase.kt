package com.paris_2.san3a.domain.usecase.messaging

import com.paris_2.san3a.domain.repository.MessagingRepository

class MarkMessagesAsSeenUseCase(
    private val messagingRepository: MessagingRepository
) {
    suspend operator fun invoke(chatId: String, userId: String) {
        return messagingRepository.markMessagesAsSeen(chatId, userId)
    }
}