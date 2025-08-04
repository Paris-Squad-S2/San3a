package com.paris_2.san3a.presentation.screen.home.customer

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.home.utils.getResource
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class CustomerHomeViewModel(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val getMostRequestedServicesUseCase: GetMostRequestedServicesUseCase,
    private val requestServicesUseCase: RequestServiceUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val updateNumOfRequestsUseCase: UpdateNumOfRequestsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
) : CustomerHomeInteractionListener, BaseViewModel<CustomerHomeUiState>(CustomerHomeUiState()) {

    init {
        loadUserData()
        loadMostRequestedServices()
        loadServices()
        getGovernments()
    }

    override fun initBottomSheet(serviceTitle: String, serviceId: String, iconRes: Int) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = BottomSheetUiState(
                    bottomSheetState = true,
                    bottomSheetStep = BottomSheetStep.SELECT_SERVICE,
                    bottomSheetServiceTitle = serviceTitle,
                    bottomSheetServiceId = serviceId,
                    bottomSheetIconRes = iconRes,
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernment = "",
                    bottomSheetSelectedCity = "",
                    bottomSheetAddressDetails = "",
                    isGovernmentSheetVisible = false,
                    isCitySheetVisible = false
                )
            )
        )
    }

    override fun updateBottomSheetStep(step: BottomSheetStep) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetStep = step
                )
            )
        )
    }

    override fun nextBottomSheetStep() {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetStep = when (screenState.value.bottomSheetUiState.bottomSheetStep) {
                        BottomSheetStep.SELECT_SERVICE -> BottomSheetStep.PROBLEM_DESCRIPTION
                        BottomSheetStep.PROBLEM_DESCRIPTION -> BottomSheetStep.SELECT_LOCATION
                        BottomSheetStep.SELECT_LOCATION -> BottomSheetStep.IMAGE_UPLOAD
                        BottomSheetStep.IMAGE_UPLOAD -> BottomSheetStep.IMAGE_UPLOAD
                    }
                )
            )
        )
    }

    override fun previousBottomSheetStep() {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetStep = when (screenState.value.bottomSheetUiState.bottomSheetStep) {
                        BottomSheetStep.IMAGE_UPLOAD -> BottomSheetStep.SELECT_LOCATION
                        BottomSheetStep.SELECT_LOCATION -> BottomSheetStep.PROBLEM_DESCRIPTION
                        BottomSheetStep.PROBLEM_DESCRIPTION -> BottomSheetStep.SELECT_SERVICE
                        BottomSheetStep.SELECT_SERVICE -> BottomSheetStep.SELECT_SERVICE
                    }
                )
            )
        )
    }

    override fun setBottomSheetServiceSubTitle(title: String) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetSubtitle = title
                )
            )
        )
    }

    override fun setBottomSheetDescription(description: String) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetDescription = description
                )
            )
        )
    }

    override fun setBottomSheetSelectedSuggestion(suggestion: String?) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetSelectedSuggestion = suggestion,
                )
            )
        )
    }

    override fun addBottomSheetImages(newImages: List<String>) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetImages = screenState.value.bottomSheetUiState.bottomSheetImages + newImages
                )
            )
        )
    }

    override fun deleteBottomSheetImageAt(index: Int) {
        val images = screenState.value.bottomSheetUiState.bottomSheetImages.toMutableList()
        if (index in images.indices) images.removeAt(index)
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetImages = images
                )
            )
        )
    }

    override fun setBottomSheetAddressDetails(address: String) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetAddressDetails = address
                )
            )
        )
    }

    override fun setBottomSheetSelectedGovernment(government: String) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetSelectedGovernment = government,
                    isGovernmentSheetVisible = false
                )
            )
        )
        getCities(government)
    }

    override fun setBottomSheetSelectedCity(city: String) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetSelectedCity = city,
                    isCitySheetVisible = false
                )
            )
        )
    }

    override fun showGovernmentSheet(show: Boolean) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    isGovernmentSheetVisible = show
                )
            )
        )
    }

    override fun showCitySheet(show: Boolean) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    isCitySheetVisible = show
                )
            )
        )
    }

    override fun resetBottomSheetState() {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = BottomSheetUiState(
                    bottomSheetStep = BottomSheetStep.SELECT_SERVICE,
                    bottomSheetServiceTitle = "",
                    bottomSheetSubtitle = "",
                    bottomSheetServiceId = "",
                    bottomSheetIconRes = 0,
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernment = "",
                    bottomSheetSelectedCity = "",
                    bottomSheetAddressDetails = "",
                    isGovernmentSheetVisible = false,
                    isCitySheetVisible = false
                )
            )
        )
    }

    override fun createRequest(service: RequestServiceUiState, serviceId: String) {
        tryToExecute(
            execute = {
                requestServicesUseCase(service.toRequestService())
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetState = false
                        )
                    )
                )
                updateNumOfRequests(serviceId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
                Log.e("CustomerHomeViewModel", it.message ?: "Unknown Error")
            }
        )
    }

    override fun updateNumOfRequests(serviceId: String) {
        tryToExecute(
            execute = { updateNumOfRequestsUseCase(serviceId) },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
                Log.e("CustomerHomeViewModel", it.message ?: "Unknown Error")
            }
        )
    }

    private fun getGovernments() {
        tryToExecute(
            execute = { getLocationInfoUseCase.getGovernments(countryName = "Egypt") },
            onSuccess = { governments ->
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetGovernments = governments.names,
                            bottomSheetSelectedGovernment = governments.names.firstOrNull() ?: ""
                        ),
                        customerUiState = screenState.value.customerUiState.copy(
                            locationUiState = screenState.value.customerUiState.locationUiState.copy(
                                government = governments.names.firstOrNull() ?: ""
                            ),
                        ),
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage.message,
                        isLoading = false
                    )
                )
            },
        )
    }

    private fun getCities(stateName: String) {
        tryToExecute(
            execute = { getLocationInfoUseCase.getCities(countryName = "Egypt", stateName = stateName) },
            onSuccess = { cities ->
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetCities = cities.names,
                            bottomSheetSelectedCity = cities.names.firstOrNull() ?: "",
                            isCitySheetVisible = true
                        ),
                        customerUiState = screenState.value.customerUiState.copy(
                            locationUiState = screenState.value.customerUiState.locationUiState.copy(
                                city = cities.names.firstOrNull() ?: ""
                            ),
                        ),
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMessage = errorMessage.message,
                        isLoading = false
                    )
                )
            }
        )
