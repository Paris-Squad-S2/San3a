package com.paris_2.san3a.presentation.screen.more.locationScreen
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.presentation.screen.more.components.SelectionItemData
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.UiText

data class LocationScreenState(
    val locationUiState: LocationUiState = LocationUiState(),
    val isLoading: Boolean = false,
    val isNoInternet: Boolean = false,
    val showSnackBarError: Boolean = false,
    val showSnackBarSuccess: Boolean = false,
    val isGovernorateSheetVisible: Boolean = false,
    val isStreetSheetVisible: Boolean = false,
    val errorMessage: UiText? = null,
    val successMessageSnackBar: UiText? = null,
    val locationButtonState: AppButtonState = AppButtonState.Enable
)

data class LocationUiState(
    val governorates: List<Governorate> = emptyList(),
    val selectedGovernorateName: String = "",
    val selectedGovernorateId: Int? = null,
    val cities: List<City> = emptyList(),
    val selectedCityName: String = "",
    val selectedCityId: Int? = null,
    val activeBottomSheet: LocationBottomSheetType? = null,
)

data class BottomSheetContent(
    val title: String,
    val items: List<SelectionItemData>,
    val onClick: (Int) -> Unit
)

enum class LocationBottomSheetType {
      GOVERNORATE, CITY
}
