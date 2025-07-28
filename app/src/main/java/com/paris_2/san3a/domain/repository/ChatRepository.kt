package com.paris_2.san3a.domain.repository

import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.Message
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
            lastMessage = Message(
                id = 1,
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                senderId = 1,
                receiverId = 2,
                chatId = chatId,
                messageContent = MessageContent.Text("Hello world!"),
                false
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
                lastMessage = Message(
                    id = 1,
                    time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    senderId = 1,
                    receiverId = 2,
                    chatId = 9,
                    messageContent = MessageContent.Text("Hello world!"),
                    false
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
                lastMessage = Message(
                    id = 1,
                    time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    senderId = 1,
                    receiverId = 2,
                    chatId = 9,
                    messageContent = MessageContent.Text("Hello world!"),
                    false
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
                lastMessage = Message(
                    id = 1,
                    time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    senderId = 1,
                    receiverId = 2,
                    chatId = 9,
                    messageContent = MessageContent.Text("Hello world!"),
                    false
                ),
                unreadMessagesCount = 0,
                senderImageUrl = "https://example.com/image$1.jpg"
            )
        )
    }

}