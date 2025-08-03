package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.Location

data class AccountScreenUiState(
    val accountUiState: AccountUiState = AccountUiState(),
    val isLoading: Boolean = false,
    val errorMassage: String? = "",
)

data class AccountUiState(
    val pageNumber: String = "",
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
)

data class ServiceUiState(
    val id: String = "",
    val serviceTitle: String = "",
    val serviceDescription: String = "",
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
    )
}

enum class UserType(val displayActor: Int) {
    CUSTOMER(R.string.customer),
    CRAFTSMAN(R.string.craftman)
}
