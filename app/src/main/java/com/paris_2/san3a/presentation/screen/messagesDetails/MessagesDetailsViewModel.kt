package com.paris_2.san3a.presentation.screen.messagesDetails

import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.Message
import com.paris_2.san3a.domain.entity.MessageContent
import com.paris_2.san3a.domain.usecase.messaging.DeleteChatByIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.GetMessagesByChatIdUseCase
import com.paris_2.san3a.domain.usecase.messaging.MarkMessagesAsSeenUseCase
import com.paris_2.san3a.domain.usecase.messaging.SendMessageUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.shared.utils.UiText
import com.paris_2.san3a.presentation.utill.fakeImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class MessagesDetailsViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val deleteChatByIdUseCase: DeleteChatByIdUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val markMessagesAsSeenUseCase: MarkMessagesAsSeenUseCase,
    savedStateHandle: SavedStateHandle,
) : MessageInteractionListener, BaseViewModel<MessageDetailsUiState>(
    MessageDetailsUiState()
) {

    val chatId = savedStateHandle.toRoute<Destinations.MessageDetails>().chatId
    val otherUserId = savedStateHandle.toRoute<Destinations.MessageDetails>().otherUserId
    val currentUserId = savedStateHandle.toRoute<Destinations.MessageDetails>().currentUserId

    init {
        getTheOtherUserData(otherUserId)
    }

    private fun markMessagesAsSeen() {
        tryToExecute(
            execute = {
                markMessagesAsSeenUseCase(chatId, currentUserId)
            },
            onError = {
                Log.d("MessagesDetailsViewModel", "markMessagesAsSeen error: ${it.message}", it)
            }
        )
    }

    private fun getTheOtherUserData(otherUserId: String) {
        tryToExecute(
            execute = {
                updateState(screenState.value.copy(isLoading = true))
                getUserUseCase(otherUserId)
            },
            onSuccess = { user ->
                updateState(
                    screenState.value.copy(
                        chatTitle = user.fullName,
                        profilePhoto = user.profilePhoto ?: fakeImage,
                    )
                )
                loadMessages(chatId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        chatTitle = FAKE_USER_NAME,
                        profilePhoto = fakeImage,
                    )
                )
                Log.d("MessagesDetailsViewModel", "getTheOtherUserData error: ${it.message}", it)
                loadMessages(chatId)
            }
        )
    }

    private fun loadMessages(chatId: String) {
        tryToObserve(
            observe = {
                updateState(screenState.value.copy(isLoading = true))
                getMessagesByChatIdUseCase(chatId)
            },
            onEach = { messages ->
                val messageUis =
                    messages?.map { it.toMessageUi(screenState.value.profilePhoto, currentUserId) }
                        ?: emptyList()
                val groupedMessages = messageUis
                    .groupBy { it.date }
                    .toSortedMap(compareByDescending { it })

                updateState(
                    screenState.value.copy(
                        messagesSize = messageUis.size,
                        groupedMessages = groupedMessages,
                        chatTitle = screenState.value.chatTitle,
                        isLoading = false,
                        sendingTextMessage = null,
                        sendingVoiceMessage = null
                    )
                )
                markMessagesAsSeen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen),
                        isLoading = false
                    )
                )
            }
        )
    }


    fun sendMessage() {
        if (screenState.value.voiceDuration > 0f) {
            sendVoiceMessage()
        } else {
            sendTextMessage()
        }

    }


    fun saveImages(uris: List<Uri>) {
        tryToExecute(
            execute = {
                if (uris.isNotEmpty()) {
                    Message(
                        senderId = currentUserId,
                        receiverId = otherUserId,
                        chatId = chatId,
                        messageContent = MessageContent.Image(
                            uris = uris.map { it.toString() }
                        ),
                        seen = false
                    ).let { message ->
                        updateState(
                            screenState.value.copy(
                                messagesSize = screenState.value.messagesSize + 1,
                                sendingImageMessage = message.toMessageUi(
                                    imageUserUrl = "",
                                    currentUserId = currentUserId
                                ),
                            )
                        )
                        sendMessageUseCase(message)
                    }
                }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        sendingImageMessage = null
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen),
                        showSnackBar = true,
                        sendingImageMessage = null,
                        messagesSize = (screenState.value.messagesSize - 1).coerceAtLeast(0)
                    )
                )
                hideSnackBar()
            },
        )
    }

    private fun sendTextMessage() {
        tryToExecute(
            execute = {
                screenState.value.textMessage.trim().let { message ->
                    if (message.isBlank()) return@tryToExecute
                    Message(
                        senderId = currentUserId,
                        receiverId = otherUserId,
                        chatId = chatId,
                        messageContent = MessageContent.Text(
                            text = message
                        ),
                        seen = false
                    ).let { message ->
                        updateState(
                            screenState.value.copy(
                                sendButtonState = AppButtonState.Disabled,
                                textMessage = "",
                                sendingTextMessage = message.toMessageUi(
                                    imageUserUrl = "",
                                    currentUserId = currentUserId
                                ),
                                messagesSize = screenState.value.messagesSize + 1,
                            )
                        )
                        sendMessageUseCase(message)
                    }
                }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        sendButtonState = AppButtonState.Enable
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen),
                        sendButtonState = AppButtonState.Enable,
                        showSnackBar = true,
                        sendingTextMessage = null,
                        messagesSize = (screenState.value.messagesSize - 1).coerceAtLeast(0)
                    )
                )
                hideSnackBar()
            }
        )
    }

    private fun sendVoiceMessage() {
        tryToExecute(
            execute = {
                stopRecording()
                if (screenState.value.voiceUriToSend == null) return@tryToExecute
                Message(
                    senderId = currentUserId,
                    receiverId = otherUserId,
                    chatId = chatId,
                    messageContent = MessageContent.Audio(
                        url = screenState.value.voiceUriToSend ?: Uri.EMPTY,
                        duration = screenState.value.voiceDuration.toInt(),
                        waves = screenState.value.voiceWave.normalize()
                    ),
                    seen = false
                ).let { message ->
                    updateState(
                        screenState.value.copy(
                            messagesSize = screenState.value.messagesSize + 1,
                            sendingVoiceMessage = message.toMessageUi(
                                imageUserUrl = "",
                                currentUserId = currentUserId
                            ),
                            voiceDuration = 0f,
                            voiceWave = emptyList(),
                            voiceUriToSend = null,
                        )
                    )
                    sendMessageUseCase(message)
                }
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        sendingVoiceMessage = null,
                        voiceDuration = 0f,
                        voiceWave = emptyList(),
                        voiceUriToSend = null,
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen),
                        showSnackBar = true,
                        sendingVoiceMessage = null,
                        messagesSize = (screenState.value.messagesSize - 1).coerceAtLeast(0)
                    )
                )
                hideSnackBar()
            },
        )
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBar) {
                delay(3000)
                updateState(
                    screenState.value.copy(
                        showSnackBar = false,
                        errorMessage = null
                    )
                )
            }
        }
    }


    fun onMessageChange(message: String) {
        updateState(
            screenState.value.copy(
                textMessage = message
            )
        )
    }

    private var mediaRecorder: MediaRecorder? = null
    private var recordingJob: Job? = null
    private var isRecording = false
    private var audioFilePath: String? = null

    fun startRecordingWithContext(context: Context) {
        if (isRecording) return
        try {
            val fileName = "voice_${UUID.randomUUID()}.m4a"
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                fileName
            )
            audioFilePath = file.absolutePath
            mediaRecorder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFilePath)
                prepare()
                start()
            }
            isRecording = true
            updateState(
                screenState.value.copy(
                    voiceDuration = 0f,
                    voiceWave = emptyList()
                )
            )
            startAmplitudePolling()
        } catch (e: Exception) {
            Log.e("MessagesDetailsViewModel", "startRecording error: ${e.message}", e)
            isRecording = false
            updateState(
                screenState.value.copy(
                    errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen),
                    showSnackBar = true
                )
            )
        }
    }

    private fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
            recordingJob?.cancel()
            // Set the URI for the recorded file
            audioFilePath?.let { path ->
                val uri = Uri.fromFile(File(path))
                updateState(
                    screenState.value.copy(
                        voiceDuration = screenState.value.voiceDuration,
                        voiceWave = screenState.value.voiceWave,
                        voiceUriToSend = uri,
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("MessagesDetailsViewModel", "stopRecording error: ${e.message}", e)
            updateState(
                screenState.value.copy(
                    errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen),
                    showSnackBar = true
                )
            )
        }
    }

    private fun startAmplitudePolling() {
        recordingJob = viewModelScope.launch {
            val wave = mutableListOf<Float>()
            var duration = 0f
            while (isRecording) {
                delay(100)
                val amp = mediaRecorder?.maxAmplitude ?: 0
                val normalized = (amp / MAX_AMPLITUDE_DIVISOR).coerceIn(0.1f, 1f)
                wave.add(normalized)
                duration += 0.1f
                updateState(
                    screenState.value.copy(
                        voiceDuration = duration,
                        voiceWave = wave.toList()
                    )
                )
            }
        }
    }

    override fun onBackClick() {
        navigateUp()
    }

    override fun onDropMenuClick() {
        updateState(
            screenState.value.copy(
                showDropMenu = true
            )
        )
    }

    override fun onDismissDropMenu() {
        updateState(
            screenState.value.copy(
                showDropMenu = false
            )
        )
    }

    override fun onDropMenuItemClick() {
        updateState(
            screenState.value.copy(
                showDeleteChatBottomSheet = true
            )
        )
    }

    override fun onDeleteButtonClick() {
        tryToExecute(
            execute = {
                deleteChatByIdUseCase(chatId)
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        showDeleteChatBottomSheet = false
                    )
                )
                navigateUp()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = UiText.StringResource(R.string.occur_error_while_using_messages_screen)
                    )
                )
            },
        )
    }

    override fun onDismissDeleteBottomSheet() {
        updateState(
            screenState.value.copy(
                showDeleteChatBottomSheet = false
            )
        )
    }

    override fun onRetryClick() {
        loadMessages(chatId)
    }

    override fun onDismissSnackBar() {
        updateState(
            screenState.value.copy(
                showSnackBar = false,
                errorMessage = null
            )
        )
    }

    companion object {
        const val IMAGE_TYPE = "image/*"
        const val FAKE_USER_NAME = "Unknown User"
        private const val MAX_AMPLITUDE_DIVISOR = 8191.5f
    }

}

private fun List<Float>.normalize(maxWaveSize: Int = 30): List<Float> {
    if (isEmpty()) return emptyList()
    if (size <= maxWaveSize) return this

    val step = size.toFloat() / maxWaveSize
    return List(maxWaveSize) { i ->
        val index = (i * step).toInt().coerceIn(0, size - 1)
        this[index]
    }
}