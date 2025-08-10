package com.paris_2.san3a.presentation.screen.requests.customer

interface MyRequestCustomerInteractionListener {
    fun onRequestClick(requestId: String)
    fun onNotificationClick()
    fun onRetryClick()
    fun onClickChat(phoneNumber: String)
}