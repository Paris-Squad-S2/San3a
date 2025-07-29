package com.paris_2.san3a.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {

    @Serializable
    data object Graph1 : Graph

    @Serializable
    data object Screen : Destination

    @Serializable
    data object OTPRegisterScreen: Destination
}