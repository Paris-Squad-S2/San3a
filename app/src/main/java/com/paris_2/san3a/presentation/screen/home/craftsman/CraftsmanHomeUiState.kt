package com.paris_2.san3a.presentation.screen.home.craftsman

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Stats

data class CraftsmanHomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val craftsmanHomeUiState: CraftsmanHomeUiState = CraftsmanHomeUiState()
)

data class CraftsmanHomeUiState(
    val currentUserName: String = "",
    val relatedJob: String = "",
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
