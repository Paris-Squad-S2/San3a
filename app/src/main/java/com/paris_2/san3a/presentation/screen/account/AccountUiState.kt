package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
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
    val governments: List<Governorate> = emptyList(),
    val cities: List<City> = emptyList(),
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
    val verifyLaterButtonState: AppButtonState = AppButtonState.Enable,
    val verifyIdentityButtonState: AppButtonState = AppButtonState.Disabled,
)

data class ServiceUiState(
    val id: String = "",
    val serviceTitle: String = "",
    val serviceDescription: String = "",
    val serviceImage: String = "",
    val colorCode: String = "#FF4C8FD3",
    val isSelected: Boolean = false,
)

data class LocationUiState(
    val governorate: Governorate? = null,
    val city: City? = null,
    val addressInDetails: String = "",
)

fun LocationUiState.toEntity(): Location {
    return Location(
        governmentId = this.governorate?.id ?: throw IllegalArgumentException("Government cannot be null"),
        cityId = this.city?.id ?: throw IllegalArgumentException("City cannot be null"),
        addressInDetails = this.addressInDetails
    )
}

fun Location.toUiState(governorate: Governorate?, city: City?): LocationUiState {
    return LocationUiState(
        governorate = governorate,
        city = city,
        addressInDetails = this.addressInDetails
    )
}

enum class UserType() {
    CUSTOMER,
    CRAFTSMAN
}
