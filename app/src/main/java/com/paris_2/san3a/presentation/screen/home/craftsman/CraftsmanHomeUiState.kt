package com.paris_2.san3a.presentation.screen.home.craftsman

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Stats
import com.paris_2.san3a.presentation.screen.account.UserType
import kotlinx.datetime.LocalDateTime

data class CraftsmanHomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val craftsmanHomeUiState: CraftsmanHomeUiState = CraftsmanHomeUiState()
)

data class CraftsmanHomeUiState(
    val phoneNumber: String = "",
    val currentUserName: String = "",
    val userType: UserType = UserType.CRAFTSMAN,
    val userServices: List<String> = emptyList(),
    val relatedJob: String = "",
    val location: String = "",
    val stats: Stats = Stats(
        userId = "",
        jobsDone = 0,
        earnings = 0.0,
        rating = 0.0
    ),
    val recentRelatedJobs: Map<String, RequestServiceUiState> = emptyMap(),
    val availableJobs: Map<String, RequestServiceUiState> = emptyMap(),
)

fun RequestService.toRequestServiceUiState(): RequestServiceUiState {
    return RequestServiceUiState(
        id = id,
        userId = userId,
        requestStatus = requestStatus,
        title = title,
        serviceType = serviceType,
        description = description,
        location = location,
        locationDetails = locationDetails,
        time = time
    )
}

fun List<RequestService>.toRequestServiceUiStateMap(): Map<String, RequestServiceUiState> {
    return this.associate { requestService ->
        requestService.id to requestService.toRequestServiceUiState()
    }
}

data class RequestServiceUiState(
    val id: String,
    val userId: String,
    val requestStatus: RequestStatus,
    val title: String,
    val serviceType: String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val time: LocalDateTime,
    val offersCount: Int = 0,
)