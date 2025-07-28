package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class Chat(
    val id:Int,
    val title:String,
    val usersParticipantIds:List<Int>,
    val lastMessage:LastMessage,
    val unreadMessagesCount:Int,
    val senderImageUrl:String,
)

data class LastMessage(
    val messageContent:MessageContent,
    val time:LocalDateTime,
)
