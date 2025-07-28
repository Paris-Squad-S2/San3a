package com.paris_2.san3a.presentation.screen.onboarding

import com.paris_2.san3a.presentation.screen.base.BaseViewModel

class OnBoardingViewModel(
    private val appPreferences: AppPreferences
) : OnBoardingInteractionListener, BaseViewModel<OnBoardingUIState>(
    OnBoardingUIState(
        isCompleted = appPreferences.isOnboardingCompleted()
    )
) {

    fun setOnboardingCompleted() {
        tryToExecute(
            execute = {
                appPreferences.setOnboardingCompleted()
            },
            onSuccess = {
                updateState(screenState.value.copy(isCompleted = true))
            },
            onError = {message ->
                updateState(screenState.value.copy(error = message))
            },
        )
    }

    fun updateCurrentPage(index: Int) {
        updateState(screenState.value.copy(currentPage = index))
    }

    override fun onNextClicked() {
        if (screenState.value.currentPage == 2) {
            setOnboardingCompleted()
        } else {
            updateCurrentPage(screenState.value.currentPage + 1)
        }
    }

    override fun onSkipClicked() {
        setOnboardingCompleted()
    }

    override fun onPageChanged(index: Int) {
        updateCurrentPage(index)
    }

}