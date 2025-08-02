package com.paris_2.san3a.presentation.screen.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.entity.AccountType
import com.paris_2.san3a.domain.entity.Service
import com.paris_2.san3a.domain.usecase.GetAllServicesUseCase
import com.paris_2.san3a.domain.usecase.SetUpAccountUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class AccountViewModel(
    private val getAllServicesUseCase: GetAllServicesUseCase,
    private val setUpAccountUseCase: SetUpAccountUseCase
) :
    BaseViewModel<AccountScreenUiState>(AccountScreenUiState()) {

    private val stepsCount = 4
    private val _currentScreen = mutableIntStateOf(0)
    val currentScreen: State<Int> get() = _currentScreen

    private val phoneNumber = "123456789"

    val progress: Float
        get() = (_currentScreen.intValue + 1) / stepsCount.toFloat()

    init {
        getAllServices()
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
                        errorMassage = errorMessage,
                        isLoading = false
                    )
                )
            },
            scope = viewModelScope
        )
    }

    private fun mapServiceToUiState(services: List<Service>): List<ServiceUiState> {
        val currentLocale = "englishName"
        return services.map {
            ServiceUiState(
                id = it.id,
                serviceTitle = it.title[currentLocale] ?: it.title.values.firstOrNull() ?: "",
                serviceDescription = it.description[currentLocale] ?: it.description.values.firstOrNull() ?: "",
                isSelected = false
            )
        }
    }

    fun toggleServiceSelection(serviceId: String) {
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

    fun updateUserType(type: UserType) {
        val updatedUiState = screenState.value.copy(
            accountUiState = screenState.value.accountUiState.copy(
                userType = type
            )
        )
        updateState(updatedUiState)
    }


    fun nextStep() {
        val userType = screenState.value.accountUiState.userType
        if (userType != null) {
            tryToExecute(
                execute = {
                    when (_currentScreen.intValue) {
                        0 -> {
                            setUpAccountUseCase.saveAccountType(
                                phoneNumber,
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
                                phoneNumber,
                                services,
                                isCraftsman = isCraftsman
                            )
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
                }
            )
        }
    }

    fun previousStep() {
        if (_currentScreen.intValue > 0) {
            _currentScreen.intValue--
        }
    }

    fun getTitle(): String {
        return when (_currentScreen.intValue) {
            0 -> "How would you like to use San3a?"
            1 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "What do you usually need help with?"
                UserType.CRAFTSMAN -> "What services do you offer?"
                else -> ""
            }

            2 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "Where are you located?"
                UserType.CRAFTSMAN -> "Show Us Your Work"
                else -> ""
            }

            3 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "Let’s personalize your profile"
                UserType.CRAFTSMAN -> "Verify Your Identity (Optional)"
                else -> ""
            }

            else -> ""
        }
    }

    fun getDescription(): String {
        return when (_currentScreen.intValue) {
            0 -> "You can switch roles anytime from your profile."
            1 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "This helps us personalize your experience. You can change it anytime."
                UserType.CRAFTSMAN -> "Choose your specialties to get relevant job requests. You can change this later."
                else -> ""
            }

            2 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "Location helps improve accuracy, but don’t worry, you can update it later."
                UserType.CRAFTSMAN -> "Add photos or a video of your past work. This helps build trust with customers."
                else -> ""
            }

            3 -> when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "We’ll use this to personalize your experience. You can add a profile photo too, or skip for now."
                UserType.CRAFTSMAN -> "Uploading your ID helps build trust with customers. Verified craftsmen get more jobs and a special badge on their profile."
                else -> ""
            }

            else -> ""
        }
    }

    fun getButtonText(): String {
        return if (_currentScreen.intValue == stepsCount - 1) {
            when (screenState.value.accountUiState.userType) {
                UserType.CUSTOMER -> "Browse Services"
                UserType.CRAFTSMAN -> "See Nearby Requests"
                else -> "Next"
            }
        } else {
            "Next"
        }
    }
}

