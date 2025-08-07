package com.paris_2.san3a.presentation.screen.requests.craftsman

interface MyJobCraftsmanInteractionListener {
    fun onSendAsDone(requestId: String)
    fun onSendMessageClick(phoneNumber: String)
    fun onViewRequestDetails(requestId: String)
    fun onNotificationClick()
    fun onRetryClick()
}