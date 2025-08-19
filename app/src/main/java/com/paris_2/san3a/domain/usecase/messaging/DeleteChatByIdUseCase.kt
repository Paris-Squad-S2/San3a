package com.paris_2.san3a.domain.usecase.messaging

import com.paris_2.san3a.domain.repository.MessagingRepository

class DeleteChatByIdUseCase(private val messagingRepository: MessagingRepository) {
    suspend operator fun invoke(chatId: String) = messagingRepository.deleteChatById(chatId)
}