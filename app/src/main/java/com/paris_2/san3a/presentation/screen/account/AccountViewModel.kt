package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import androidx.navigation.toRoute
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.AccountSetupStep
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.City
import com.paris_2.san3a.domain.entity.Governorate
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.entity.User
import com.paris_2.san3a.domain.usecase.location.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.services.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserServicesUseCase
import com.paris_2.san3a.domain.usecase.user.GetUserUseCase
import com.paris_2.san3a.domain.usecase.user.SetUpAccountUseCase
import com.paris_2.san3a.presentation.mapper.mapServiceToUiState
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.screen.account.components.LocationBottomSheetContentType
import com.paris_2.san3a.presentation.shared.components.AppButtonState
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.shared.utils.UiText
import kotlinx.coroutines.flow.Flow

class AccountViewModel(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserServicesUseCase: GetUserServicesUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AccountScreenUiState>(AccountScreenUiState()), AccountInteractionListener {

    private val _currentScreen = mutableIntStateOf(0)
    val currentScreen: State<Int> get() = _currentScreen

    private val stepsCount: Int
        get() = when (screenState.value.accountUiState.userType) {
            UserType.CRAFTSMAN -> 5
            UserType.CUSTOMER -> 4
            else -> 4
        }

    val progress: Float
        get() = (_currentScreen.intValue + 1) / stepsCount.toFloat()

    val accountSetupStep = savedStateHandle.toRoute<Destinations.Account>().accountSetupStep

    init {
        getPhoneNumber()
        getGovernments()
        getAllServices()
    }


    private fun getPhoneNumber() {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = ::onGetPhoneNumberSuccess,
            onError = ::onError,
        )
    }

    private fun onGetPhoneNumberSuccess(phoneNumber: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    phoneNumber = phoneNumber
                )
            )
        )
        loadUserAndGoToLastStep()
        getUserSelectedServices()
        getWorkMedia()
    }

    private fun getWorkMedia() {
        tryToExecute(
            execute = { setUpAccountUseCase.getWorkMedia(screenState.value.accountUiState.phoneNumber) },
            onSuccess = ::onGetWorkMediaSuccess,
            onError = ::onError
        )
    }

    private fun onGetWorkMediaSuccess(workMedia: List<String>) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    workImagesUris = workMedia.map { it.toUri() }
                )
            )
        )
    }

    private fun getUserSelectedServices() {
        tryToObserve(
            observe = {
                getUserServicesUseCase(
                    phoneNumber = screenState.value.accountUiState.phoneNumber,
                    isCraftsman = screenState.value.accountUiState.userType == UserType.CRAFTSMAN
                )
            },
            onEach = ::onGetUserSelectedServicesEach,
            onError = ::onError
        )
    }

    private fun onGetUserSelectedServicesEach(services: List<Service>?) {
        val serviceUiStates = mapServiceToUiState(services ?: emptyList())
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    serviceUiState = screenState.value.accountUiState.serviceUiState.map { service ->
                        service.copy(isSelected = serviceUiStates.any { service.id == it.id })
                    },
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        serviceButtonState = if (serviceUiStates.isNotEmpty()) AppButtonState.Enable
                        else AppButtonState.Disabled
                    )
                )
            )
        )
    }

    private fun loadUserAndGoToLastStep() {
        tryToExecute(
            execute = { getUserUseCase(screenState.value.accountUiState.phoneNumber) },
            onSuccess = ::onLoadUserAndGoToLastStepSuccess,
            onError = ::onError
        )
    }

    private suspend fun onLoadUserAndGoToLastStepSuccess(user: User) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    userType = UserType.valueOf(user.accountType.name),
                    locationUiState = user.location.toUiState(
                        getLocationInfoUseCase.getGovernorateById(user.location.governmentId),
                        getLocationInfoUseCase.getCityById(user.location.cityId)
                    ),
                    customerName = user.fullName,
                    customerProfilePhotoUri = if (user.profilePhoto.isNotBlank()) user.profilePhoto.toUri() else null,
                    frontOfNationalIdUri = if (user.nationalIdFrontImage.isNotBlank()) user.nationalIdFrontImage.toUri() else null,
                    backOfNationalIdUri = if (user.nationalIdBackImage.isNotBlank()) user.nationalIdBackImage.toUri() else null,
                    workDescription = user.workDescription,
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        userTypeButtonState = if (user.accountType.name.isNotBlank()) AppButtonState.Enable else AppButtonState.Disabled,
                        profileButtonState = if (user.fullName.isNotBlank()) AppButtonState.Enable else AppButtonState.Disabled,
                        locationButtonState = if (user.location.governmentId == 0) AppButtonState.Enable else AppButtonState.Disabled,
                        verifyIdentityButtonState = if (user.nationalIdBackImage.isNotBlank() && user.nationalIdFrontImage.isNotEmpty()) AppButtonState.Enable else AppButtonState.Disabled,
                    )
                )
            )
        )
        getAccountSetupStep()
    }

    private fun getAccountSetupStep() {
        _currentScreen.intValue = when (accountSetupStep) {
            AccountSetupStep.ACCOUNT_TYPE -> 0
            AccountSetupStep.SERVICES -> 1
            AccountSetupStep.PERSONAL_INFO -> 2
            AccountSetupStep.LOCATION, AccountSetupStep.WORK_SHOWCASE -> 3
            AccountSetupStep.UPLOAD_NATIONAL_ID -> 4
            AccountSetupStep.COMPLETED -> 5
        }
    }

    private fun getAllServices() {
        updateState(screenState.value.copy(isLoading = true))
        tryToExecute(
            execute = { getAllServicesUseCase() },
            onSuccess = ::onGetAllServicesSuccess,
            onError = ::onError,
        )
    }

    private suspend fun onGetAllServicesSuccess(services: Flow<List<Service>>) {
        services.collect {
            val serviceUiStates = mapServiceToUiState(it)
            updateState(
                screenState.value.copy(
                    accountUiState = screenState.value.accountUiState.copy(
                        serviceUiState = serviceUiStates
                    ),
                    isLoading = false,
                    errorMassage = null
                )
            )
        }
    }

    override fun onToggleServiceClicked(serviceId: String) {
        val updatedServices = screenState.value.accountUiState.serviceUiState.map {
            if (it.id == serviceId) {
                changeAppButtonStateInService(AppButtonState.Enable)
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    serviceUiState = updatedServices,
                )
            )
        )

        if (screenState.value.accountUiState.serviceUiState.any { it.isSelected }) {
            changeAppButtonStateInService(AppButtonState.Enable)
        } else {
            changeAppButtonStateInService(AppButtonState.Disabled)
        }
    }

    override fun onUserTypeSelected(type: UserType) {
        Log.d("AccountSetup", "onUserTypeSelected: ${type.name}")
        val updatedUiState = screenState.value.copy(
            accountUiState = screenState.value.accountUiState.copy(
                userType = type,
                accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                    userTypeButtonState = AppButtonState.Enable
                )
            )
        )
        updateState(updatedUiState)
    }

    override fun onCustomerNameChanged(name: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    customerName = name,
                )
            )
        )
        if (screenState.value.accountUiState.customerName.isNotBlank()) {
            changeAppButtonStateInProfile(AppButtonState.Enable)
        } else {
            changeAppButtonStateInProfile(AppButtonState.Disabled)
        }
    }

    override fun onDescriptionChanged(description: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    workDescription = description,
                )
            )
        )
    }

    fun onCustomerProfilePhotoSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    customerProfilePhotoUri = uri
                )
            )
        )
    }

    fun onFrontNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    frontOfNationalIdUri = uri
                )
            )
        )
        if (screenState.value.accountUiState.backOfNationalIdUri != null)
            changeAppButtonStateInVerifyIdentity(AppButtonState.Enable)
    }

    fun onBackNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    backOfNationalIdUri = uri
                )
            )
        )
        if (screenState.value.accountUiState.frontOfNationalIdUri != null)
            changeAppButtonStateInVerifyIdentity(AppButtonState.Enable)
    }

    fun onWorkImageSelected(uris: List<Uri>) {
        val lastWorkImagesUris = screenState.value.accountUiState.workImagesUris

        val filteredUris = uris.filter { uri ->
            !lastWorkImagesUris.orEmpty().contains(uri)
        }

        val workImagesUris = lastWorkImagesUris?.plus(filteredUris) ?: filteredUris

        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    workImagesUris = workImagesUris,
                )
            )
        )

        if (!screenState.value.accountUiState.workImagesUris.isNullOrEmpty()) {
            changeAppButtonStateInWorkShowCase(AppButtonState.Enable)
        }
    }

    override fun onDeleteWorkImageClicked(uri: Uri) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    workImagesUris = screenState.value.accountUiState.workImagesUris?.filter { it != uri },
                )
            )
        )
        if (screenState.value.accountUiState.workImagesUris.isNullOrEmpty()) {
            changeAppButtonStateInWorkShowCase(AppButtonState.Disabled)
        }
    }

    override fun onPreviousClicked() {
        if (_currentScreen.intValue > 0) {
            _currentScreen.intValue--
        }
        when (_currentScreen.intValue) {
            1 -> {
                if (screenState.value.accountUiState.serviceUiState.isNotEmpty())
                    changeAppButtonStateInService(AppButtonState.Enable)
            }

            2 -> {
                if (screenState.value.accountUiState.customerName.isBlank()) {
                    changeAppButtonStateInProfile(AppButtonState.Disabled)
                } else {
                    changeAppButtonStateInProfile(AppButtonState.Enable)
                }
            }

            3 -> {
                if (!screenState.value.accountUiState.workImagesUris.isNullOrEmpty()) {
                    changeAppButtonStateInWorkShowCase(AppButtonState.Enable)
                }
            }

            else -> Unit
        }
    }

    fun getTitle(): UiText {
        return when (_currentScreen.intValue) {
            0 -> UiText.StringResource(R.string.how_would_you_like_to_use_san3a)
            1 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> UiText.StringResource(R.string.what_do_you_need_help_with)
                UserType.CRAFTSMAN -> UiText.StringResource(R.string.what_services_do_you_offer)
                else -> UiText.DynamicString("")
            }

            2 -> UiText.StringResource(R.string.personalize_your_profile)
            3 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> UiText.StringResource(R.string.where_are_you_located)
                UserType.CRAFTSMAN -> UiText.StringResource(R.string.show_us_your_work)
                else -> UiText.DynamicString("")
            }

            4 -> UiText.StringResource(R.string.verify_your_identity_optional)
            else -> UiText.DynamicString("")
        }
    }

    fun getDescription(): UiText {
        return when (_currentScreen.intValue) {
            0 -> UiText.StringResource(R.string.switch_roles_anytime)
            1 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> UiText.StringResource(R.string.personalize_experience_change_anytime)
                UserType.CRAFTSMAN -> UiText.StringResource(R.string.choose_specialties_change_later)
                else -> UiText.DynamicString("")
            }

            2 -> UiText.StringResource(R.string.use_to_personalize_experience_skip)
            3 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> UiText.StringResource(R.string.location_improves_accuracy_update_later)
                UserType.CRAFTSMAN -> UiText.StringResource(R.string.add_photos_or_video_build_trust)
                else -> UiText.DynamicString("")
            }

            4 -> UiText.StringResource(R.string.upload_id_build_trust_get_jobs)
            else -> UiText.DynamicString("")
        }
    }

    fun getButtonText(): UiText {
        return if (_currentScreen.intValue == stepsCount - 1) {
            when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> UiText.StringResource(R.string.browse_services)
                UserType.CRAFTSMAN -> UiText.StringResource(R.string.see_nearby_requests)
                else -> UiText.StringResource(R.string.next)
            }
        } else {
            UiText.StringResource(R.string.next)
        }
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
                accountUiState = screenState.value.accountUiState.copy(
                    governments = governments
                )
            )
        )
    }

    fun getCities(governorateId: Int) {
        tryToExecute(
            execute = { getLocationInfoUseCase.getCities(governorateId) },
            onSuccess = ::onGetCitiesSuccess,
            onError = ::onError
        )
    }

    private fun onGetCitiesSuccess(cities: List<City>) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    cities = cities,
                    locationType = LocationBottomSheetContentType.CITY
                )
            )
        )
    }

    fun updateGovernmentLocation(governorate: Governorate) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    locationUiState = screenState.value.accountUiState.locationUiState.copy(
                        governorate = governorate
                    )
                )
            )
        )
    }

    override fun onGovernmentSelected(governorate: Governorate) {
        getCities(governorate.id)
        onCitiesBottomSheetVisibilityToggled()
        updateGovernmentLocation(governorate)
    }

    override fun onCitiesSelected(city: City) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isGovernmentBottomSheetShowed = false,
                    locationUiState = screenState.value.accountUiState.locationUiState.copy(
                        city = city,
                    ),
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        locationButtonState = AppButtonState.Enable
                    )
                )
            )
        )
    }

    override fun onGovernmentBottomSheetVisibilityToggled() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isGovernmentBottomSheetShowed = !screenState.value.accountUiState.isGovernmentBottomSheetShowed,
                    locationType = LocationBottomSheetContentType.GOVERNMENT
                )
            )
        )
    }

    private fun onCitiesBottomSheetVisibilityToggled() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isCitiesBottomSheetShowed = !screenState.value.accountUiState.isCitiesBottomSheetShowed
                )
            )
        )
    }

    override fun onGovernmentBottomSheetDismissed() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isGovernmentBottomSheetShowed = false
                )
            )
        )
    }

    override fun onCitiesBottomSheetDismissed() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isCitiesBottomSheetShowed = false
                )
            )
        )
    }

    override fun onAddressDetailsChanged(addressDetails: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    locationUiState = screenState.value.accountUiState.locationUiState.copy(
                        addressInDetails = addressDetails
                    )
                )
            )
        )
    }

    override fun onUserTypeButtonClicked() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                userTypeButtonState = AppButtonState.Loading
                            )
                        )
                    )
                )
                val userType = screenState.value.accountUiState.userType
                if (userType != null)
                    setUpAccountUseCase.saveAccountType(
                        phone = screenState.value.accountUiState.phoneNumber,
                        AccountType.valueOf(userType.name)
                    )
                updateState(
                    screenState.value.copy(
                        accountUiState = screenState.value.accountUiState.copy(
                            serviceUiState = screenState.value.accountUiState.serviceUiState.map { service ->
                                service.copy(isSelected = false)
                            },
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                userTypeButtonState = AppButtonState.Enable
                            )
                        )
                    )
                )
                getUserSelectedServices()
            },
            onSuccess = {
                setUpAccountUseCase.updateUserProgress(
                    phone = screenState.value.accountUiState.phoneNumber,
                    step = AccountSetupStep.SERVICES
                )
                incrementCurrentScreen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMassage = it.message.orEmpty(),
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                userTypeButtonState = AppButtonState.Disabled
                            )
                        )
                    )
                )
            }
        )
    }

    override fun onServiceButtonClicked() {
        tryToExecute(
            execute = {
                changeAppButtonStateInService(AppButtonState.Loading)
                val selectedServices =
                    screenState.value.accountUiState.serviceUiState.filter { it.isSelected }
                val isCraftsman = screenState.value.accountUiState.userType == UserType.CRAFTSMAN
                val services = selectedServices.map { it.id }
                setUpAccountUseCase.saveServices(
                    phone = screenState.value.accountUiState.phoneNumber,
                    services,
                    isCraftsman = isCraftsman
                )
            },
            onSuccess = {
                setUpAccountUseCase.updateUserProgress(
                    phone = screenState.value.accountUiState.phoneNumber,
                    step = AccountSetupStep.PERSONAL_INFO
                )
                incrementCurrentScreen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMassage = it.message.orEmpty(),
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                serviceButtonState = AppButtonState.Disabled
                            )
                        )
                    )
                )
            },
        )
    }

    override fun onProfileButtonClicked() {
        tryToExecute(
            execute = {
                val fullName = screenState.value.accountUiState.customerName
                val profilePhotoUri =
                    screenState.value.accountUiState.customerProfilePhotoUri
                if (fullName.isNotBlank()) {
                    changeAppButtonStateInProfile(AppButtonState.Enable)
                }
                changeAppButtonStateInProfile(AppButtonState.Loading)
                setUpAccountUseCase.savePersonalInfo(
                    phone = screenState.value.accountUiState.phoneNumber,
                    fullName,
                    profilePhotoUri
                )

            },
            onSuccess = {
                if (screenState.value.accountUiState.userType == UserType.CRAFTSMAN) {
                    if (!screenState.value.accountUiState.workImagesUris.isNullOrEmpty()) {
                        changeAppButtonStateInWorkShowCase(AppButtonState.Enable)
                    }
                    setUpAccountUseCase.updateUserProgress(
                        phone = screenState.value.accountUiState.phoneNumber,
                        step = AccountSetupStep.WORK_SHOWCASE
                    )
                } else {
                    setUpAccountUseCase.updateUserProgress(
                        phone = screenState.value.accountUiState.phoneNumber,
                        step = AccountSetupStep.LOCATION
                    )
                }
                incrementCurrentScreen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMassage = it.message.orEmpty(),
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                profileButtonState = AppButtonState.Enable
                            )
                        )
                    )
                )
            }
        )
    }

    override fun onLocationButtonClicked() {
        tryToExecute(
            execute = {
                updateState(
                    screenState.value.copy(
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                locationButtonState = AppButtonState.Loading
                            )
                        )
                    )
                )
                setUpAccountUseCase.saveLocation(
                    phone = screenState.value.accountUiState.phoneNumber,
                    location = screenState.value.accountUiState.locationUiState.toEntity()
                )
            },
            onSuccess = {
                setUpAccountUseCase.updateUserProgress(
                    phone = screenState.value.accountUiState.phoneNumber,
                    step = AccountSetupStep.COMPLETED
                )
                navigate(
                    Destinations.CustomerGraph,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(
                            Destinations.Account(accountSetupStep),
                            inclusive = true
                        ).build()
                )
                incrementCurrentScreen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMassage = it.message.orEmpty(),
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                locationButtonState = AppButtonState.Disabled
                            )
                        )
                    )
                )
            },
        )
    }

    override fun onShowWorkButtonClicked() {
        tryToExecute(
            execute = {
                changeAppButtonStateInWorkShowCase(AppButtonState.Loading)
                setUpAccountUseCase.saveWorkShowcase(
                    phone = screenState.value.accountUiState.phoneNumber,
                    workMedia = screenState.value.accountUiState.workImagesUris,
                    workDescription = screenState.value.accountUiState.workDescription
                )
            },
            onSuccess = {
                setUpAccountUseCase.updateUserProgress(
                    phone = screenState.value.accountUiState.phoneNumber,
                    step = AccountSetupStep.UPLOAD_NATIONAL_ID
                )
                incrementCurrentScreen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMassage = it.message.orEmpty(),
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                workShowCaseButtonState = AppButtonState.Disabled
                            )
                        )
                    )
                )
            },
        )
    }

    override fun onVerifyIdentityButtonClicked() {
        tryToExecute(
            execute = {
                changeAppButtonStateInVerifyIdentity(AppButtonState.Loading)
                setUpAccountUseCase.uploadNationalIdImages(
                    phone = screenState.value.accountUiState.phoneNumber,
                    screenState.value.accountUiState.frontOfNationalIdUri,
                    screenState.value.accountUiState.backOfNationalIdUri
                )
                navigate(
                    Destinations.CraftManGraph,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(
                            Destinations.Account(accountSetupStep),
                            inclusive = true
                        ).build()
                )
            },
            onSuccess = {
                setUpAccountUseCase.updateUserProgress(
                    phone = screenState.value.accountUiState.phoneNumber,
                    step = AccountSetupStep.COMPLETED
                )
                navigate(
                    Destinations.CraftManGraph,
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(
                            Destinations.Account(accountSetupStep),
                            inclusive = true
                        ).build()
                )
                incrementCurrentScreen()
            },
            onError = {
                updateState(
                    screenState.value.copy(
                        errorMassage = it.message.orEmpty(),
                        accountUiState = screenState.value.accountUiState.copy(
                            accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                                verifyIdentityButtonState = AppButtonState.Disabled
                            )
                        )
                    )
                )
            },
        )
    }

    private fun incrementCurrentScreen() {
        if (_currentScreen.intValue < stepsCount - 1) {
            _currentScreen.intValue++
        }
    }

    private fun onError(throwable: Throwable) {
        updateState(
            screenState.value.copy(
                errorMassage = throwable.message,
                isLoading = false
            )
        )
    }

    private fun changeAppButtonStateInService(appButtonState: AppButtonState) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        serviceButtonState = appButtonState
                    )
                )
            )
        )
    }

    private fun changeAppButtonStateInProfile(appButtonState: AppButtonState) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        profileButtonState = appButtonState
                    )
                )
            )
        )
    }

    private fun changeAppButtonStateInVerifyIdentity(appButtonState: AppButtonState) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        verifyIdentityButtonState = appButtonState
                    )
                )
            )
        )
    }

    private fun changeAppButtonStateInWorkShowCase(appButtonState: AppButtonState) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    accountButtonState = screenState.value.accountUiState.accountButtonState.copy(
                        workShowCaseButtonState = appButtonState
                    )
                )
            )
        )
    }
}