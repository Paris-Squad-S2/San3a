package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.utill.getCurrentDateTime

data class CustomerHomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customerUiState: CustomerUiState = CustomerUiState(),
    val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState()
)

data class CustomerUiState(
    val id: String = "",
    val currentUserName: String = "",
    val government: String = "",
    val city: String = "",
    val mostRequestedServices: List<Service> = emptyList(),
    val services: List<Service> = emptyList(),
    val requestService: RequestServiceUiState? = null,
    val locationUiState: LocationUiState = LocationUiState(),
    val searchQuery: String = "",
    val searchResults: List<Service> = emptyList()
)

data class LocationUiState(
    val government: String = "",
    val city: String = "",
    val addressInDetails: String = "",
    val cities: List<String> = emptyList()
)

data class BottomSheetUiState(
    val bottomSheetState: Boolean = false,
    val bottomSheetStep: BottomSheetStep = BottomSheetStep.SELECT_SERVICE,
    val bottomSheetServiceTitle: String = "",
    val bottomSheetSubtitle: String = "",
    val bottomSheetServiceId: String = "",
    val bottomSheetIconRes: Int = 0,
    val bottomSheetDescription: String = "",
    val bottomSheetImages: List<String> = emptyList(),
    val bottomSheetSelectedSuggestion: String? = null,
    val bottomSheetGovernments: List<String> = emptyList(),
    val bottomSheetCities: List<String> = emptyList(),
    val bottomSheetSelectedGovernment: String = "",
    val bottomSheetSelectedCity: String = "",
    val bottomSheetAddressDetails: String = "",
    val isGovernmentSheetVisible: Boolean = false,
    val locationBottomSheetType: LocationBottomSheetContentType = LocationBottomSheetContentType.GOVERNMENT

)

fun LocationUiState.toEntity(): Location {
    return Location(
        government = this.government,
        cityName = this.city,
        addressInDetails = this.addressInDetails
    )
}

data class RequestServiceUiState(
    val title: String,
    val serviceType: String,
    val description: String,
    val location: String,
    val locationDetails: String,
    val image: List<String>,
    val serviceId: String,
    val userId: String = "",
    val requestStatus: RequestStatus = RequestStatus.ONGOING
)

fun RequestService.toRequestServiceUiState(): RequestServiceUiState {
    return RequestServiceUiState(
        title = this.title,
        serviceType = this.serviceType,
        description = this.description,
        location = this.location,
        locationDetails = this.locationDetails,
        image = this.image,
        userId = this.userId,
        serviceId = serviceId
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
        selectedCraftsmanId = null,
        time = getCurrentDateTime(),
        state = "",
        requestStatus = this.requestStatus,
        serviceId = serviceId
    )
}

enum class BottomSheetStep {
    SELECT_SERVICE,
    PROBLEM_DESCRIPTION,
    SELECT_LOCATION,
    IMAGE_UPLOAD
}
