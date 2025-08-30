package com.paris_2.san3a.presentation.screen.home.customer

import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.domain.entity.RequestService
import com.paris_2.san3a.domain.entity.RequestStatus
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.notification.GetUnReadNotificationsCountUseCase
import com.paris_2.san3a.domain.usecase.requests.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.services.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.services.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.domain.usecase.user.CustomizeProfileSettingsUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetSortedServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.utill.getCurrentDateTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
    private val getSortedServicesUseCase: GetSortedServicesUseCase,
    private val customizeProfileSettingsUseCase: CustomizeProfileSettingsUseCase,
) : CustomerHomeInteractionListener, BaseViewModel<CustomerHomeUiState>(CustomerHomeUiState()) {

    private val _triggerVoiceSearch = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val triggerVoiceSearch: SharedFlow<Unit> = _triggerVoiceSearch

    init {
        getCurrentLanguage()
        loadUserData()
        loadMostRequestedServices()
        loadServices()
        getGovernments()
    }

    private fun getCurrentLanguage() {
        tryToObserve(
            observe = { customizeProfileSettingsUseCase.getLatestSelectedAppLanguage() },
            onEach = { language ->
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            currentLanguage = language ?: DEFAULT_LANGUAGE
                        )
                    )
                )
            },
            onError = ::onError
        )
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
                    bottomSheetSelectedGovernorate = screenState.value.customerUiState.governorate,
                    bottomSheetSelectedCity = screenState.value.customerUiState.city,
                    bottomSheetAddressDetails = screenState.value.customerUiState.addressDetails,
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
        if (description.length > 100) return
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
            onSuccess = ::onGetGovernorateByIdSuccess,
            onError = ::onError
        )
    }

    private fun onGetGovernorateByIdSuccess(government: Governorate?) {
        government?.let {
            updateState(
                screenState.value.copy(
                    bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                        bottomSheetSelectedGovernorate = government,
                        bottomSheetSelectedCity = null,
                    )
                )
            )
            getCities(government.id)
        }
    }

    override fun setBottomSheetSelectedCity(cityId: Int) {
        tryToExecute(
            execute = { getLocationInfoUseCase.getCityById(cityId) },
            onSuccess = ::onGetCityByIdSuccess,
            onError = ::onError
        )
    }

    private fun onGetCityByIdSuccess(city: City?) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetSelectedCity = city,
                    isGovernmentSheetVisible = false
                )
            )
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
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernorate = null,
                    bottomSheetSelectedCity = null,
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

    var sendRequestJob: Job? = null
    override fun createRequest() {
        if (screenState.value.bottomSheetUiState.bottomSheetSelectedGovernorate == null
            || screenState.value.bottomSheetUiState.bottomSheetSelectedCity == null
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
        if (screenState.value.bottomSheetUiState.bottomSheetService == null) {
            updateState(
                screenState.value.copy(
                    errorMessage = "Please select a service",
                    buttonSheetState = AppButtonState.Enable,
                    showSnackBarError = true
                )
            )
            hideSnackBar()
            return
        }

        sendRequestJob = tryToExecute(
            execute = {
                updateState(screenState.value.copy(buttonSheetState = AppButtonState.Loading))
                requestServicesUseCase(
                    RequestService(
                        id = "",
                        title = screenState.value.bottomSheetUiState.bottomSheetSubtitle,
                        description = screenState.value.bottomSheetUiState.bottomSheetDescription,
                        governorateId = screenState.value.bottomSheetUiState.bottomSheetSelectedGovernorate?.id
                            ?: 0,
                        cityId = screenState.value.bottomSheetUiState.bottomSheetSelectedCity?.id
                            ?: 0,
                        locationDetails = screenState.value.bottomSheetUiState.bottomSheetAddressDetails,
                        image = screenState.value.bottomSheetUiState.bottomSheetImages,
                        userId = screenState.value.customerUiState.id,
                        selectedCraftsmanId = null,
                        time = getCurrentDateTime(),
                        requestStatus = RequestStatus.ONGOING,
                        serviceId = screenState.value.bottomSheetUiState.bottomSheetService?.id
                            ?: return@tryToExecute,
                    )
                )
            },
            onSuccess = {
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = BottomSheetUiState(
                            bottomSheetState = false
                        ),
                        buttonSheetState = AppButtonState.Enable,
                        showSnackBarSuccess = true,
                    )
                )
                hideSnackBar()
                updateNumOfRequests(
                    screenState.value.bottomSheetUiState.bottomSheetService?.id
                        ?: return@tryToExecute
                )
            },
            onError = {
                updateState(
                    screenState.value.copy(
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
            onError = ::onError
        )
    }

    private fun getGovernments() {
        tryToExecute(
            execute = { getLocationInfoUseCase.getGovernments() },
            onSuccess = ::onGetGovernmentsSuccess,
            onError = ::onError,
        )
    }

    private fun onGetGovernmentsSuccess(governments: List<Governorate>) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetGovernments = governments,
                    bottomSheetSelectedGovernorate = null,
                    locationBottomSheetType = LocationBottomSheetContentType.GOVERNMENT,
                ),
            )
        )
    }

    private fun getCities(governmentId: Int) {
        tryToExecute(
            execute = { getLocationInfoUseCase.getCities(governorateId = governmentId) },
            onSuccess = ::onGetCitiesSuccess,
            onError = ::onError
        )
    }

    private fun onGetCitiesSuccess(cities: List<City>) {
        if (cities.isNotEmpty()) {
            updateState(
                screenState.value.copy(
                    bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                        bottomSheetCities = cities,
                        bottomSheetSelectedCity = null,
                        locationBottomSheetType = LocationBottomSheetContentType.CITY,
                    ),
                )
            )
        }
    }

    private fun loadServices() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isFindWhatYouNeedLoading = true
                    )
                )
                getSortedServicesUseCase(
                    phoneNumber = getPhoneNumberUseCase(),
                    isCraftsman = false
                )
            },
            onSuccess = ::onLoadServicesSuccess,
            onError = ::onError
        )
    }

    private suspend fun onLoadServicesSuccess(services: Flow<List<Service>>) {
        services.collect { userServices ->
            updateState(
                screenState.value.copy(
                    customerUiState = screenState.value.customerUiState.copy(
                        services = userServices,
                    ),
                    isFindWhatYouNeedLoading = false
                )
            )
        }
    }

    private fun loadMostRequestedServices() {
        tryToObserve(
            observe = {
                updateState(
                    screenState.value.copy(
                        isRequestLoading = true
                    )
                )
                getMostRequestedServicesUseCase.invoke()
                      },
            onEach = { mostRequestedServices ->
                updateState(
                    screenState.value.copy(
                        customerUiState = screenState.value.customerUiState.copy(
                            mostRequestedServices = mostRequestedServices ?: emptyList(),
                        ),
                        isRequestLoading = false
                    )
                )
            },
            onError = ::onError
        )
    }

    private fun loadUserData() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        isUserDataLoading = true
                    )
                )
                getUserUseCase(getPhoneNumberUseCase())
                      },
            onSuccess = ::onLoadUserDataSuccess,
            onError = ::onError
        )
    }

    private suspend fun onLoadUserDataSuccess(user: User) {
        val governorate =
            getLocationInfoUseCase.getGovernorateById(user.location.governmentId)
        val city = getLocationInfoUseCase.getCityById(user.location.cityId)
        updateState(
            screenState.value.copy(
                customerUiState = screenState.value.customerUiState.copy(
                    id = user.id,
                    currentUserName = user.fullName,
                    governorate = governorate,
                    city = city,
                    addressDetails = user.location.addressInDetails,
                ),
            )
        )
        getNotificationsCount(user.id)
    }

    private fun getNotificationsCount(userId: String) {
        tryToObserve(
            observe = { getUnReadNotificationsCountUseCase(userId) },
            onEach = { count ->
                updateState(
                    screenState.value.copy(
                        notificationsCount = count ?: 0,
                        isUserDataLoading = false
                    )
                )
            },
            onError = ::onError,
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
                service.title.contains(
                    searchQuery,
                    ignoreCase = true
                ) || service.description.contains(searchQuery, ignoreCase = true)
                        || service.suggestions.any { suggestion ->
                    suggestion.contains(searchQuery, ignoreCase = true)
                } || service.hint.contains(searchQuery, ignoreCase = true)
            }
        }
        updateState(
            screenState.value.copy(
                customerUiState = screenState.value.customerUiState.copy(
                    searchQuery = query,
                    searchResults = results,
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
        sendRequestJob?.cancel()
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

    override fun onRetry() {
        updateState(
            screenState.value.copy(
                errorMessage = null,
                isUserDataLoading = false,
                isFindWhatYouNeedLoading = false,
                isRequestLoading = false
            )
        )
        loadUserData()
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

    private fun onError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                errorMessage = throwable.message ?: UNKNOWN_ERROR,
                isUserDataLoading = false,
                isFindWhatYouNeedLoading = false,
                isRequestLoading = false
            )
        )
    }

    private companion object {
        const val UNKNOWN_ERROR = "Unknown Error"
        const val DEFAULT_LANGUAGE = "en"
    }
}