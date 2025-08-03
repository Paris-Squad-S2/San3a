package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.Service

data class CustomerHomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customerUiState: CustomerUiState = CustomerUiState(),
    val bottomSheetState: Boolean = false
)

data class CustomerUiState(
    val id: String = "",
    val currentUserName: String = "",
    val government: String = "",
    val city: String = "",
    val mostRequestedServices: List<Service> = emptyList(),
    val services: List<Service> = emptyList(),
    val requestService : RequestServiceUiState? = null,
    val locationUiState: LocationUiState = LocationUiState()
)
data class LocationUiState(
    val government: String = "",
    val city: String = "",
    val addressInDetails: String = "",
    val cities: List<String> = emptyList()
)

fun LocationUiState.toEntity(): Location {
    return Location(
        government = this.government,
        cityName = this.city,
    )
}
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

