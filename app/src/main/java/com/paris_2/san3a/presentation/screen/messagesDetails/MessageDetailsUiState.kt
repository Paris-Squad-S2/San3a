package com.paris_2.san3a.presentation.screen.messagesDetails

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number
import java.time.format.DateTimeFormatter


data class MessageDetailsUiState(
    val textMessage: String = "",
    val messagesSize: Int = 0,
    val chatTitle: String = "",
    val profilePhoto: String = "",
    val showDropMenu: Boolean = false,
    val groupedMessages: Map<String, List<MessageUi>> = emptyMap(),
    val showDeleteChatBottomSheet: Boolean = false,
    val bottomSheetButtonState: AppButtonState = AppButtonState.Enable,
    val sendButtonState: AppButtonState = AppButtonState.Enable,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val sendingMessage : MessageUi? = null,
)

data class MessageUi(
    val text: String = "",
    val images: List<String> = emptyList(),
    val anotherUserImage: String,
    val time: String,
    val date: String,
    val isReceived: Boolean = false,
    val isSeen: Boolean = false,
)

fun Message.toMessageUi(imageUserUrl: String, currentUserId: String) = MessageUi(
    text = handleTextMessage(messageContent),
    images = handleImagesMessage(messageContent),
    anotherUserImage = imageUserUrl,
    time = formatChatTime(this.time.time),
    date = formatDateHeader(this.time.date),
    isReceived = this.senderId != currentUserId,
    isSeen = this.seen,
)


private fun formatChatTime(time: LocalTime?): String {
    return time?.let {
        try {
            val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")
            val javaLocalTime = java.time.LocalTime.of(it.hour, it.minute, it.second, it.nanosecond)
            javaLocalTime.format(outputFormat)
        } catch (e: Exception) {
            ""
        }
    } ?: ""
}

private fun formatDateHeader(date: LocalDate): String {
    val javaLocalDate = java.time.LocalDate.of(date.year, date.month.number, date.day)
    return  javaLocalDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
}

fun handleTextMessage(messageContent: MessageContent): String {
    return if (messageContent is MessageContent.Text)
        messageContent.content
    else
        ""
}

fun handleImagesMessage(messageContent: MessageContent): List<String> {
    return if (messageContent is MessageContent.Image)
        messageContent.uris
    else
        emptyList()

}