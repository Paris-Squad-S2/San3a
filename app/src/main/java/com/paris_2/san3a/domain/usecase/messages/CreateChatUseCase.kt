package com.paris_2.san3a.domain.usecase.messages

import com.paris_2.san3a.domain.repository.ChatRepository

class CreateChatUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(participants: List<String>) {
        chatRepository.createChat(participants)
    }
}