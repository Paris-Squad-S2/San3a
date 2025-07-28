package com.paris_2.san3a.domain.usecase

import com.paris_2.san3a.domain.entity.Message
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class FilterMessagesByTimeUseCase{
    suspend operator fun invoke(messages:List<Message>): List<Message> {
        return messages.sortedBy { it.time.hour } // 9:00 9:30 10:00 10:30 11:00
    }
}