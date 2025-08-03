package com.paris_2.san3a.domain.usecase.messages

import com.paris_2.san3a.domain.repository.ChatRepository

class DeleteChatByIdUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String) {
        chatRepository.deleteChatById(chatId)
    }
}