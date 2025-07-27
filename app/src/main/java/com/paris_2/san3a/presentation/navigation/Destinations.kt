package com.paris_2.san3a.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Destinations : Graph {

    @Serializable
    data object MainGraph : Graph

    @Serializable
    data object Splash : Destination

    @Serializable
    data object OnBoarding : Destination
}