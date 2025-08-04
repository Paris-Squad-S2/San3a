package com.paris_2.san3a.domain.usecase.messages

import com.paris_2.san3a.domain.repository.MessageRepository

class MarkMessagesAsSeenUseCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(chatId: String, userId: String) {
        return messageRepository.markMessagesAsSeen(chatId, userId)
    }
}