package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.utill.getCurrentDateTime

data class CustomerHomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val customerUiState: CustomerUiState = CustomerUiState(),
    val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState(),
    val showSnackBarError: Boolean = false,
    val showSnackBarSuccess: Boolean = false,
    val successSnackBarMessage: String? = null,
    val buttonSheetState: AppButtonState = AppButtonState.Enable,
)

data class CustomerUiState(
    val id: String = "",
    val currentUserName: String = "",
    val government: String = "",
    val city: String = "",
    val mostRequestedServices: List<Service> = emptyList(),
    val services: List<Service> = emptyList(),
    val requestService: RequestServiceUiState? = null,
    val searchQuery: String = "",
    val searchResults: List<Service> = emptyList()
)

data class BottomSheetUiState(
    val bottomSheetState: Boolean = false,
    val bottomSheetStep: BottomSheetStep = BottomSheetStep.SELECT_SERVICE,
    val bottomSheetServiceTitle: String = "",
    val bottomSheetSubtitle: String = "",
    val bottomSheetServiceId: String = "",
    val bottomSheetServiceImageUrl: String = "",
    val bottomSheetDescription: String = "",
    val bottomSheetImages: List<String> = emptyList(),
    val bottomSheetSelectedSuggestion: String? = null,
    val bottomSheetGovernments: List<Governorate> = emptyList(),
    val bottomSheetCities: List<City> = emptyList(),
    val bottomSheetSelectedGovernmentName: String = "",
    val bottomSheetSelectedGovernmentId: Int? = null,
    val bottomSheetSelectedCityName: String = "",
    val bottomSheetSelectedCityId: Int? = null,
    val bottomSheetAddressDetails: String = "",
    val isGovernmentSheetVisible: Boolean = false,
    val locationBottomSheetType: LocationBottomSheetContentType = LocationBottomSheetContentType.GOVERNMENT

)

data class RequestServiceUiState(
    val title: String,
    val serviceType: String,
    val description: String,
    val governorateId: Int,
    val cityId: Int,
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
        governorateId = this.governorateId,
        cityId = this.cityId,
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
        governorateId = this.governorateId,
        cityId = this.cityId,
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
