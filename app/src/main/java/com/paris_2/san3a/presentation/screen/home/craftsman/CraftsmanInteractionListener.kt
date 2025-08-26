package com.paris_2.san3a.presentation.screen.home.craftsman

interface CraftsmanInteractionListener {

    fun onNotificationClick()
    fun onClickAllService()
    fun onServiceSelected(serviceId: String)
    fun onJobClick(serviceId: String)
    fun onRetry()

}