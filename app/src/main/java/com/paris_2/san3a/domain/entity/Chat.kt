package com.paris_2.san3a.domain.entity


data class Chat(
    val id:Int,
    val title:String,
    val usersParticipantIds:List<Int>,
    val lastMessage: Message,
    val unreadMessagesCount:Int,
    val senderImageUrl:String,
)