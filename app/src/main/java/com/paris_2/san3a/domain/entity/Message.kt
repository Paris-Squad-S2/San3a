package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime

data class Message(
    val id:Int,
    val time: LocalDateTime,
    val senderId:Int,
    val receiverId:Int,
    val chatId: Int,
    val messageContent:MessageContent,
    val seen: Boolean,
)
sealed class MessageContent{
    data class Text(val content:String): MessageContent()
    data class Audio(val url:String,val duration:Int,val waves:List<Float>): MessageContent()
    data class Image(val title:String,val urls:List<String>): MessageContent()
}



