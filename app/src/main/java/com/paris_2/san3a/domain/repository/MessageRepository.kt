package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
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
                senderId = 1,
                receiverId = 2,
                chatId = chatId,
                messageContent = MessageContent.Text("Hello world!"),
                false
            )
        )
    }

    fun saveMessage(message: Message){
        // Todo( save message to fire store )
    }
}