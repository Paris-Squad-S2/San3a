package com.paris_2.san3a.presentation.screen.home.craftsman

interface CraftsmanInteractionListener {

    fun onNotificationClick()

    fun onSearch(query: String)

    fun onJobClick(serviceId: String)

}