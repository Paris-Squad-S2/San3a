package com.paris_2.san3a.presentation.screen.more.locationScreen

import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class LocationViewModel(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
) : BaseViewModel<LocationScreenState>(LocationScreenState()), LocationInteractionListener {

    init {
        fetchGovernorates()
    }

    private fun fetchGovernorates() {
        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = {
                getLocationInfoUseCase.getGovernments("Egypt")
            },

            onSuccess = { result ->
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        locationUiState = screenState.value.locationUiState.copy(
                            governorates = result.names
                        )
                    )
                )
            },
            onError = {
                updateState(screenState.value.copy(isLoading = false, isNoInternet = true))
            },
        )
    }

    private fun fetchCities(governorate: String) {
        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = {
                getLocationInfoUseCase.getCities("Egypt", governorate)
            },
            onSuccess = { cities ->
                updateState(
                    screenState.value.copy(
                        isLoading = false,
                        locationUiState = screenState.value.locationUiState.copy(
                            streets = cities.names
                        )
                    )
                )
            },
            onError = {
                updateState(screenState.value.copy(isLoading = false, isNoInternet = true))
            },

            )
    }

    override fun onClickSave() {
        val uiState = screenState.value.locationUiState
        if (uiState.selectedGovernorate.isEmpty() || uiState.selectedStreet.isEmpty()) {
            updateState(screenState.value.copy(showSnackBarError = true))
        } else {
            updateState(screenState.value.copy(showSnackBarSuccess = true))
        }
    }

    override fun onClickRetry() {
        updateState(screenState.value.copy(isNoInternet = false))
        fetchGovernorates()
    }

    override fun onNavigateBack() {
        navigateUp()
    }

    override fun onAreaSelected(area: String) {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    selectedGovernorate = area,
                    selectedStreet = "",
                    streets = emptyList(),
                    activeBottomSheet = LocationBottomSheetType.STREET
                )
            )
        )
        fetchCities(area)
    }

    override fun onStreetChanged(street: String) {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    selectedStreet = street,
                    activeBottomSheet = LocationBottomSheetType.NONE
                )
            )
        )
    }

    override fun onShowGovernorateBottomSheet() {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    activeBottomSheet = LocationBottomSheetType.GOVERNORATE
                )
            )
        )
    }

    override fun onShowStreetBottomSheet() {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    activeBottomSheet = LocationBottomSheetType.STREET
                )
            )
        )
    }

    override fun onDismissBottomSheet() {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    activeBottomSheet = LocationBottomSheetType.NONE
                )
            )
        )
    }


}
