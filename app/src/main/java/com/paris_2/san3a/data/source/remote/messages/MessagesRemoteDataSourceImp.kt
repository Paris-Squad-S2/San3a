package com.paris_2.san3a.data.source.remote.messages

import com.google.firebase.firestore.Query
import com.paris_2.san3a.data.service.firestore.DocumentNotFoundException
import com.paris_2.san3a.data.service.firestore.FireStoreService
import com.paris_2.san3a.data.service.firestore.FireStoreServiceException
import com.paris_2.san3a.data.service.firestore.UpdateOperation
import com.paris_2.san3a.data.source.remote.messages.dto.ChatDto
import com.paris_2.san3a.data.source.remote.messages.dto.MessageDto
import com.paris_2.san3a.data.utils.toLong
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlin.time.ExperimentalTime

class MessagesRemoteDataSourceImp(
    private val fireStoreService: FireStoreService,
) : MessagesRemoteDataSource {

    override fun getChatMessages(chatId: String): Flow<List<MessageDto>> {
        return fireStoreService.streamCollection(
            path = "$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION",
            fromJson = MessageDto::fromJson,
            queryBuilder = { query ->
                query
                    .orderBy("timestamp", Query.Direction.ASCENDING)
            }
        )
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun sendMessage(message: MessageDto): MessageDto {
        val chatId: String = try {
            getChatById(message.chatId).id
        } catch (_: FireStoreServiceException) {
            addChat(
                participants = listOf(message.senderId, message.receiverId)
            )
        }

        val messageData = message.copy(chatId = chatId).toJson()
        val messageId = fireStoreService.addToCollection(
            path = "$CHATS_COLLECTION/${chatId}/$MESSAGES_COLLECTION",
            data = messageData
        )

        val savedMessage = message.copy(id = messageId)

        fireStoreService.updateDoc(
            "$CHATS_COLLECTION/$chatId",
            mapOf(
                "lastMessage" to savedMessage.toJson(),
                "updatedAt" to savedMessage.dateTime.toLong()
            )
        )

        return savedMessage
    }

    fun getUnreadMessageCountForUserByChatId(chatId: String, userId: String): Flow<Int> {
        return fireStoreService.streamCountOfCollection(
            path = "$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION",
            queryBuilder = { query ->
                query
                    .whereEqualTo("chatId", chatId)
                    .whereEqualTo("receiverId", userId)
                    .whereEqualTo("seen", false)
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserChats(userId: String): Flow<List<ChatDto>> {
        val chats = fireStoreService.streamCollection(
            path = CHATS_COLLECTION,
            fromJson = ChatDto::fromJson,
            queryBuilder = { query ->
                query.whereArrayContains("participants", userId)
                    .orderBy("updatedAt", Query.Direction.DESCENDING)
            },
        )

        val countsFlow = chats.flatMapLatest { chatList ->
            combine(
                chatList.map { chat ->
                    getUnreadMessageCountForUserByChatId(
                        chatId = chat.id,
                        userId = userId
                    ).map { count -> chat.id to count }
                }
            ) { pairs -> pairs.toMap() }
        }

        return combine(chats, countsFlow) { chatList, countsMap ->
            chatList.map { chat ->
                chat.copy(unreadMessageCount = countsMap[chat.id] ?: 0)
            }
        }
    }

    suspend fun getChatById(chatId: String): ChatDto {
        return fireStoreService.getDoc(
            path = "$CHATS_COLLECTION/$chatId",
            fromJson = ChatDto::fromJson
        ) ?: throw DocumentNotFoundException("$CHATS_COLLECTION/$chatId")
    }

    suspend fun getChatByParticipants(participants: List<String>): ChatDto? {
        return fireStoreService.getCollection(
            path = CHATS_COLLECTION,
            fromJson = ChatDto::fromJson,
            queryBuilder = { query ->
                query.whereArrayContains("participants", participants.first())
                    .whereEqualTo("participants", participants)
            }
        ).firstOrNull()
    }

    override suspend fun addChat(participants: List<String>): String {
        val existingChat = getChatByParticipants(participants)
        if (existingChat != null) return existingChat.id

        val chatData = ChatDto(id = "", participants = participants).toJson()
        val chatId = fireStoreService.addToCollection(CHATS_COLLECTION, chatData)
        return chatId
    }

    override suspend fun deleteChat(chatId: String) {
        fireStoreService.deleteDoc("$CHATS_COLLECTION/$chatId")
    }

    override suspend fun markMessagesAsSeen(chatId: String, userId: String) {
        val messages = fireStoreService.getCollection(
            path = "$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION",
            fromJson = MessageDto::fromJson,
            queryBuilder = { query ->
                query.whereEqualTo("receiverId", userId)
                    .whereEqualTo("seen", false)
            }
        )

        if (messages.isEmpty()) return

        val operations = messages.map { message ->
            UpdateOperation(
                path = "$CHATS_COLLECTION/$chatId/$MESSAGES_COLLECTION/${message.id}",
                data = mapOf("seen" to true)
            )
        }

        fireStoreService.batchWrite(operations)
    }

    companion object {
        const val CHATS_COLLECTION = "chats"
        const val MESSAGES_COLLECTION = "messages"
    }
}
