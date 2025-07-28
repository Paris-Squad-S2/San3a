package com.paris_2.san3a.presentation.screen.onboarding

import com.paris_2.san3a.presentation.screen.base.BaseViewModel

class OnBoardingViewModel(
    private val appPreferences: AppPreferences
) :  BaseViewModel<OnBoardingUIState>(
    OnBoardingUIState(
        isCompleted = appPreferences.isOnboardingCompleted()
    )
) {
    fun setOnboardingCompleted() {
        appPreferences.setOnboardingCompleted()
        _uiState.value = _uiState.value.copy(isCompleted = true)
    }

    fun updateCurrentPage(index: Int) {
        _uiState.value = _uiState.value.copy(currentPage = index)
    }
}