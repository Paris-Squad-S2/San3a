package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.screen.messagesDetails.MessageUi
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MessageList(
    messagesSize: Int,
    groupedMessages: Map<String, List<MessageUi>>,
    modifier: Modifier,
    sendingTextMessage: MessageUi?,
    sendingImageMessage: MessageUi?,
    sendingVoiceMessage: MessageUi?
) {
    val listState = rememberLazyListState(0)

    LaunchedEffect(messagesSize) {
            listState.scrollToItem(0)
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 80.dp),
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        sendingTextMessage?.let { message ->
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Message(
                        images = message.images,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .animateItem(),
                        text = message.text,
                        isReceived = message.isReceived,
                        isSeen = null,
                        time = message.time,
                        profileImageUrl = null,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        sendingVoiceMessage?.let { message ->
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Message(
                        images = message.images,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .animateItem(),
                        voiceUri = message.voiceUrl,
                        recordWave = message.voiceWaveform,
                        isReceived = message.isReceived,
                        isSeen = null,
                        time = message.time,
                        profileImageUrl = null,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        sendingImageMessage?.let { message ->
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Message(
                        images = message.images,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .animateItem(),
                        text = message.text,
                        isReceived = message.isReceived,
                        isSeen = null,
                        time = message.time,
                        profileImageUrl = null,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        groupedMessages.forEach { (date, messagesForDate) ->
            items(messagesForDate) { item ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Message(
                        images = item.images,
                        modifier = Modifier
                            .align(if (item.isReceived) Alignment.CenterStart else Alignment.CenterEnd)
                            .animateItem(),
                        text = item.text,
                        isReceived = item.isReceived,
                        isSeen = item.isSeen,
                        recordWave = item.voiceWaveform,
                        voiceUri = item.voiceUrl,
                        time = item.time,
                        profileImageUrl = if (item.isReceived) item.anotherUserImage else null,
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            stickyHeader {
                Text(
                    text = date,
                    style = Theme.textStyle.body.medium.regular,
                    color = Theme.colors.shade.tertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}