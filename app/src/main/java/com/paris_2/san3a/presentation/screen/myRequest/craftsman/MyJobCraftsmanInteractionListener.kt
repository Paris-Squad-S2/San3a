package com.paris_2.san3a.presentation.screen.myRequest.craftsman

interface MyJobCraftsmanInteractionListener {
    fun onRequestClick(requestId: String)
    fun onSendMessageClick(phoneNumber: String)
    fun onViewRequestDetails(requestId: String)
    fun onNotificationClick()
    fun onRetryClick()
}