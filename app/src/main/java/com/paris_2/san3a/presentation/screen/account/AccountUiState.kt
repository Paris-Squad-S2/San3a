package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState

data class AccountScreenUiState(
    val accountUiState: AccountUiState = AccountUiState(),
    val isLoading: Boolean = false,
    val errorMassage: String? = "",
)

data class AccountUiState(
    val pageNumber: String = "",
    val phoneNumber: String = "",
    val userType: UserType? = null,
    val serviceUiState: List<ServiceUiState> = emptyList(),
    val locationUiState: LocationUiState = LocationUiState(),
    val governments: List<String> = emptyList(),
    val cities: List<String> = emptyList(),
    val isGovernmentBottomSheetShowed: Boolean = false,
    val isCitiesBottomSheetShowed: Boolean = false,
    val customerName: String = "",
    val customerProfilePhotoUri: Uri? = null,
    val frontOfNationalIdUri: Uri? = null,
    val backOfNationalIdUri: Uri? = null,
    val workImagesUris: List<Uri>? = null,
    val workDescription: String = "",
    val accountButtonState: AccountButtonState = AccountButtonState(),
    val locationType: LocationBottomSheetContentType = LocationBottomSheetContentType.GOVERNMENT,
)

data class AccountButtonState(
    val userTypeButtonState: AppButtonState = AppButtonState.Disabled,
    val serviceButtonState: AppButtonState = AppButtonState.Disabled,
    val profileButtonState: AppButtonState = AppButtonState.Disabled,
    val locationButtonState: AppButtonState = AppButtonState.Disabled,
    val workShowCaseButtonState: AppButtonState = AppButtonState.Disabled,
    val verifyIdentityButtonState: AppButtonState = AppButtonState.Disabled,
)

data class ServiceUiState(
    val id: String = "",
    val serviceTitle: String = "",
    val serviceDescription: String = "",
    val serviceImage: String = "",
    val isSelected: Boolean = false,
)

data class LocationUiState(
    val government: String = "",
    val city: String = "",
    val addressInDetails: String = "",
)

fun LocationUiState.toEntity(): Location {
    return Location(
        government = this.government,
        cityName = this.city,
        addressInDetails = this.addressInDetails
    )
}

enum class UserType() {
    CUSTOMER,
    CRAFTSMAN
}
