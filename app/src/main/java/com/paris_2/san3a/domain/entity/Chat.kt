package com.paris_2.san3a.domain.entity


data class Chat(
    val id:String = "",
    val usersParticipantIds:List<String>,
    val lastMessage: Message,
    val unreadMessagesCount:Int,
)