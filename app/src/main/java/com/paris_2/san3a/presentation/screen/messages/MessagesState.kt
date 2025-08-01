package com.paris_2.san3a.presentation.screen.messages

import com.paris_2.san3a.domain.entity.Chat

data class MessagesState(
    val chats: List<Chat> = emptyList(),
    val currentUserId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)