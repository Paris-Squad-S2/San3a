package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class Message(
    val id:Int,
    val time: LocalDateTime,
    val userId:Int,
    val chatId: Int,
    val messageType:MessageType,
)
sealed class MessageType{
    data class Text(val content:String): MessageType()
    data class Audio(val url:String,val duration:Int): MessageType()
    data class Image(val title:String,val url:String): MessageType()
}



