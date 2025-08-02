package com.paris_2.san3a.presentation.screen.messages.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.paris_2.san3a.R
import com.paris_2.san3a.presentation.shared.designSystem.theme.San3aTheme
import com.paris_2.san3a.presentation.shared.designSystem.theme.Theme
import com.paris_2.san3a.presentation.screen.messages.utils.ArabicStringMapper.isArabicLanguage
import com.paris_2.san3a.presentation.screen.messages.utils.ArabicStringMapper.toArabicNumerals
import com.paris_2.san3a.presentation.screen.messages.utils.ArabicStringMapper.toArabicSafe


@SuppressLint("LocalContextConfigurationRead")
@Composable
fun ChatCard(
    modifier: Modifier = Modifier,
    onChatClick: () -> Unit = {},
    name: String,
    imageUrl: String,
    lastMessage: String,
    unreadMessagesCount: Int = 0,
    dateTime: String,
    isCurrentUserSendLastMessage: Boolean,
) {
    val unreadMessagesDisplay = animateIntAsState(
        targetValue = when (unreadMessagesCount) {
            in 0..99 -> unreadMessagesCount
            in Int.MIN_VALUE..-1 -> 0
            else -> 99
        }
    )


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Theme.colors.background.card, RoundedCornerShape(12.dp))
            .border(
                color = Theme.colors.shade.quinary,
                width = 1.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onChatClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .padding(start = 10.dp)
                .size(56.dp)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 12.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = Theme.textStyle.body.medium.medium,
                    color = Theme.colors.shade.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(2.7f)
                )

                if (isArabicLanguage()) {
                    Text(
                        text = dateTime.toArabicSafe(),
                        style = Theme.textStyle.body.small.regular,
                        color = Theme.colors.shade.secondary,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                    )
                } else {
                    Text(
                        text = dateTime,
                        style = Theme.textStyle.body.small.regular,
                        color = Theme.colors.shade.secondary,
                        maxLines = 1,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 43.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(2.6f)
                ) {
                    AnimatedVisibility(
                        visible = isCurrentUserSendLastMessage
                    ) {
                        Text(
                            text = stringResource(R.string.you),
                            style = Theme.textStyle.body.small.regular,
                            color = Theme.colors.brand.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Text(
                        text = lastMessage,
                        style = Theme.textStyle.body.small.regular,
                        color = Theme.colors.shade.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                AnimatedVisibility(
                    visible = unreadMessagesCount > 0
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = Theme.colors.brand.primary,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isArabicLanguage())
                                unreadMessagesDisplay.value.toString().toArabicNumerals()
                            else
                                unreadMessagesDisplay.value.toString(),
                            color = Theme.colors.background.card,
                            style = Theme.textStyle.label.medium.regular,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatCardPreview() {
    San3aTheme {
        Column {
            ChatCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                name = "Ahmed Abdelnasser",
                imageUrl = "https://plus.unsplash.com/premium_photo-1689568126014-06fea9d5d341?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                lastMessage = "where ara you know",
                unreadMessagesCount = 6,
                dateTime = "19:34",
                isCurrentUserSendLastMessage = true
            )
            ChatCard(
                name = "حمصلاح المقنع",
                imageUrl = "https://plus.unsplash.com/premium_photo-1689568126014-06fea9d5d341?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                lastMessage = "انت فين يلاا",
                unreadMessagesCount = 99,
                dateTime = "monday",
                isCurrentUserSendLastMessage = false
            )

        }
    }
}