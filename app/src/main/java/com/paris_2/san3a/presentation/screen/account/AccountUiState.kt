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
    val verifyIdentityButtonState: AppButtonState = AppButtonState.Disabled,
)

data class ServiceUiState(
    val id: String = "",
    val serviceTitle: String = "",
    val serviceDescription: String = "",
    val isSelected: Boolean = false,
)

data class LocationUiState(
    val government: Governorate? = null,
    val city: City? = null,
    val addressInDetails: String = "",
)

fun LocationUiState.toEntity(): Location {
    return Location(
        government = this.government?.name ?: "", //TODO
        cityName = this.city?.name ?: "",
        addressInDetails = this.addressInDetails
    )
}

fun Location.toUiState(): LocationUiState { //TODO
//    return LocationUiState(
//        government = this.government,
//        city = this.cityName,
//        addressInDetails = this.addressInDetails
//    )
    return LocationUiState(
        government = Governorate(id = 0, name = this.government),
        city = City(id = 0, name = this.cityName, governorateId = 0),
        addressInDetails = this.addressInDetails
    )
}

enum class UserType() {
    CUSTOMER,
    CRAFTSMAN
}
