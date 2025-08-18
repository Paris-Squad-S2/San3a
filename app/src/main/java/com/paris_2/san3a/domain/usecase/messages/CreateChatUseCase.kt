package com.paris_2.san3a.domain.usecase.messages

import com.paris_2.san3a.domain.repository.MessagingRepository

class CreateChatUseCase(
    private val messagingRepository: MessagingRepository
) {
    suspend operator fun invoke(participants: List<String>): String {
        return messagingRepository.createChat(participants)
    }
}