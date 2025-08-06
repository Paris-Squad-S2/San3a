package com.paris_2.san3a.presentation.screen.home.customer

import android.util.Log
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _triggerVoiceSearch = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val triggerVoiceSearch: SharedFlow<Unit> = _triggerVoiceSearch

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

    override fun onMicClick() {
        _triggerVoiceSearch.tryEmit(Unit)
    }

    override fun onSpeechRecognized(query: String) {
        onSearch(query)
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
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
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
            execute = { getLocationInfoUseCase.getGovernments(countryName = COUNTRY_NAME) },
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
            execute = { getLocationInfoUseCase.getCities(countryName = COUNTRY_NAME, stateName = stateName) },
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
                        errorMessage = it.message ?: UNKNOWN_ERROR
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
                        errorMessage = it.message ?: UNKNOWN_ERROR
                    )
                )
            }
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
                val titleEn = service.title[ENGLISH_NAME]?.lowercase() ?: ""
                val titleAr = service.title[ARABIC_NAME]?.lowercase() ?: ""
                val descEn = service.description[ENGLISH_DESCRIPTION]?.lowercase() ?: ""
                val descAr = service.description[ARABIC_DESCRIPTION]?.lowercase() ?: ""

                titleEn.contains(searchQuery) ||
                        titleAr.contains(searchQuery) ||
                        descEn.contains(searchQuery) ||
                        descAr.contains(searchQuery)
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

    override fun onServiceClick(serviceId: String) {
        val selectedService = screenState.value.customerUiState.services.find { it.id == serviceId }
        val isArabic = Locale.getDefault().language == ARABIC_LANGUAGE
        val serviceTitle = if (isArabic) {
            selectedService?.title?.get(ARABIC_NAME)
        } else {
            selectedService?.title?.get(ENGLISH_NAME)
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
companion object{
     const val ARABIC_NAME = "arabicName"
     const val ENGLISH_NAME = "englishName"
     const val ARABIC_DESCRIPTION = "arabicDescription"
     const val ENGLISH_DESCRIPTION = "englishDescription"
     const val ARABIC_LANGUAGE = "ar"
     const val UNKNOWN_ERROR = "Unknown Error"
     const val COUNTRY_NAME = "Egypt"
}
}
