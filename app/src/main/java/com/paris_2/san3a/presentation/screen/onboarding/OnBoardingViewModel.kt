package com.paris_2.san3a.presentation.screen.onboarding

import androidx.navigation.NavOptions
import com.paris_2.san3a.domain.usecase.SetOnboardingCompletedUseCase
import com.paris_2.san3a.presentation.navigation.Destinations
import com.paris_2.san3a.presentation.shared.utils.BaseViewModel

class OnBoardingViewModel(
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : OnBoardingInteractionListener, BaseViewModel<OnBoardingUIState>(
    OnBoardingUIState(
        isCompleted = false
    )
) {
    fun setOnboardingCompleted() {
        tryToExecute(
            execute = {
                setOnboardingCompletedUseCase()
            },
            onSuccess = {
                updateState(screenState.value.copy(isCompleted = true))
                navigateToHome()
            },

            onError = { exception ->
                updateState(screenState.value.copy(error = exception.message))
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

    private fun navigateToHome() {
        navigate(
            destination = Destinations.Account,
            navOptions = NavOptions.Builder()
                .setPopUpTo(Destinations.OnBoarding, inclusive = true)
                .build()
        )
    }
}