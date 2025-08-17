package com.paris_2.san3a.presentation.screen.home.customer

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CustomerHomeViewModel(
    private val getMostRequestedServicesUseCase: GetMostRequestedServicesUseCase,
    private val requestServicesUseCase: RequestServiceUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val updateNumOfRequestsUseCase: UpdateNumOfRequestsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUnReadNotificationsCountUseCase: GetUnReadNotificationsCountUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getCustomerServiceUseCase: GetUserServicesUseCase,
) : CustomerHomeInteractionListener, BaseViewModel<CustomerHomeUiState>(CustomerHomeUiState()) {

    private val _triggerVoiceSearch = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val triggerVoiceSearch: SharedFlow<Unit> = _triggerVoiceSearch

    init {
        loadUserData()
        loadMostRequestedServices()
        loadServices()
        getGovernments()
    }

    override fun initBottomSheet(service: Service) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetState = true,
                    bottomSheetStep = BottomSheetStep.SELECT_SERVICE,
                    bottomSheetService = service,
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernmentName = "",
                    bottomSheetSelectedCityName = "",
                    bottomSheetAddressDetails = "",
                    isGovernmentSheetVisible = false,
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

        val filteredNewImages = newImages.filter {
            !screenState.value.bottomSheetUiState.bottomSheetImages.contains(it)
        }

        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetImages = screenState.value.bottomSheetUiState.bottomSheetImages + filteredNewImages
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

    override fun setBottomSheetSelectedGovernment(governmentId: Int) {
        tryToExecute(
            execute = { getLocationInfoUseCase.getGovernorateById(governmentId) },
            onSuccess = { government ->
                government?.let {
                    updateState(
                        screenState.value.copy(
                            bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                                bottomSheetSelectedGovernmentName = government.name,
                                bottomSheetSelectedGovernmentId = government.id,
                                bottomSheetSelectedCityName = "",
                                bottomSheetSelectedCityId = null,
                            )
                        )
                    )
                    getCities(government.id)
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
        )
    }

    override fun setBottomSheetSelectedCity(cityId: Int) {
        tryToExecute(
            execute = { getLocationInfoUseCase.getCityById(cityId) },
            onSuccess = { city ->
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetSelectedCityName = city?.name.orEmpty(),
                            bottomSheetSelectedCityId = city?.id,
                            isGovernmentSheetVisible = false
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
        )
    }

    override fun showGovernmentSheet(show: Boolean) {
        val currentGovernments = screenState.value.bottomSheetUiState.bottomSheetGovernments
        if (show && currentGovernments.isEmpty()) {
            getGovernments()
        }

        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    isGovernmentSheetVisible = show,
                    locationBottomSheetType = LocationBottomSheetContentType.GOVERNMENT
                )
            )
        )
    }

    override fun resetBottomSheetState() {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetStep = BottomSheetStep.SELECT_SERVICE,
                    bottomSheetService = null,
                    bottomSheetSubtitle = "",
                    bottomSheetServiceId = "",
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernmentName = "",
                    bottomSheetSelectedCityName = "",
                    bottomSheetAddressDetails = "",
                    isGovernmentSheetVisible = false,
                )
            )
        )
    }

    override fun onMicClick() {
        _triggerVoiceSearch.tryEmit(Unit)
    }

    override fun onSpeechRecognized(query: String) {
        onSearch(query)
    }

    override fun createRequest() {
        if (screenState.value.bottomSheetUiState.bottomSheetSelectedGovernmentId == null
            || screenState.value.bottomSheetUiState.bottomSheetSelectedCityId == null
        ) {
            updateState(
                screenState.value.copy(
                    errorMessage = "Please select a government and city",
                    buttonSheetState = AppButtonState.Enable,
                    showSnackBarError = true
                )
            )
            hideSnackBar()
            return
        }

        val request = RequestServiceUiState(
            serviceType = screenState.value.bottomSheetUiState.bottomSheetService?.title.orEmpty(), //TODO: replace it with id
            title = screenState.value.bottomSheetUiState.bottomSheetSubtitle,
            description = screenState.value.bottomSheetUiState.bottomSheetDescription,
            governorateId = screenState.value.bottomSheetUiState.bottomSheetSelectedGovernmentId
                ?: 0,
            cityId = screenState.value.bottomSheetUiState.bottomSheetSelectedCityId ?: 0,
            locationDetails = screenState.value.bottomSheetUiState.bottomSheetAddressDetails,
            image = screenState.value.bottomSheetUiState.bottomSheetImages,
            userId = screenState.value.customerUiState.id,
            serviceId = screenState.value.bottomSheetUiState.bottomSheetServiceId,
        )
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        buttonSheetState = AppButtonState.Loading
                    )
                )
                requestServicesUseCase(request.toRequestService())
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetState = false
                        ),
                        buttonSheetState = AppButtonState.Enable,
                        showSnackBarSuccess = true,

                        )
                )
                hideSnackBar()
                updateNumOfRequests(request.serviceId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR,
                        buttonSheetState = AppButtonState.Enable,
                        showSnackBarError = true
                    )
                )
                hideSnackBar()
            }
        )
    }

    override fun updateNumOfRequests(serviceId: String) {
        tryToExecute(
            execute = { updateNumOfRequestsUseCase(serviceId) },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
        )
    }

    private fun getGovernments() {
        tryToExecute(
            execute = { getLocationInfoUseCase.getGovernments() },
            onSuccess = { governments ->
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetGovernments = governments,
                            bottomSheetSelectedGovernmentName = "",
                            locationBottomSheetType = LocationBottomSheetContentType.GOVERNMENT,
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

    private fun getCities(governmentId: Int) {
        tryToExecute(
            execute = {
                getLocationInfoUseCase.getCities(
                    governorateId = governmentId
                )
            },
            onSuccess = { cities ->
                if (cities.isNotEmpty()) {
                    updateState(
                        screenState.value.copy(
                            bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                                bottomSheetCities = cities,
                                bottomSheetSelectedCityName = "",
                                locationBottomSheetType = LocationBottomSheetContentType.CITY,
                            ),
                        )
                    )
                }
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
    }

    private fun loadServices() {
        tryToExecute(
            execute = {
                getCustomerServiceUseCase(
                    phoneNumber = getPhoneNumberUseCase(),
                    isCraftsman = false
                )
            },
            onSuccess = { services ->
                services.collect { userServices ->
                    updateState(
                        screenState.value.copy(
                            customerUiState = screenState.value.customerUiState.copy(
                                services = userServices
                            )
                        )
                    )
                }
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
        )
    }

    private fun loadMostRequestedServices() {
        tryToObserve(
            observe = getMostRequestedServicesUseCase::invoke,
            onEach = { mostRequestedServices ->
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            mostRequestedServices = mostRequestedServices ?: emptyList()
                        )
                    )
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
        )
    }

    private fun loadUserData() {
        tryToExecute(
            execute = { getUserUseCase(getPhoneNumberUseCase()) },
            onSuccess = {
                val governorate =
                    getLocationInfoUseCase.getGovernorateById(it.location.governmentId)
                val city = getLocationInfoUseCase.getCityById(it.location.cityId)
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            id = it.id,
                            currentUserName = it.fullName,
                            government = governorate?.name.orEmpty(),
                            city = city?.name.orEmpty(),
                        )
                    )
                )
                getNotificationsCount(it.id)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
        )
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = {
                getUnReadNotificationsCountUseCase(userId)
            },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        notificationsCount = count ?: 0,
                    )
                )
            },
            onError = { exception ->
                Log.e(
                    "MessagesViewModel",
                    "Error fetching notifications count: ${exception.message}"
                )
            },
        )
    }


    override fun onNotificationClick() {
        navigate(Destinations.Notification)
    }

    override fun onSearch(query: String) {
        val allServices = screenState.value.customerUiState.services
        val searchQuery = query.trim().lowercase()

        val results = if (searchQuery.isEmpty()) {
            emptyList()
        } else {
            allServices.filter { service ->
                service.title.contains(searchQuery) || service.description.contains(searchQuery)
            }
        }
        updateState(
            screenState.value.copy(
                customerUiState = screenState.value.customerUiState.copy(
                    searchQuery = query,
                    searchResults = results
                )
            )
        )
    }

    override fun onServiceClick(service: Service) {
        initBottomSheet(service)
    }

    override fun onBecomeCraftsmanClick() {
        LocalAccountType.value = AccountType.CRAFTSMAN
        navigate(Destinations.CraftManGraph)
    }

    override fun onDismissBottomSheet() {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetState = false
                )
            )
        )
    }

    override fun onDismissSnackBar() {
        updateState(
            screenState.value.copy(
                showSnackBarError = false,
                showSnackBarSuccess = false,
                errorMessage = null,
                successSnackBarMessage = null
            )
        )
    }

    private fun hideSnackBar() {
        viewModelScope.launch {
            if (screenState.value.showSnackBarError || screenState.value.showSnackBarSuccess) {
                delay(3000)
                updateState(
                    screenState.value.copy(
                        showSnackBarError = false,
                        showSnackBarSuccess = false
                    )
                )
            }
        }
    }


    companion object {
        const val UNKNOWN_ERROR = "Unknown Error"
    }
}
