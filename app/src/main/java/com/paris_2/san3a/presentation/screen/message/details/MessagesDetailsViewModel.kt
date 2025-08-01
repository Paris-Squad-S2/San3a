package com.paris_2.san3a.presentation.screen.message.details

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.usecase.GetChatsByUserIdUseCase
import com.paris_2.san3a.domain.usecase.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessagesDetailsViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val getChatsByUserIdUseCase: GetChatsByUserIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MediaDetails())
    val state = _state.asStateFlow()


    fun saveImagesUris(uris: List<Uri>) {
        viewModelScope.launch {
            try {
                state.value.copy(
                    messageImageList = uris
                )
               sendMessageUseCase(
                 Message(
                        senderId = "1",
                        receiverId = "2",
                        chatId = "8",
                        messageContent = MessageContent.Image(
                            uris = uris.map { it.toString() }
                        ),
                        seen = false
                    )
                )
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage =  e.message
                    )
                }
            }
        }
    }

}

data class MediaDetails(
    val showMediaPicker: Boolean = false,
    val errorMessage: String?=null,
    val messageImageList: List<Uri> = emptyList(),
)