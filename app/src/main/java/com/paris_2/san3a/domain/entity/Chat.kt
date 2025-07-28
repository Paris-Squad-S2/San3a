package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class Chat(
    val id:Int,
    val title:String,
    val senderId:Int,
    val receiverId:Int,
    val lastMessage:String,
    val lastMessageTime: LocalDateTime,
    val unreadMessagesCount:Int,
    val senderImageUrl:String,
)