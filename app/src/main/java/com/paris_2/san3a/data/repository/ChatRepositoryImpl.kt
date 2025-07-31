package com.paris_2.san3a.data.repository

import com.paris_2.san3a.data.mapper.toChatList
import com.paris_2.san3a.data.source.remote.messages.MessagesRemoteDataSource
import com.paris_2.san3a.domain.ReadChatException
import com.paris_2.san3a.domain.entity.Chat
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ChatRepositoryImpl(
    private val messagesRemoteDataSource: MessagesRemoteDataSource
): ChatRepository {
   override  fun getChatsByUserId(userId: String): Flow<List<Chat>> {
       try {
           return messagesRemoteDataSource.getUserChats(userId).map { it.toChatList() }
       } catch (e: Exception){
           throw ReadChatException(userId)
       }
    }
}