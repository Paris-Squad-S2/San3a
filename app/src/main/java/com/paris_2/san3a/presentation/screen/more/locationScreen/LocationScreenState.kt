package com.paris_2.san3a.presentation.screen.more.locationScreen
import androidx.annotation.StringRes

data class LocationScreenState(
    val locationUiState: LocationUiState = LocationUiState(),
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val showSnackBarError: Boolean = false,
    val showSnackBarSuccess: Boolean = false,
    val isGovernorateSheetVisible: Boolean = false,
    val isStreetSheetVisible: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    @StringRes val successMessageSnackBar: Int? = null,
)

data class LocationUiState(
    val governorates: List<String> = emptyList(),
    val selectedGovernorate: String = "",
    val streets: List<String> = emptyList(),
    val selectedStreet: String = "",
    val activeBottomSheet: LocationBottomSheetType = LocationBottomSheetType.NONE
)

enum class LocationBottomSheetType {
    NONE, GOVERNORATE, STREET
}
