package com.paris_2.san3a.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations : Graph {

    @Serializable
    data object Graph1 : Graph

    @Serializable
    data object Screen : Destination

    @Serializable
    data object MyRequest : Destination

    @Serializable
    data object Messages : Destination

    @Serializable
    data object More : Destination
    @Serializable
    data object Splash : Destination

    @Serializable
    data object OnBoarding : Destination

    @Serializable
    data object Home : Destination
}