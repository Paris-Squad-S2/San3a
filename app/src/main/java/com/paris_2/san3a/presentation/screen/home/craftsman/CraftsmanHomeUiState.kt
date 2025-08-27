package com.paris_2.san3a.presentation.screen.home.craftsman

import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.Stats
import com.paris_2.san3a.presentation.screen.account.UserType
import kotlinx.datetime.LocalDateTime

data class CraftsmanHomeState(
    val isRecentJobsLoading: Boolean = false,
    val isAvailableJobsLoading: Boolean = false,
    val errorMessage: String? = null,
    val craftsmanHomeUiState: CraftsmanHomeUiState = CraftsmanHomeUiState()
)

data class CraftsmanHomeUiState(
    val phoneNumber: String = "",
    val notificationsCount: Int = 0,
    val currentUserName: String = "",
    val userType: UserType = UserType.CRAFTSMAN,
    val userServices: Map<String, Service> = emptyMap(),
    val relatedJob: String = "",
    val location: String = "",
    val selectedServiceId: String? = null,
    val stats: StatsUiState = StatsUiState(),
    val recentRelatedJobs: Map<String, RequestServiceUiState> = emptyMap(),
    val availableJobs: Map<String, RequestServiceUiState> = emptyMap(),
    val filteredAvailableJobs: Map<String, RequestServiceUiState> = emptyMap()
)

data class StatsUiState(
    val userId: String = "",
    val jobsDone: Int = 0,
    val earnings: Double = 0.0,
    val rating: Float = 0.0f
)

fun Stats.toStatsUiState(): StatsUiState {
    return StatsUiState(
        userId = userId,
        jobsDone = jobsDone,
        earnings = earnings,
        rating = rating
    )
}

fun RequestService.toRequestServiceUiState(location: String, imageUrl: String, serviceType: String): RequestServiceUiState {
    return RequestServiceUiState(
        id = id,
        userId = userId,
        serviceId = serviceId,
        requestStatus = requestStatus,
        title = title,
        imageUrl = imageUrl,
        serviceType = serviceType,
        description = description,
        location = location,
        locationDetails = locationDetails,
        time = time
    )
}

data class RequestServiceUiState(
    val id: String,
    val userId: String,
    val serviceId: String,
    val requestStatus: RequestStatus,
    val title: String,
    val imageUrl: String,
    val serviceType: String,
    val isSelectedServiceType: Boolean = false,
    val description: String,
    val location: String,
    val locationDetails: String,
    val time: LocalDateTime,
    val offersCount: Int = 0,
)