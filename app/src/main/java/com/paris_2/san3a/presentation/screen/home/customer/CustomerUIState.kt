package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState

data class CustomerHomeUiState(
    val isUserDataLoading: Boolean = false,
    val isRequestLoading: Boolean = false,
    val isFindWhatYouNeedLoading: Boolean = false,
    val errorMessage: String? = null,
    val notificationsCount: Int = 0,
    val customerUiState: CustomerUiState = CustomerUiState(),
    val bottomSheetUiState: BottomSheetUiState = BottomSheetUiState(),
    val showSnackBarError: Boolean = false,
    val showSnackBarSuccess: Boolean = false,
    val successSnackBarMessage: String? = null,
    val buttonSheetState: AppButtonState = AppButtonState.Enable,
)

data class CustomerUiState(
    val id: String = "",
    val currentLanguage: String = "en",
    val currentUserName: String = "",
    val governorate: Governorate? = null,
    val city: City? = null,
    val addressDetails: String = "",
    val mostRequestedServices: List<Service> = emptyList(),
    val services: List<Service> = emptyList(),
    val searchQuery: String = "",
    val searchResults: List<Service> = emptyList(),
)

data class BottomSheetUiState(
    val bottomSheetState: Boolean = false,
    val bottomSheetStep: BottomSheetStep = BottomSheetStep.SELECT_SERVICE,
    val bottomSheetService: Service? = null,
    val bottomSheetSubtitle: String = "",
    val bottomSheetDescription: String = "",
    val bottomSheetImages: List<String> = emptyList(),
    val bottomSheetSelectedSuggestion: String? = null,
    val bottomSheetGovernments: List<Governorate> = emptyList(),
    val bottomSheetCities: List<City> = emptyList(),
    val bottomSheetSelectedGovernorate: Governorate? = null,
    val bottomSheetSelectedCity: City? = null,
    val bottomSheetAddressDetails: String = "",
    val isGovernmentSheetVisible: Boolean = false,
    val locationBottomSheetType: LocationBottomSheetContentType = LocationBottomSheetContentType.GOVERNMENT
)

enum class BottomSheetStep {
    SELECT_SERVICE,
    PROBLEM_DESCRIPTION,
    SELECT_LOCATION,
    IMAGE_UPLOAD
}
