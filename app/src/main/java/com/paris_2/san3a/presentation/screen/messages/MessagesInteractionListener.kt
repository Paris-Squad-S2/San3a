package com.paris_2.san3a.presentation.screen.messages

interface MessagesInteractionListener {
    fun onNotificationClick()
    fun onRetryClick()
    fun onChatClick(chatId: String)
}