package com.paris_2.san3a.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface San3aDestinations : San3aGraph {

    @Serializable
    data object San3aGraph1 : San3aGraph

    @Serializable
    data object San3aScreen : San3aDestination
}