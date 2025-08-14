package com.paris_2.san3a.presentation.screen.home.customer

import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetMostRequestedServicesUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.GetUserUseCase
import com.paris_2.san3a.domain.usecase.RequestServiceUseCase
import com.paris_2.san3a.domain.usecase.UpdateNumOfRequestsUseCase
import com.paris_2.san3a.presentation.LocalAccountType
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.Locale

class CustomerHomeViewModel(
    private val getMostRequestedServicesUseCase: GetMostRequestedServicesUseCase,
    private val requestServicesUseCase: RequestServiceUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val updateNumOfRequestsUseCase: UpdateNumOfRequestsUseCase,
    private val getUserUseCase: GetUserUseCase,
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

    override fun initBottomSheet(serviceTitle: String, serviceId: String) {
        val imageUrl = findServiceImageUrl(serviceId)
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetState = true,
                    bottomSheetStep = BottomSheetStep.SELECT_SERVICE,
                    bottomSheetServiceTitle = serviceTitle,
                    bottomSheetServiceId = serviceId,
                    bottomSheetServiceImageUrl = imageUrl,
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernment = "",
                    bottomSheetSelectedCity = "",
                    bottomSheetAddressDetails = "",
                    isGovernmentSheetVisible = false,
                )
            )
        )
    }

    private fun findServiceImageUrl(serviceId: String): String {
        val services = screenState.value.customerUiState.services
        val mostRequested = screenState.value.customerUiState.mostRequestedServices
        return (services + mostRequested).find { it.id == serviceId }?.imageUrl ?: ""
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

    override fun setBottomSheetSelectedGovernment(government: String) {
        updateState(
            screenState.value.copy(
                bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                    bottomSheetSelectedGovernment = government,
                    isGovernmentSheetVisible = true,
                    locationBottomSheetType = LocationBottomSheetContentType.CITY
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
                    isGovernmentSheetVisible = false,
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
                    bottomSheetServiceTitle = "",
                    bottomSheetSubtitle = "",
                    bottomSheetServiceId = "",
                    bottomSheetServiceImageUrl = "",
                    bottomSheetDescription = "",
                    bottomSheetImages = emptyList(),
                    bottomSheetSelectedSuggestion = null,
                    bottomSheetSelectedGovernment = "",
                    bottomSheetSelectedCity = "",
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

    override fun createRequest(service: RequestServiceUiState) {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        buttonSheetState = AppButtonState.Loading
                    )
                )
                requestServicesUseCase(service.toRequestService())
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
                updateNumOfRequests(service.serviceId)
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMessage = it.message ?: UNKNOWN_ERROR,
                        buttonSheetState = AppButtonState.Enable,
                        showSnackBarError = true
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
                            bottomSheetSelectedGovernment = ""
                        ),
                        customerUiState = screenState.value.customerUiState.copy(
                            locationUiState = screenState.value.customerUiState.locationUiState.copy(
                                government = ""
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
            execute = {
                getLocationInfoUseCase.getCities(
                    countryName = COUNTRY_NAME,
                    stateName = stateName
                )
            },
            onSuccess = { cities ->
                updateState(
                    screenState.value.copy(
                        bottomSheetUiState = screenState.value.bottomSheetUiState.copy(
                            bottomSheetCities = cities.names,
                            bottomSheetSelectedCity = "",
                        ),
                        customerUiState = screenState.value.customerUiState.copy(
                            locationUiState = screenState.value.customerUiState.locationUiState.copy(
                                city = ""
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
        initBottomSheet(serviceTitle, serviceId)
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


    companion object {
        const val ARABIC_NAME = "arabicName"
        const val ENGLISH_NAME = "englishName"
        const val ARABIC_DESCRIPTION = "arabicDescription"
        const val ENGLISH_DESCRIPTION = "englishDescription"
        const val ARABIC_LANGUAGE = "ar"
        const val UNKNOWN_ERROR = "Unknown Error"
        const val COUNTRY_NAME = "Egypt"
    }
}
