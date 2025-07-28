package com.paris_2.san3a.domain.fakerepo

import com.paris_2.san3a.domain.entity.Chat
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
            senderId = 2333,
            receiverId = 3744,
            lastMessage = "Last message $chatId",
            lastMessageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            unreadMessagesCount = 0,
            senderImageUrl = "https://example.com/image$chatId.jpg"
        )
    }

    companion object {
        val chatList = listOf(
            Chat(
                id = 1,
                title = "Chat 1",
                senderId = 2333,
                receiverId = 4,
                lastMessage = "Last message 1",
                lastMessageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                unreadMessagesCount = 5,
                senderImageUrl = "https://example.com/image1.jpg"
            ),
            Chat(
                id = 2,
                title = "Chat 2",
                senderId = 3744,
                receiverId = 4,
                lastMessage = "Last message 2",
                lastMessageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                unreadMessagesCount = 2,
                senderImageUrl = "https://example.com/image2.jpg"
            ),
            Chat(
                id = 3,
                title = "Chat 3",
                senderId = 2333,
                receiverId = 4,
                lastMessage = "Last message 3",
                lastMessageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                unreadMessagesCount = 0,
                senderImageUrl = "https://example.com/image3.jpg"
            )

        )
    }

}