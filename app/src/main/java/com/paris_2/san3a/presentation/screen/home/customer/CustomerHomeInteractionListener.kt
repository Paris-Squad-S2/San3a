package com.paris_2.san3a.presentation.screen.home.customer

interface CustomerHomeInteractionListener {

    fun onNotificationClick()

    fun onSearch(query: String)

    fun onServiceClick(serviceId: String)

    fun onBecomeCraftsmanClick()

    fun onDismissBottomSheet()
    fun createRequest(service: RequestServiceUiState)

}