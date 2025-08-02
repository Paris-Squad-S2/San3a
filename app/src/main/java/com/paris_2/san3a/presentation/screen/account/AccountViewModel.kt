package com.paris_2.san3a.presentation.screen.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.paris_2.san3a.domain.usecase.GetCurrentLocatedUseCase
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getCurrentLocatedUseCase: GetCurrentLocatedUseCase,
) : BaseViewModel<AccountScreenUiState>(AccountScreenUiState()) {

    private val stepsCount = 4
    private val _currentScreen = mutableIntStateOf(0)
    val currentScreen: State<Int> get() = _currentScreen

    val progress: Float
        get() = (_currentScreen.intValue + 1) / stepsCount.toFloat()

    init {
        getGovernments()
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
        if (_currentScreen.intValue < stepsCount - 1) {
            _currentScreen.intValue++
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

    fun getGovernments() {
        viewModelScope.launch {
            val governments = getCurrentLocatedUseCase.getGovernments(countryName = "Egypt")
            Log.d("TAG", "getGovernments: ")
            updateState(
                screenState.value.copy(
                    accountUiState = screenState.value.accountUiState.copy(
                        governments = governments.names
                    )
                )
            )
        }
    }

    fun getCities(stateName: String) {
        viewModelScope.launch {
            val cities = getCurrentLocatedUseCase.getCities(
                countryName = "Egypt",
                stateName = stateName
            )
            updateState(
                screenState.value.copy(
                    accountUiState = screenState.value.accountUiState.copy(
                        cities = cities.names
                    )
                )
            )
        }
    }

    fun updateBottomSheetVisibility() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isBottomSheetShowed = !screenState.value.accountUiState.isBottomSheetShowed
                )
            )
        )
    }

    fun onButtonSheetDismiss() {
        updateState(
            screenState.value.copy(
                accountUiState = screenState.value.accountUiState.copy(
                    isBottomSheetShowed = false
                )
            )
        )
    }
}

