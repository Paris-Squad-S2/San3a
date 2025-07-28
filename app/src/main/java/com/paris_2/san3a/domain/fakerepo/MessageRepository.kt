package com.paris_2.san3a.domain.fakerepo

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageType
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class MessageRepository(
    // data source
) {
    fun getMessagesByChatId(chatId: Int): List<Message> {
        return listOf(
            Message(
                id = 1,
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                userId = 1,
                chatId = chatId,
                messageType = MessageType.Text("Hello world!")
            )
        )
    }

    fun saveMessage(message: Message){
        // Todo( save message to fire store )
    }
}