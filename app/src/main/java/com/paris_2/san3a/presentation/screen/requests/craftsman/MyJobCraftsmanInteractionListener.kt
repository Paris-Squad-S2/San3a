package com.paris_2.san3a.presentation.screen.requests.craftsman

interface MyJobCraftsmanInteractionListener {
    fun onMarkAsDone(requestId: String, requestTitle: String, customerId: String)
    fun onSendMessageClick(phoneNumber: String)
    fun onViewRequestDetails(requestId: String)
    fun onNotificationClick()
    fun onRetryClick()
}