package com.paris_2.san3a.presentation.screen.account

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class AccountViewModel : ViewModel() {

    private val _currentScreen = mutableIntStateOf(0)
    val currentScreen: State<Int> get() = _currentScreen

    private val stepsCount = 4

    val progress: State<Float> get() = mutableFloatStateOf((_currentScreen.intValue + 1) / stepsCount.toFloat())


    private var _userType by mutableStateOf(AccountScreenUiState())
    val userType: State<AccountScreenUiState> get() = mutableStateOf(_userType)

    fun updateUserType(type: UserType) {
        _userType = _userType.copy(
            accountUiState = _userType.accountUiState.copy(
                userType = type
            )
        )
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
    fun getTitle(): String = when (_currentScreen.intValue) {
        0 -> "How would you like to use San3a?"
        1 -> "What do you usually need help with?"
        2 -> "Where are you located?"
        3 -> "Let’s personalize your profile"
        else -> ""
    }

    fun getDescription(): String = when (_currentScreen.intValue) {
        0 -> "You can switch roles anytime from your profile."
        1 -> "This helps us personalize your experience. You can change it anytime."
        2 -> "Location helps improve accuracy, but don’t worry, you can update it later."
        3 -> "We’ll use this to personalize your experience. You can add a profile photo too, or skip for now."
        else -> ""
    }

    fun getButtonText(): String = if (_currentScreen.intValue == stepsCount - 1) "Browse Services" else "Next"


}

