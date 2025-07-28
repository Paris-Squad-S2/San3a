package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.LastMessage
import com.paris_2.san3a.domain.entity.MessageContent
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ChatRepository {

    suspend fun getChatsByUserId(userId: Int): List<Chat> {
        return chatList
    }

    suspend fun getChatById(chatId: Int): Chat {
        return Chat(
            id = chatId,
            title = "Chat",
            usersParticipantIds = listOf(
                12,22
            ),
            lastMessage = LastMessage(
                messageContent = MessageContent.Text("Hello world!"),
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            ),
            unreadMessagesCount = 0,
            senderImageUrl = "https://example.com/image$chatId.jpg"
        )
    }

    companion object {
        val chatList = listOf(
            Chat(
                id = 1,
                title = "Chat",
                usersParticipantIds = listOf(
                    12,22
                ),
                lastMessage = LastMessage(
                    messageContent = MessageContent.Text("Hello world!"),
                    time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ),
                unreadMessagesCount = 0,
                senderImageUrl = "https://example.com/image$1.jpg"
            ),
            Chat(
                id = 1,
                title = "Chat",
                usersParticipantIds = listOf(
                    12,22
                ),
                lastMessage = LastMessage(
                    messageContent = MessageContent.Text("Hello world!"),
                    time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ),
                unreadMessagesCount = 0,
                senderImageUrl = "https://example.com/image$1.jpg"
            ),
            Chat(
                id = 1,
                title = "Chat",
                usersParticipantIds = listOf(
                    12,22
                ),
                lastMessage = LastMessage(
                    messageContent = MessageContent.Text("Hello world!"),
                    time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ),
                unreadMessagesCount = 0,
                senderImageUrl = "https://example.com/image$1.jpg"
            )
        )
    }

}