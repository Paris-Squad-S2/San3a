package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.fakerepo.MessageRepository

class SaveMessageUseCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: Message) {
        messageRepository.saveMessage(message)
    }
}