package com.paris_2.san3a.domain.usecase.messages

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.repository.MessageRepository

class SendMessageUseCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: Message) {
        messageRepository.saveMessage(message)
    }
}