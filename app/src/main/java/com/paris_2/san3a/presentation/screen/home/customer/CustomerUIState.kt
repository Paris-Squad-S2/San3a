package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.MostRequestedServices
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service

data class CustomerHomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customerUiState: CustomerUiState = CustomerUiState(),
    val bottomSheetState: Boolean = false
)

data class CustomerUiState(
    val currentUserName: String = "",
    val location: String = "",
    val mostRequestedServices: List<MostRequestedServices> = emptyList(),
    val services: List<Service> = emptyList(),
    val requestService : RequestServiceUiState? = null
)

data class RequestServiceUiState(
    val title: String,
    val serviceType: String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val image: List<String>,
    val userId: String = "",
)

fun RequestService.toRequestServiceUiState(): RequestServiceUiState {
    return RequestServiceUiState(
        title = this.title,
        serviceType = this.serviceType,
        description = this.description,
        location = this.location,
        locationDetails = this.locationDetails,
        image = this.image,
        userId = this.userId
    )
}
fun RequestServiceUiState.toRequestService(): RequestService {
    return RequestService(
        id = "",
        title = this.title,
        serviceType = this.serviceType,
        description = this.description,
        location = this.location,
        locationDetails = this.locationDetails,
        image = this.image,
        userId = this.userId,
        offers = emptyList(),
    )
}

