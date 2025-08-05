package com.paris_2.san3a.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {

    @Serializable
    data object MainGraph : Graph

    @Serializable
    data object CustomerGraph : Graph

    @Serializable
    data object CraftManGraph : Graph

    @Serializable
    data class OTPRegisterScreen(val phoneNumber: String = "") : Destination

    @Serializable
    data object RegisterScreen : Destination

    @Serializable
    data object Account : Destination

    @Serializable
    data object Home : Destination

    @Serializable
    data object MyRequest : Destination

    @Serializable
    data object Messages : Destination

    @Serializable
    data class MessageDetails(val chatId: String, val currentUserId: String, val otherUserId: String) : Destination

    @Serializable
    data object Notifications : Destination

    @Serializable
    data object More : Destination

    @Serializable
    data object Splash : Destination

    @Serializable
    data object OnBoarding : Destination

    @Serializable
    data object Verification : Destination

    @Serializable
    data class MyService(val phoneNumber: String = "", val isCraftsman: Boolean = false) :
        Destination
    data object Location : Destination

}