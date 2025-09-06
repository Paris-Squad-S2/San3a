package com.paris_2.san3a.presentation.screen.messagesDetails.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.utils.PreviewMultiDevices

@Composable
fun Message(
    modifier: Modifier = Modifier,
    profileImageUrl: String? = null,
    text: String? = null,
    time: String? = null,
    isReceived: Boolean = true,
    isSeen: Boolean? = false,
    images: List<String> = emptyList(),
    voiceUri: Uri? = null,
    recordWave: List<Float> = emptyList(),
) {
    Column (
        modifier = modifier,
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            if (isReceived) {
                ProfileImage(profileImageUrl)
            }
            MessageContent(
                text = text,
                time = time,
                isReceived = isReceived,
                isSeen = isSeen,
                images = images,
                voiceUri = voiceUri,
                recordWave = recordWave,
            )
            if (!isReceived) {
                ProfileImage(profileImageUrl)
            }
        }
        if (voiceUri != null) {
            MessageMeta(
                time = time,
                isSeen = isSeen,
                isReceived = isReceived,
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
            )
        }
    }
}

@Composable
@PreviewMultiDevices
fun MessagePreview() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                profileImageUrl = "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                text = "Hello, this is a sample message to demonstrate the message component in the app. It can be quite long to test text wrapping and alignment properly. Let's see how it looks! This is a test message.",
                time = "10:30 AM",
                isReceived = false,
                images = listOf(
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878"
                ),
                isSeen = true,
                voiceUri = Uri.EMPTY,
                recordWave = listOf(
                    0.3f,
                    0.2f,
                    0.3f,
                    0.7f,
                    0.5f,
                    0.1f,
                    0.7f,
                    0.4f,
                    0.9f,
                    1.0f,
                    0.3f,
                    0.2f,
                    0.3f,
                    0.7f,
                    0.5f,
                    0.1f,
                    0.7f,
                    0.4f,
                    0.9f,
                    1.0f
                ),
            )
        }
    }

}

@Composable
@PreviewMultiDevices
fun MessagePreview2() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                profileImageUrl = "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                text = "مرحبًا، هذه رسالة نموذجية لعرض مكون الرسائل في التطبيق. يمكن أن تكون طويلة لاختبار التفاف النص والمحاذاة بشكل صحيح. لنرى كيف تبدو! هذه رسالة اختبار.",
                time = "١٠:٣٠ ص",
                isReceived = false,
                images = listOf(
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878"
                ),
                voiceUri = Uri.EMPTY,
                recordWave = listOf(
                    0.3f,
                    0.2f,
                    0.3f,
                    0.7f,
                    0.5f,
                    0.1f,
                    0.7f,
                    0.4f,
                    0.9f,
                    1.0f,
                    0.3f,
                    0.2f,
                    0.3f,
                    0.7f,
                    0.5f,
                    0.1f,
                    0.7f,
                    0.4f,
                    0.9f,
                    1.0f
                ),
                isSeen = true,
            )
        }
    }
}

@Composable
@PreviewMultiDevices
fun MessagePreview3() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                profileImageUrl = "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                text = "مرحبًا، هذه رسالة نموذجية لعرض مكون الرسائل في التطبيق. يمكن أن تكون طويلة لاختبار التفاف النص والمحاذاة بشكل صحيح. لنرى كيف تبدو! هذه رسالة اختبار.",
                time = "١٠:٣٠ ص",
            )
        }
    }
}

@Composable
@PreviewMultiDevices
fun MessagePreview4() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                profileImageUrl = "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                time = "10:30 AM",
                isReceived = false,
                images = listOf(
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                    "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878"
                ),
                isSeen = true,
            )
        }
    }
}

@Composable
@PreviewMultiDevices
fun MessagePreview5() {
    San3aTheme {
        Surface(color = Color.Red) {
            Message(
                profileImageUrl = "https://www.facebook.com/photo/?fbid=709359247644755&set=a.102154745031878",
                voiceUri = Uri.EMPTY,
                recordWave = listOf(
                    0.3f,
                    0.2f,
                    0.3f,
                    0.7f,
                    0.5f,
                    0.1f,
                    0.7f,
                    0.4f,
                    0.9f,
                    1.0f,
                    0.3f,
                    0.2f,
                    0.3f,
                    0.7f,
                    0.5f,
                    0.1f,
                    0.7f,
                    0.4f,
                    0.9f,
                    1.0f
                ),
            )
        }
    }
}