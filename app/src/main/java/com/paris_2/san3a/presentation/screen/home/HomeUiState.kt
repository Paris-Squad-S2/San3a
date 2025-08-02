package com.paris_2.san3a.presentation.screen.home

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.Stats

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customerHomeUiState: CustomerHomeUiState = CustomerHomeUiState(),
    val craftsmanHomeUiState: CraftsmanHomeUiState = CraftsmanHomeUiState(),
)

data class CustomerHomeUiState(
    val currentUserName: String = "",
    val location: String = "",
    val mostRequestedServices: List<Service> = emptyList(),
    val services: List<Service> = emptyList(),
    val isCraftsman: Boolean = false,
)

data class CraftsmanHomeUiState(
    val currentUserName: String = "",
    val location: String = "",
    val stats: Stats = Stats(
        userId = "",
        jobsDone = 0,
        earnings = 0.0,
        rating = 0.0
    ),
    val recentRelatedJobs: List<RequestService> = emptyList(),
    val availableJobs: List<RequestService> = emptyList(),
)