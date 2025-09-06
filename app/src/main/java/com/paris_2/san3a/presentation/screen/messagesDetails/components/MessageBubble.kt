package com.paris_2.san3a.presentation.screen.messagesDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme

@Composable
fun MessageBubble(
    text: String?,
    time: String?,
    isReceived: Boolean,
    isSeen: Boolean?,
    images: List<String>,
) {
    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = Theme.radius.extraLarge,
                    topEnd = Theme.radius.extraLarge,
                    bottomEnd = if (!isReceived) Theme.radius.extraSmall else Theme.radius.extraLarge,
                    bottomStart = if (!isReceived) Theme.radius.extraLarge else Theme.radius.extraSmall,
                )
            )
            .background(if (isReceived) Theme.colors.background.card else Theme.colors.brand.tertiary),
    ) {
        if (!text.isNullOrBlank()) {
            ExpandableText(text = text)
        }
        if (images.isNotEmpty()) {
            ImagesGrid(images)
        }
        if (text.isNullOrBlank().not() || images.isNotEmpty()) {
            MessageMeta(
                time = time,
                isSeen = isSeen,
                isReceived = isReceived,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 3,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }


    Column {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = Theme.textStyle.body.medium.regular,
            modifier = Modifier.padding(
                start = 12.dp,
                end = 12.dp,
                top = 12.dp,
                bottom = 4.dp
            ),
            color = Theme.colors.shade.secondary,
            textAlign = TextAlign.Start,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (!isExpanded) {
                    isOverflowing = textLayoutResult.hasVisualOverflow
                }
            }

        )
        if (isOverflowing || isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { isExpanded = !isExpanded },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (isExpanded) stringResource(R.string.see_less) else stringResource(
                            R.string.see_more
                        ),
                        style = Theme.textStyle.body.medium.regular,
                        color = Theme.colors.button.primary
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MessageBubblePreview_Received() {
    San3aTheme {
        MessageBubble(
            text = "This is a very long message to test the expandable text functionality inside a message bubble. " +
                    "If the text is longer than three lines, a 'See more' button should appear to expand the content fully. " +
                    "Clicking 'See less' should collapse it again for a cleaner chat experience." +
                    "This is a very long message to test the expandable text functionality inside a message bubble. " +
                    "If the text is longer than three lines, a 'See more' button should appear to expand the content fully. " +
                    "Clicking 'See less' should collapse it again for a cleaner chat experience.",

            time = "12:45 PM",
            isReceived = true,
            isSeen = true,
            images = emptyList(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview_Sent() {
    San3aTheme {
        MessageBubble(
            text = "Short message example.",
            time = "12:46 PM",
            isReceived = false,
            isSeen = true,
            images = emptyList()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview2() {
    San3aTheme {
        MessageBubble(
            text = null,
            time = "12:46 PM",
            isReceived = false,
            isSeen = true,
            images = emptyList()
        )
    }
}