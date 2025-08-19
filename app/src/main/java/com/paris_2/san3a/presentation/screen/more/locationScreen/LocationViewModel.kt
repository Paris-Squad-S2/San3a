package com.paris_2.san3a.presentation.screen.more.locationScreen

import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.Location
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                getLocationInfoUseCase.getGovernments()
            },

            onSuccess = { result ->
                updateState(
                    screenState.value.copy(
                        isLoading = false, locationUiState = screenState.value.locationUiState.copy(
                            governorates = result
                        )
                    )
                )
            },
            onError = {
                updateState(screenState.value.copy(isLoading = false, isNoInternet = true))
            },
        )
    }

    private fun fetchCities(governorateId: Int) {
        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = {
                getLocationInfoUseCase.getCities(governorateId)
            },
            onSuccess = { cities ->
                updateState(
                    screenState.value.copy(
                        isLoading = false, locationUiState = screenState.value.locationUiState.copy(
                            cities = cities
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

        if (uiState.selectedGovernorateId == null || uiState.selectedCityId == null) {
            updateState(
                screenState.value.copy(
                    showSnackBarError = true, errorMessage = R.string.please_select_location
                )
            )
            hideSnackBar()
            return
        }

        updateState(
            screenState.value.copy(
                locationButtonState = AppButtonState.Loading
            )
        )

        tryToExecute(execute = {
            val phone = getPhoneNumberUseCase()
            val location = Location(
                governmentId = uiState.selectedGovernorateId,
                cityId = uiState.selectedCityId,
                addressInDetails = "${uiState.selectedGovernorateName}, ${uiState.selectedCityName}"
            )
            setUpAccountUseCase.saveLocation(phone, location)
        }, onSuccess = {
            updateState(
                screenState.value.copy(
                    showSnackBarSuccess = true,
                    locationButtonState = AppButtonState.Enable,
                    successMessageSnackBar = R.string.success_location_saved
                )
            )
            hideSnackBar()
            navigateUp()
        }, onError = {
            updateState(
                screenState.value.copy(
                    showSnackBarError = true,
                    locationButtonState = AppButtonState.Enable,
                    errorMessage = R.string.some_error_happened
                )
            )
            hideSnackBar()
        })
    }

    override fun onClickRetry() {
        updateState(screenState.value.copy(isNoInternet = false))
        fetchGovernorates()
    }

    override fun onNavigateBack() {
        navigateUp()
    }

    override fun onGovernorateSelected(governorateId: Int) {
        tryToExecute(execute = {
            getLocationInfoUseCase.getGovernorateById(governorateId)
        }, onSuccess = { governorate ->
            updateState(
                screenState.value.copy(
                    locationUiState = screenState.value.locationUiState.copy(
                        selectedGovernorateName = governorate?.name.orEmpty(),
                        selectedGovernorateId = governorateId,
                        selectedCityName = "",
                        selectedCityId = null,
                        cities = emptyList(),
                        activeBottomSheet = LocationBottomSheetType.CITY,
                    ),
                    locationButtonState = AppButtonState.Disabled,
                )
            )
            fetchCities(governorateId)
        }, onError = {
            updateState(
                screenState.value.copy(
                    showSnackBarError = true,
                    errorMessage = R.string.some_error_happened,
                )
            )
            hideSnackBar()
        })
    }

    override fun onCityChanged(cityId: Int) {
        tryToExecute(execute = {
            getLocationInfoUseCase.getCityById(cityId)
        }, onSuccess = { city ->
            updateState(
                screenState.value.copy(
                    locationUiState = screenState.value.locationUiState.copy(
                        selectedCityName = city?.name.orEmpty(),
                        selectedCityId = cityId,
                        activeBottomSheet = null,
                    ),
                    locationButtonState = AppButtonState.Enable,
                )
            )
        }, onError = {
            updateState(
                screenState.value.copy(
                    showSnackBarError = true,
                    errorMessage = R.string.some_error_happened,
                )
            )
            hideSnackBar()
        })
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
        tryToExecute(execute = {
            val phone = getPhoneNumberUseCase()
            getUserUseCase(phone)
        }, onSuccess = { user ->
            val location = user.location
            val governorate =
                getLocationInfoUseCase.getGovernorateById(governorateId = location.governmentId)
            val city = getLocationInfoUseCase.getCityById(cityId = location.cityId)
            updateState(
                screenState.value.copy(
                    locationUiState = screenState.value.locationUiState.copy(
                        selectedGovernorateName = governorate?.name.orEmpty(),
                        selectedGovernorateId = governorate?.id,
                        selectedCityName = city?.name.orEmpty(),
                        selectedCityId = city?.id,
                    )
                )
            )
            fetchCities(governorate?.id ?: 0)
        }, onError = {
            updateState(screenState.value.copy(isNoInternet = true))
        })
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBarError || screenState.value.showSnackBarSuccess) {
                delay(3000)
                updateState(
                    screenState.value.copy(
                        showSnackBarError = false, showSnackBarSuccess = false
                    )
                )
            }
        }
    }


}
