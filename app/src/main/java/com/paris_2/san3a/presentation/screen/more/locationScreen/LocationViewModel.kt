package com.paris_2.san3a.presentation.screen.more.locationScreen

import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class LocationViewModel(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getUserUseCase: GetUserUseCase,
) : BaseViewModel<LocationScreenState>(LocationScreenState()), LocationInteractionListener {

    init {
        fetchGovernorates()
        fetchUserLocation()
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
            updateState(
                screenState.value.copy(
                    showSnackBarError = true,
                    errorMessage = R.string.please_select_location
                )
            )
            return
        }

        updateState(
            screenState.value.copy(
                locationButtonState = AppButtonState.Loading
            )
        )

        tryToExecute(
            execute = {
                val phone = getPhoneNumberUseCase()
                val location = Location(
                    government = uiState.selectedGovernorate,
                    cityName = uiState.selectedStreet,
                    addressInDetails = "${uiState.selectedGovernorate},${uiState.selectedStreet}"
                )
                setUpAccountUseCase.saveLocation(phone, location)
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        showSnackBarSuccess = true,
                        locationButtonState = AppButtonState.Enable,
                        successMessageSnackBar = R.string.success_location_saved
                    )
                )
                navigateUp()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        showSnackBarError = true,
                        locationButtonState = AppButtonState.Enable,
                        errorMessage = R.string.some_error_happened
                    )
                )
            }
        )
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
                    activeBottomSheet = LocationBottomSheetType.CITY,
                ),
                locationButtonState = AppButtonState.Enable,
            )
        )
        fetchCities(area)
    }

    override fun onStreetChanged(street: String) {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    selectedStreet = street,
                    activeBottomSheet = null
                ),
                locationButtonState = AppButtonState.Enable,
            )
        )
    }

    override fun onShowBottomSheet(type: LocationBottomSheetType) {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    activeBottomSheet = type
                )
            )
        )
    }


    override fun onDismissBottomSheet() {
        updateState(
            screenState.value.copy(
                locationUiState = screenState.value.locationUiState.copy(
                    activeBottomSheet = null
                )
            )
        )
    }

    private fun fetchUserLocation() {
        tryToExecute(
            execute = {
                val phone = getPhoneNumberUseCase()
                getUserUseCase(phone)
            },
            onSuccess = { user ->
                val location = user.location
                updateState(
                    screenState.value.copy(
                        locationUiState = screenState.value.locationUiState.copy(
                            selectedGovernorate = location.government,
                            selectedStreet = location.cityName
                        )
                    )
                )
            },
            onError = {
                updateState(screenState.value.copy(isNoInternet = true))
            }
        )
    }


}
