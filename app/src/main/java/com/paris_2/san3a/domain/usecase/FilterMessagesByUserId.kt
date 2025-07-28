package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Message

class FilterMessagesByUserId {
    suspend operator fun invoke(userId: Int,messages:List<Message>): List<Message> {
        return messages.filter { it.userId == userId }
    }
}