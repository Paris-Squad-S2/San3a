package com.paris_2.san3a.presentation.screen.messagesDetails

import android.net.Uri
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.UiText
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number
import java.time.format.DateTimeFormatter

data class MessageDetailsUiState(
    val textMessage: String = "",
    val messagesSize: Int = 0,
    val chatTitle: String? = null,
    val profilePhoto: String = "",
    val showDropMenu: Boolean = false,
    val groupedMessages: Map<String, List<MessageUi>> = emptyMap(),
    val showDeleteChatBottomSheet: Boolean = false,
    val bottomSheetButtonState: AppButtonState = AppButtonState.Enable,
    val sendButtonState: AppButtonState = AppButtonState.Enable,
    val voiceDuration: Float = 0f,
    val voiceWave: List<Float> = emptyList(),
    val voiceUriToSend: Uri? = null,
    val errorMessage: UiText? = null,
    val isLoading: Boolean = false,
    val sendingTextMessage: MessageUi? = null,
    val sendingImageMessage: MessageUi? = null,
    val sendingVoiceMessage: MessageUi? = null,
    val showSnackBar: Boolean = false,
)

data class MessageUi(
    val text: String? = null,
    val images: List<String> = emptyList(),
    val voiceUrl: Uri?,
    val voiceDuration: Int = 0,
    val voiceWaveform: List<Float> = emptyList(),
    val anotherUserImage: String,
    val time: String,
    val date: String,
    val isReceived: Boolean = false,
    val isSeen: Boolean = false,
)

fun Message.toMessageUi(imageUserUrl: String, currentUserId: String): MessageUi {
    val messageContent = this.messageContent.flatten()
    return MessageUi(
        text = messageContent.text,
        images = messageContent.imageUris,
        voiceUrl = messageContent.voiceUrl,
        voiceDuration = messageContent.voiceDuration,
        voiceWaveform = messageContent.voiceWaveform,
        anotherUserImage = imageUserUrl,
        time = formatChatTime(this.time.time),
        date = formatDateHeader(this.time.date),
        isReceived = this.senderId != currentUserId,
        isSeen = this.seen,
    )
}


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
    return javaLocalDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
}

fun MessageContent.flatten(): FlattenedMessageContent {
    return when (this) {
        is MessageContent.Text -> FlattenedMessageContent(
            text = this.text,
        )

        is MessageContent.Image -> FlattenedMessageContent(
            imageUris = this.uris,
        )

        is MessageContent.Audio -> FlattenedMessageContent(
            voiceUrl = this.url,
            voiceDuration = this.duration,
            voiceWaveform = this.waves
        )
    }
}

data class FlattenedMessageContent(
    val text: String? = null,
    val imageUris: List<String> = emptyList(),
    val voiceUrl: Uri? = null,
    val voiceDuration: Int = 0,
    val voiceWaveform: List<Float> = emptyList()
)