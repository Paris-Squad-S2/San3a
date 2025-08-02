package com.paris_2.san3a.presentation.screen.messagesDetails

import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.presentation.shared.components.AppButtonState

data class MessageDetailsUiState(
    val textMessage: String = "",
    val messages: List<MessageUi> = emptyList(),
    val chatTitle: String = "",
    val showDropMenu: Boolean = false,
    val showDeleteChatBottomSheet: Boolean = false,
    val bottomSheetButtonState: AppButtonState = AppButtonState.Enable,
    val errorMessage: String? = null,
    val isLoading:Boolean = false,
)

data class MessageUi(
    val text: String = "",
    val images: List<String> = emptyList(),
    val anotherUserImage: String,
    val time: String,
    val isReceived: Boolean = false,
    val isSeen: Boolean = false,
)

fun Message.toMessageUi() = MessageUi(
    text = handleTextMessage(messageContent),
    images = handleImagesMessage(messageContent),
    anotherUserImage = "https://firebasestorage.googleapis.com/v0/b/cell-monitor21.appspot.com/o/user2%2Fchat8%2F1000179245.jpg?alt=media&token=714e333b-7fc6-4be3-83a6-30d6b7f7fd4e", // todo(get user image from authentication)
    time = time.time.toString(),
    isReceived = receiverId == "1",
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