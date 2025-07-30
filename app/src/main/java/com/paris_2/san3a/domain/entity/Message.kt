package com.paris_2.san3a.domain.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class Message(
    val id:Int = Random.nextInt(1,Int.MAX_VALUE),
    val time: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val senderId:Int,
    val receiverId:Int,
    val chatId: Int,
    val messageContent:MessageContent,
    val seen: Boolean,
)
sealed class MessageContent{
    data class Text(val content:String): MessageContent()
    data class Audio(val url:String,val duration:Int,val waves:List<Float>): MessageContent()
    data class Image(val uris:List<String>): MessageContent()
}