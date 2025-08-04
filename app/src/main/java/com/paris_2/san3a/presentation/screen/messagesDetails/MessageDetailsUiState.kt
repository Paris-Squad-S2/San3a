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
    time = this.time.time.toString(),
    isReceived = this.receiverId == currentUserId,
    isSeen = this.seen,
)

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