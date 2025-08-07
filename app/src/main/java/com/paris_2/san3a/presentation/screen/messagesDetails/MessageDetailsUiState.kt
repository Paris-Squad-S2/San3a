package com.paris_2.san3a.presentation.screen.messagesDetails

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.presentation.shared.components.AppButtonState

data class MessageDetailsUiState(
    val textMessage: String = "",
    val messages: List<MessageUi> = emptyList(),
    val chatTitle: String = "",
    val profilePhoto: String = "",
    val showDropMenu: Boolean = false,
    val showDeleteChatBottomSheet: Boolean = false,
    val bottomSheetButtonState: AppButtonState = AppButtonState.Enable,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)

data class MessageUi(
    val text: String = "",
    val images: List<String> = emptyList(),
    val anotherUserImage: String,
    val time: String,
    val isReceived: Boolean = false,
    val isSeen: Boolean = false,
)

fun Message.toMessageUi(imageUserUrl: String, currentUserId: String) = MessageUi(
    text = handleTextMessage(messageContent),
    images = handleImagesMessage(messageContent),
    anotherUserImage = imageUserUrl,
    time = formatChatTime(this.time.time),
    isReceived = this.receiverId == currentUserId,
    isSeen = this.seen,
)

private fun formatChatTime(time: kotlinx.datetime.LocalTime?): String {
    return time?.let {
        try {
            val outputFormat = java.time.format.DateTimeFormatter.ofPattern("hh:mm a")
            val javaLocalTime = java.time.LocalTime.of(it.hour, it.minute, it.second, it.nanosecond)
            javaLocalTime.format(outputFormat)
        } catch (e: Exception) {
            ""
        }
    } ?: ""
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