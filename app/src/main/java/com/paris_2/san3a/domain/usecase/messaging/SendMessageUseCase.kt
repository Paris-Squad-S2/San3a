package com.paris_2.san3a.domain.usecase.messaging

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.repository.MessagingRepository

class SendMessageUseCase(
    private val messagingRepository: MessagingRepository
) {
    suspend operator fun invoke(message: Message) {
        messagingRepository.sendMessage(message)
    }
}