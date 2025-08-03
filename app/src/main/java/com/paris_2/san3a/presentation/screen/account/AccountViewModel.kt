package com.paris_2.san3a.presentation.screen.account

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import com.paris_2.san3a.R
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.GetLocationInfoUseCase
import com.paris_2.san3a.domain.usecase.GetPhoneNumberUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import com.paris_2.san3a.presentation.shared.utils.UiText

class AccountViewModel(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase,
    private val getPhoneNumberUseCase: GetPhoneNumberUseCase
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

    init {
        getPhoneNumber()
        getGovernments()
        getAllServices()
    }

    private fun getPhoneNumber() {
        tryToExecute(
            execute = { getPhoneNumberUseCase() },
            onSuccess = { phoneNumber ->
                updateState(
                    screenState.value.copy(
                        accountUiState = screenState.value.accountUiState.copy(
                            phoneNumber = phoneNumber
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMassage = errorMessage.message,
                    )
                )
            },
        )
    }

    private fun getAllServices() {
        updateState(screenState.value.copy(isLoading = true))

        tryToExecute(
            execute = { getAllServicesUseCase() },
            onSuccess = { services ->
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

            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMassage = errorMessage.message,
                        isLoading = false
                    )
                )
            },
        )
    }

    private fun mapServiceToUiState(services: List<Service>): List<ServiceUiState> {
        val currentLocale = "englishName"
        return services.map {
            ServiceUiState(
                id = it.id,
                serviceTitle = it.title[currentLocale] ?: it.title.values.firstOrNull() ?: "",
                serviceDescription = it.description[currentLocale]
                    ?: it.description.values.firstOrNull() ?: "",
                isSelected = false
            )
        }
    }

    override fun onToggleServiceClicked(serviceId: String) {
        val updatedServices = screenState.value.accountUiState.serviceUiState.map {
            if (it.id == serviceId) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    serviceUiState = updatedServices
                )
            )
        )
    }

    override fun onUserTypeSelected(type: UserType) {
        val updatedUiState = screenState.value.copy(
            accountUiState = screenState.value.accountUiState.copy(
                userType = type
            )
        )
        updateState(updatedUiState)
    }

    override fun onCustomerNameChanged(name: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    customerName = name
                )
            )
        )
    }

    override fun onDescriptionChanged(description: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    workDescription = description
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
    }

    fun onBackNationalIdSelected(uri: Uri) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    backOfNationalIdUri = uri
                )
            )
        )
    }

    fun onWorkImageSelected(uris: List<Uri>) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    workImagesUris = uris
                )
            )
        )

    }

    override fun onNextClicked() {
        val userType = screenState.value.accountUiState.userType
        if (userType != null) {
            tryToExecute(
                execute = {
                    when (_currentScreen.intValue) {
                        0 -> {
                            setUpAccountUseCase.saveAccountType(
                                phone = screenState.value.accountUiState.phoneNumber,
                                AccountType.valueOf(userType.name)
                            )
                        }

                        1 -> {
                            val currentLocale = "englishName"
                            val selectedServices =
                                screenState.value.accountUiState.serviceUiState.filter { it.isSelected }
                            val isCraftsman =
                                screenState.value.accountUiState.userType == UserType.CRAFTSMAN
                            val services = selectedServices.map { serviceUiState ->
                                Service(
                                    id = serviceUiState.id,
                                    title = mapOf(currentLocale to serviceUiState.serviceTitle),
                                    description = mapOf(currentLocale to serviceUiState.serviceDescription)
                                )
                            }
                            setUpAccountUseCase.saveServices(
                                phone = screenState.value.accountUiState.phoneNumber,
                                services,
                                isCraftsman = isCraftsman
                            )
                        }

                        2 -> {
                            val fullName = screenState.value.accountUiState.customerName
                            val profilePhotoUri =
                                screenState.value.accountUiState.customerProfilePhotoUri
                            setUpAccountUseCase.savePersonalInfo(
                                phone = screenState.value.accountUiState.phoneNumber,
                                fullName,
                                profilePhotoUri
                            )
                        }

                        3 -> {
                            if (screenState.value.accountUiState.userType == UserType.CRAFTSMAN) {
                                setUpAccountUseCase.saveWorkShowcase(
                                    phone = screenState.value.accountUiState.phoneNumber,
                                    workMedia = screenState.value.accountUiState.workImagesUris,
                                    workDescription = screenState.value.accountUiState.workDescription
                                )
                            } else {
                                setUpAccountUseCase.saveLocation(
                                    phone = screenState.value.accountUiState.phoneNumber,
                                    location = screenState.value.accountUiState.locationUiState.toEntity()
                                )
                                navigate(
                                    Destinations.Home
                                )
                            }
                        }

                        4 -> {
                            if (screenState.value.accountUiState.userType == UserType.CRAFTSMAN) {
                                setUpAccountUseCase.uploadNationalIdImages(
                                    phone = screenState.value.accountUiState.phoneNumber,
                                    screenState.value.accountUiState.frontOfNationalIdUri,
                                    screenState.value.accountUiState.backOfNationalIdUri
                                )
                                navigate(Destinations.Home)
                            }
                        }
                    }
                },
                onSuccess = {
                    Log.d("AccountSetup", "Account type saved successfully")
                    if (_currentScreen.intValue < stepsCount - 1) {
                        _currentScreen.intValue++
                    }
                },
                onError = { errorMessage ->
                    Log.e("AccountSetup", "Error saving account type: $errorMessage")
                    Log.e("PhoneNumber", "Phone number: ${screenState.value.accountUiState.phoneNumber}")
                }
            )
        }
    }

    override fun onPreviousClicked() {
        if (_currentScreen.intValue > 0) {
            _currentScreen.intValue--
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
            execute = { getLocationInfoUseCase.getGovernments(countryName = "Egypt") },
            onSuccess = { governments ->
                updateState(
                    screenState.value.copy(
                        accountUiState = screenState.value.accountUiState.copy(
                            governments = governments.names
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMassage = errorMessage.message,
                        isLoading = false
                    )
                )
            },
        )
    }

    fun getCities(stateName: String) {
        tryToExecute(
            execute = {
                getLocationInfoUseCase.getCities(
                    countryName = "Egypt",
                    stateName = stateName
                )
            },
            onSuccess = { cities ->
                updateState(
                    screenState.value.copy(
                        accountUiState = screenState.value.accountUiState.copy(
                            cities = cities.names,
                            isCitiesBottomSheetShowed = true,
                            isGovernmentBottomSheetShowed = false
                        )
                    )
                )
            },
            onError = { errorMessage ->
                updateState(
                    screenState.value.copy(
                        errorMassage = errorMessage.message,
                        isLoading = false
                    )
                )
            }
        )
    }

    fun updateGovernmentLocation(government: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    locationUiState = screenState.value.accountUiState.locationUiState.copy(
                        government = government
                    )
                )
            )
        )
    }

    override fun onGovernmentSelected(government: String) {
        getCities(government)
        onCitiesBottomSheetVisibilityToggled()
        updateGovernmentLocation(government)
    }

    override fun onCitiesSelected(city: String) {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isCitiesBottomSheetShowed = false,
                    isGovernmentBottomSheetShowed = false,
                    locationUiState = screenState.value.accountUiState.locationUiState.copy(
                        city = city,
                    )
                )
            )
        )
    }

    override fun onGovernmentBottomSheetVisibilityToggled() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isGovernmentBottomSheetShowed = !screenState.value.accountUiState.isGovernmentBottomSheetShowed
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
}