//        viewModelScope.launch {
//            val cities = getLocationInfoUseCase.getCities(
//                countryName = "Egypt",
//                stateName = stateName
//            )
//
//            updateState(
//                screenState.value.copy(
//                    customerUiState = screenState.value.customerUiState.copy(
//                        locationUiState = screenState.value.customerUiState.locationUiState.copy(
//                            cities = cities.names
//                        ),
//                    ),
//                    bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
//                        isCitySheetVisible = true
//                    )
//                )
//            )
//        }
    }

    private fun loadServices() {
        tryToExecute(
            execute = getAllServicesUseCase::invoke,
            onSuccess = { services ->
                services.collect {
                    updateState(
                        screenState.value.copy(
                            customerUiState = screenState.value.customerUiState.copy(
                                services = it
                            )
                        )
                    )
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun loadMostRequestedServices() {
        tryToExecute(
            execute = getMostRequestedServicesUseCase::invoke,
            onSuccess = { mostRequestedServices ->
                mostRequestedServices.collect {
                    updateState(
                        screenState.value.copy(
                            customerUiState = screenState.value.customerUiState.copy(
                                mostRequestedServices = it
                            )
                        )
                    )
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
            }
        )
    }

    private fun loadUserData() {
        tryToExecute(
            execute = { getUserUseCase(getPhoneNumberUseCase()) },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            id = it.id,
                            currentUserName = it.fullName,
                            government = it.location.government,
                            city = it.location.cityName,
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: "Unknown Error"
                    )
                )
                Log.e("CustomerHomeViewModel", it.message ?: "Unknown Error")
            }
        )
    }

    override fun onNotificationClick() {
        navigate(Destinations.Notifications)
    }

    override fun onSearch(query: String) {}

    override fun onServiceClick(serviceId: String) {
        val selectedService = screenState.value.customerUiState.services.find { it.id == serviceId }
        val isArabic = Locale.getDefault().language == "ar"
        val serviceTitle = if (isArabic) {
            selectedService?.title?.get("arabicName")
        } else {
            selectedService?.title?.get("englishName")
        } ?: ""
        val iconRes = getResource(serviceId)
        initBottomSheet(serviceTitle, serviceId, iconRes)
    }

    override fun onBecomeCraftsmanClick() {
        navigate(Destinations.More)
    }

    override fun onDismissBottomSheet() {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetState = false
                )
            )
        )
        resetBottomSheetState()
    }
}